/**
 * 
 */
package com.johnwayner.android.tichu.scorer.actions;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

import com.johnwayner.android.tichu.scorer.R;
import com.johnwayner.android.tichu.scorer.models.ApplicationState;
import com.johnwayner.android.tichu.scorer.models.Player;

/**
 * @author johnwayner
 *
 */
public class ManagePlayers extends ListActivity {
	private final static int DIALOG_NEW = 0;
	private final static int DIALOG_EDIT = 1;
	
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
				showDialog(DIALOG_EDIT);
				
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
			showDialog(DIALOG_NEW);
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
        return createEditorDialog(id);
    }

    private Dialog createEditorDialog(final int id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        
        if(DIALOG_NEW == id) {
        	builder.setTitle(R.string.create_new_player_title);
        } else {
        	builder.setTitle(R.string.edit_player_title);
        }

        View content = getLayoutInflater().inflate(R.layout.player_edit,
            (ViewGroup) findViewById(R.id.player_edit_layout));
        builder.setView(content);
        
        final EditText nameField = (EditText) 
			content.findViewById(R.id.name_edit_text);
        
        nameField.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if((null != event) && (KeyEvent.KEYCODE_ENTER == event.getKeyCode())) {
					return true;
				}
				return false;
			}
		});
        
        builder.setPositiveButton(R.string.player_edit_save_button_label,
            new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int which)
                {
                    String newName = nameField.getText().toString();

                    if(DIALOG_NEW == id) {
                    	arrayAdapter.add(
                    		new Player(newName));
                    } else {
                    	playerToEdit.setName(newName);
                    }
                    
                    arrayAdapter.notifyDataSetChanged();                    
                    dialog.dismiss();
                }
            });

        builder.setNegativeButton(R.string.cancel_button_label,
            new DialogInterface.OnClickListener()
            {

                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

        return builder.create();
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	super.onPrepareDialog(id, dialog);
    	
    	EditText nameField = (EditText) 
    		dialog.findViewById(R.id.name_edit_text);
    	
        if(DIALOG_EDIT == id) {
        	nameField.setText(playerToEdit.getName());
        } else {
        	nameField.setText("");
        }
        
        //Work around to put the cursor at the end
        Selection.setSelection(
        		nameField.getText(),
        		nameField.getText().length());
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
