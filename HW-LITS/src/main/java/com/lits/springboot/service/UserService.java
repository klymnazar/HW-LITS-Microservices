package com.lits.springboot.service;

import com.lits.springboot.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getByUsername(String username);

    Integer create(UserDto userDto);

    List<UserDto> getAll();

    UserDto update(UserDto newUserDto);

    void delete(Integer id);

}
