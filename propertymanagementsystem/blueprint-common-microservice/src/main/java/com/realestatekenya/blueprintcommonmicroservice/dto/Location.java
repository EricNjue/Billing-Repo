package com.realestatekenya.blueprintcommonmicroservice.dto;

import lombok.Data;

@Data
public class Location {

    private String country;
    private String city;
    private String building;
    private String street;

    @Override
    public String toString() {
        return "Location{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", building='" + building + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
