package com.simpsonn.mancala.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.simpsonn.mancala.model.components.Board;
import com.simpsonn.mancala.model.components.BoardComponent;
import com.simpsonn.mancala.model.components.Game;
import com.simpsonn.mancala.model.components.GameState;
import com.simpsonn.mancala.model.components.Kalah;
import com.simpsonn.mancala.model.components.Pit;
import com.simpsonn.mancala.model.components.Stone;
import com.simpsonn.mancala.model.player.Player;
import com.simpsonn.mancala.model.player.PlayerId;

/**
 * this class contains the game logic for kalah, it also processes moves, checks
 * for end of game and it updates game state if necessary
 */
@Service
public class GameController {
	
	private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

	/**
	 * controls the flow of a move in the game
	 * first check to make sure that the game has not finished, then make sure we
	 * are starting from a valid pit and then process the move chain, finally 
	 * check to see if the game is complete
	 * 
	 * @param game the game we are doing the move for
	 * @param pitId the pit id to start the move from (as a 1 based index)
	 */
	public void makeMove(Game game, int pitId) {
		
		// if the game is finished, just return so the user will be represented with the board and game status
		if (isGameFinished(game)) {
			LOG.info("game id [{}] is finished, move cannot be processed", game.getGameId().toString());
			return;
		}
		// check move is valid before processing the move
		pitId = convertPitId(pitId);
		if (isMoveValid(game, pitId)) {
			LOG.info("handling move request with game id [{}] and pit id [{}]", game.getGameId().toString(), pitId);
			processMove(game, pitId);
		}		
		// if the game is done see what the outcome was and set the status
		if (gameOverCheck(game)) {
			LOG.info("game id [{}] complete, setting final game state", game.getGameId().toString());
			setFinalGamestate(game);
		}
	}
	
	/*
	 * game over, count the stones and set the game state accordingly 
	 */
	private void setFinalGamestate(Game game) {
		
		long player1Stones = getPlayerKalah(game.getBoard(), game.getPlayer1()).countStones(); 
		long player2Stones = getPlayerKalah(game.getBoard(), game.getPlayer2()).countStones();
		
		if(player1Stones == player2Stones) {
			game.setGameState(GameState.DRAW);
		} else if (player1Stones > player2Stones) {
			game.setGameState(GameState.PLAYER_1_WIN);
		} else {
			game.setGameState(GameState.PLAYER_2_WIN);
		}
	}
	
	/*
	 * if either of the players have got all their stones home, the other player
	 * places all the stones on their side into their kalah, we then count the
	 * stones in each kalah to see who won or if it's a draw
	 */
	private boolean gameOverCheck(Game game) {
		
		boolean gameFinished = false;
		
		if (countRemainingStones(game.getBoard(), game.getPlayer1()) == 0) {			
			// Player 1 pits are empty so make sure player 2 remaining stones are in their pit
			finalisePlayer(game.getBoard(), game.getPlayer2());
			gameFinished = true;
		}
		if (countRemainingStones(game.getBoard(), game.getPlayer2()) == 0) {			
			// Player 2 pits are empty so make sure player 1 remaining stones are in their pit
			finalisePlayer(game.getBoard(), game.getPlayer1());
			gameFinished = true;
		} 
		return gameFinished;
	}

	/*
	 * count how many stones this player has left in their pits, if this returns
	 * zero this will trigger the end of the game as it means all the players stones
	 * are in their kalah
	 */
	private int countRemainingStones(Board board, Player player) {

		int stonesRemaining = 0;
		for(BoardComponent component : board.getBoardComponents()) {	
			if(component.getOwnerId().equals(player.getPlayerId()) && component instanceof Pit) {
				stonesRemaining += component.countStones();
			}
		}
		return stonesRemaining;
	}
	
	/*
	 * transfer the remaining stones for this player into their kalah as the game is
	 * over
	 */
	private void finalisePlayer (Board board, Player player) {
		
		Kalah kalah = getPlayerKalah(board, player);		
		for(BoardComponent component : board.getBoardComponents()) {			
			if(component.getOwnerId().equals(player.getPlayerId()) && component instanceof Pit) {
				Pit pit = (Pit) component;
				List<Stone> stones = pit.getStones();
				if(stones != null) {
					kalah.addStones(stones);	
				}
			}
		}
	}
	
	/*
	 * process the move, this is where the game logic is implemented
	 */
	private void processMove(Game game, int pitId) {
		
		// get the player who's turn it is
		Player player = game.getWhoseTurnItIs();
		Board board = game.getBoard();
		
		// pick up the stones at this pit
		Pit pit = (Pit) board.getBoardComponents().get(pitId);
		player.getHand().addStonesToHand(pit.getStones());
		
		// sow the stones into the pits moving around the board anti-clockwise until the
		// hand is empty		
		while (!player.getHand().isHandEmpty()) {
			
			// get the next viable location and sow a stone
			pitId = getNextLocationToPlace(pitId, board, player);
			board.getBoardComponents().get(pitId).addStone(player.getHand().getOneStoneFromHand());
			
			// if that was the last stone in hand and it was sown into an empty pit owned by this player then we
			// need to capture opposite stones and this one and put into this players kalah
			if (player.getHand().isHandEmpty() &&
					isLastMoveInOwnEmptyPit(player, board.getBoardComponents().get(pitId))) {
				LOG.info("{} captures any stones in pit opposite as landed in own empty pit [{}]", player.getPlayerId().getDisplayId(), pitId);
				processLastStoneInOwnedEmptyPit(pitId, player, board);				
			} 
			
			// if that was the last stone in hand and it went into the players kalah, return
			// without switching game status thereby giving this player another go
			if (player.getHand().isHandEmpty() && 
					board.getBoardComponents().get(pitId).equals(getPlayerKalah(board, player))) {
				LOG.info("{} gets another turn as last stone went into their kalah", player.getPlayerId().getDisplayId());
				return;
			}
		}
		switchControlToOtherPlayer(player, game);
	}

