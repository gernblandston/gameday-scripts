package com.temkostudios.gameday.scripts;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.temkostudios.gameday.model.City;

import lombok.Data;

@Data
 @JsonIgnoreProperties(ignoreUnknown=true)
public class AllCityResponse {
    boolean success;
    String error;
    ArrayList<City> data;
}
