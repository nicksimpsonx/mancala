package com.simpsonn.mancala.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.simpsonn.mancala.model.components.Game;
import com.simpsonn.mancala.model.response.CreateGameResponse;
import com.simpsonn.mancala.model.response.MakeMoveResponse;

/**
 * defines the methods mancala implementation service will expose.
 */
public interface MancalaService {

	ResponseEntity<CreateGameResponse> createNewGame();
	
	ResponseEntity<MakeMoveResponse> makeMove(UUID gameId, int pitId);

	Game getGameById(UUID gameId);
	
}
