package com.simpsonn.mancala.model.player;

/**
 * there are always two players in a game of Mancala, this Enum identifies them
 * and provides a user friendly name too
 */
public enum PlayerId {

	PLAYER_1("Player One"),
	PLAYER_2("Player Two");
	
	private final String displayName;
	
	PlayerId(String displayName) {
		
		this.displayName = displayName;
	}
	
	/**
	 * get the display Id rather than the Enum Id for this, should be used in
	 * responses as it looks better
	 * 
	 * @return the player ID display name
	 */
	public String getDisplayId() {
		
		return displayName;
	}
	
}
