package com.example.demo.controller;

import com.example.demo.model.Journal;
import com.example.demo.service.api.ActiveService;
import com.example.demo.service.api.JournalFoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("api/profile/{id_profile}/journal/food")
public class JournalFoodController {
    private final JournalFoodService journalService;

    public JournalFoodController(JournalFoodService journalService, ActiveService activeService) {
        this.journalService = journalService;
    }

    @GetMapping("/byDay/{date}")
    public ResponseEntity<List<Journal>> getOneDay(@PathVariable("id_profile") Long idProfile,
                                                   @PathVariable String date) {
        List<Journal> journals = journalService.getOneDay(idProfile, date);
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@PathVariable("id_profile") Long idProfile, @RequestBody Journal journal) {
        journalService.save(journal, idProfile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id_food}")
    public ResponseEntity<Journal> get(@PathVariable("id_profile") Long idProfile,
                                       @PathVariable("id_food") Long idFood) {
        Journal journal = journalService.get(idProfile, idFood);
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }

    @PutMapping("/{id_food}")
    public ResponseEntity<?> update(@PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_food") Long idFood,
                                    @RequestBody Journal updateJournal) {
        journalService.update(updateJournal, idFood, idProfile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id_food}")
    public ResponseEntity<?> delete(@PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_food") Long idFood) {
        journalService.delete(idFood, idProfile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
