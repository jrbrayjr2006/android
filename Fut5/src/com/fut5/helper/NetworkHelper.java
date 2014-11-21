/**
 * 
 */
package com.fut5.helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

/**
 * <pre>
 * <b>Description: </b> Helper class to make network connections in a separate thread from the main application  thread.
 * </pre>
 * @author james_r_bray
 * @version 0.1
 *
 */
public class NetworkHelper {
	
	NetworkConnector connect;
	
	public static final String TAG = "NetworkHelper";
	public static final String SERVER_URL = "http://54.88.113.204/soccerbooking";  //TODO get server url

	/**
	 * 
	 */
	public NetworkHelper() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void connectToNetwork(String username, String password) {
		//TODO
	}
	
	public boolean sendLoginData(List<NameValuePair> parameters) {
        connect = new NetworkConnector();
        String strUrl = SERVER_URL;

        //TODO change this once code is ready for production
        if ((parameters == null) || (parameters.isEmpty())) {
        	Log.d(TAG, "Parameters are null!");
            parameters.add(new BasicNameValuePair("username", "test"));
            parameters.add(new BasicNameValuePair("password", "test123"));
        }

        Object[] params = {strUrl, parameters};
        AsyncTask<Object, Void, String> async = connect.execute(params);
        try {
            Log.d(TAG, async.get().toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }
	
	/**
	 * 
	 * @author james_r_bray
	 *
	 */
	private class NetworkConnector extends AsyncTask<Object, Void, String> {

		int responseCode = 0;
        String response = null;

        /**
         * <pre>
         *     Implementation of abstract method
         * </pre>
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            List<NameValuePair> valuePairs = (ArrayList<NameValuePair>) params[1];
            String result = sendConnectionData(url, valuePairs);
            Log.d("Results", result);
            return result;

        }

        /**
         * @param _serverUrl
         * @return
         */
        public String sendConnectionData(String _serverUrl, List<NameValuePair> parameters) {
            OutputStream out = null;
            String queryString = null;


            try {
                URL url = new URL(_serverUrl + "/login_service.php");
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();


                httpConn.setRequestMethod("POST");
                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConn.setDoInput(true);
                httpConn.setDoOutput(true);

                out = httpConn.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                queryString = getQuery(parameters);
                bufferedWriter.write(queryString);
                bufferedWriter.flush();
                bufferedWriter.close();
                httpConn.connect();
                responseCode = httpConn.getResponseCode();
                Log.d(TAG, Integer.toString(responseCode));
                // this.response = streamToString(httpConn.getInputStream());
                // Log.d("Response Text", response);
                //Log.d("Response code", Integer.toString(responseCode));
            } catch (MalformedURLException mue) {
                Log.e("Error", mue.getMessage());
            } catch (IOException ioe) {
                Log.e("Error", ioe.getMessage());
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ioe) {
                        Log.e("Error", "Closing input stream: " + ioe.getMessage());
                    }
                }
            }
            Log.d("POST URL", _serverUrl + queryString);
            return "Response code is " + Integer.toString(responseCode);
        }
        
        /**
         * Build query string for HTTP Post
         * @param params
         * @return
         * @throws UnsupportedEncodingException
         */
        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
            return result.toString();
        }

        public int getResponseCode() {
            return responseCode;
        }
		
	}

}
