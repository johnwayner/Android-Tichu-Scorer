/**
 * 
 */
package com.johnwayner.android.tichu.scorer.actions;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.johnwayner.android.tichu.scorer.models.ApplicationState;
import com.johnwayner.android.tichu.scorer.models.Game;

/**
 * @author johnwayner
 *
 */
public class ManageGames extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ApplicationState appState = ApplicationState.getState(this);
		ArrayAdapter<Game> arrayAdapter = 
			new ArrayAdapter<Game>(
				this, 
				android.R.layout.simple_list_item_single_choice, 
				appState.getGames());
		
		setListAdapter(arrayAdapter);
		
		getListView().setTextFilterEnabled(true);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
//				playerToEdit = arrayAdapter.getItem(position);
//				showDialog(ManagePlayerDialogBuilder.DIALOG_EDIT);
				
			}
		    
		  });

	}
}
