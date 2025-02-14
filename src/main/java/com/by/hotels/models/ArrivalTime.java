package com.by.hotels.models;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class ArrivalTime {
    private String checkIn;
    private String checkOut;
}