/**
 * 
 */
package com.fut5;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class LoginFragment extends Fragment {
	
	OnLoginButtonClickedListener mCallback;
	EditText usernameEditText;
	EditText passwordEditText;
    Button signinBtn;
    HttpClient httpClient;
    HttpPost httpPost;
    List<NameValuePair> nameValuePair;
    HttpResponse response;
	
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
				login(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                //TODO add logic to go beyond login if valid
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
	
	/**
     * <pre>
     * Make call to remote server to login to application
     * </pre>
     * @param username
     * @param passwd
     * @return
     */
    public String login(String username, String passwd) {
    	String result = null;
    	httpClient = new DefaultHttpClient();
    	httpPost = new HttpPost(getActivity().getString(R.string.url_login));
    	nameValuePair = new ArrayList<NameValuePair>();
    	nameValuePair.add(new BasicNameValuePair("email",username));
    	nameValuePair.add(new BasicNameValuePair("password",passwd));
    	
    	try {
    		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    	} catch(UnsupportedEncodingException uee) {
    		Log.e("error", uee.getMessage());
    	}
    	
    	try {
    		response = httpClient.execute(httpPost);
    		result = response.toString();
    		Log.d("RESPONSE", result);
    	} catch(ClientProtocolException cpe) {
    		Log.e("error", cpe.getMessage());
    	} catch(IOException ioe) {
    		Log.e("error", ioe.getMessage());
    	}
    	
    	return result;
    }

}
