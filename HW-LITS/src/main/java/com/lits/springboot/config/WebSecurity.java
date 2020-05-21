package com.lits.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.lits.springboot.service.UserService;

import static com.lits.springboot.config.SecurityConstants.*;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()

                .antMatchers("/users/**").permitAll()
                .antMatchers("/courses/**").permitAll()
                .antMatchers("/students/**").permitAll()
                .antMatchers("/teachers/**").permitAll()


//                .antMatchers("/users/**").hasRole(ROLE_ADMIN)

//                .antMatchers(HttpMethod.POST,"/courses/**").hasRole(ROLE_ADMIN)
//                .antMatchers(HttpMethod.PUT,"/courses/**").hasRole(ROLE_ADMIN)
//                .antMatchers(HttpMethod.DELETE,"/courses/**").hasRole(ROLE_ADMIN)

//                .antMatchers(HttpMethod.GET,"/students/**").hasAnyRole(ROLE_ADMIN, ROLE_STUDENT, ROLE_TEACHER)
//                .antMatchers(HttpMethod.POST,"/students/**").hasRole(ROLE_ADMIN)
//                .antMatchers(HttpMethod.PUT,"/students/**").hasRole(ROLE_ADMIN)
//                .antMatchers(HttpMethod.DELETE,"/students/**").hasRole(ROLE_ADMIN)

//                .antMatchers(HttpMethod.GET,"/teachers/**").hasAnyRole(ROLE_ADMIN, ROLE_STUDENT, ROLE_TEACHER)
//                .antMatchers(HttpMethod.GET,"/teachers").hasRole(ROLE_USER)
//                .antMatchers(HttpMethod.POST,"/teachers/**").hasRole(ROLE_ADMIN)
//                .antMatchers(HttpMethod.PUT,"/teachers/**").hasRole(ROLE_ADMIN)
//                .antMatchers(HttpMethod.DELETE,"/teachers/**").hasRole(ROLE_ADMIN)

                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}