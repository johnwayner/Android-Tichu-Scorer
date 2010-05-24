/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.Serializable;

/**
 * A Seat is a Player in a specific position at a table.
 * @author johnwayner
 *
 */
public class Seat extends AbstractIdentifiedObject implements Serializable {
	private static final long serialVersionUID = -3056209183225015644L;

	
	public Seat() {
		super();
	}
	
	public Seat(PlayerPosition position, Player player) {
		super();
		this.position = position;
		this.player = player;
	}
	
	public PlayerPosition getPosition() {
		return position;
	}

	public void setPosition(PlayerPosition position) {
		this.position = position;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	protected PlayerPosition position;
	protected Player player;
}
