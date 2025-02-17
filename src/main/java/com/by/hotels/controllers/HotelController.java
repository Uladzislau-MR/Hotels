package com.by.hotels.controllers;

import com.by.hotels.dto.HistogramDto;
import com.by.hotels.dto.HotelCreationDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    private static final Logger log = LoggerFactory.getLogger(HotelController.class);
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
    public ResponseEntity<HotelDto> getHotel(@PathVariable Long id) {
        HotelDto hotelDto = hotelService.getById(id);
        return ResponseEntity.ok(hotelDto);
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
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut) {

        List<HotelOverviewDto> hotels = hotelService.searchHotels(name, description, brand, houseNumber,
                street, city, county, postCode, phone, email, amenities, checkIn, checkOut);

        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }


    @PostMapping("/hotels")
    public ResponseEntity<HotelOverviewDto> create(@RequestBody HotelCreationDto hotelCreationDto) {
        HotelOverviewDto createdHotel = hotelService.create(hotelCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }


    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(@RequestBody Set<String> newAmenities, @PathVariable Long id) {
        hotelService.addAmenities(id, newAmenities);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/histogram/{param}")
    public ResponseEntity<HistogramDto> getHistogram(@PathVariable String param) {

        try {
            log.info("Received parameter: {}", param);
            HistogramDto dto = hotelService.getHistogramData(param);

            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Invalid parameter: {}", param);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}


