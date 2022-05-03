package com.example.demo.repository;

import com.example.demo.model.Journal;
import com.example.demo.model.Product;
import com.example.demo.model.Profile;
import com.example.demo.repository.api.JournalFoodRepository;
import com.example.demo.repository.apiDao.JournalsFoodDao;
import com.example.demo.service.api.ProductService;
import com.example.demo.service.api.ProfileService;
import com.example.security.UserHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class DefaultJournalFoodRepository implements JournalFoodRepository {

    private final JournalsFoodDao journalDao;
    private final ProfileService profileService;
    private final ProductService productService;
    private final UserHolder userHolder;

    public DefaultJournalFoodRepository(JournalsFoodDao journalDao, ProfileService profileService,
                                     ProductService productService, UserHolder userHolder) {
        this.journalDao = journalDao;
        this.profileService = profileService;
        this.productService = productService;
        this.userHolder = userHolder;
    }

    @Override
    public void save(Journal journal, Long idProfile) {
        Profile profile = profileService.get(idProfile);
        journal.setProfile(profile);
        journal.setDtCreate(LocalDateTime.now());
        Product product = productService.get(journal.getProduct().getId());
        journal.setProduct(product);
        journalDao.save(journal);
    }

    @Override
    public Journal get(Long idProfile, Long idFood) {
        Journal journal = journalDao.findByProfileIdAndId(idProfile, idFood);
        if (journal == null) {
            throw new IllegalArgumentException("Не существует записи по заданым id");
        }
        if (!isValidWhoReq(journal.getProfile())) {
            throw new IllegalArgumentException("У вас нет доступа к данному журналу");
        }
        return journal;
    }

    @Override
    public List<Journal> getOneDay(Long idProfile, LocalDateTime startDay) {
        Profile profile = profileService.get(idProfile);
        isValidWhoReq(profile);
        LocalDateTime endDay = startDay.plusDays(1);
        return journalDao.findAllByProfileIdAndDtCreateBetween(idProfile, startDay, endDay);
    }

    @Override
    public void update(Journal updateJournal, Long idFood, Long idProfile) {
        Journal journal = get(idProfile, idFood);
        updateJournal.setDtCreate(journal.getDtCreate());
        updateJournal.setProfile(journal.getProfile());
        updateJournal.setId(idFood);
        journalDao.save(updateJournal);
    }

    @Override
    public void delete(Long idFood, Long idProfile) {
        Journal journal = get(idProfile, idFood);
        journalDao.delete(journal);
    }

    private boolean isValidWhoReq(Profile profile) {
        String login = userHolder.getAuthentication().getName();
        return profile.getUser().getLogin().equals(login) || login.equals("admin");
    }
}