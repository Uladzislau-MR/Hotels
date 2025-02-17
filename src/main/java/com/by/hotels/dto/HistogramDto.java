package com.by.hotels.dto;

import java.util.HashMap;
import java.util.Map;

public class HistogramDto {
    private Map<String, Long> data;

    public HistogramDto() {
        this.data = new HashMap<>();
    }

    public void addEntry(String key, Long value) {
        data.put(key, value);
    }

    public Map<String, Long> getData() {
        return data;
    }
}