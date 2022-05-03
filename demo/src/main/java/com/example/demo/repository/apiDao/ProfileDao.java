package com.example.demo.repository.apiDao;

import com.example.demo.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDao extends JpaRepository<Profile,Long > {
}
