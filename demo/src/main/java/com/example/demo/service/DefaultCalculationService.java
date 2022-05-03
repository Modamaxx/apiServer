package com.example.demo.service;

import com.example.demo.dto.ProfileDto;
import com.example.demo.model.Journal;
import com.example.demo.model.Profile;
import com.example.demo.model.type.Goal;
import com.example.demo.model.type.Lifestyle;
import com.example.demo.service.api.CalculationService;
import com.example.demo.service.api.JournalFoodService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DefaultCalculationService implements CalculationService {
    private final JournalFoodService journalFoodService;

    public DefaultCalculationService(JournalFoodService journalFoodService) {
        this.journalFoodService = journalFoodService;
    }

    @Override
    public ProfileDto standardUser(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        double weight = profile.getWeightActual();
        double height = profile.getHeight();
        int age = LocalDate.now().getYear() - profile.getBirthday().getYear();
        if (profile.getBirthday().getDayOfYear() >= LocalDate.now().getDayOfYear()) {
            age++;
        }
        Lifestyle lifestyle = Lifestyle.valueOfIgnoreCase(profile.getLifestyle().name());
        double active = lifestyle.getV();

        Goal goal = Goal.valueOfIgnoreCase(profile.getGoal().name());
        double goalWeight = goal.getI();

        String gender = profile.getGender().name();
        double calories = ((((10 * weight) + (6.25 * height)) - (5 * age)));
        if (gender.equals("MAN")) {
            calories = (calories + 5) * active * goalWeight;
        } else {
            calories = (calories - 161) * active * goalWeight;
        }
        double protein = (calories * 0.3) / 4;
        double fats = (calories * 0.3) / 9;
        double carbohydrates = (calories * 0.4) / 4;

        profileDto.setNormCalories((int) calories);
        profileDto.setNormProtein((int) protein);
        profileDto.setNormFats((int) fats);
        profileDto.setNormCarbohydrates((int) carbohydrates);
        return profileDto;
    }

    @Override
    public void standardEat(ProfileDto profileDto, Long idProfile, String date) {
        List<Journal> journals = journalFoodService.getOneDay(idProfile, date);
        double calories = 0;
        double protein = 0;
        double fats = 0;
        double carbohydrates = 0;
        double part = 0;
        for (Journal j : journals) {
            if (j.getProduct() != null) {
                part = (j.getMass() * 100) / j.getProduct().getMass();
                calories = calories + (j.getProduct().getCalories() * part) / 100;
                protein = protein + (j.getProduct().getProteins() * part) / 100;
                fats = fats + (j.getProduct().getFats() * part) / 100;
                carbohydrates = carbohydrates + (j.getProduct().getCarbohydrates() * part) / 100;
            }
        }
        profileDto.setActualCalories((int) calories);
        profileDto.setActualProtein((int) protein);
        profileDto.setActualCarbohydrates((int) carbohydrates);
        profileDto.setActualFats((int) fats);
    }
}