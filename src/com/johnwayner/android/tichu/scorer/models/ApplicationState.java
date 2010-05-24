/**
 * 
 */
package com.johnwayner.android.tichu.scorer.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

/**
 * Represents everything the application knows about --
 *  - all games
 *  - all payers
 *  etc.
 * @author johnwayner
 *
 */
public class ApplicationState implements Serializable {
	private static final long serialVersionUID = -3608950811073141673L;
	private static final String STATE_FILE_NAME = "TichuScorerAppState";
	private static ApplicationState appState = null;
	
	
	public ApplicationState() {
		super();
		players = new ArrayList<Player>();
		games = new ArrayList<Game>();
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public ArrayList<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public Player findPlayer(long id) throws Exception
	{
		for(Player player : players) {
			if(id == player.getId()) {
				return player;
			}	
		}
		
		throw new Exception("UnknownPlayerId:" + id);
	}

	public static byte[] serialize(ApplicationState appState) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(appState);
		oos.close();
		
		return baos.toByteArray();			
	}
	
	public static ApplicationState deserialize(byte[] serializedGame) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(serializedGame);
		ObjectInputStream ois = new ObjectInputStream(bais);
		ApplicationState appState = (ApplicationState)ois.readObject();
		ois.close();
		
		return appState;
	}
	
	/**
	 * Returns the application state either from cache or from
	 * file storage.
	 * @return
	 * @throws Exception 
	 */
	public static ApplicationState getState(Context context) {
		if(null == appState)
		{
			//Load the state from file storage
			try {
				FileInputStream fis = context.openFileInput(STATE_FILE_NAME);
				byte[] fileBytes = new byte[fis.available()];
				fis.read(fileBytes);
				appState = deserialize(fileBytes);
			} catch (Exception e) {
				//Failed to read existing state.
				//Make a new one.
				appState = new ApplicationState();
			}
			
			appState.setContext(context);
		}
		
		return appState;
	}
	
	public static void saveState() throws IOException {
		FileOutputStream fos = 
			appState.context.openFileOutput(STATE_FILE_NAME, Context.MODE_PRIVATE);
		fos.write(serialize(appState));
		fos.close();
	}

	protected ArrayList<Player> players;
	protected ArrayList<Game> games; 
	transient protected Context context;
}
