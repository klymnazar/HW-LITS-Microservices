package com.lits.springboot.exceptions.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TeacherCreateException extends RuntimeException {

    public TeacherCreateException(String msg) {
        super(msg);
    }
}
