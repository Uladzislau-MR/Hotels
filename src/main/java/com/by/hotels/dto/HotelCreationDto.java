package com.by.hotels.dto;

import com.by.hotels.entities.Amenities;
import com.by.hotels.models.Address;
import com.by.hotels.models.ArrivalTime;
import com.by.hotels.models.Contacts;

import java.util.Set;

public class HotelCreationDto {

    private String name;
    private String brand;
    private String description;
    private Address address;
    private Contacts contacts;
    private ArrivalTime arrivalTime;
    private Set<Amenities> amenities;

    private HotelCreationDto() {
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

    public Address getAddress() {
        return address;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public ArrivalTime getArrivalTime() {
        return arrivalTime;
    }

    public Set<Amenities> getAmenities() {
        return amenities;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final  HotelCreationDto hotelDto;

        public Builder() {
            this.hotelDto = new HotelCreationDto();
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

        public Builder address(Address address) {
            hotelDto.address = address;
            return this;
        }

        public Builder contacts (Contacts contacts) {
            hotelDto.contacts = contacts;
            return this;
        }

        public Builder arrivalTime(ArrivalTime arrivalTime) {
            hotelDto.arrivalTime = arrivalTime;
            return this;
        }



        public Builder amenities(Set<Amenities> amenities) {
            hotelDto.amenities = amenities;
            return this;
        }

        public HotelCreationDto build() {
            return hotelDto;
        }
    }
}
