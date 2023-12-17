package com.example.player_lobby;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@SpringBootTest
class PlayerLobbyApplicationTests {
	@Autowired
	private ApiService apiService;

	@Test
	void requestTest() {
		ApiService service = new ApiService();
		String cookie = service.getCsrfCookie();
		ResponseEntity<String> a = service.loginAsPlayerLobby(cookie);
		String server_session = service.getServerSessionCookie(a);
		service.apiRequestAllUsers(cookie, server_session);
	}
	@Test
	void listOfLobbiesTest() {
		ApiService service = new ApiService();
		Hub hub = new Hub();
		ServerConnection servercon = hub.chooseGame(1);
		PlayerLobby lobby = servercon.createGameLobby(1);
		PlayerLobby lobby2 = servercon.createGameLobby(2);
		PlayerLobby lobby3 = servercon.createGameLobby(3);
		assertEquals(servercon.getPlayerLobbies().size(), 3);

	}
	@Test
	void addingPlayersTest() {
		ApiService service = new ApiService();
		Hub hub = new Hub();
		ServerConnection servercon = hub.chooseGame(1);
		PlayerLobby lobby = servercon.createGameLobby(1);
		lobby.addPlayer("e32f1367-b460-4a69-80c2-c7b80b79b3e4");
		lobby.addPlayer("abb123ca-6fe7-400d-96bc-c551c9ae8df1");
		dummyGameInstance game = lobby.startGame();
		//System.out.println("Players: " + game.getPlayers() + "UUID: " + game.getUuid());
		//assertNotNull(game.getUuid()); currenty starting game for playerlobby user, which is not cool
		for(int i=0;i<lobby.getListOfPlayers().size();i++){
			assertTrue(lobby.getListOfPlayers().get(i) == game.getPlayers().get(i));
		}
		lobby.kickPlayer("e32f1367-b460-4a69-80c2-c7b80b79b3e4");
		dummyGameInstance game2 = lobby.startGame();
		for(int i=0;i<lobby.getListOfPlayers().size();i++){
			assertTrue(lobby.getListOfPlayers().get(i) == game.getPlayers().get(i));
		}
	}
	@Test
	void addingXpandPointsTest() {
		ApiService service = new ApiService();
		Hub hub = new Hub();
		//service.getCsrfCookie();
		//service.makeApiRequestWithCsrf(service.getCsrfCookie());
		ServerConnection servercon = hub.chooseGame(1);
		PlayerLobby lobby = servercon.createGameLobby(1);
		lobby.addPlayer("e32f1367-b460-4a69-80c2-c7b80b79b3e4");
		lobby.addPlayer("abb123ca-6fe7-400d-96bc-c551c9ae8df1");
		//System.out.println(service.apiRequestAllUsers((service.getCsrfCookie()), service.getServerSessionCookie(service.loginAsPlayerLobby(service.getCsrfCookie()))));

		// First game instance
		dummyGameInstance game1 = lobby.startGame();
		game1.startGame();
		game1.finishGame();

		// Second game instance
		dummyGameInstance game2 = lobby.startGame();
		game2.startGame();
		game2.finishGame();

		// Accumulate points from both games for each player
		ArrayList<Integer> expectedPoints = new ArrayList<>();
		for (int i = 0; i < lobby.getListOfPlayers().size(); i++) {
			int pointsFromGame1 = game1.getPoints().get(i);
			int pointsFromGame2 = game2.getPoints().get(i);
			expectedPoints.add(pointsFromGame1 + pointsFromGame2);
		}

		// Check if the lobby points match the accumulated points
		for (int i = 0; i < lobby.getListOfPlayers().size(); i++) {
			assertTrue(lobby.getPoints().get(i).equals(expectedPoints.get(i)),
					"Expected points do not match for player at index: " + i);
		}
		// Kick one player to check if points are handled properly
		lobby.kickPlayer("abb123ca-6fe7-400d-96bc-c551c9ae8df1");
		assertTrue(lobby.getListOfPlayers().size() == 1 && lobby.getPoints().size() == 1 && lobby.getXp().size() == 1);
		for (int i = 0; i < lobby.getListOfPlayers().size(); i++) {
			assertTrue(lobby.getPoints().get(i).equals(expectedPoints.get(i)),
					"Expected points do not match for player at index: " + i);
		}
	}

}
