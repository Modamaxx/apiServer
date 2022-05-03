package com.example.demo.config;

import com.example.demo.config.Jwt.JwtFilter;
import com.example.demo.model.type.Role;
import com.example.security.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserHolder userHolder;

    @Autowired
    private JwtFilter jwtFilter;

    public SecurityConfig(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST,"/api/user/**").anonymous()
                .antMatchers(HttpMethod.GET, "/api/user/**").hasAuthority(Role.ROLE_ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority(Role.ROLE_ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/api/user/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
//                .antMatchers(HttpMethod.PUT,"/api/product/**").hasAuthority(Role.ROLE_ADMIN.name())
//                .antMatchers(HttpMethod.DELETE,"/api/product/**").hasAuthority(Role.ROLE_ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
