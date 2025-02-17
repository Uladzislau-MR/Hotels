package com.by.hotels.models;

import jakarta.persistence.Embeddable;


@Embeddable

public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;

    public String toFormattedString() {
        return (houseNumber != null ? houseNumber + " " : "") +
               (street != null ? street + ", " : "") +
               (city != null ? city + ", " : "") +
               (county != null ? county + ", " : "") +
               (postCode != null ? postCode : "");
    }


    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}