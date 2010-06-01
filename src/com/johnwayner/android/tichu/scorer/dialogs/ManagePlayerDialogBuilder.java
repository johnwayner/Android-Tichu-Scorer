/**
 * 
 */
package com.johnwayner.android.tichu.scorer.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import com.johnwayner.android.tichu.scorer.R;
import com.johnwayner.android.tichu.scorer.models.Player;

/**
 * @author johnwayner
 * 
 */
public class ManagePlayerDialogBuilder {
	public final static int DIALOG_NEW = 0;
	public final static int DIALOG_EDIT = 1;

	public static Dialog createEditorDialog(
			Activity activity, 
			final int id,
			final ManagePlayerDialogCallBack callback) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		if (DIALOG_NEW == id) {
			builder.setTitle(R.string.create_new_player_title);
		} else {
			builder.setTitle(R.string.edit_player_title);
		}

		View content = activity.getLayoutInflater().inflate(R.layout.player_edit,
				(ViewGroup) activity.findViewById(R.id.player_edit_layout));
		builder.setView(content);

		final EditText nameField = (EditText) content
				.findViewById(R.id.name_edit_text);

		nameField.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((null != event)
						&& (KeyEvent.KEYCODE_ENTER == event.getKeyCode())) {
					return true;
				}
				return false;
			}
		});

		builder.setPositiveButton(R.string.player_edit_save_button_label,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						String newName = nameField.getText().toString();

						if (DIALOG_NEW == id) {
							callback.addNewPlayer(newName);
						} else {
							callback.editPlayer(newName);
						}
						
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(R.string.cancel_button_label,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		return builder.create();
	}

	public static void onPrepareDialog(int id, Dialog dialog, Player playerToEdit) {

		EditText nameField = (EditText) dialog
				.findViewById(R.id.name_edit_text);

		if (DIALOG_EDIT == id) {
			nameField.setText(playerToEdit.getName());
		} else {
			nameField.setText("");
		}

		// Work around to put the cursor at the end
		Selection.setSelection(nameField.getText(), nameField.getText()
				.length());
	}

	public interface ManagePlayerDialogCallBack {
		public void addNewPlayer(String name);
		public void editPlayer(String name);
	}
}
