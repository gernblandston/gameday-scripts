package com.temkostudios.gameday.scripts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
 @JsonIgnoreProperties(ignoreUnknown=true)
public class SchoolLocation {
    int id;
    String name;
    String city;
    String state;
    String zip;
    String countryCode;
    String timezone;
    double latitude;
    double longitude;
    String elevation;
    int capacity;
    int constructionYear;
    boolean grass;
    boolean dome;
}
