package com.example.vitasofttask.application.application.service.impl;


import com.example.vitasofttask.application.application.domain.Application;
import com.example.vitasofttask.application.application.domain.ApplicationRepository;
import com.example.vitasofttask.application.application.model.ApplicationDto;
import com.example.vitasofttask.application.application.model.ApplicationStatus;
import com.example.vitasofttask.application.application.service.ApplicationService;
import com.example.vitasofttask.application.role.domain.Role;
import com.example.vitasofttask.application.role.domain.RoleRepository;
import com.example.vitasofttask.application.role.model.RoleType;
import com.example.vitasofttask.application.user.domain.Customer;
import com.example.vitasofttask.application.user.domain.CustomerRepository;
import com.example.vitasofttask.common.Filter;
import com.example.vitasofttask.common.Mapper;
import com.example.vitasofttask.common.SortType;
import com.example.vitasofttask.domain.Page;
import com.example.vitasofttask.errors.ErrorDescriptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final Mapper mapper;
    private final CustomerRepository customerRepository;
    private final ApplicationRepository applicationRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void createApplication(ApplicationDto applicationDto) {
        Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.USER_NOT_FOUND.throwIsTrue(ObjectUtils.isEmpty(user));
        Role role = roleRepository.getRoleByName(RoleType.USER);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(role.getCustomers().contains(user));
        Application application = mapper.convertToApplication(applicationDto, user);
        applicationRepository.save(application);
    }

    @Override
    @Transactional
    public String updateApplication(ApplicationDto applicationDto) {
        Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.USER_NOT_FOUND.throwIsTrue(ObjectUtils.isEmpty(user));
        Role role = roleRepository.getRoleByName(RoleType.USER);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(role.getCustomers().contains(user));

        ErrorDescriptor.APPLICATION_NOT_FOUND.throwIsFalse(applicationRepository.existsById(applicationDto.getId()));
        Application application = applicationRepository.getById(applicationDto.getId());
        ErrorDescriptor.NOT_YOUR_APP.throwIsFalse(application.getUser().getId()==user.getId());
        log.info("app {}", application.getStatus());

        Boolean checkStatus = application.getStatus().equals(ApplicationStatus.DRAFT);
        if (checkStatus) {
            mapper.convertToApplication(applicationDto, user);
            applicationRepository.save(application);
            return "Заявка была изменена";
        } else return "Cтатус заявки не Черновик";
    }

    @Override
    @Transactional
    public String sendToModerator(Long applicationId) {
        Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.APPLICATION_NOT_FOUND.throwIsFalse(applicationRepository.existsById(applicationId));
        Application application = applicationRepository.getById(applicationId);
        ErrorDescriptor.NOT_YOUR_APP.throwIsFalse(application.getUser().getId() == user.getId());

        application.setStatus(ApplicationStatus.SEND);
        applicationRepository.save(application);
        return "Заявка отправлена оператору";
    }

    @Override
    @Transactional
    public ApplicationDto getApplication(Long id) {
        ErrorDescriptor.APPLICATION_NOT_FOUND.throwIsFalse(applicationRepository.existsById(id));
        return mapper.convertToApplicationDto(applicationRepository.getById(id));
    }

    @Override
    @Transactional
    public Page<ApplicationDto> getAllApplicationsWithStatusSend(Filter filterParams) {
        Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.USER_NOT_FOUND.throwIsTrue(ObjectUtils.isEmpty(user));
        Role role = roleRepository.getRoleByName(RoleType.MODERATOR);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(role.getCustomers().contains(user));
        Sort.Order order = !ObjectUtils.isEmpty(filterParams.getSort()) ?
                filterParams.getSort().getSortType().equals(SortType.LESS_TO_MORE)
                        ? Sort.Order.asc(filterParams.getSort().getName())
                        : Sort.Order.desc(filterParams.getSort().getName())
                : Sort.Order.asc("id");
        PageRequest pageable = PageRequest.of(filterParams.getPage() - 1, filterParams.getLimit(),
                Sort.by(order));
        val entities = applicationRepository.findAllByStatus(ApplicationStatus.SEND, pageable)
                .map(mapper::convertToApplicationDto);
        return Page.of(entities);
    }

    @Override
    @Transactional
    public Page<ApplicationDto> getAllUserApplications(Long userId, Filter filterParams) {
        Customer userAndModerator = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.USER_NOT_FOUND.throwIsFalse(customerRepository.existsById(userId));
        Customer user = customerRepository.getUserById(userId);

        Role roleUser = roleRepository.getRoleByName(RoleType.USER);
        Role roleModerator = roleRepository.getRoleByName(RoleType.MODERATOR);

        Sort.Order order = !ObjectUtils.isEmpty(filterParams.getSort()) ?
                filterParams.getSort().getSortType().equals(SortType.LESS_TO_MORE)
                        ? Sort.Order.asc(filterParams.getSort().getName())
                        : Sort.Order.desc(filterParams.getSort().getName())
                : Sort.Order.asc("id");
        PageRequest pageable = PageRequest.of(filterParams.getPage() - 1, filterParams.getLimit(),
                Sort.by(order));

        if (roleUser.getCustomers().contains(userAndModerator) && userAndModerator.getId() == userId) {
            val entities = applicationRepository.findAllByUser(user, pageable)
                    .map(mapper::convertToApplicationDto);
            return Page.of(entities);
        } else if (roleModerator.getCustomers().contains(userAndModerator)) {
            val entities = applicationRepository.findAllByUserAndStatus(user, ApplicationStatus.SEND, pageable)
                    .map(mapper::convertToApplicationDto);
            return Page.of(entities);
        }
        return null;
    }

    @Override
    @Transactional
    public String updateStatus(ApplicationDto applicationDto) {
        Customer user = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.APPLICATION_NOT_FOUND.throwIsFalse(applicationRepository.existsById(applicationDto.getId()));
        Application application = applicationRepository.getById(applicationDto.getId());

        Role role = roleRepository.getRoleByName(RoleType.MODERATOR);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(role.getCustomers().contains(user));
        Boolean checkStatus = application.getStatus().equals(ApplicationStatus.SEND);
        if (checkStatus) {
            application.setStatus(applicationDto.getStatus());
            return "Статус заявки с ИД " + application.getId() + " изменен на " + applicationDto.getStatus();
        } else return "Статус заявки не Отправлено";

    }


}
