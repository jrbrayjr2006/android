/**
 * 
 */
package com.fut5;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fut5.helper.NetworkHelper;

/**
 * @author james_r_bray
 *
 */
public class LoginFragment extends Fragment {
	
	OnLoginButtonClickedListener mCallback;
	EditText usernameEditText;
	EditText passwordEditText;
    Button signinBtn;
    HttpClient httpClient;
    HttpPost httpPost;
    List<NameValuePair> nameValuePair;
    HttpResponse response;
    NetworkHelper networkHelper;
	
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
		
		usernameEditText = (EditText)v.findViewById(R.id.editUserName);
        passwordEditText = (EditText)v.findViewById(R.id.editRegisterPassword);
		
		mLoginButton = (Button)v.findViewById(R.id.buttonLogin);
		
		mLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				if((username.length() == 0) || (password.length() == 0)) {
					//bypass authentication if login credentials are missing
					Toast.makeText(getActivity(), getResources().getString(R.string.error_missing_credentials), Toast.LENGTH_SHORT).show();
				} else {
					boolean result = login(usernameEditText.getText().toString(),passwordEditText.getText().toString());
					if(result == true) {
						Toast.makeText(getActivity(), "Logging In...", Toast.LENGTH_SHORT).show();
						mCallback.onLoginButtonClicked();
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.error_authentication_failure), Toast.LENGTH_SHORT).show();
					}
				}
				
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
	
	/**
     * <pre>
     * Make call to remote server to login to application
     * </pre>
     * @param username
     * @param passwd
     * @return
     */
    public boolean login(String username, String password) {
    	boolean result = false;
    	networkHelper = new NetworkHelper();
    	//TODO get rid of test code later
    	result = true;
    	networkHelper.connectToNetwork(username, password);
    	
    	return result;
    }

}
