package com.lits.springboot.exceptions.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StudentRequestException extends RuntimeException {

    public StudentRequestException(String msg) {
        super(msg);
    }
}

