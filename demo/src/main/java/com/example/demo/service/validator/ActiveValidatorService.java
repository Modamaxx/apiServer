package com.example.demo.service.validator;


import com.example.demo.model.Active;
import com.example.demo.service.api.ActiveService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Aspect
@Component
public class ActiveValidatorService {
    private final ActiveService activeService;

    public ActiveValidatorService(ActiveService activeService) {
        this.activeService = activeService;
    }

    @Before("execution(* com.example.demo.service.DefaultActiveService.getAll(..))")
    public void getAll(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
    }

    @Before("execution(* com.example.demo.service.DefaultActiveService.save(..))")
    public void save(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Active active = (Active) args[0];
        validBasicParameters(active);
    }

    @Before("execution(* com.example.demo.service.DefaultActiveService.update(..))")
    public void update(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Active activeUpdate = (Active) args[0];
        validBasicParameters(activeUpdate);
    }

    @Before("execution(* com.example.demo.service.DefaultActiveService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
    }

    public void validBasicParameters(Active active){
        if (active.getName() == null) {
            throw new IllegalArgumentException("Не указано название вашей активности");

        }
        if (active.getCalories() <= 0) {
            throw new IllegalArgumentException("Активность указано неверно");
        }
    }
}
