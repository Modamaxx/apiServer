package com.example.demo.repository.api;


import com.example.demo.model.Active;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActiveRepository {
    List<Active> getAll(Long idProfile);

    void save(Active active, Long idProfile);

    Active get(Long idActive, Long idProfile);

    void update(Active updateActive, Long idActive, Long idProfile, long dtUpdate);

    void delete(Long idActive, Long idProfile, long dtUpdate);
}
