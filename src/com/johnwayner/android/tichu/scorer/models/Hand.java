/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The results of a single hand of a game of Tichu. 
 * @author johnwayner
 *
 */
public class Hand extends AbstractIdentifiedObject implements Serializable {
	private static final long serialVersionUID = -7860913419887616788L;
	
	
	
	public Hand() {
		super();
		team1HandScore = 0;
		team2HandScore = 0;
		grandTichuCalls = new ArrayList<PlayerPosition>();
		tichuCalls = new ArrayList<PlayerPosition>();
	}
	
	public Hand(int team1HandScore, int team2HandScore,
			ArrayList<PlayerPosition> grandTichuCalls, ArrayList<PlayerPosition> tichuCalls,
			PlayerPosition grandTichuMaker, PlayerPosition tichuMaker) {
		super();
		this.team1HandScore = team1HandScore;
		this.team2HandScore = team2HandScore;
		this.grandTichuCalls = grandTichuCalls;
		this.tichuCalls = tichuCalls;
		this.grandTichuMaker = grandTichuMaker;
		this.tichuMaker = tichuMaker;
	}
	
	public int getTeam1HandScore() {
		return team1HandScore;
	}

	public void setTeam1HandScore(int team1HandScore) {
		this.team1HandScore = team1HandScore;
	}

	public int getTeam2HandScore() {
		return team2HandScore;
	}

	public void setTeam2HandScore(int team2HandScore) {
		this.team2HandScore = team2HandScore;
	}

	public ArrayList<PlayerPosition> getGrandTichuCalls() {
		return grandTichuCalls;
	}

	public void setGrandTichuCalls(ArrayList<PlayerPosition> grandTichuCalls) {
		this.grandTichuCalls = grandTichuCalls;
	}

	public ArrayList<PlayerPosition> getTichuCalls() {
		return tichuCalls;
	}

	public void setTichuCalls(ArrayList<PlayerPosition> tichuCalls) {
		this.tichuCalls = tichuCalls;
	}

	public PlayerPosition getGrandTichuMaker() {
		return grandTichuMaker;
	}

	public void setGrandTichuMaker(PlayerPosition grandTichuMaker) {
		this.grandTichuMaker = grandTichuMaker;
	}

	public PlayerPosition getTichuMaker() {
		return tichuMaker;
	}

	public void setTichuMaker(PlayerPosition tichuMaker) {
		this.tichuMaker = tichuMaker;
	}

	protected int team1HandScore;
	protected int team2HandScore;
	protected ArrayList<PlayerPosition> grandTichuCalls;
	protected ArrayList<PlayerPosition> tichuCalls;
	protected PlayerPosition grandTichuMaker;
	protected PlayerPosition tichuMaker;
}
