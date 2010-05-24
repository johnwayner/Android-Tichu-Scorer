package com.johnwayner.android.tichu.scorer.actions;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

import com.johnwayner.android.tichu.scorer.R;
import com.johnwayner.android.tichu.scorer.models.ApplicationState;
import com.johnwayner.android.tichu.scorer.models.Player;

public class CreateNewGame extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationState appState = ApplicationState.getState(this.getApplicationContext());
        appState.getPlayers().add(new Player("Steve"));
        setContentView(R.layout.main);
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
}