package com.example.demo.dto;

import com.example.demo.model.Profile;

public class ProfileDto {
    private Profile profile;
    private double normCalories;
    private double actualCalories;
    private double normFats;
    private double actualFats;
    private double normCarbohydrates;
    private double actualCarbohydrates;
    private double normProtein;
    private double actualProtein;

    public double getNormCalories() {
        return normCalories;
    }

    public void setNormCalories(double normCalories) {
        this.normCalories = normCalories;
    }

    public double getNormFats() {
        return normFats;
    }

    public void setNormFats(double normFats) {
        this.normFats = normFats;
    }

    public double getNormCarbohydrates() {
        return normCarbohydrates;
    }

    public void setNormCarbohydrates(double normCarbohydrates) {
        this.normCarbohydrates = normCarbohydrates;
    }

    public double getNormProtein() {
        return normProtein;
    }

    public void setNormProtein(double normProtein) {
        this.normProtein = normProtein;
    }

    public double getActualCalories() {
        return actualCalories;
    }

    public void setActualCalories(double actualCalories) {
        this.actualCalories = actualCalories;
    }

    public double getActualFats() {
        return actualFats;
    }

    public void setActualFats(double actualFats) {
        this.actualFats = actualFats;
    }

    public double getActualCarbohydrates() {
        return actualCarbohydrates;
    }

    public void setActualCarbohydrates(double actualCarbohydrates) {
        this.actualCarbohydrates = actualCarbohydrates;
    }

    public double getActualProtein() {
        return actualProtein;
    }

    public void setActualProtein(double actualProtein) {
        this.actualProtein = actualProtein;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
