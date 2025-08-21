package com.temkostudios.gameday.scripts;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
 @JsonIgnoreProperties(ignoreUnknown=true)
public class SchoolData {
    int id;
    String school;
    String mascot;
    String abbreviation;
    ArrayList<String> alternateNames;
    String conference;
    String division;
    String classification;
    String color;
    String alternateColor;
    ArrayList<String> logos;
    String twitter;
    SchoolLocation location;
}
