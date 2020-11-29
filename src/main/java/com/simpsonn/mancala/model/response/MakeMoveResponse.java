package com.simpsonn.mancala.model.response;

import java.util.Map;

import com.simpsonn.mancala.model.components.GameState;

/**
 * represents the details which are returned in the response body when a player
 * makes a move
 */
public class MakeMoveResponse {
	
	public MakeMoveResponse(String id, String url, GameState gameState) {
		this.id = id;
		this.url = url;
		this.gameState = gameState;
	}
	
	private final String id;
	private final String url;
	private Map<String,String> status;
	private final GameState gameState;
	
	/**
	 * get the game id
	 * @return the game id for this game
	 */
	public String getId() {
		
		return id;
	}
	
	/**
	 * get the game uri
	 * @return the uri for this game
	 */
	public String getUrl() {
		
		return url;
	}
	
	/**
	 * get the game state
	 * @return the game state
	 */
	public String getGameState() {

		return gameState.getDescription();
	}
	
	/**
	 * get a representation of the game board
	 * @return the game board
	 */
	public Map<String, String> getStatus() {
		
		return status;
	}
	
	/**
	 * set the status map which should show the pit numbers and number of stones in each
	 * @param status game board map to use
	 */
	public void setStatus(Map<String, String> status) {
		
		this.status = status;
	}	
	
}
