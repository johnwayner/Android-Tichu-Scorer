/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.Serializable;

/**
 * A tichu player.
 * @author johnwayner
 *
 */
public class Player extends AbstractIdentifiedObject implements Serializable {
	private static final long serialVersionUID = -1305107698507151458L;

	public Player() {
		super();
	}
	
	public Player(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	protected String name;

}
