package com.example.demo.service.api;

import com.example.demo.model.Journal;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalFoodService {
    void save(Journal journal, Long idProfile);

    Journal get(Long idProfile, Long idFood);

    List<Journal> getOneDay(Long idProfile, String day);

    void update(Journal updateJournal, Long idFood, Long idProfile);

    void delete(Long idFood, Long idProfile);
}
