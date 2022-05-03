package com.example.demo.repository.apiDao;

import com.example.demo.model.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface JournalsFoodDao extends JpaRepository<Journal, Long> {

    Collection<Journal> findAllByProfileId(Long profileId);

    List<Journal> findAllByProfileIdAndDtCreateBetween(Long profileId, LocalDateTime dayStart, LocalDateTime dayEnd);

    Journal findByProfileIdAndId(Long idProfile, Long idFood);
}
