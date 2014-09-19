/**
 * 
 */
package com.fut5;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author james_r_bray
 *
 */
public class LoginFragment extends Fragment {
	
	OnLoginButtonClickedListener mCallback;
	
	/**
	 * Listener to communicate with parent other fragments via parent activity
	 * @author james_r_bray
	 *
	 */
	public interface OnLoginButtonClickedListener {
		public void onLoginButtonClicked();
	}
	
	private Button mLoginButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		super.onCreateView(inflater, parent, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_login, parent, false);
		
		mLoginButton = (Button)v.findViewById(R.id.buttonLogin);
		
		mLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO replace toast with action to login
				Toast.makeText(getActivity(), "Logging In...", Toast.LENGTH_SHORT).show();
				mCallback.onLoginButtonClicked();
				
			}
			
		});
		
		return v;
	}
	
	/**
	 * set up a callback to the main activity to go to the next screen
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		mCallback = (OnLoginButtonClickedListener) activity;
		} catch(ClassCastException cce) {
			//TODO add exception handling here
		}
	}

}
