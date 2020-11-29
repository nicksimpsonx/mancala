package com.simpsonn.mancala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.simpsonn.mancala.model.components.Kalah;
import com.simpsonn.mancala.model.components.Pit;
import com.simpsonn.mancala.model.components.Stone;
import com.simpsonn.mancala.model.player.HumanPlayer;
import com.simpsonn.mancala.model.player.Player;
import com.simpsonn.mancala.model.player.PlayerId;

/**
 * Unit tests for Pits and Kalahs 
 */
@SpringBootTest
public class BoardComponentTests {

	Player player;
	Pit pit;
	Kalah kalah;
	
	@BeforeEach
	void setupTests() {
		
		player = new HumanPlayer(PlayerId.PLAYER_1);
		pit = new Pit(player);
		kalah = new Kalah(player);
	}
	
	/*
	 * Given: A correctly instantiated pit owned by player 1
	 * When: Checking to see who the owner is
	 * Then: Player Id should be player 1
	 */
	@Test
	public void pitShouldReturnCorrectOwnerId() {

		assertEquals(PlayerId.PLAYER_1, pit.getOwnerId());
	}
	
	/*
	 * Given: A correctly instantiated pit
	 * When: Getting all the stones
	 * Then: Should result in a list of six stones
	 */	
	@Test
	public void pitShouldInitiallyContainSixStones() {

		assertEquals(pit.getStones().size(), 6);
	}

	/*
	 * Given: a pit initially contains six stones
	 * When: we test if it is empty before removing them and then test after we've removed them
	 * Then: the first test will be true, the second test will be false
	 */
	@Test
	public void checkIfPitEmptyReturnsCorrectValue() {
		
		assertFalse(pit.isPitEmpty());
		pit.getStones(); // (This removes all the stones)
		assertTrue(pit.isPitEmpty());
	}

	/*
	 * Given: a pit initially contains six stones
	 * When: we get the stones
	 * Then: we get six stones back and the pit is left empty
	 */  
	@Test
	public void removeAllStonesResultsInAListOfStonesAndAnEmptyPit() {
		
		List<Stone> stonesFromPit = pit.getStones();
		assertEquals(6,stonesFromPit.size());
		assertTrue(pit.isPitEmpty());		
	}

	/*
	 * Given: Our pit contains 6 stones
	 * When: We add a stone
	 * Then: When we get the stones from the pit again there are 7 stones
	 */
	@Test
	public void addingOneStoneAddsOneToThePit() {
		
		pit.addStone(new Stone());
		assertEquals(7, pit.getStones().size());
	}

	/*
	 * Given: We have a newly instantiated Kalah 
	 * When: We count the number of stones contained
	 * Then: The result is 0
	 */
	@Test
	public void kalahContainsNoStonesWhenInstantiated() {
		
		assertEquals(0, kalah.countStones());
	}

	/*
	 * Given: A correctly instantiated kalah owned by player 1
	 * When: Checking to see who the owner is
	 * Then: Player Id should be player 1
	 */
	@Test
	public void kalahShouldReturnCorrectOwnerId() {

		assertEquals(PlayerId.PLAYER_1, kalah.getOwnerId());
	}	
	
	/*
	 * Given: Our kalah contains 0 stones
	 * When: We add a stone
	 * Then: When we count the number of stones we get 1 
	 */
	@Test
	public void addingOneStoneAddsOneToTheKalah() {
		
		kalah.addStone(new Stone());
		assertEquals(1, kalah.countStones());
	}

	/*
	 * Given: Our kalah contains 0 stones
	 * When: We add five stones at once
	 * Then: When we count the number of stones we get 5 
	 */
	@Test
	public void addingFiveStonseAddsFiveToTheKalah() {

		List<Stone> fiveStones = new ArrayList<>(Arrays.asList(
				new Stone(), new Stone(), new Stone(), new Stone(), new Stone()
		));
		kalah.addStones(fiveStones);
		assertEquals(5, kalah.countStones());
	}
	
}
