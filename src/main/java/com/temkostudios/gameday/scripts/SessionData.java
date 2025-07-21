package com.temkostudios.gameday.scripts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
 @JsonIgnoreProperties(ignoreUnknown=true)

public class SessionData {
    String sessionToken;
    String accessToken;
}
