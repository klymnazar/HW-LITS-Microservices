package com.lits.springboot.controller;

import com.lits.springboot.dto.UserDto;
import com.lits.springboot.exceptions.user.UserCreateException;
import com.lits.springboot.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("/users")
@Log
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer signUp(@RequestBody @Valid UserDto userDto) {
        Integer userId;
        if (userDto.getRole().equals("USER") || userDto.getRole().equals("STUDENT") || userDto.getRole().equals("TEACHER") || userDto.getRole().equals("ADMIN")) {
            log.info("Create new user");
            userId = userService.create(userDto);
        } else {
            throw new UserCreateException(format("Incorrect user role = %s. Enter the correct user role (USER, STUDENT, TEACHER, ADMIN)", userDto.getRole()));
        }
        return userId;
    }

    @GetMapping()
    public List<UserDto> getAll() {
        log.info("Get all users");
        return userService.getAll();
    }

    @PutMapping("/user/{userId}")
    public UserDto update(@PathVariable(name = "userId") Integer userId, @RequestBody @Valid UserDto userDto) {
        if (userDto.getRole().equals("USER") || userDto.getRole().equals("STUDENT") || userDto.getRole().equals("TEACHER") || userDto.getRole().equals("ADMIN")) {
            log.info("Update student by userId = " + userId);
            userDto.setId(userId);
            userService.update(userDto);
        } else {
            throw new UserCreateException(format("Incorrect user role = %s. Enter the correct user role (USER, STUDENT, TEACHER, ADMIN)", userDto.getRole()));
        }
        return userDto;
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable(name = "userId") Integer userId) {
        log.info("Delete user by userId = " + userId);
        userService.delete(userId);
    }

}
