package com.lits.springboot.service;

import com.lits.springboot.dto.StudentDto;
import com.lits.springboot.model.Student;

import java.util.List;

public interface StudentService {

    StudentDto getOne(Integer id);
    void delete(Integer id);
    List<StudentDto> getAll(String sortBy);
    StudentDto create(String firstName, String lastName, Integer age);
    List<StudentDto> getAll();

    StudentDto update(StudentDto newStudentDto);

}
