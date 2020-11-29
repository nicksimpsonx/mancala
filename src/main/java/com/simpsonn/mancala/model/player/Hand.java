package com.simpsonn.mancala.model.player;

import com.simpsonn.mancala.model.components.Stone;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the players hand which is used to hold stones. Stones can be added or taken away from this hand
 */
public class Hand {

	private static final int LAST_STONE_SIZE = 1;
	private final List<Stone> stones;

	public Hand() {
		
		stones = new ArrayList<>();
	}

	/**
	 * adds the given list of stones to the players hand
	 * @param stonesToAdd - The list of stones to be added to the stones in this players hand
	 */
	public void addStonesToHand (List<Stone> stonesToAdd) {
		
		stones.addAll(stonesToAdd);
	}

	/**
	 * this will get all the stones from the players hand and return them as a list if there are any
	 * doing this will actually remove all the stones that the player is holding
	 * 
	 * @return All the stones that the player is holding or null if there are no stones
	 */
	public List<Stone> getAllStonesInHand() {
		
		if(!stones.isEmpty()) {
			List<Stone> playerStones = new ArrayList<>(stones);
			stones.clear();
			return playerStones;
		}
		return null;
	}

	/**
	 * this will get one of the stones from the players hand if there are any stones
	 * and doing this will remove that stone from the players hand
	 * 
	 * @return the first stone from this hand or null if there are no stones
	 */
	public Stone getOneStoneFromHand() {
		
		if(!stones.isEmpty()) {
			return stones.remove(0);
		}
		return null;
	}
	
	/**
	 * this checks if the hand only contains one stone
	 * @return true contains one stone, otherwise false
	 */
	public boolean isLastStone() {
		
		return (stones.size() == LAST_STONE_SIZE);
	}

	/**
	 * check if the players hand is empty
	 * @return true if empty or false if not
	 */	
	public boolean isHandEmpty() {

		return stones.isEmpty();
	}

}
