package com.lits.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class TeacherDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    @Pattern(regexp = "(\\+380)[0-9]{9}")
    private String phone;
}
