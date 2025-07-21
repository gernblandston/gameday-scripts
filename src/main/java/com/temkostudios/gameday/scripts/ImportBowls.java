package com.temkostudios.gameday.scripts;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temkostudios.gameday.model.Bowl;
import com.temkostudios.gameday.model.City;
import com.temkostudios.gameday.model.Game;
import com.temkostudios.gameday.model.State;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImportBowls {

    public static final String DB_URL = "jdbc:mysql://mysql.hoster905.com:3308/yangtemk_gameday?serverTimezone=UTC";
    public static final String DB_USERNAME = "gamedayadmin";
    public static final String DB_PASSWORD = "Gameday is awesome Y3ah!";

    public ArrayList<City> cities;
    public ArrayList<State> states;
    public ArrayList<Bowl> bowls;
    public ArrayList<Game> games;
    String sessionToken;
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    public final static String[] bowlFileHeaders = { "TEAM_OR_DATE", "NAME", "TIME", "NETWORK", "FINAL_SCORE", "SPREAD",
            "CITY", "STATE" };

    public static void main(String args[]) {
        ImportBowls bowlImporter = new ImportBowls();
        bowlImporter.importBowls();
        System.exit(0);
    }

    public void importBowls() {
        int rCount = 0;
        sessionToken = login();

        cities = getCities();
        states = getStates();
        try (Reader in = new FileReader("2024bowlmaster.csv")) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(bowlFileHeaders)
                    .setSkipHeaderRecord(true)
                    .get();

            Iterable<CSVRecord> records = csvFormat.parse(in);

            Bowl bowl = new Bowl();
            Game game = new Game();
            for (CSVRecord record : records) {
                if(rCount == 0) {
                    
                }
                if (record.get("NAME") != null) {
                    newBowl.setName(record.get("NAME"));
                }
                if (record.get("NAME") != null) {
                    newBowl.setName(record.get("NAME"));
                }
                String title = record.get("title");
            }
        } catch (Exception e) {

        }
    }

    public String login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("playertest1");
        loginRequest.setPassword("69b9b260f30ae19ea59ca72c551554bf6ce80f4ae63c2fceea1c4d89b7bf51c6");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJHYW1lZGF5IFZ1ZTMiLCJpc3MiOiJHYW1lZGF5IEFkbWluIiwidG9rZW5UeXBlIjoibG9naW4iLCJpYXQiOjE3NDYxODgzMzIsImV4cCI6MTg0NjE4ODMzMn0.RNxFfmJtqr0BZU15ltph53DPWyZvyKAdBCBway6A_uw")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(loginRequest)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LoginResponse result = mapper.readValue(response.body(), LoginResponse.class);
            return result.getData().sessionToken;
        } catch (IOException | InterruptedException e) {
            log.error("Error getting City Data", e);
            return null;
        }

    }

    public ArrayList<City> getCities() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/city"))
                .GET()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + sessionToken)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            AllCityResponse result = mapper.readValue(response.body(), AllCityResponse.class);
            return result.getData();
        } catch (IOException | InterruptedException e) {
            log.error("Error getting City Data", e);
            return null;
        }
    }

    public ArrayList<State> getStates() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/state"))
                .GET()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + sessionToken)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            AllStateResponse result = mapper.readValue(response.body(), AllStateResponse.class);
            return result.getData();
        } catch (IOException | InterruptedException e) {
            log.error("Error getting State Data", e);
            return null;
        }
    }

}
