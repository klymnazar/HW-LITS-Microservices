package com.lits.springboot.exceptions.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CourseCreateException extends RuntimeException {

    public CourseCreateException(String msg) {
        super(msg);
    }
}
