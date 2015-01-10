/**
 * 
 */
package com.fut5.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.fut5.BookingFragment;

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
        boolean result = false;
        String strUrl = SERVER_URL + "/login.php";


        Object[] params = {strUrl, parameters};
        AsyncTask<Object, Void, String> async = connect.execute(params);
        try {
        	String code = async.get().toString();
        	if(code.equals("200")) {
        		result = true;
        	}
            Log.d(TAG, async.get().toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return result;
    }
	
	public boolean sendData(List<NameValuePair> _parameters, String _transaction) {
		connect = new NetworkConnector();
        boolean result = false;
        String strUrl = SERVER_URL;
        
        if(_transaction.equals(BookingFragment.TRANSACTION)) {
        	strUrl = strUrl + "/book-soccer.php";
        }


        Object[] params = {strUrl, _parameters};
        AsyncTask<Object, Void, String> async = connect.execute(params);
        try {
        	String code = async.get().toString();
        	if(code.equals("200")) {
        		result = true;
        	}
            Log.d(TAG, async.get().toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return result;
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
            int result = sendConnectionData(url, valuePairs);
            Log.d("Results", "Response code is " + Integer.toString(responseCode));
            return Integer.toString(result);

        }

        /**
         * @param _serverUrl
         * @return
         */
        public int sendConnectionData(String _serverUrl, List<NameValuePair> parameters) {
            OutputStream out = null;
            String queryString = null;

            try {
            	Log.d(TAG, "opening " + _serverUrl + getQuery(parameters));
                URL url = new URL(_serverUrl + getQuery(parameters));
                Log.d(TAG, url.getPath() + url.getQuery());
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();


                httpConn.setRequestMethod("POST");
                httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpConn.setDoInput(true);
                httpConn.setDoOutput(true);
                responseCode = httpConn.getResponseCode();
                Log.d(TAG, "The response code on first try is " + responseCode);

                out = httpConn.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                //queryString = getQuery(parameters);
                //bufferedWriter.write(queryString);
                bufferedWriter.flush();
                bufferedWriter.close();
                httpConn.connect();
                responseCode = httpConn.getResponseCode();
                Log.d(TAG, "The response code on second try is " + responseCode);
                BufferedReader reader = null;
                // read the output from the server
                reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
           
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                  stringBuilder.append(line + "\n");
                }
                Log.d(TAG, "URL " + stringBuilder.toString());
                Log.d(TAG, "The url path is " + httpConn.getURL().getPath());
                Log.d(TAG, "The query is " + httpConn.getURL().getQuery());
                Log.d(TAG, Integer.toString(responseCode));
                // this.response = streamToString(httpConn.getInputStream());
                // Log.d("Response Text", response);
                //Log.d("Response code", Integer.toString(responseCode));
            } catch (MalformedURLException mue) {
                Log.e("MalformedURLException Error", "" + mue.getMessage());
            } catch (IOException ioe) {
                Log.e("IOException Error", ioe.getMessage());
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
            //return "Response code is " + Integer.toString(responseCode);
            return responseCode;
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
                if (first) {
                	result.append("?");
                    first = false;
                }
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
            Log.d(TAG, "Query String:  " + result.toString());
            return result.toString();
        }

        public int getResponseCode() {
            return responseCode;
        }
		
	}

}
