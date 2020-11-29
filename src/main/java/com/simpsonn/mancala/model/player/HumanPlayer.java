package com.simpsonn.mancala.model.player;

/**
 * represents a human player in the game 
 */
public class HumanPlayer implements Player {

	private final PlayerId playerId;
	private final Hand playerHand;
	
	/**
	 * create a Human player
	 *
	 * @param playerId - player Id
	 */		
	public HumanPlayer(PlayerId playerId) {
		this.playerId = playerId;
		this.playerHand = new Hand(); 
	}

	/**
	 * get the player id of this player
	 * 
	 * @return the player id of this player which would be player 1 or player 2
	 */
	@Override
	public PlayerId getPlayerId() {

		return playerId;
	}

	/**
	 * get the hand of this player which has the capability of holding stones
	 * 
	 * @return the player hand for this player
	 */
	@Override
	public Hand getHand() {

		return playerHand;
	}

}
