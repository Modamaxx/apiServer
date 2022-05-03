package com.example.demo.service;

import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.example.demo.repository.api.ProfileRepository;
import com.example.demo.repository.apiDao.ProfileDao;
import com.example.demo.service.api.ProfileService;
import com.example.demo.service.api.UserService;
import com.example.security.UserHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class DefaultProfileService implements ProfileService {

    private final ProfileDao profileDao;
    private final UserHolder userHolder;
    private final UserService userService;
    private final ProfileRepository profileRepository;

    public DefaultProfileService(ProfileDao profileDao, UserHolder userHolder, UserService userService, ProfileRepository profileRepository) {
        this.profileDao = profileDao;
        this.userHolder = userHolder;
        this.userService = userService;
        this.profileRepository = profileRepository;
    }

    @Override
    public void save(Profile profile) throws SQLException {
        String login = userHolder.getAuthentication().getName();
        User user = userService.findByLogin(login);
        profile.setUser(user);
        profileDao.save(profile);
//        profileRepository.save(profile);
    }

    @Override
    public void delete(Long id) {
        Profile profile = get(id);
        profileDao.delete(profile);
    }

    @Override
    public Profile get(Long id) {
        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile по id не найден"));
        if (!isValidWhoReq(profile)) {
            throw new IllegalArgumentException("Доступ к данному профилю вам закрыт");
        }
        return profile;
    }

    @Override
    public void update(Profile updateProfile, Long idProfile) {
        Profile profile = get(idProfile);
        updateProfile.setId(idProfile);
        String login = userHolder.getAuthentication().getName();
        User user = userService.findByLogin(login);
        updateProfile.setUser(user);
        profileDao.save(updateProfile);
//        profileRepository.update(profile, dtUpdate);
    }

    @Override
    public List<Profile> getAll() {
        if (userHolder.getAuthentication().getName().equals("admin")) {
            return profileDao.findAll();
        }
      throw new IllegalArgumentException("Для вас доступ закрыт");
    }

    private boolean isValidWhoReq(Profile profile) {
        String login = userHolder.getAuthentication().getName();
        return profile.getUser().getLogin().equals(login) || login.equals("admin");
    }
}
