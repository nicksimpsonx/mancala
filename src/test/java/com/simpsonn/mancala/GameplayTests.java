package com.simpsonn.mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.simpsonn.mancala.controller.GameController;
import com.simpsonn.mancala.model.components.Game;
import com.simpsonn.mancala.model.components.GameState;
import com.simpsonn.mancala.model.components.Pit;
import com.simpsonn.mancala.model.components.Stone;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Gameplay tests. These integration tests check that the game rules are working properly
 */
@SpringBootTest
public class GameplayTests {
	
	@InjectMocks
	GameController controller;
	
	Game game;
	
	@BeforeEach
	void setupTests() {
		game = new Game();
	}
	
	/*
	 * Given: player 1 has 6 stones in pit 1 and it is player 1's turn
	 * When: pit one is chosen as the start of the move
	 * Then: the last stone falls in player 1 kalah and player 1 gets another turn
	 */		 
	@Test
	public void testLastStoneLandingInKalahResultsInExtraTurnPlayer1() {
		
		controller.makeMove(game, 1);
		assertEquals(GameState.PLAYER_1_TURN, game.getGameState());
	}
	
	/*
	 * Given: player 2 has 6 stones in pit 8 and it is player 2's turn
	 * When: pit 8 is chosen as the start of the move
	 * Then: the last stone falls in player 2 kalah and player 2 gets another turn
	 */			
	@Test
	public void testLastStoneLandingInKalahResultsInExtraTurnPlayer2() {
		
		game.setGameState(GameState.PLAYER_2_TURN);
		controller.makeMove(game, 8);
		assertEquals(GameState.PLAYER_2_TURN, game.getGameState());
	}
	
	/*
	 * Given: it is player 1's move
	 * When: pit 8 is chosen to make a move (an opponent pit)
	 * Then: this results in an illegal argument exception
	 */	
	@Test
	public void playerCantStartTurnAtOpponentPit() {
		
		assertThrows(IllegalArgumentException.class, () -> controller.makeMove(game, 8));
	}
	
	/*
	 * Given: it is player 2's move
	 * When: pit 14 is chosen to make a move (player 2 kalah)
	 * Then: this results in an illegalargument exception
	 */	
	@Test
	public void playerCantStartTurnAtKalah() {
		
		assertThrows(IllegalArgumentException.class, () -> controller.makeMove(game, 14));
	}
	
	/*
	 * Given: it is player 2's move
	 * When: pit 13 is chosen to make a move
	 * Then: the stones are picked up and sown properly and it wraps around to the start
	 */		
	@Test
	public void stonesShouldSowProperlyAndWrapAround() {
		
		game.setGameState(GameState.PLAYER_2_TURN);
		controller.makeMove(game, 13);
		assertEquals(game.getBoard().getBoardComponents().get(12).countStones(), 0);
		assertEquals(game.getBoard().getBoardComponents().get(13).countStones(), 1);
		assertEquals(game.getBoard().getBoardComponents().get(0).countStones(), 7);
		assertEquals(game.getBoard().getBoardComponents().get(1).countStones(), 7);
	}

	/*
	 * Given: it is player 1's move
	 * When: stone lands in empty pit
	 * Then: that stone plus the stones from the opposite pit are captured and properly stored in player 1's kalah
	 */		
	@Test
	public void landingInOwnEmptyPitCapturesStonesProperly() {
		
		// Empty pit 6
		Pit pit = (Pit) game.getBoard().getBoardComponents().get(5);
		pit.getStones();
		
		// Make sure there is one stone in pit 5
		pit = (Pit) game.getBoard().getBoardComponents().get(4);
		pit.getStones();
		pit.addStone(new Stone());
		
		// Start the move at pit 5
		controller.makeMove(game, 5);
		
		// If this worked then there should now be 7 stones in player 1 kalah
		assertEquals(game.getBoard().getBoardComponents().get(6).countStones(), 7);
		
		// And the opposite pit to pit 5 should be empty
		assertEquals(game.getBoard().getBoardComponents().get(7).countStones(), 0);
	}

	/*
	 * Given: it is player 1's move and player 1 has 1 stone in pit 6 and no other stones
	 * When: turn is started at pit 6
	 * Then: the game is finalised, player 2 ends up with 36 stones in their kalah and the game is won by player 2
	 */
	@Test
	public void gameFinalisationWorksProperly() {
		
		// empty all player 1's pits
		for(int i=0; i< 6; i++) {
			Pit pit = (Pit) game.getBoard().getBoardComponents().get(i);
			pit.getStones();		
		}
		
		// put one stone in pit 6
		Pit pit = (Pit) game.getBoard().getBoardComponents().get(5);
		pit.addStone(new Stone());
		
		// start the turn at pit 6
		controller.makeMove(game, 6);
		
		// game should now be a player 2 win
		assertEquals(GameState.PLAYER_2_WIN, game.getGameState());
		System.out.println(""+game.getBoard().getBoardComponents().get(13).countStones());
		
		// and player 2 should have 36 stones in their kalah
		assertEquals(game.getBoard().getBoardComponents().get(13).countStones(), 36);
	}
	
}
