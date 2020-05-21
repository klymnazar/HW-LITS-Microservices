package com.lits.springboot.exceptions.user;

public class UsernameNotFoundException extends RuntimeException{

        public UsernameNotFoundException(String msg) {
            super(msg);
        }
}
