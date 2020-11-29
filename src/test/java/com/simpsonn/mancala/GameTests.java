package com.simpsonn.mancala;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.simpsonn.mancala.model.components.Game;
import com.simpsonn.mancala.model.components.GameState;
import com.simpsonn.mancala.model.player.PlayerId;

/**
 * Unit tests for Games 
 */
@SpringBootTest
public class GameTests {

	Game game;
	
	@BeforeEach
	void setupTests() {
		game = new Game();
	}	
	
	/*
	 * Given: we have a correctly instantiated game
	 * When: we change the game state 
	 * Then: it should be set correctly
	 */	
	@Test
	public void gameStateCanBeSet() {
		
		assertEquals(GameState.PLAYER_1_TURN, game.getGameState());
		game.setGameState(GameState.PLAYER_2_WIN);
		assertEquals(GameState.PLAYER_2_WIN, game.getGameState());
	}
	
	/*
	 * Given: we have a correctly instantiated game
	 * When: we get the game state 
	 * Then: it should be player 1's turn
	 */	
	@Test
	public void initialStateShouldBePlayer1Turn() {
		
		assertEquals(GameState.PLAYER_1_TURN, game.getGameState());
	}

	/*
	 * Given: we have a correctly instantiated game
	 * When: we get the players and the board 
	 * Then: they should all be correctly set and not null
	 */	
	@Test
	public void newGameHasPlayersAndBoard() {

		assertEquals(PlayerId.PLAYER_1, game.getPlayer1().getPlayerId());
		assertEquals(PlayerId.PLAYER_2, game.getPlayer2().getPlayerId());
		assertNotNull(game.getBoard());
	}	
	
	/*
	 * Given: we have a correctly instantiated game
	 * When: we try to generate a UUID using the games ID toString method 
	 * Then: it should convert without throwing an exception
	 */
	@Test
	public void newGameShouldHaveValidUniqueId() {
		
		assertDoesNotThrow(
				()-> UUID.fromString(game.getGameId().toString()));
	}
	
}
