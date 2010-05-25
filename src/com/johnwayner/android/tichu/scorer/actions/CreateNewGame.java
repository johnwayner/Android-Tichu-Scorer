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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.johnwayner.android.tichu.scorer.R;
import com.johnwayner.android.tichu.scorer.dialogs.ManagePlayerDialogBuilder;
import com.johnwayner.android.tichu.scorer.dialogs.ManagePlayerDialogBuilder.ManagePlayerDialogCallBack;
import com.johnwayner.android.tichu.scorer.models.ApplicationState;
import com.johnwayner.android.tichu.scorer.models.Player;

public class CreateNewGame extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);
        setupSpinners();
                
    }   
    
    protected void setupSpinners() {
		setupSpinner(R.id.north_spinner, R.string.north_abbrv);
		setupSpinner(R.id.east_spinner, R.string.east_abbrv);
		setupSpinner(R.id.south_spinner, R.string.south_abbrv);
		setupSpinner(R.id.west_spinner, R.string.west_abbrv);
		setupSpinner(R.id.southeast_spinner, R.string.southeast_abbrv);
    }
    
    protected List<Player> getChoiceList(int posRsrcId) {
    	ApplicationState appState = ApplicationState.getState(this.getApplicationContext());
    	Player chooseOnePlayer = new Player(getString(posRsrcId));
        ArrayList<Player> choiceList = new ArrayList<Player>(appState.getPlayers());
        choiceList.add(0, chooseOnePlayer);
        return choiceList;
    }
    
    protected void setupSpinner(int spinnerRsrcId, int posRsrcId) {
    	Spinner spinner = (Spinner)findViewById(spinnerRsrcId);
        
        ArrayAdapter<Player> playerAdapter = 
        	new ArrayAdapter<Player>(
        				this, 
        				android.R.layout.simple_spinner_item,
        				getChoiceList(posRsrcId));
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner.setAdapter(playerAdapter);
        spinner.postInvalidate();
        playerArrayAdapters.add(playerAdapter);
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
				for(ArrayAdapter<Player> adapter : playerArrayAdapters) {
					adapter.add(newPlayer);
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
    
    protected List<ArrayAdapter<Player>> playerArrayAdapters = new ArrayList<ArrayAdapter<Player>>();
}