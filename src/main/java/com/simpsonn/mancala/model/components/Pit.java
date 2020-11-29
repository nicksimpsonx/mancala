package com.simpsonn.mancala.model.components;

import static com.simpsonn.mancala.configuration.GameSettings.INITIAL_STONES;

import java.util.ArrayList;
import java.util.List;

import com.simpsonn.mancala.model.player.Player;

/**
 * represents a pit on the game board, each player has 6 of these pits. Pits can
 * hold stones or be empty. When we get stones from a pit they must be picked up
 * all at once but when we place stones in pits they are placed one at a time
 */
public class Pit extends BoardComponent {
	
	/**
	 * create a pit with the correct amount of initial stones inside it
	 * @param player - the player who owns this pit
	 */
	public Pit(Player player) {
		
		setup(player);
	}

	/**
	 * this will get all the stones from this pit and return them as a list if there are any
	 * doing this will actually remove all the stones that this pit contains
	 * 
	 * @return All the stones in this pit or null if there are no stones
	 */
	public List<Stone> getStones() {
		
		if (!stones.isEmpty()) {

			List<Stone> pitStones = new ArrayList<>(stones);
			stones.clear();
			return pitStones;
		}
		return null;
	}
	
	/**
	 * this checks if the pit is empty
	 * 
	 * @return true if the pit contains no stones, otherwise false
	 */
	public boolean isPitEmpty() {
		
		return (stones.isEmpty());
	}
	
	// sets the owner id of this pit and puts the correct amount of starting stones into it
	private void setup(Player player) {
		
		ownerId = player.getPlayerId();
		stones = new ArrayList<>();
		for (int i=0; i < INITIAL_STONES; i++) {
			stones.add(new Stone());
		}
	}
}
