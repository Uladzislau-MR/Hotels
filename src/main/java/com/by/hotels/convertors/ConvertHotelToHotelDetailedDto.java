package com.by.hotels.convertors;

import com.by.hotels.dto.HotelDto;
import com.by.hotels.entities.Amenities;
import com.by.hotels.entities.Hotel;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class ConvertHotelToHotelDetailedDto extends Convertor<Hotel, HotelDto> {



@Override
public HotelDto convert(Hotel hotel) {
    Set<String> amenities = new HashSet<>();
    for (Amenities amenity : hotel.getAmenitiesList()) {
        amenities.add(amenity.getName());
    }
    return new HotelDto.Builder()
            .id(hotel.getId())
            .name(hotel.getName())
            .brand(hotel.getBrand())
            .description(hotel.getDescription())
            .address(hotel.getAddress())
            .phone(hotel.getContacts().getPhone())
            .email(hotel.getContacts().getEmail())
            .checkIn(hotel.getArrivalTime().getCheckIn())
            .checkOut(hotel.getArrivalTime().getCheckOut())
            .amenities(amenities)
            .build();
}
}