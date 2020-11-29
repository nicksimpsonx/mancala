package com.simpsonn.mancala.model.components;

import java.util.ArrayList;
import java.util.List;

import com.simpsonn.mancala.model.player.Player;

/**
 * represents a Kalah on the game board, each player has 1 of these. Kalahs can
 * hold stones or be empty. We can't remove stones from Kalahs. We can place
 * stones in one by one or as collections and as stones need to be stored in
 * Kalahs to be counted for scores, a kalah can also return the quantity  of
 * stones it contains.
 */
public class Kalah extends BoardComponent {

	/**
	 * create a Kalah for the given player
	 * @param player - the player who owns this pit
	 */
	public Kalah(Player player) {
		
		setup(player);
	}	

	/**
	 * add the given list of stones to this kalah
	 * @param stonesToAdd a list of stones to add
	 */
	public void addStones(List<Stone> stonesToAdd) {
		
		stones.addAll(stonesToAdd);
	}	
	
	// Sets the owner id of this pit and initialises to be empty of stones
	private void setup(Player player) {
		
		ownerId = player.getPlayerId();
		stones = new ArrayList<>();
	}	
	
}
