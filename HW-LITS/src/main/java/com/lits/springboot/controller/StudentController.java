package com.lits.springboot.controller;

import com.lits.springboot.dto.StudentDto;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.service.StudentService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
@Log
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("student/{studentId}")
    public StudentDto getById(@PathVariable(name = "studentId") Integer studentId) {
        log.info("Get student by studentId = " + studentId);
        return studentService.getOne(studentId);
    }

    @GetMapping
    public List<StudentDto> getAll() {
        log.info("Get all students");
        return studentService.getAll();
    }

    @PutMapping("/student/{studentId}")
    public StudentDto update(@PathVariable(name = "studentId") Integer studentId, @RequestBody @Valid StudentDto studentDto) {
        log.info("Update student by studentId = " + studentId);
        studentDto.setId(studentId);
        return studentService.update(studentDto);
    }

    @DeleteMapping("/student/{studentId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "studentId") Integer studentId) {
        log.info("Delete student by studentId = " + studentId);
        studentService.delete(studentId);
    }

    @PostMapping("/student")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StudentDto create(@RequestBody @Valid StudentDto studentDto) {
        log.info("Create new student");
        return studentService.create(studentDto.getFirstName(), studentDto.getLastName(), studentDto.getAge());
    }

}

