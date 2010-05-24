/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.Serializable;

/**
 * Generic ID-adding class.
 * @author johnwayner
 *
 */
public abstract class AbstractIdentifiedObject implements Serializable {
	private static final long serialVersionUID = 8647346339618617912L;
	private static final long NO_ID = 0;
	private static long nextID = 1;
	private static Object IDLock = new Object();
	
	public AbstractIdentifiedObject() {
		super();
		this.id = getNextID();
	}

	protected long getNextID() {
		synchronized (IDLock) {
			return nextID++;
		}
	}
	
	protected long id;

	public long getId() {
		if(NO_ID == id) {
			id = getNextID();
		}
		return id;
	}
}
