package com.lits.springboot.exceptions.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StudentCreateException extends RuntimeException {

    public StudentCreateException(String msg) {
        super(msg);
    }
}
