package com.example.demo.repository;


import com.example.demo.model.Active;
import com.example.demo.model.Profile;
import com.example.demo.repository.api.ActiveRepository;
import com.example.demo.repository.apiDao.ActiveDao;
import com.example.demo.service.api.ProfileService;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DefaultActiveRepository implements ActiveRepository {
    private final ActiveDao activeDao;
    private final ProfileService profileService;

    public DefaultActiveRepository(ActiveDao activeDao, ProfileService profileService) {
        this.activeDao = activeDao;
        this.profileService = profileService;
    }

    @Override
    public List<Active> getAll(Long idProfile) {
//        if (filter.getDtStart() != null && filter.getDtEnd() != null) {
//            LocalDateTime start = Instant.ofEpochMilli(filter.getDtStart()).atZone(ZoneId.systemDefault()).toLocalDateTime();
//            LocalDateTime end = Instant.ofEpochMilli(filter.getDtEnd()).atZone(ZoneId.systemDefault()).toLocalDateTime();
//            return activeDao.findAllByProfileIdAndDtCreateBetween(idProfile, start, end, pageable);
//        }
        return null;
    }

    @Override
    public void save(Active active, Long idProfile) {
        Profile profile = profileService.get(idProfile);
        LocalDateTime time = LocalDateTime.now();
        active.setProfile(profile);
        active.setDtCreate(time);
        activeDao.save(active);
    }

    @Override
    public Active get(Long idActive, Long idProfile) {
        Active active = activeDao.findByProfileIdAndId(idProfile, idActive);
        if (active == null) {
            throw new IllegalArgumentException("По заданым id не существует записи в базе ");
        }
        return active;
    }

    @Override
    public void update(Active updateActive, Long idActive, Long idProfile, long dtUpdate) {
        Profile profile = profileService.get(idProfile);
        Active active = get(idActive, idProfile);
        updateActive.setProfile(profile);
        updateActive.setDtCreate(active.getDtCreate());
        updateActive.setId(idActive);
        activeDao.save(updateActive);
    }

    @Override
    public void delete(Long idActive, Long idProfile, long dtUpdate) {
        Active deleteActive = get(idActive, idProfile);
        activeDao.delete(deleteActive);
    }
}