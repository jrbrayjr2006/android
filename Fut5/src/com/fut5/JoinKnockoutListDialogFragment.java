/**
 * 
 */
package com.fut5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

/**
 * @author james_r_bray
 *
 */
public class JoinKnockoutListDialogFragment extends DialogFragment {
	
	public JoinKnockoutListDialogFragment() {
		super();
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		final EditText userinput = new EditText(getActivity());
		userinput.setInputType(InputType.TYPE_CLASS_NUMBER);
		userinput.setHint(getActivity().getResources().getString(R.string.hint_number_of_guests));
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getResources().getString(R.string.join_knockout_list));
		builder.setMessage(getKnockoutText());
		builder.setView(userinput);
		// add code to show EditText
		builder.setPositiveButton(getActivity().getResources().getString(R.string.ok_label), new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setNegativeButton(getActivity().getResources().getString(R.string.cancel_label), null);
		return builder.create();
	}
	
	private String getKnockoutText() {
		StringBuffer sb = new StringBuffer();
		sb.append("You will be joining the knockout list for\n");
		sb.append("\n");  //TODO add date and time value here
		sb.append("Please type the number of guests");
		return sb.toString();
	}

}
