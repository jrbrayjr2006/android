/**
 * 
 */
package com.fut5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getResources().getString(R.string.join_knockout_list));
		builder.setMessage(getKnockoutText());
		// add code to show EditText
		builder.setPositiveButton(getActivity().getResources().getString(R.string.ok_label), null);
		builder.setNegativeButton(getActivity().getResources().getString(R.string.cancel_label), null);
		return builder.create();
	}
	
	private String getKnockoutText() {
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}

}
