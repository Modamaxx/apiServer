package com.example.demo.service.api;

import com.example.demo.dto.ProfileDto;
import com.example.demo.model.Profile;

public interface CalculationService {
    ProfileDto standardUser(Profile profile);

    void standardEat(ProfileDto profileDto, Long idProfile,String date);
}
