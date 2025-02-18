package com.by.hotels.controllers;

import com.by.hotels.repositories.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class HotelControllerTest {
    @Mock
    HotelRepository hotelRepository;
    @InjectMocks
    HotelController hotelController = new HotelController();

    @Test
    void getHotels() {
    }

    @Test
    void getHotel() {
    }

    @Test
    void getHotelsByField() {
    }

    @Test
    void create() {
    }

    @Test
    void addAmenities() {
    }

    @Test
    void getHistogram() {
    }
}