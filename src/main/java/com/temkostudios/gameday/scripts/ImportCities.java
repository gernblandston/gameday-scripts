package com.temkostudios.gameday.scripts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temkostudios.gameday.model.Bowl;
import com.temkostudios.gameday.model.City;
import com.temkostudios.gameday.model.Game;
import com.temkostudios.gameday.model.State;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImportCities {

    public ArrayList<City> newCities;
    public ArrayList<City> existingCities;
    public HashMap<String, State> stateMap;
    String sessionToken;
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    public static void main(String args[]) {
        ImportCities cityImporter = new ImportCities();
        cityImporter.importCities();
        System.exit(0);
    }

    public void importCities() {
        int rCount = 0;
        sessionToken = login();
        stateMap = getStates();

        newCities = getNewCities();
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

    public ArrayList<City> getNewCities() {
        try {
            ArrayList<City> cityList = new ArrayList<>();
            List<SchoolData> schoolList = mapper.readValue(new File("schoolList.json"),  new TypeReference<List<SchoolData>>(){});
            schoolList.forEach(school -> {
                City city = new City();
                city.setName(school.getLocation().getCity());
                // YOU ARE HERE - NEED TO GET THE STATE FROM THE SERVICE AND USE THE IDS WHILE POPULATING THIS LIST
                city.setStateId(stateMap.get(school.getAbbreviation()).getId());
            });
            return cityList;
        } catch (IOException e) {
            log.error("Error getting City Data", e);
            return null;
        }
    }

    public HashMap<String, State> getStates() {
        HashMap<String, State> newStateMap = new HashMap<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/state"))
                .GET()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + sessionToken)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            AllStateResponse result = mapper.readValue(response.body(), AllStateResponse.class);
            result.getData().forEach(city -> newStateMap.put(city.getAbbrv(), city));
            return newStateMap;
        } catch (IOException | InterruptedException e) {
            log.error("Error getting State Data", e);
            return null;
        }
    }

}
