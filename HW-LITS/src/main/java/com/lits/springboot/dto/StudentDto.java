package com.lits.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class StudentDto {
    private Integer id;
    @NotNull(message = "Student can not be created/updated because firstName is null")
    private String firstName;
    @NotNull(message = "Student can not be created/updated because lastName is null")
    private String lastName;
    @NotNull(message = "Student can not be created/updated because age is null")
    private Integer age;
    private List<Integer> courseIds;
}
