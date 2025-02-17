package com.by.hotels.dto;

public class HotelOverviewDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;

    private HotelOverviewDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public static class Builder {
        private final HotelOverviewDto hotelDto;

        public Builder() {
            this.hotelDto = new HotelOverviewDto();
        }


        public Builder id(Long id) {
            hotelDto.id = id;
            return this;
        }

        public Builder name(String name) {
            hotelDto.name = name;
            return this;
        }

        public Builder phone(String phone) {
            hotelDto.phone = phone;
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

        public HotelOverviewDto build() {
            return hotelDto;
        }
    }
}
