package com.example.player_lobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate = AppConfig.restTemplate();

    public String getCsrfCookie() {
        String url = "https://se-test-server.it-core.fun/api/csrf-cookie";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Referer", "https://se-test-server.it-core.fun");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getHeaders().get("Set-Cookie").stream()
                .filter(cookie -> cookie.startsWith("XSRF-TOKEN"))  // Replace with the actual CSRF cookie name
                .findFirst()
                .orElse(null); // Return null if the cookie is not found
    }

    public String getServerSessionCookie(ResponseEntity<String> response) {
        return response.getHeaders().get("Set-Cookie").stream()
                .filter(cookie -> cookie.startsWith("server_session"))  // Replace with the actual CSRF cookie name
                .findFirst()
                .orElse(null); // Return null if the cookie is not found
    }

    public ResponseEntity<String> apiRequestAllUsers(String csrfCookie, String serverSessionCookie) {
        String apiUrl = "https://se-test-server.it-core.fun/api/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Referer", "https://se-test-server.it-core.fun");
        headers.add("Cookie", csrfCookie);
        headers.add("Cookie", serverSessionCookie);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }
    public ResponseEntity<String> loginAsPlayerLobby(String csrfCookie){
        String apiUrl = "https://se-test-server.it-core.fun/api/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Referer", "https://se-test-server.it-core.fun");
        headers.add("XSRF-TOKEN", csrfCookie);
        String jsonBody = """
                      {
                        "name": "playerLobby",
                        "email": "player.lobby@gmail.com",
                        "password": "frytkiBatatki1",
                        "password_confirmation": "frytkiBatatki1"
                      }""";

        // Create the HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        // Return the response
        return response;

    }
    public ResponseEntity<String> createGame(String csrfCookie,  String serverSessionCookie){
        String apiUrl = "https://se-test-server.it-core.fun/api/games";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Referer", "https://se-test-server.it-core.fun");
        headers.add("Cookie", csrfCookie);
        headers.add("Cookie", serverSessionCookie);
        String jsonBody = "{\"limit\": 4, \"level\": 3}";


        // Create the HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        // Return the response
        return response;

    }
    public ResponseEntity<String> amILoggedInPls(String csrfCookie, String serverSessionCookie) {
        String apiUrl = "https://se-test-server.it-core.fun/api/user";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Referer", "https://se-test-server.it-core.fun");
        headers.add("Cookie", csrfCookie);
        headers.add("Cookie", serverSessionCookie);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }
}
