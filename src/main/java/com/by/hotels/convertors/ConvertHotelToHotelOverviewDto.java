package com.by.hotels.convertors;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.entities.Hotel;
import org.springframework.stereotype.Component;

@Component
public class ConvertHotelToHotelOverviewDto extends Convertor<Hotel, HotelOverviewDto> {


    @Override
    public HotelOverviewDto convert(Hotel hotel) {
        return new HotelOverviewDto.Builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(hotel.getAddress().toFormattedString())
                .phone(hotel.getContacts().getPhone())
                .build();
    }



}
