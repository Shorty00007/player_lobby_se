package com.example.player_lobby;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerConnection {
    private ArrayList<PlayerLobby> playerLobbies;
    private ApiService service;
    private ObjectMapper objectMapper;

    public ServerConnection(ApiService service) {
        this.playerLobbies = new ArrayList<>();
        this.service = service;
    }

    public PlayerLobby createGameLobby(int gameId) {
        PlayerLobby lobby = new PlayerLobby(gameId, System.nanoTime(), this);
        playerLobbies.add(lobby);
        return lobby;
    }

    public void checkKickPlayer(int lobbyId, int playerId) {
        // Implementation...
    }

    public List<PlayerLobby> getPlayerLobbies() {
        return new ArrayList<>(playerLobbies);
    }

    public boolean dummyCheck(String userId) {
        return true; // Sample implementation
    }

    public boolean checkUuidExists(String uuidToCheck) {
        try {
            ResponseEntity<String> response = service.apiRequestAllUsers(service.getCsrfCookie(), service.getServerSessionCookie(service.loginAsPlayerLobby(service.getCsrfCookie())));
            String responseBody = response.getBody();
            ObjectMapper mapper = getObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> users = (List<Map<String, Object>>) responseMap.get("data");

            for (Map<String, Object> user : users) {
                if (uuidToCheck.equals(user.get("uuid"))) {
                    return true; // UUID found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // UUID not found
    }

    private ObjectMapper getObjectMapper() {
        if (this.objectMapper == null) {
            this.objectMapper = new ObjectMapper();
        }
        return this.objectMapper;
    }
    public String getUuidFromCreateGameResponse() {
        try {
            ResponseEntity<String> response = service.createGame(service.getCsrfCookie(), service.getServerSessionCookie(service.loginAsPlayerLobby(service.getCsrfCookie())));
            String responseBody = response.getBody();
            //System.out.println("Response Body: " + responseBody); // Debug line

            Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            if (responseMap != null) {
                Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
                if (data != null && data.containsKey("uuid")) {
                    return (String) data.get("uuid");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // or handle the error appropriately
    }


}
