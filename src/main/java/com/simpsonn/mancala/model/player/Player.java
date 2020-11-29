package com.simpsonn.mancala.model.player;

/**
 * things which players must be able to do
 */
public interface Player {

	PlayerId getPlayerId();
	Hand getHand();

}
