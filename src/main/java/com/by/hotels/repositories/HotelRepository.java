package com.by.hotels.repositories;

import com.by.hotels.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();


    @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city")
    List<Object[]> countHotelsByCity();


    @Query("SELECT h.address.county, COUNT(h) FROM Hotel h GROUP BY h.address.county")
    List<Object[]> countHotelsByCounty();

    @Query("SELECT a.name, COUNT(DISTINCT h) FROM Hotel h JOIN h.amenitiesList a GROUP BY a.name")
    List<Object[]> countHotelsByAmenities();
}


