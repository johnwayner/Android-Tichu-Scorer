/**
 * 
 */
package com.johnwayner.android.tichu.scorer.actions;

import java.io.IOException;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.johnwayner.android.tichu.scorer.R;
import com.johnwayner.android.tichu.scorer.dialogs.ManagePlayerDialogBuilder;
import com.johnwayner.android.tichu.scorer.dialogs.ManagePlayerDialogBuilder.ManagePlayerDialogCallBack;
import com.johnwayner.android.tichu.scorer.models.ApplicationState;
import com.johnwayner.android.tichu.scorer.models.Player;

/**
 * @author johnwayner
 *
 */
public class ManagePlayers extends ListActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ApplicationState appState = ApplicationState.getState(this);
		arrayAdapter = 
			new ArrayAdapter<Player>(
				this, 
				android.R.layout.simple_list_item_single_choice, 
				appState.getPlayers());
		
		setListAdapter(arrayAdapter);
		
		getListView().setTextFilterEnabled(true);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				playerToEdit = arrayAdapter.getItem(position);
				showDialog(ManagePlayerDialogBuilder.DIALOG_EDIT);
				
			}
		    
		  });

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.manage_players_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.new_player_menu_item:
//			startActivity(new Intent(EditPlayer.NEW_USER_ACTION_NAME));
//			arrayAdapter.notifyDataSetChanged();
			showDialog(ManagePlayerDialogBuilder.DIALOG_NEW);
			return true;
		case R.id.delete_all_menu_item:
			arrayAdapter.clear();
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
				playerToEdit.setName(name);
				arrayAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void addNewPlayer(String name) {
				arrayAdapter.add(
                		new Player(name));
			}
		});
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	super.onPrepareDialog(id, dialog);
    	
    	ManagePlayerDialogBuilder.onPrepareDialog(id, dialog, playerToEdit);
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

    private Player playerToEdit;
    private ArrayAdapter<Player> arrayAdapter;
	
}
