package com.example.demo.controller;

import com.example.demo.dto.ProfileDto;
import com.example.demo.model.Profile;
import com.example.demo.service.api.CalculationService;
import com.example.demo.service.api.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final CalculationService calculationService;

    public ProfileController(ProfileService profileService, CalculationService calculationService) {
        this.profileService = profileService;
        this.calculationService = calculationService;
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAll() {
        List<Profile> profiles = profileService.getAll();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Profile profile) throws SQLException {
        profileService.save(profile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/{date}")
    public ResponseEntity<ProfileDto> get(@PathVariable Long id,
                                          @PathVariable String date) {
        Profile profile = profileService.get(id);
        ProfileDto profileDto = calculationService.standardUser(profile);
        profileDto.setProfile(profile);
        calculationService.standardEat(profileDto, id, date);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Profile updateProfile, @PathVariable Long id) {
        profileService.update(updateProfile, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        profileService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}