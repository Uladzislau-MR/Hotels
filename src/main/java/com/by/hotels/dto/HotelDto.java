package com.by.hotels.dto;

import com.by.hotels.entities.Amenities;

import java.util.Set;

public class HotelDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private String address;
    private String phone;
    Set<String> amenities;

    private HotelDto() {}


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Set<String> getAmenities() {
        return amenities;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private final HotelDto hotelDto;

        public Builder() {
            this.hotelDto = new HotelDto();
        }

        public Builder id(Long id) {
            hotelDto.id = id;
            return this;
        }

        public Builder name(String name) {
            hotelDto.name = name;
            return this;
        }

        public Builder brand(String brand) {
            hotelDto.brand = brand;
            return this;
        }

        public Builder description(String description) {
            hotelDto.description = description;
            return this;
        }

        public Builder address(String address) {
            hotelDto.address = address;
            return this;
        }

        public Builder phone(String phone) {
            hotelDto.phone = phone;
            return this;
        }
        public Builder amenities(Set<String> amenities) {
            hotelDto.amenities = amenities;
            return this;
        }



        public HotelDto build() {
            return hotelDto;
        }
    }
}
