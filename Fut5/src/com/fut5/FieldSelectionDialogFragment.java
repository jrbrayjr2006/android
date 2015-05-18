package com.fut5;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.fut5.model.SoccerField;

/**
 * <b>Description</b>
 * <p>
 * Controller for the list of soccer fields
 * </p>
 * 
 * @author james_r_bray
 *
 */
public class FieldSelectionDialogFragment extends DialogFragment {
	
	List<SoccerField> fields;
	
	CharSequence[] items;
	
	private static final String TAG = "FieldSelectionDialogFragment";
	
	public interface SoccerFieldDialogCallbackInterface {
		public String getSoccerFieldName(String name);
	}

	public FieldSelectionDialogFragment() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getResources().getString(R.string.booking_field_names));
		builder.setItems(getResources().getStringArray(R.array.soccer_fields), new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String tmp = getResources().getStringArray(R.array.soccer_fields)[which];
				Log.i(TAG, "Selected field is " + tmp);
				((SoccerFieldDialogCallbackInterface)getTargetFragment()).getSoccerFieldName(getResources().getStringArray(R.array.soccer_fields)[which]);
			}});
		return builder.create();
	}
	
	/**
	 * This is a development method to generate dummy soccer fields
	 * @return
	 */
	private List<SoccerField> getDummySoccerFields() {
		fields = new ArrayList<SoccerField>();
		
		for(int i = 0; i < 5; i++) {
			SoccerField sf = new SoccerField();
			sf.setId(i);
			sf.setLocation("Some Location");
			sf.setName("Field " + i);
			sf.setSize("unknown");
			sf.setType("default");
			fields.add(sf);
		}
		
		return fields;
	}

}
