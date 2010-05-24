/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author johnwayner
 *
 */
public class Game extends AbstractIdentifiedObject implements Serializable {
	private static final long serialVersionUID = 290465725140346096L;
	
	

	public Game() {
		super();
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

	protected ArrayList<Seat> seats;
	protected ArrayList<Hand> hands;
}
