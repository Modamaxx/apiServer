package com.example.demo.service.validator;

import com.example.demo.model.Journal;
import com.example.demo.service.api.JournalFoodService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Aspect
@Component
public class JournalsFoodValidatorService {
    private final JournalFoodService journalFoodService;

    public JournalsFoodValidatorService(JournalFoodService journalFoodService) {
        this.journalFoodService = journalFoodService;
    }

    @Before("execution(* com.example.demo.service.DefaultJournalFoodService.save(..))")
    public void save(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Journal journal = (Journal) args[0];
        validBasicParameters(journal);
    }

    @Before("execution(* com.example.demo.service.DefaultJournalFoodService.update(..))")
    public void update(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Journal journalUpdate = (Journal) args[0];
        validBasicParameters(journalUpdate);
    }

    @Before("execution(* com.example.demo.service.DefaultJournalFoodService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        }

    public void validBasicParameters(Journal journal) {
        if (journal.getProduct() == null) {
            throw new IllegalArgumentException("Не указано что съел пользователь");
        }
        if (journal.getTypeEating() == null) {
            throw new IllegalArgumentException("Неизвестный прием пищи");
        }
        if (journal.getMass() <= 0) {
            throw new IllegalArgumentException("Масса продукта введена неверено");
        }
    }
}