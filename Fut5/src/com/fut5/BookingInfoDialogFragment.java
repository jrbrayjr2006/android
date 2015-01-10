/**
 * 
 */
package com.fut5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Show booking information as confirmation of booking
 * 
 * @author james_r_bray
 *
 */
public class BookingInfoDialogFragment extends DialogFragment {
	
	private String mMessage;

	/**
	 * 
	 */
	public BookingInfoDialogFragment(String _message) {
		this.mMessage = _message;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		//TODO flesh out method
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getResources().getString(R.string.booking_info));
		builder.setMessage(mMessage);
		builder.setPositiveButton(getActivity().getResources().getString(R.string.ok_label), new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setNegativeButton(getActivity().getResources().getString(R.string.cancel_label), null);
		return builder.create();
	}
	
	/**
	 * 
	 * @return
	 */
	private String buildMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("date and time\n");  //TODO add date and time value here
		sb.append(mMessage);
		return sb.toString();
	}

}
