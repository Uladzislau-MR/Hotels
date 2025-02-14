package com.by.hotels.services;

import com.by.hotels.convertors.ConvertHotelToHotelDetailedDto;
import com.by.hotels.convertors.ConvertHotelToHotelOverviewDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.entities.Hotel;
import com.by.hotels.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service


public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelDto> getAll(){
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDto> hotelDtoList = new ArrayList<>();
        ConvertHotelToHotelOverviewDto convertor = new ConvertHotelToHotelOverviewDto();
           for(Hotel hotel: hotels){
               hotelDtoList.add(convertor.convert(hotel));
           }
        return hotelDtoList;
    }
    public List<HotelDto> getAll2(){
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDto> hotelDtoList = new ArrayList<>();
       ConvertHotelToHotelDetailedDto convertor = new ConvertHotelToHotelDetailedDto();
        for(Hotel hotel: hotels){
            hotelDtoList.add(convertor.convert(hotel));
        }
        return hotelDtoList;
    }
}
