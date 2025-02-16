package com.by.hotels.controllers;

import com.by.hotels.dto.HotelCreationDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.entities.Hotel;
import com.by.hotels.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/property-view")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelOverviewDto>> getHotels() {
        List<HotelOverviewDto> hotels = hotelService.getAll();
        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/hotels/{id}")
    public HotelDto getHotel(@PathVariable Long id) {

        return hotelService.getById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelOverviewDto>> getHotelsByField(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String houseNumber,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String county,
            @RequestParam(required = false) String postCode,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Set<String> amenities,
            @RequestParam(required = false)  String checkIn,
            @RequestParam(required = false)  String checkOut) {

        List<HotelOverviewDto> hotels = hotelService.searchHotels(name, description, brand, houseNumber,
                street, city, county, postCode, phone, email, amenities, checkIn, checkOut);

        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }


    @PostMapping("/hotels")
    public HotelOverviewDto create(@RequestBody HotelCreationDto hotelCreationDto){
       return hotelService.create(hotelCreationDto);
    }




}