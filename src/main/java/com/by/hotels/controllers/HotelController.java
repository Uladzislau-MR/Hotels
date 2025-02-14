package com.by.hotels.controllers;

import com.by.hotels.dto.HotelDto;
import com.by.hotels.entities.Hotel;
import com.by.hotels.repositories.HotelRepository;
import com.by.hotels.services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/property-view")
public class HotelController {

    @Autowired
    private HotelService hotelService;

//    @GetMapping("/hotels")
//    public ResponseEntity<List<HotelDto>> getHotels() {
//        List<HotelDto> hotels = hotelService.getAll();
//        if (hotels.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(hotels, HttpStatus.OK);
//    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDto>> getHotels() {
        List<HotelDto> hotels = hotelService.getAll2();
        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

}
