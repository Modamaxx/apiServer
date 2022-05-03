package com.example.demo.service.validator;

import com.example.demo.model.User;
import com.example.demo.service.api.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserValidatorService {
    private final UserService userService;

    public UserValidatorService(UserService userService) {
        this.userService = userService;
    }

    @Before("execution(* com.example.demo.service.DefaultUserService.update(..))")
    public void update(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User updateUser = (User) args[0];
        Long idUser = (Long) args[1];
        User user = userService.get(idUser);

        User userBd = userService.findByLogin(updateUser.getLogin());
        if (userBd != null) {
            throw new IllegalArgumentException("Данный пользователь уже существует");
        }
        validBasicParameters(updateUser);
    }

    @Before("execution(* com.example.demo.service.DefaultUserService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long idUser = (Long) args[0];

        User user = userService.get(idUser);
    }

    @Before("execution(* com.example.demo.service.DefaultUserService.save(..))")
    public void save(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        validBasicParameters(user);
        User userBd = userService.findByLogin(user.getLogin());
        if (userBd != null) {
            throw new IllegalArgumentException("Данный пользователь уже существует");
        }
    }

    @Before("execution(* com.example.demo.service.DefaultUserService.findByLoginAndPassword(..))")
    public void findByLoginAndPassword(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String login = (String) args[0];
        String password = (String) args[1];
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException("Не указан логин");
        }
        if (password == null || password.equals("")) {
            throw new IllegalArgumentException("Не указан пароль");
        }
    }

    public void validBasicParameters(User user) {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new IllegalArgumentException("Не указан пароль");

        }
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new IllegalArgumentException("Не указан логин");

        }
    }

}
