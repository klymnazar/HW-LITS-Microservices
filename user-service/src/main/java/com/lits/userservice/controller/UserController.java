package com.lits.userservice.controller;

import com.lits.userservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user-api/users")
public class UserController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String getUser() {
        return "Hello World";
    }

    @GetMapping(value = "/courses")
    public String getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(value = "/courses/course/{courseId}")
    public String getCoursesById(@PathVariable(name = "courseId") Integer courseId) {
        return courseService.getById(courseId);
    }

}
