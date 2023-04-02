package com.example.vitasofttask.application.user.web;

import com.example.vitasofttask.application.role.model.RoleType;
import com.example.vitasofttask.application.user.model.UserDto;
import com.example.vitasofttask.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers (){
        return userService.getAllUsers();
    }

    @GetMapping("users/{userId}")
    public UserDto getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @PutMapping("users/{userId}")
    public String setRole(@PathVariable Long userId, @RequestParam RoleType role){
        return userService.setRole(userId, role);
    }
}
