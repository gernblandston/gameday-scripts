package com.temkostudios.gameday.scripts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
 @JsonIgnoreProperties(ignoreUnknown=true)
public class LoginResponse {
    boolean success;
    String error;
    SessionData data;
}
