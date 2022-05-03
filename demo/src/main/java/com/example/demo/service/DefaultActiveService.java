package com.example.demo.service;

import com.example.demo.model.Active;
import com.example.demo.model.Profile;
import com.example.demo.repository.apiDao.ActiveDao;
import com.example.demo.service.api.ActiveService;
import com.example.demo.service.api.ProfileService;
import com.example.security.UserHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class DefaultActiveService implements ActiveService {
    private final ActiveDao activeDao;
    private final ProfileService profileService;
    private final UserHolder userHolder;

    public DefaultActiveService(ActiveDao activeDao,
                                ProfileService profileService,
                                UserHolder userHolder) {
        this.activeDao = activeDao;
        this.profileService = profileService;
        this.userHolder = userHolder;
    }

    @Override
    public Collection<Active> getAll(LocalDateTime startDate, Long idProfile) {
        LocalDateTime end = startDate.plusDays(1);
        return activeDao.findAllByProfileIdAndDtCreateBetween(idProfile, startDate, end);
    }

    @Override
    public void save(Active active, Long idProfile) {
        Profile profile = profileService.get(idProfile);
        active.setProfile(profile);
        active.setDtCreate(LocalDateTime.now());
        activeDao.save(active);
    }

    @Override
    public Active get(Long idActive, Long idProfile) {
        Active active = activeDao.findByProfileIdAndId(idProfile, idActive);
        if (active == null) {
            throw new IllegalArgumentException("По заданым id не существует записи в базе ");
        }
        if (!isValidWhoReq(active.getProfile())) {
            throw new IllegalArgumentException("К данной активности для вас доступ закрыт");
        }
        return active;
    }

    @Override
    public void update(Active updateActive, Long idActive, Long idProfile) {
        Profile profile = profileService.get(idProfile);
        Active active = get(idActive, idProfile);
        updateActive.setProfile(profile);
        updateActive.setDtCreate(active.getDtCreate());
        updateActive.setId(idActive);
        activeDao.save(updateActive);
    }

    @Override
    public void delete(Long idActive, Long idProfile) {
        Active deleteActive = get(idActive, idProfile);
        activeDao.delete(deleteActive);
    }

    private boolean isValidWhoReq(Profile profile) {
        String login = userHolder.getAuthentication().getName();
        return profile.getUser().getLogin().equals(login) || login.equals("admin");
    }
}