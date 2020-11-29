package com.simpsonn.mancala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.simpsonn.mancala.model.components.Stone;
import com.simpsonn.mancala.model.player.HumanPlayer;
import com.simpsonn.mancala.model.player.Player;
import com.simpsonn.mancala.model.player.PlayerId;

/**
 * Unit tests for Player and Hand 
 */
@SpringBootTest
public class PlayerAndHandTests {

	Player playerOne;
	Player playerTwo;	
	
	@BeforeEach
	void setupPlayerTests() {
		
		playerOne = new HumanPlayer(PlayerId.PLAYER_1);
		playerTwo = new HumanPlayer(PlayerId.PLAYER_2);
	}

	/*
	 * Given: We have two correctly instantiated players
	 * When: We get the player Ids
	 * Then: The correct Id is returned
	 */
	@Test
	public void playersShouldReturnCorrectIds() {
		
		assertEquals(PlayerId.PLAYER_1, playerOne.getPlayerId());
		assertEquals(PlayerId.PLAYER_2, playerTwo.getPlayerId());
	}

	/*
	 * Given: We have two correctly instantiated players
	 * When: We get the player display Ids
	 * Then: The correct display Id is returned
	 */
	@Test
	public void playersShouldReturnCorrectDisplayIds() {
		assertEquals("Player One", playerOne.getPlayerId().getDisplayId());
		assertEquals("Player Two", playerTwo.getPlayerId().getDisplayId());
	}
	
	/*
	 * Given: We have a correctly instantiated player
	 * When: We get the players hand
	 * Then: The hand is not null
	 */
	@Test
	public void playerHandShouldNotBeNull() {
		
		assertNotNull(playerOne.getHand());
	}

	/*
	 * Given: We have a correctly instantiated player 
	 * When: The player has empty hand and we try to get all stones or one stone
	 * Then: The hand should be empty and return null
	 */
	@Test
	public void emptyPlayerHandShouldReturnNullWhenWeGetStones() {
		
		assertNull(playerOne.getHand().getAllStonesInHand());
		assertNull(playerTwo.getHand().getOneStoneFromHand());
	}

	/*
	 * Given: We have a correctly instantiated player with an empty hand
	 * When: We add stones to the hand 
	 * Then: The hand should contain the correct number of stones
	 */
	@Test
	public void addingStonesToHandsWorksProperly() {

		List<Stone> oneStone = new ArrayList<>();
		oneStone.add(new Stone());
		
		List<Stone> twoStones = new ArrayList<>();
		twoStones.add(new Stone());
		twoStones.add(new Stone());			
		
		playerOne.getHand().addStonesToHand(twoStones);
		playerOne.getHand().addStonesToHand(oneStone);		
		assertEquals(oneStone.size() + twoStones.size(), playerOne.getHand().getAllStonesInHand().size());
	}

	/*
	 * Given: We have a correctly instantiated player with some stones in hand
	 * When: We remove one and then more of the stones
	 * Then: The hand should always contain the correct number of stones and we should get back the right amount of stones
	 */	
	@Test
	public void removingStonesFromHandsWorksProperly() {

		List<Stone> fiveStones = new ArrayList<>(Arrays.asList(
				new Stone(), new Stone(), new Stone(), new Stone(), new Stone()
		));
		
		playerOne.getHand().addStonesToHand(fiveStones); // Add all five
		
		assertNotNull(playerOne.getHand().getOneStoneFromHand()); // Take one out should not return null		
		
		List<Stone> retrievedStones = playerOne.getHand().getAllStonesInHand(); // When we get what's left
		assertEquals(4, retrievedStones.size());  // There should have been 4

	}
	
	/*
	 * Given: We have a correctly instantiated player with one stone in hand
	 * When: We test to see if it is the last stone
	 * Then: The result should be true
	 */		
	@Test
	public void oneStoneLeftIsLastStoneShouldBeTrue() {
		
		playerOne.getHand().addStonesToHand(Collections.singletonList(new Stone()));
		assertTrue(playerOne.getHand().isLastStone());
	}

	/*
	 * Given: We have a correctly instantiated player with zero or more than one stones in hand
	 * When: We test to see if it is the last stone
	 * Then: The result should be false
	 */		
	@Test
	public void notOneStoneLeftLastStoneShouldBeFalse() {
		
		assertFalse(playerOne.getHand().isLastStone());
		playerOne.getHand().addStonesToHand(Arrays.asList(new Stone(), new Stone()));
		assertFalse(playerOne.getHand().isLastStone());
	}	
	
}
