package com.fut5;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.fut5.helper.NetworkHelper;
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
	
	private CharSequence[] items;
	
	public static final String TRANSACTION = "getSoccerFields";
	
	private static final String TAG = "FieldSelectionDialogFragment";
	
	/**
	 * No parameters are used, so this property is for convenience only
	 */
	private List<NameValuePair> nameValuePairs;
	
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
		
		List<String> fieldnames = new ArrayList<String>();
		builder.setTitle(getResources().getString(R.string.booking_field_names));
		fields = getSoccerFields();
		
		for(SoccerField sf : fields) {
			String fieldname = sf.getName();
			fieldnames.add(fieldname);
		}
		
		String[] soccerFieldNames = fieldnames.toArray(new String[fieldnames.size()]);
		
		// getResources().getStringArray(R.array.soccer_fields)
		builder.setItems(soccerFieldNames, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String selectedFieldName = fields.get(which).getName();
				Log.i(TAG, "Selected field is " + selectedFieldName);
				((SoccerFieldDialogCallbackInterface)getTargetFragment()).getSoccerFieldName(selectedFieldName);
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
	
	private List<SoccerField> getSoccerFields() {
		List<SoccerField> _fields = new ArrayList<SoccerField>();
		
		NetworkHelper networkHelper = new NetworkHelper();
		nameValuePairs = new ArrayList<NameValuePair>();
		ArrayList<Object> results = (ArrayList<Object>)networkHelper.sendData(nameValuePairs, TRANSACTION);
		String status = (String)results.get(0);
		_fields = (ArrayList<SoccerField>)results.get(1);
		
		return _fields;
	}

}
