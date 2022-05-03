package com.example.demo.model;

import com.example.demo.model.type.Eating;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Profile profile;
    @Column(name = "typeEating")
    private Eating typeEating;
    @OneToOne
    private Product product;
    @Column(name = "mass")
    private double mass;
    @Column(name = "dtCreate")
    private LocalDateTime dtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Eating getTypeEating() {
        return typeEating;
    }

    public void setTypeEating(Eating typeEating) {
        this.typeEating = typeEating;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }
}
