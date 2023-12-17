package com.example.player_lobby;
import java.util.ArrayList;
import java.util.List;

public interface GameInstance {
    //public List <String> players = null;
    public void startGame();
    public void finishGame();
    public void updateGameState();
    public List<String> getPlayers();
    public List<Integer> getXp();
    public List<Integer> getPoints();
}
