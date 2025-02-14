package com.by.hotels.models;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;

    public String toFormattedString() {
        return (houseNumber != 0 ? houseNumber + " " : "") +
               (street != null ? street + ", " : "") +
               (city != null ? city + ", " : "") +
               (county != null ? county + ", " : "") +
               (postCode != null ? postCode : "");
    }

}