package com.example.demo.model;

import com.example.demo.model.type.Gender;
import com.example.demo.model.type.Goal;
import com.example.demo.model.type.Lifestyle;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthday;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "lifestyle")
    private Lifestyle lifestyle;
    @Column(name = "weightActual")
    private double weightActual;
    @Column(name = "goal")
    private Goal goal;
    @Column(name = "height")
    private double height;
    @OneToOne
    private User user;

    public double getWeightActual() {
        return weightActual;
    }

    public void setWeightActual(double weightActual) {
        this.weightActual = weightActual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Lifestyle getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(Lifestyle lifestyle) {
        this.lifestyle = lifestyle;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
