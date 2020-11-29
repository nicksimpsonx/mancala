package com.simpsonn.mancala.controller;

import com.simpsonn.mancala.model.response.CreateGameResponse;
import com.simpsonn.mancala.model.response.MakeMoveResponse;
import com.simpsonn.mancala.service.MancalaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * an api which allows games of mancala to be created and played
 * 
 * @implNote the specification says that we can create new games using the
 *           <b>/games</b> endpoint. This means that it will be possible to have
 *           many games going on at once, and potentially creating enough games
 *           to cause the application to run out of memory. Also the
 *           specification for this version lacks a mechanism for cleaning up
 *           finished or abandoned games which could potentially lead the the
 *           same issue.<br>
 *           the application should have a mechanism to limit the amount of
 *           games in progress and also a mechanism to get rid of finished and
 *           abandoned games. It would also be useful to be able to retrieve a list
 *           of games in progress, these have not been implemented here due to them
 *           not being in spec and time constraints
 * 
 * @version 0.0.1
 * @author Nick Simpson
 * @since 1.8
 */
@Api(value = "Interact with the game using this rest controller")
@RestController
@Validated
public class GameRestController {

	private static final Logger LOG = LoggerFactory.getLogger(GameRestController.class);
	
	final
	MancalaService mancalaService;

	public GameRestController(MancalaService mancalaService) {
		this.mancalaService = mancalaService;
	}

	/**
	 * create a new game of mancala
	 * 
	 * @return create new game response - the id and url for the game just created
	 */
	@ApiOperation(value = "Create a new game")	
	@PostMapping(path = "/games")
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Success - Game created")})
	public ResponseEntity<CreateGameResponse> createNewGame() {
		
		LOG.info("received create new game request");
		return mancalaService.createNewGame();
	}

	/**
	 * make a move in a game by providing the GUID of the game and the pit number 
	 * 
	 * @param gameId - the GUID of the game
	 * @param pitId - the pit number to use in this move
	 * @return make move response - text representation of the current board and who's turn it is
	 */
	@ApiOperation(value = "Make a move")
	@PutMapping(path = "/games/{gameId}/pits/{pitId}")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Error in parameter(s)")})
	public ResponseEntity<MakeMoveResponse> makeAMove(
			@Pattern (regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "game id must be a valid GUID") 
			@PathVariable ("gameId") String gameId, 
			@Digits (fraction = 0, integer = 2) @Min(1) @Max(14) 
			@PathVariable ("pitId") int pitId) {		

		LOG.info("received make move request with pit number [{}]", pitId);
		UUID gameIdGuid = getGuidFromString(gameId);
		return mancalaService.makeMove(gameIdGuid, pitId);
	}

	/*
	 *  we accept the gameId as a string so we can use a pattern to validate it but
	 *  it must be converted into a UUID so we can use it
	 */
	private UUID getGuidFromString(String stringToConvert) {
		
		return UUID.fromString(stringToConvert);
	}

}
