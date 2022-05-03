package com.example.demo.service.validator;

import com.example.demo.model.Profile;
import com.example.demo.service.api.ProfileService;
import com.example.security.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfileValidatorService {
    private final ProfileService profileService;
    private final UserHolder userHolder;

    public ProfileValidatorService(ProfileService profileService, UserHolder userHolder) {
        this.profileService = profileService;
        this.userHolder = userHolder;
    }

    @Before("execution(* com.example.demo.service.DefaultProfileService.save(..))")
    public void save(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Profile profile = (Profile) args[0];
        validBasicParameters(profile);
    }

    @Before("execution(* com.example.demo.service.DefaultProfileService.update(..))")
    public void update(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Profile updateProfile = (Profile) args[0];
        Long idProfile = (Long) args[1];
        updateProfile.setId(idProfile);
        validBasicParameters(updateProfile);
    }

    @Before("execution(* com.example.demo.service.DefaultProfileService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long idProfile = (Long) args[0];
    }

    public void validBasicParameters(Profile profile) {
        if (profile.getBirthday() == null || profile.getBirthday().equals("")) {
            throw new IllegalArgumentException("Не указано дата рождения");
        }
        if (profile.getGender() == null) {
            throw new IllegalArgumentException("Не указан гендер");
        }
        if (profile.getGoal() == null) {
            throw new IllegalArgumentException("Не указана ваша желаемая форма тела");
        }
        if (profile.getHeight() <= 0) {
            throw new IllegalArgumentException("рост указан неверно");
        }
        if (profile.getLifestyle() == null) {
            throw new IllegalArgumentException("Не указан образ жизни");
        }
        if (profile.getWeightActual() <= 0) {
            throw new IllegalArgumentException("Текущий вес указан неверно");
        }
    }
}
