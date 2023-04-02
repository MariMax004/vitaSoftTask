package com.example.vitasofttask.application.user.service.impl;

import com.example.vitasofttask.application.role.domain.Role;
import com.example.vitasofttask.application.role.domain.RoleRepository;
import com.example.vitasofttask.application.role.model.RoleType;
import com.example.vitasofttask.application.user.domain.Customer;
import com.example.vitasofttask.application.user.domain.CustomerRepository;
import com.example.vitasofttask.application.user.model.UserDto;
import com.example.vitasofttask.application.user.service.UserService;
import com.example.vitasofttask.common.Mapper;
import com.example.vitasofttask.errors.ErrorDescriptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final Mapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        Customer userAdmin = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role role = roleRepository.getRoleByName(RoleType.ADMIN);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(role.getCustomers().contains(userAdmin));
        return customerRepository.findAll().stream().map(mapper::convertToUserDto)
                .sorted(Comparator.comparingLong(UserDto::getId))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public String setRole(Long userId, RoleType role) {
        Customer userAdmin = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.USER_NOT_FOUND.throwIsFalse(customerRepository.existsById(userId));
        Customer user = customerRepository.getUserById(userId);
        Role roleAdmin = roleRepository.getRoleByName(RoleType.ADMIN);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(roleAdmin.getCustomers().contains(userAdmin));
        if (!roleRepository.getRoleByName(role).getCustomers().contains(user)) {
            List<Role> roles = user.getRoles();
            roles.add(roleRepository.getRoleByName(role));
            user.setRoles(roles);
            return "Пользователю с ИД " + userId + " добавлена роль " + role;
        } else
            return "У пользователя уже есть данная роль";
    }

    @Override
    public UserDto getUser(Long id) {
        Customer userAdmin = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ErrorDescriptor.USER_NOT_FOUND.throwIsFalse(customerRepository.existsById(id));
        Customer user = customerRepository.getUserById(id);
        Role roleAdmin = roleRepository.getRoleByName(RoleType.ADMIN);
        ErrorDescriptor.NO_PREVELEGIES.throwIsFalse(roleAdmin.getCustomers().contains(userAdmin));
        return mapper.convertToUserDto(user);


    }

}
