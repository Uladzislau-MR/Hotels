package com.by.hotels.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="amenities")


public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Amenities() {
    }

    public Amenities(String name) {
        this.name = name;
    }

    @Column(unique = true,nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Amenities{" +
               "name='" + name + '\'' +
               '}';
    }
}
