package com.lits.springboot.exceptions.user;

public class UserCreateException extends RuntimeException{

        public UserCreateException(String msg) {
            super(msg);
        }
}
