package com.johnwayner.android.tichu.scorer.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.johnwayner.android.tichu.scorer.R;
import com.johnwayner.android.tichu.scorer.dialogs.ManagePlayerDialogBuilder;
import com.johnwayner.android.tichu.scorer.dialogs.ManagePlayerDialogBuilder.ManagePlayerDialogCallBack;
import com.johnwayner.android.tichu.scorer.models.ApplicationState;
import com.johnwayner.android.tichu.scorer.models.Game;
import com.johnwayner.android.tichu.scorer.models.Player;
import com.johnwayner.android.tichu.scorer.models.PlayerPosition;
import com.johnwayner.android.tichu.scorer.models.Seat;

public class CreateNewGame extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);
        setupSpinners();
        
        Button gameButton = (Button)findViewById(R.id.create_game_button);
        gameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Game newGame = new Game();
				ArrayList<Seat> seats = new ArrayList<Seat>();
				for(PlayerSpinner pSpinner : playerSpinners) {
					seats.add(pSpinner.getSeat());
				}
				newGame.setSeats(seats);
				ApplicationState.getState(
						CreateNewGame.this.getApplicationContext())
							.getGames().add(newGame);
			}
		});
    }   
    
    protected void setupSpinners() {
		setupSpinner(R.id.north_spinner, R.string.north_abbrv, PlayerPosition.NORTH);
		setupSpinner(R.id.east_spinner, R.string.east_abbrv, PlayerPosition.EAST);
		setupSpinner(R.id.south_spinner, R.string.south_abbrv, PlayerPosition.SOUTH);
		setupSpinner(R.id.west_spinner, R.string.west_abbrv, PlayerPosition.WEST);
		setupSpinner(R.id.southeast_spinner, R.string.southeast_abbrv, PlayerPosition.SOUTHEAST);
    }
    
    protected List<Player> getChoiceList(int posRsrcId) {
    	ApplicationState appState = ApplicationState.getState(this.getApplicationContext());
    	Player chooseOnePlayer = new Player(getString(posRsrcId));
        ArrayList<Player> choiceList = new ArrayList<Player>(appState.getPlayers());
        choiceList.add(0, chooseOnePlayer);
        return choiceList;
    }
    
    protected void setupSpinner(int spinnerRsrcId, int posRsrcId, PlayerPosition position) {
    	Spinner spinner = (Spinner)findViewById(spinnerRsrcId);
        
        ArrayAdapter<Player> playerAdapter = 
        	new ArrayAdapter<Player>(
        				this, 
        				android.R.layout.simple_spinner_item,
        				getChoiceList(posRsrcId));
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner.setAdapter(playerAdapter);
        spinner.postInvalidate();
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> thisSpinner, View arg1,
					int arg2, long arg3) {
				Player selectedPlayer = (Player) thisSpinner.getSelectedItem();
				// de-select selected player from other spinners
				for(PlayerSpinner pSpinner : playerSpinners) {
					if(! pSpinner.spinner.equals(thisSpinner)) {
						if(pSpinner.spinner.getSelectedItem().equals(selectedPlayer)) {
							pSpinner.spinner.setSelection(0);
						}
					}
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
        playerSpinners.add(new PlayerSpinner(playerAdapter, spinner, position));
    }
    	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_game_options_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.create_game_new_player_menu_item:
			showDialog(ManagePlayerDialogBuilder.DIALOG_NEW);
			return true;
		case R.id.create_game_clear_seats_menu_item:
			setupSpinners();
			return true;
		case R.id.create_game_manage_players_menu_item:
			startActivity(new Intent("com.johnwayner.android.tichu.scorer.MANAGE_PLAYERS"));
			return true;
		}
		return false;
	}
	
	@Override
    protected Dialog onCreateDialog(int id)
    {
        return ManagePlayerDialogBuilder.createEditorDialog(this, id, new ManagePlayerDialogCallBack() {
			
			@Override
			public void editPlayer(String name) {
				//Shouldn't be calling this.
			}
			
			@Override
			public void addNewPlayer(String name) {
				Player newPlayer = new Player(name);
				ApplicationState.getState(CreateNewGame.this).getPlayers().add(newPlayer);
				for(PlayerSpinner pSpinner : playerSpinners) {
					pSpinner.adapter.add(newPlayer);
				}
			}
		});
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	super.onPrepareDialog(id, dialog);
    	
    	ManagePlayerDialogBuilder.onPrepareDialog(id, dialog, null);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	setupSpinners();
    }
    
    @Override
    protected void onPause() {
    	try {
			ApplicationState.saveState();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	super.onPause();
    }
    
    public static class PlayerSpinner {
    	public ArrayAdapter<Player> adapter;
    	public Spinner spinner;
    	public PlayerPosition position;
		
    	public PlayerSpinner(ArrayAdapter<Player> adapter, Spinner spinner,
				PlayerPosition position) {
			super();
			this.adapter = adapter;
			this.spinner = spinner;
			this.position = position;
		}
    	
    	public Seat getSeat() {
    		return new Seat(position, (Player)spinner.getSelectedItem());
    	}
    	
    }
    
    protected List<PlayerSpinner> playerSpinners = new ArrayList<PlayerSpinner>();
}