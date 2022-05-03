package com.example.demo.service.api;

import com.example.demo.model.Active;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ActiveService {
    Collection<Active> getAll(LocalDateTime startDate, Long idProfile);

    void save(Active active, Long idProfile);

    Active get(Long idActive, Long idProfile);

    void update(Active updateActive, Long idActive, Long idProfile);

    void delete(Long idActive, Long idProfile);
}
