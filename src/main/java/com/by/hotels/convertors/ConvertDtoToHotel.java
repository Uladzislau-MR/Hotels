package com.by.hotels.convertors;

import com.by.hotels.dto.HotelCreationDto;
import com.by.hotels.entities.Hotel;
import org.springframework.stereotype.Component;

import java.util.HashSet;
@Component
public class ConvertDtoToHotel extends Convertor<HotelCreationDto, Hotel> {
    @Override
    public Hotel convert(HotelCreationDto hotelDto) {
        if (hotelDto == null) {
            return null;
        }

        Hotel hotel = new Hotel();

        hotel.setName(hotelDto.getName());
        hotel.setBrand(hotelDto.getBrand());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setContacts(hotelDto.getContacts());
        hotel.setArrivalTime(hotelDto.getArrivalTime());

        if (hotelDto.getAmenities() != null) {
            hotel.setAmenitiesList(new HashSet<>(hotelDto.getAmenities()));
        } else {
            hotel.setAmenitiesList(new HashSet<>());
        }

        return hotel;
    }
}