	/*
	 * Change the game state so the other player can take their turn
	 */
	private void switchControlToOtherPlayer(Player player, Game game) {
		
		if(player.getPlayerId().equals(PlayerId.PLAYER_1)) {
			game.setGameState(GameState.PLAYER_2_TURN);
		} else {
			game.setGameState(GameState.PLAYER_1_TURN);
		}
	}

	/*
	 * pick up the stone in the players pit and all the stones in the opposite pit
	 * then put them in the players kalah 
	 */
	private void processLastStoneInOwnedEmptyPit(int pitId, Player player, Board board) {
		
		Kalah playerKalah = getPlayerKalah(board, player);
		
		Pit playerPit = (Pit) board.getBoardComponents().get(pitId);
		Pit opponentPit = getPitOpposite(playerPit, board);
		
		List<Stone> playerStones = playerPit.getStones();
		List<Stone> opponentStones = opponentPit.getStones();
		
		if (playerStones !=null ) {
			playerKalah.addStones(playerStones);
		}
		if(opponentStones !=null) {
			playerKalah.addStones(opponentStones);
		}
	}
	
	/*
	 * return true if the last stone went into an empty pit owned by the player
	 */
	private boolean isLastMoveInOwnEmptyPit(Player player, BoardComponent component) {
		
		return component instanceof Pit && 
				component.getOwnerId().equals(player.getPlayerId()) && 
				component.countStones() == 1;
	}

	/*
	 * return the pit on the opposite side of the board to the given pit
	 * the board is set out like this:
	 * 
	 *  (13) 12 11 10  9  8  7                 
	 *        0  1  2  3  4  5 (6)
	 * So:
	 * oppositeIndex = abs(indexToFindOppositeFor - 12)
	 * will always work to return the opposite pit
	 */
	private Pit getPitOpposite(BoardComponent pitToUse, Board board) {
		
		if (pitToUse instanceof Pit) {
			int index = board.getBoardComponents().indexOf(pitToUse);
			int indexToGet = Math.abs(index - (board.getBoardComponents().size() - 2));
			return (Pit) board.getBoardComponents().get(indexToGet);
		}
		// If it's a kalah return null
		return null;
	}	

	/*
	 * retrieve the kalah owned by this player from the board
	 */
	private Kalah getPlayerKalah(Board board, Player player) {
		
		return (Kalah) board.getBoardComponents().stream()
				.filter(component -> component instanceof Kalah)
				.filter(component -> component.getOwnerId().equals(player.getPlayerId()))
				.findFirst()
				.orElse(null);
	}
	
	/*
	 * sets pitId to the next index a player can put a stone. This index cannot be the opponent's kalah
	 * if we go off the end of the board, wrap around
	 */
	private int getNextLocationToPlace(int pitId, Board board, Player player) {
		
		pitId++;
		if (pitId >= board.getBoardComponents().size()) {
			pitId = 0;
		}
		BoardComponent componentToTest = board.getBoardComponents().get(pitId);
		if (componentToTest instanceof Kalah && !componentToTest.getOwnerId().equals(player.getPlayerId())) {
			pitId++;
			if (pitId >= board.getBoardComponents().size()) {
				pitId = 0;
			}			
		}
		return pitId;
	}

	/*
	 * before we attempt to make a move we have to make sure that the turn is:
	 * 
	 * - not starting at a kalah 
	 * - the pit belongs to the player making the move 
	 * - the pit is not empty 
	 * 
	 * if this is not a valid move we throw an exception which will be
	 * picked up by the error handler and send back as a message
	 */
	private boolean isMoveValid(Game game, int pitId) {
		
		if (isComponentAKalah(game, pitId)) {
			throw new IllegalArgumentException("you cannot start your turn at a kalah");
		}
		if (!isComponentOwnedByPlayer(game, pitId)) {
			throw new IllegalArgumentException("that pit does not belong to you");			
		}
		if (isComponentEmpty(game, pitId)) {			
			throw new IllegalArgumentException("please choose a pit which is not empty");
		}
		return true;
	}
	
	/*
	 * test to see if this pit / kalah is empty
	 */
	private boolean isComponentEmpty(Game game, int pitId) {
		
		return (game.getBoard().getBoardComponents().get(pitId).countStones() == 0);
	}
	
	/*
	 * players may only start their turn using their own pit
	 */
	private boolean isComponentOwnedByPlayer(Game game, int pitId) {
		
		return (
				game.getBoard().getBoardComponents().get(pitId).getOwnerId().equals(game.getWhoseTurnItIs().getPlayerId()));
	}
	
	/*
	 * effectively any of the statuses checked for here are end of game
	 */
	private boolean isGameFinished(Game game) {
		
		return (game.getGameState().equals(GameState.DRAW) || 
				game.getGameState().equals(GameState.PLAYER_1_WIN) ||
				game.getGameState().equals(GameState.PLAYER_2_WIN));
	}

	/*
	 * this test is used to see if the player is trying to start their turn at a kalah
	 */
	private boolean isComponentAKalah(Game game, int pitId) {
		
		return (game.getBoard().getBoardComponents().get(pitId) instanceof Kalah);
	}
	
	/*
	 * the status is displayed to the player as a 1 based index but internally it is
	 * zero based so we need to subtract 1 from the pit id the player gave us
	 */
	private int convertPitId(int pitId) {
		
		return pitId - 1;
	}
	
}
