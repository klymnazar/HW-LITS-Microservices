package com.lits.userservice.service;

import com.lits.userservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "course-service", configuration = FeignConfig.class)
public interface CourseService {

    @GetMapping(value = "/courses")
    String getAllCourses();

    @GetMapping(value = "/courses/course/{courseId}")
    String getById(Integer courseId);


}
