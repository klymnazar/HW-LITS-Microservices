package com.lits.springboot.config;

import com.lits.springboot.dto.UserDto;
import com.lits.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.lits.springboot.config.SecurityConstants.ROLE_PREFIX;

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto loginDto = userService.getByUsername(username);
        return new User(loginDto.getUsername(), loginDto.getPassword(), getAuthorities(loginDto.getRole()));
    }

    public List<GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }

}
