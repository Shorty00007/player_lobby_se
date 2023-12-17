package com.example.player_lobby;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class dummyGameInstance implements GameInstance{
    private ArrayList<Integer> xp;
    private ArrayList<Integer> points;
    private ArrayList<String> players;
    private PlayerLobby lobby;
    private String uuid;
    dummyGameInstance(ArrayList<String> players, PlayerLobby lobby, String uuid){
        this.players = players;
        this.lobby = lobby;
        this.xp = new ArrayList<>();
        this.points = new ArrayList<>();
        this.uuid = uuid;
    }
    public void finishGame() {
        updateGameState();
        System.out.println("Game finished");
        lobby.addXP(xp);
        lobby.addPoints(points);

    }
    public void startGame(){
        System.out.println("Game started");
    }
    public void updateGameState(){
        for(int i = 0; i < players.size(); i++){
            int randomXP = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1);
            xp.add(randomXP);

            int randomPoints = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1);
            points.add(randomPoints);
        }
    }

    public ArrayList<Integer> getPoints(){
        return points;
    }
    public ArrayList<Integer> getXp(){
        return xp;
    }
    public ArrayList<String> getPlayers(){
        return players;
    }
    public String getUuid(){ return uuid; }
}
