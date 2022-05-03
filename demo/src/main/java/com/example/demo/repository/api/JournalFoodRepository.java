package com.example.demo.repository.api;

import com.example.demo.model.Journal;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalFoodRepository {
    void save(Journal journal, Long idProfile);

    Journal get(Long idProfile, Long idFood);

    List<Journal> getOneDay(Long idProfile, LocalDateTime day);

    void update(Journal updateJournal, Long idFood, Long idProfile);

    void delete(Long idFood, Long idProfile);
}
