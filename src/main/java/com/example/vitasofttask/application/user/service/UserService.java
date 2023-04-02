package com.example.vitasofttask.application.user.service;

import com.example.vitasofttask.application.role.model.RoleType;
import com.example.vitasofttask.application.user.model.UserDto;


import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    String setRole (Long userId, RoleType role);
    UserDto getUser(Long id);
}
