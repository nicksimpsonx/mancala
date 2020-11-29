package com.simpsonn.mancala.model.components;

import java.util.Collections;
import java.util.List;

import com.simpsonn.mancala.model.player.PlayerId;

/**
 * represents a board component, these are the pits and the kalahs. 
 * This class contains common implementation details of pits and kalahs
 */
public abstract class BoardComponent {

	protected PlayerId ownerId;
	protected List<Stone> stones = Collections.emptyList();

	/**
	 * return who this component belongs to
	 * @return the id of the owner of the pit
	 */
	public PlayerId getOwnerId() {
		
		return ownerId;
	}

	/**
	 * add the given stone to this component
	 * @param stoneToAdd the stone to add
	 */
	public void addStone(Stone stoneToAdd) {
		
		stones.add(stoneToAdd);
	}	

	/**
	 * get the total number of stones stored in this component
	 * @return the number of stones contained in this component
	 */
	public int countStones() {
		
		return stones.size();
	}
	
}
