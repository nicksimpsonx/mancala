package com.simpsonn.mancala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.simpsonn.mancala.model.components.Board;
import com.simpsonn.mancala.model.components.Kalah;
import com.simpsonn.mancala.model.components.Pit;
import com.simpsonn.mancala.model.player.HumanPlayer;
import com.simpsonn.mancala.model.player.Player;
import com.simpsonn.mancala.model.player.PlayerId;

/**
 * Unit tests for Boards 
 */
@SpringBootTest
public class BoardTests {
	
	Player player1;
	Player player2;
	Board board;

	@BeforeEach
	void setupTests() {
		player1 = new HumanPlayer(PlayerId.PLAYER_1);
		player2 = new HumanPlayer(PlayerId.PLAYER_2);
		board = new Board(player1, player2);
	}

	/*
	 * Given: We have a freshly created board
	 * When: We get all the board components  
	 * Then: It should contain 14 components in total
	 */
	@Test
	public void boardsMustHave14Components() {
		
		assertEquals(14, board.getBoardComponents().size());
	}

	/*
	 * Given: We have a freshly created board
	 * When: We get all the board components  
	 * Then: The components should include exactly 2 kalahs
	 */
	@Test
	public void boardMustContain2Kalahs() {		
		long kalahCount = board.getBoardComponents().stream()
			.filter(component -> component instanceof Kalah)
			.count();
		assertEquals(2, kalahCount);
	}

	/*
	 * Given: We have a freshly created board
	 * When: We get all the board components  
	 * Then: The components should include exactly 12 pits
	 */	
	@Test
	public void boardMustContain12Pits() {		
		long kalahCount = board.getBoardComponents().stream()
			.filter(component -> component instanceof Pit)
			.count();
		assertEquals(12, kalahCount);
	}

	/*
	 * Given: We have a freshly created board
	 * When: We get all the board components and accounting for the list being zero base index
	 * Then: Kalahs should occupy the 7th and 14th index of the list 
	 */	
	@Test
	public void kalahsAreInTheCorrectPositions() {
		
		assertTrue(board.getBoardComponents().get(6) instanceof Kalah);
		assertTrue(board.getBoardComponents().get(13) instanceof Kalah);
	}
	
}
