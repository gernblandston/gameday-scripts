package com.temkostudios.gameday.scripts;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.temkostudios.gameday.model.State;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class AllStateResponse {
    boolean success;
    String error;
    ArrayList<State> data;
}
