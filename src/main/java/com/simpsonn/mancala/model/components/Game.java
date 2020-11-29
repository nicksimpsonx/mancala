package com.simpsonn.mancala.model.components;

import java.util.UUID;

import com.simpsonn.mancala.model.player.HumanPlayer;
import com.simpsonn.mancala.model.player.Player;
import com.simpsonn.mancala.model.player.PlayerId;

/**
 * a representation of the things inside a game of mancala, a game comprises of
 * a unique id, a game board and two players.
 * 
 * when a game is instantiated it is assigned a unique ID and the players are
 * generated. The board is also generated which in turn creates the things which
 * are part of the board (pits, kalahs).
 */
public class Game {

	private UUID gameId;
	private Player player1;
	private Player player2;	
	private Board board;
	private GameState gameState;

	/**
	 * Create all the parts needed for a game of mancala
	 */		
	public Game() {	
		
		setupGame();
	}

	// generate a GUID for the game, two players and a board and set the game state
	// to player 1 turn ready to make the first move
	private void setupGame() {
		
		gameId = UUID.randomUUID();
		player1 = new HumanPlayer(PlayerId.PLAYER_1);
		player2 = new HumanPlayer(PlayerId.PLAYER_2);
		board = new Board(player1, player2);
		gameState = GameState.PLAYER_1_TURN;
	}
	
	/**
	 * get the game id
	 * @return the Unique ID of this game
	 */
	public UUID getGameId() {
		
		return gameId;
	}

	/**
	 * get the game board to provide access to the components
	 * @return the game board
	 */	
	public Board getBoard() {	
		
		return board;
	}

	/**
	 * get player 1 to provide access to his behaviour and hand
	 * @return player 1
	 */		
	public Player getPlayer1() {
		
		return player1;
	}

	/**
	 * get player 2 to provide access to his behaviour and hand
	 * @return player 2
	 */		
	public Player getPlayer2() {
		
		return player2;
	}	

	/**
	 * get the game state for this game
	 * @return the current game state
	 */			
	public GameState getGameState() {
		
		return gameState;
	}
	
	/**
	 * set the game state for this game
	 */		
	public void setGameState(GameState gameState) {
		
		this.gameState = gameState;
	}	

	/**
	 * see who's turn it is
	 * @return player1, player2 or if game over - null 
	 */
	public Player getWhoseTurnItIs() {
		
		if (gameState.equals(GameState.PLAYER_1_TURN)) {
			return player1;
		}
		if (gameState.equals(GameState.PLAYER_2_TURN)) {
			return player2;
		}		
		else return null;
	}
	
}
