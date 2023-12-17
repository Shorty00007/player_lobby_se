package com.example.player_lobby;

import org.apache.catalina.Server;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby {
    private ServerConnection servercon;
    private int gameId;
    private long lobbyId;
    private ArrayList<Integer> points;
    private ArrayList<Integer> xp;

    private ArrayList<String> listOfPlayers;

    public PlayerLobby(int gameId, long lobbyId, ServerConnection servercon) {
        this.gameId = gameId;
        this.lobbyId = lobbyId;
        this.servercon = servercon;
        this.listOfPlayers = new ArrayList<>();
        this.points = new ArrayList<>();
        this.xp = new ArrayList<>();
    }

    public void addPlayer(String playerId) {
        if (servercon.checkUuidExists(playerId) == true) {
            listOfPlayers.add(playerId); //add sending to server
            points.add(0);
            xp.add(0);
        }
    }

    public void kickPlayer(String playerId) {
            for (int i = 0; i < getListOfPlayers().size(); i++) {
                if (playerId.equals(getListOfPlayers().get(i))) {
                    listOfPlayers.remove(i);
                    points.remove(i);
                    xp.remove(i);
                    break;
            }
        }
    }

    public dummyGameInstance startGame() {

        dummyGameInstance game = new dummyGameInstance(listOfPlayers, this, servercon.getUuidFromCreateGameResponse()); //fixx
        return game;
    }


    public int getGameId() {
        return gameId;
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public List<String> getListOfPlayers() {
        return new ArrayList<>(listOfPlayers);
    }
    public void addXP (ArrayList<Integer> xp){
        for(int i=0;i<xp.size();i++){
            this.xp.set(i, this.xp.get(i) + xp.get(i));
        }
    }
    public void addPoints (ArrayList<Integer> points){
        for(int i=0;i<xp.size();i++){
            this.points.set(i, this.points.get(i) + points.get(i));
        }
    }
    public List<Integer> getPoints() {
        return points;
    }
    public List<Integer> getXp() {
        return xp;
    }
}

