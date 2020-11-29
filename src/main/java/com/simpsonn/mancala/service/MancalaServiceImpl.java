package com.simpsonn.mancala.service;

import com.simpsonn.mancala.controller.GameController;
import com.simpsonn.mancala.model.components.BoardComponent;
import com.simpsonn.mancala.model.components.Game;
import com.simpsonn.mancala.model.response.CreateGameResponse;
import com.simpsonn.mancala.model.response.MakeMoveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * this implementation is responsible for creating new games and sending move
 * requests to existing games and then sending the responses back for these requests
 * illegal moves will be thrown up the chain as illegal argument exceptions and converted
 * into an appropriate http response by the error controller
 */
@Service
public class MancalaServiceImpl implements MancalaService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MancalaService.class);
	
	private static final int MAP_INDEX_OFFSET = 1;
	
	@Value("${game.root.address}")
	private String gameRootAddress;
	
	final
	GameController gameController;
		
	private final List<Game> games = new ArrayList<>();

	public MancalaServiceImpl(GameController gameController) {

		this.gameController = gameController;
	}

	/**
	 * creates a new game of mancala and returns the details
	 */
	@Override
	public ResponseEntity<CreateGameResponse> createNewGame() {

		Game game = new Game();
		games.add(game);
		LOG.info("successfully created game id [{}]", game.getGameId().toString());
		return new ResponseEntity<>(getNewGameCreatedResponse(game),HttpStatus.CREATED);
	}

	/**
	 * attempts to make a move and returns the board status afterwards
	 */
	@Override
	public ResponseEntity<MakeMoveResponse> makeMove(UUID gameId, int pitId) {
		
		Game game = getGameById(gameId);
		if (game == null) {
			LOG.info("player tried to make a move for game id [{}] which does not exist", gameId.toString());
			throw new IllegalArgumentException("game id " + gameId.toString() + " not found");
		}
		gameController.makeMove(game, pitId);
		MakeMoveResponse response = new MakeMoveResponse(
				gameId.toString(), getGameUri(game), game.getGameState());
		response.setStatus(getBoardAsMap(gameId));
		LOG.info("successfully handled move for game id [{}] with pit id [{}]", gameId.toString(), pitId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// find game by UUID
	@Override
	public Game getGameById(UUID gameId) {
		
		return games.stream()
			.filter(game -> game.getGameId().equals(gameId))
			.findFirst()
			.orElse(null);
	}

	// wrap the create game up into a response body
	private CreateGameResponse getNewGameCreatedResponse(Game game) {
		
		return new CreateGameResponse(game.getGameId(), getGameUri(game));
	}

	// get the game uri as a string
	private String getGameUri(Game game) {
		
		return gameRootAddress +
				game.getGameId().toString();
	}
	
	// return the game board for this game as a map
	private Map<String,String> getBoardAsMap(UUID gameId) {
		
		Game game = getGameById(gameId);
		if(game !=null) {
			return generateBoardMap(game.getBoard().getBoardComponents());
		}
		return null;
	}
	
	// convert the board to a map, adding 1 to the indexes as they are internally zero based 
	private Map<String,String> generateBoardMap(List<BoardComponent> components) {
		
		Map<String,String> boardMap = new LinkedHashMap<>(); 
		for(BoardComponent component :  components) {			
			boardMap.put(String.valueOf((components.indexOf(component) + MAP_INDEX_OFFSET)), 
					String.valueOf(component.countStones()));
		}
		return boardMap;
	}
	
}
