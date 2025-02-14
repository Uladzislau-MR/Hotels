package com.by.hotels.convertors;

import com.by.hotels.dto.HotelDto;
import com.by.hotels.entities.Amenities;
import com.by.hotels.entities.Hotel;

import java.util.Set;

public class ConvertHotelToHotelDetailedDto extends Convertor<Hotel, HotelDto> {


    @Override
    public HotelDto convert(Hotel hotel) {


        return new HotelDto.Builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(hotel.getAddress().toFormattedString())
                .phone(hotel.getContacts().getPhone())
                .build();
    }
}