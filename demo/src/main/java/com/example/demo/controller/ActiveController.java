package com.example.demo.controller;

import com.example.demo.model.Active;
import com.example.demo.service.api.ActiveService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@RestController
@RequestMapping("api/profile/{id_profile}/journal/active")
public class ActiveController {
    private final ActiveService activeService;

    public ActiveController(ActiveService activeService) {
        this.activeService = activeService;
    }

    @GetMapping
    public ResponseEntity<Collection<Active>> getAll(@PathVariable("id_profile") Long idProfile,
                                                     @RequestParam(value = "date", required = false) String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(date + " 00:00", formatter);
        Collection<Active> actives = activeService.getAll(startDate, idProfile);
        return new ResponseEntity<>(actives, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@PathVariable("id_profile") Long idProfile, @RequestBody Active active) {
        activeService.save(active, idProfile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id_active}")
    public ResponseEntity<Active> get(@PathVariable("id_profile") Long idProfile,
                                      @PathVariable("id_active") Long idActive) {
        Active actives = activeService.get(idActive, idProfile);
        return new ResponseEntity<>(actives, HttpStatus.OK);
    }

    @PutMapping("/{id_active}")
    public ResponseEntity<?> update(@PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive,
                                    @RequestBody Active updateActive) {
        activeService.update(updateActive, idActive, idProfile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id_active}")
    public ResponseEntity<?> delete(@PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive) {
        activeService.delete(idActive, idProfile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
