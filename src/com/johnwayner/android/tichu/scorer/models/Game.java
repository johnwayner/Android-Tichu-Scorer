/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author johnwayner
 *
 */
public class Game extends AbstractIdentifiedObject implements Serializable {
	private static final long serialVersionUID = 290465725140346096L;
	
	

	public Game() {
		super();
		gameDate = new Date();
		seats = new ArrayList<Seat>();
		hands = new ArrayList<Hand>();
	}
	
	public Game(ArrayList<Seat> seats, ArrayList<Hand> hands) {
		super();
		this.seats = seats;
		this.hands = hands;
	}
	
	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

	public ArrayList<Hand> getHands() {
		return hands;
	}

	public void setHands(ArrayList<Hand> hands) {
		this.hands = hands;
	}
	
	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		if(null == gameDate) gameDate = new Date(); //TODO REMOVE!!!!
		return ((null != name)?name+" - ":"") + gameDate.toLocaleString();
	}

	protected Date gameDate;
	protected String name;
	protected ArrayList<Seat> seats;
	protected ArrayList<Hand> hands;
}
