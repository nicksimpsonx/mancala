package com.simpsonn.mancala.model.response;

import java.util.UUID;

/**
 * represents the details which are returned in the response body when a player
 * creates a new game
 */
public class CreateGameResponse {

	/**
	 * create a response body details object
	 * @param id the guid for the game
	 * @param uri the uri for the game
	 */
	public CreateGameResponse(UUID id, String uri) {
		
		this.id = id;
		this.uri = uri;
	}
	
	private final UUID id;
	private final String uri;

	/**
	 * get game UUID
	 * @return the unique id of the game
	 */
	public UUID getId() {
		
		return id;
	}

	/**
	 * get game uri
	 * @return the uri for this game
	 */	
	public String getUri() {
		
		return uri;
	}
	
}
