package com.example.demo.repository.apiDao;

import com.example.demo.model.Active;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ActiveDao extends JpaRepository<Active, Long> {
    Collection<Active> findAllByProfileId(Long idProfile);

    Collection<Active> findAllByProfileIdAndDtCreateBetween(Long profileId, LocalDateTime dtStart,
                                                      LocalDateTime dtEnd);

    Active findByProfileIdAndId(Long idProfile, Long idWeight);
}
