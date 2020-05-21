package com.lits.springboot.controller;

import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.service.TeacherService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@Log
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/{teacherId}")
    public TeacherDto getById(@PathVariable(name = "teacherId") Integer teacherId) {
        log.info("Get teacher by teacherId = " + teacherId);
        return teacherService.getOne(teacherId);
    }

    @GetMapping("/teacher")
    public TeacherDto getByPhone(@RequestParam(name = "phone", required = false) String phone) {
        log.info("Get teacher by phone = " + phone);
        return teacherService.getOneByPhone(phone);
    }

    @GetMapping
    public List<TeacherDto> getAll(@RequestParam(name = "sortBy", required = false) String sortBy) {
        List<TeacherDto> teacherDtos;
        if (("age").equals(sortBy)) {
            teacherDtos = teacherService.getAll(sortBy);
        } else {
            teacherDtos = teacherService.getAll();
        }
        log.info("Get all teachers");
        return teacherDtos;
    }

    @PutMapping("/teacher/{teacherId}")
    public TeacherDto update(@PathVariable(name = "teacherId") Integer teacherId, @RequestBody TeacherDto teacherDto) {
        log.info("Update teacher by teacherId = " + teacherId);
        return teacherService.update(teacherId, teacherDto.getFirstName(), teacherDto.getLastName());
    }

    @DeleteMapping("/teacher/{teacherId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "teacherId") Integer teacherId) {
        log.info("Delete teacher by teacherId = " + teacherId);
        teacherService.delete(teacherId);
    }

    @PostMapping("/teacher")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeacherDto create(@RequestBody @Valid TeacherDto teacherDto) {
        log.info("Create new teacher");
        return teacherService.create(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getAge(), teacherDto.getPhone());
    }

}

