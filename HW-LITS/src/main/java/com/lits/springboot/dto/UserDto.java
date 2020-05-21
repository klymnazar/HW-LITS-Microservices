package com.lits.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    private Integer id;
    @NotNull(message = "User can not be created/updated because username is null")
    private String username;
    @NotNull(message = "User can not be created/updated because password is null")
    private String password;
    @NotNull(message = "User can not be created/updated because role is null")
    private String role;
}
