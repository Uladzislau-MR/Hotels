package com.by.hotels.repositories;

import com.by.hotels.entities.Amenities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {
    Optional<Amenities> findByName(String name);
}
