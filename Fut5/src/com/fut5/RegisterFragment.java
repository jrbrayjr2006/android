/**
 * 
 */
package com.fut5;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fut5.helper.NetworkHelper;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author james_r_bray
 *
 */
public class RegisterFragment extends Fragment {
	
	private static final String TAG = "RegisterFragment";
	public static final String TRANSACTION = "register";
	
	private EditText editTextFirstName;
	private EditText editTextLastName;
	private EditText editTextRegisterUsername;
	private EditText editTextRegisterEmail;
	private EditText editTextRegisterPassword;
	private EditText editTextConfirmPassword;
	private Button buttonCreateAccount;
	
	private List<NameValuePair> nameValuePairs;
    private NetworkHelper networkHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		super.onCreateView(inflater, parent, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_register, parent, false);
		
		editTextFirstName = (EditText)v.findViewById(R.id.editFirstName);
		editTextLastName = (EditText)v.findViewById(R.id.editLastName);
		editTextRegisterUsername = (EditText)v.findViewById(R.id.editRegisterUsername);
		editTextRegisterEmail = (EditText)v.findViewById(R.id.editRegisterEmail);
		editTextRegisterPassword = (EditText)v.findViewById(R.id.editRegisterPassword);
		editTextConfirmPassword = (EditText)v.findViewById(R.id.editConfirmPassword);
		buttonCreateAccount = (Button)v.findViewById(R.id.buttonCreateAccount);
		buttonCreateAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(editTextRegisterPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
					String firstname = editTextFirstName.getText().toString();
					String lastname = editTextLastName.getText().toString();
					String username = editTextRegisterUsername.getText().toString();
					String password = editTextRegisterPassword.getText().toString();
					nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
					nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
					nameValuePairs.add(new BasicNameValuePair("username", username));
					nameValuePairs.add(new BasicNameValuePair("password", password));
					register(nameValuePairs);
					Toast.makeText(getActivity(), getResources().getString(R.string.register_success_message), Toast.LENGTH_SHORT).show();
				} else {
					String message = "Passwords do not match, please re-enter your password!";
					Log.i(TAG, message);
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		
		return v;
	}
	
	/**
	 * 
	 * @param _nameValuePairs
	 * @return
	 */
	private Object register(List<NameValuePair> _nameValuePairs) {
		Object result = new Object();
		networkHelper = new NetworkHelper();
		result = networkHelper.sendData(_nameValuePairs, TRANSACTION);
		return result;
	}

}
