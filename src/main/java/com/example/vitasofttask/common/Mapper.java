package com.example.vitasofttask.common;

import com.example.vitasofttask.application.application.domain.Application;
import com.example.vitasofttask.application.application.domain.ApplicationRepository;
import com.example.vitasofttask.application.application.model.ApplicationDto;
import com.example.vitasofttask.application.application.model.ApplicationStatus;
import com.example.vitasofttask.application.role.domain.Role;
import com.example.vitasofttask.application.role.model.RoleDto;
import com.example.vitasofttask.application.user.domain.Customer;
import com.example.vitasofttask.application.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.stream.Collectors;



@Component
@Slf4j
@RequiredArgsConstructor
public class Mapper {
    private final ApplicationRepository applicationRepository;

    public ApplicationDto convertToApplicationDto(Application application){
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setId(application.getId());
        applicationDto.setName(application.getName());
        applicationDto.setComment(application.getComment());
        applicationDto.setUser(convertToUserDto(application.getUser()));
        applicationDto.setStatus(application.getStatus());
        applicationDto.setDateCreate(application.getDateCreate());
        return applicationDto;
    }
    public Application convertToApplication(ApplicationDto applicationDto, Customer customer) {
        Application application = new Application();
        if (!ObjectUtils.isEmpty(applicationDto.getId())) {
            application.setId(application.getId());
            application = applicationRepository.getById(applicationDto.getId());
        }
        application.setName("Заявка пользователя "+ customer.getName());
        application.setComment(convertToComment(applicationDto.getComment().toCharArray()));
        application.setUser(customer);
        application.setStatus(ApplicationStatus.DRAFT);
        log.info("test2");
        application.setDateCreate(new Date());
        log.info("test3");
        return application;

    }
    public UserDto convertToUserDto(Customer customer){
        UserDto userDto =  new UserDto();
        userDto.setId(customer.getId());
        userDto.setName(customer.getName());
        userDto.setEmail(customer.getEmail());
        userDto.setBirthdayDate(customer.getBirthdayDate());
        userDto.setLastName(customer.getLastName());
        userDto.setRoles(customer.getRoles().stream().map(this::convertToRoleDto).collect(Collectors.toList()));
        return userDto;
    }

    public Customer convertToCustomer(UserDto userDto){
        Customer customer =  new Customer();
        customer.setName(userDto.getName());
        customer.setEmail(userDto.getEmail());
        customer.setBirthdayDate(userDto.getBirthdayDate());
        customer.setLastName(userDto.getLastName());
        return customer;
    }

    public RoleDto convertToRoleDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setType(role.getName());
        return roleDto;
    }

    public String convertToComment(char[] array){
        String comment ="";
        for(int i = 0; i < array.length; i++) {
            comment+=array[i] + "-";
        }
        return comment;
    }


}
