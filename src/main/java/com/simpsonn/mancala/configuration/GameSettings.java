package com.simpsonn.mancala.configuration;

/**
 * global settings for the game - I used this for dev testing as it made it
 * easier to reproduce certain scenarios than when there are 6 stones
 */
public class GameSettings {

	// if this was a required feature it could easily be a request parameter on
	// create game end point instead 
	public static final int INITIAL_STONES = 6;
	
}
