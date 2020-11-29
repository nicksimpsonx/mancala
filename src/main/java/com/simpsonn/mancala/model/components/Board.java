package com.simpsonn.mancala.model.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simpsonn.mancala.model.player.Player;

/**
 * a representation of a board in a game of mancala, it contains all of the board
 * components (pits and kalahs) When the board is instantiated, the components
 * are generated and placed on it in the correct order.
 * 
 * the board has two sides, each side consists of six pits and one kalah, the
 * kalah is offset to the right hand side of the pits so that the players pits
 * are directly opposite each other.
 * 
 */
public class Board {

	// for our purposes we deem the first index in this collection to be the
	// leftmost position on the board and it will flow to the right as the index
	// increases
	private List<BoardComponent> boardComponents = Collections.emptyList();
	private static final int NUMBER_OF_PITS = 6;  

	/**
	 * create a new game board and assign the components to the correct owners
	 * 
	 * @param player1 - first player
	 * @param player2 - second player
	 */	
	public Board(Player player1, Player player2) {
		
		setup(player1, player2);
	}

	// initialise this board
	private void setup(Player player1, Player player2) {
		
		boardComponents = new ArrayList<>();
		addComponentsForPlayer(player1);
		addComponentsForPlayer(player2);
	}

	// add the pits and the Kalah from left to right to the board for this player
	private void addComponentsForPlayer(Player player) {
		
		for (int i=0; i < NUMBER_OF_PITS; i++) {
			
			boardComponents.add(new Pit(player));
		}
		boardComponents.add(new Kalah(player));
	}
	
	/**
	 * get all the components from this board
	 * @return the complete list of board components in this board
	 */
	public List<BoardComponent> getBoardComponents() {
		
		return boardComponents;
	}
	
}
