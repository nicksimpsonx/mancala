package com.simpsonn.mancala.model.components;

/**
 * represents the current status of a game 
 */
public enum GameState {

	PLAYER_1_TURN("Player one turn"),
	PLAYER_2_TURN("Player two turn"),
	PLAYER_1_WIN("Player one victory"),
	PLAYER_2_WIN("Player two victory"),
	DRAW("Game resulted in a draw");
	
	private final String description;
	
	GameState(String description) {
		
		this.description = description;
	}

	/**
	 * get descriptive text for this game status
	 * @return the game state description
	 */
	public String getDescription() {
		
		return description; 
	}
	
}
