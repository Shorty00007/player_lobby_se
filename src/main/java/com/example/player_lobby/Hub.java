package com.example.player_lobby;

public class Hub {
    public ServerConnection chooseGame(int gameId){
        ApiService service = new ApiService();
        ServerConnection servercon = new ServerConnection(service);
        //servercon.createGameLobby(gameId, System.nanoTime());
        return servercon;
    }
}
