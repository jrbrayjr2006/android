/**
 * 
 */
package com.fut5;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author james_r_bray
 *
 */
public class KnockoutFragment extends  CoreBookingFragment {
	
	private static final String TAG = "KnockoutFragment";
	private Button joinTheListBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_knockout, root, false);
		
		joinTheListBtn = (Button)v.findViewById(R.id.buttonJoinTheList);
		joinTheListBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Opening JoinKnockoutListDialogFragment...");
				openJoinKnockoutDialog() ;
			}});
		
		return v;
	}
	
	/**
	 * Open DialogFragment
	 */
	private void openJoinKnockoutDialog() {
		DialogFragment joinKnockoutListDialog = new JoinKnockoutListDialogFragment();
		joinKnockoutListDialog.show(getActivity().getFragmentManager(), TAG);
	}

}
