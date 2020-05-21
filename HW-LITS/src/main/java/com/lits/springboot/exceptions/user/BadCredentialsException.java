package com.lits.springboot.exceptions.user;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException(String msg) {
        super(msg);
    }
}
