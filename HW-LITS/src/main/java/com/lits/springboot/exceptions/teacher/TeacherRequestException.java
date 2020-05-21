package com.lits.springboot.exceptions.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TeacherRequestException extends RuntimeException {

    public TeacherRequestException(String msg) {
        super(msg);
    }
}

