/**
 * 
 */
package com.fut5.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.fut5.BookingFragment;
import com.fut5.FieldSelectionDialogFragment;
import com.fut5.MyBookingsFragment;
import com.fut5.RegisterFragment;
import com.fut5.model.Booking;
import com.fut5.model.SoccerField;
import com.fut5.model.User;
//import org.apache.http.message.BasicNameValuePair;

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
        AsyncTask<Object, Void, Object> async = connect.execute(params);
        try {
        	List<Object> rawResults = (ArrayList<Object>)async.get();
        	String code = (String)rawResults.get(0);
        	Log.d(TAG, "Code is " + code);
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
	 * <p>
	 * Map each request to the proper service endpoint.  The endpoints include the following:
	 * </p>
	 * <ul>
	 * <li>Book Soccer Field 	- book-soccer.php</li>
	 * <li>Register				- register.php</li>
	 * <li>Get User Bookings	- my-bookings.php</li>
	 * <li>Get Soccer Fields	- getSoccerFields.php</li>
	 * </ul>
	 * @param _parameters
	 * @param _transaction
	 * @return Object
	 */
	public Object sendData(List<NameValuePair> _parameters, String _transaction) {
		connect = new NetworkConnector();
		String code;
		ArrayList<Object> mResult;
        //boolean result = false;
        String strUrl = SERVER_URL;
        
        if(_transaction.equals(BookingFragment.TRANSACTION)) {
        	strUrl = strUrl + "/book-soccer.php";
        }
        if(_transaction.equals(RegisterFragment.TRANSACTION)) {
        	strUrl = strUrl + "/register.php";
        }
        if(_transaction.equals(MyBookingsFragment.TRANSACTION)) {
        	strUrl = strUrl + "/my-bookings.php";  // can change this to /getMyBookings.php
        }
        if(_transaction.equals(FieldSelectionDialogFragment.TRANSACTION)) {
        	strUrl = strUrl + "/getSoccerFields.php";
        }


        Object[] params = {strUrl, _parameters, _transaction};
        AsyncTask<Object, Void, Object> async = connect.execute(params);
        try {
        	mResult = (ArrayList<Object>)async.get();
        	// the status code will always be the first element of the list
        	code = (String)mResult.get(0);
        	if(code.equals("200")) {
        		//result = true;
        	}
        	// check to see if there is more than the status code in the list
        	if(mResult.size() > 1) {
        		
        	}
            Log.d(TAG, async.get().toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return mResult;
	}
	
	/**
	 * 
	 * @author james_r_bray
	 *
	 */
	private class NetworkConnector extends AsyncTask<Object, Void, Object> {
		
		private String content;
		private String status;
		String data = "";
		String error = null;
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
        protected Object doInBackground(Object... params) {
            String serverUrl = (String) params[0];
            @SuppressWarnings("unchecked")
			List<NameValuePair> valuePairs = (ArrayList<NameValuePair>) params[1];
            List<Object> result = new ArrayList<Object>();
            //int result = sendConnectionData(url, valuePairs);
            //Log.d("Results", "Response code is " + Integer.toString(responseCode));
            
            // BEGIN new code here
            
            /******* Make a Post Call to Web Server ******/
            BufferedReader reader = null;

            // Send data
            try {
                // Defined URL where to send data
                URL url = new URL(serverUrl + getQuery(valuePairs));

                // Send POST request data
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read server response
                while((line = reader.readLine()) != null) {
                    // Append server response string
                    sb.append(line + " ");
                }

                // Append server response to content string
                content = sb.toString();
                
                // determine which action to take based on transaction type
                if(params.length > 2) {
                	String transaction = (String) params[2];
                	if(transaction.equals(MyBookingsFragment.TRANSACTION)) {
                		readBookingsJsonData();
                		result.add(status);
                	}
                	if(transaction.equals(FieldSelectionDialogFragment.TRANSACTION)) {
                		Object soccerData = readSoccerFieldJsonData();
                		result.add(status);
                		result.add(soccerData);
                	}
                } else {
                	readJsonData();
                	result.add(status);
                }
                Log.d("Results", "Response code is " + Integer.toString(responseCode));

            } catch(MalformedURLException mue) {
                Log.e(TAG, mue.getMessage());
            } catch(IOException ioe) {
                Log.e(TAG, ioe.getMessage());
            } finally {
                try
                {
                    reader.close();
                } catch(IOException ioe) {
                    // do nothing here
                }
            }
            
            // END
            
            return result;

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
        
        /**
         * 
         */
        protected void readJsonData() {
        	Log.d(TAG, "Entering readJsonData()...");
            // Close dialog
            //dialog.dismiss();

            if(error != null) {
                Log.w(TAG, "Output : " + error);
            } else {
                // Show response json on screen
                Log.d(TAG,content);

                /****** Start parse response data ******/
                String outputData = "";
                JSONObject jsonResponse;

                try {
                    /****** Creates a new JSONObject with name/value mappings from JSON string *****/
                    jsonResponse = new JSONObject(content);

                    /****** Returns the value mapped by name if it exists and is a JSONArray ******/
                    /****** returns null otherwise ******/
                    //JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

                    //JSONObject jsonBookingStatusNode = jsonResponse.optJSONObject("status");
                    status = jsonResponse.optString("status");
                    outputData = status;
                    String statusMessage = jsonResponse.optString("status_message");
                    outputData = "Status: " + status + ",/n Status Message: " + statusMessage;
                    String arrayString = jsonResponse.optString("data");
                    JSONArray jsonBookingDataNode = new JSONArray(arrayString);

                    /****** Process each JSON Node ******/
                    int lengthJsonArr = jsonBookingDataNode.length();

                    StringBuilder sb2 = new StringBuilder();
                    User user = User.getInstance();
                    for(int i = 0; i < lengthJsonArr ; i++) {
                        JSONObject jsonChildNode = jsonBookingDataNode.getJSONObject(i);
                        String id = jsonChildNode.optString("id");
                        String username = jsonChildNode.optString("username");
                        String firstname = jsonChildNode.optString("firstname");
                        String lastname = jsonChildNode.optString("lastname");
                        
                        user.setUserId(Integer.parseInt(id));
                        user.setUsername(username);
                        user.setFirstname(firstname);
                        user.setLastname(lastname);

                        sb2.append("/n");
                        sb2.append("id: " + id);
                        sb2.append("/n");
                        sb2.append("username: " + username);
                        sb2.append("/n");
                        sb2.append("firstname: " + firstname);
                        sb2.append("/n");
                        sb2.append("lastname: " + lastname);
                        sb2.append("/n");
                    }

                    outputData = outputData + sb2.toString();
                    Log.d(TAG, outputData);
                } catch(JSONException je) {
                    Log.e(TAG, je.getMessage());
                }
            }
            Log.d(TAG, "Exiting readJsonData()...");
        }
        
        /**
         * 
         */
        protected void readBookingsJsonData() {
        	Log.d(TAG, "Entering readJsonData(JsonReader)...");
            // Close dialog
            //dialog.dismiss();
        	List<Booking> bookings = new ArrayList<Booking>();

            if(error != null) {
                Log.w(TAG, "Output : " + error);
            } else {
                // Show response json on screen
                Log.d(TAG,content);

                /****** Start parse response data ******/
                String outputData = "";
                JSONObject jsonResponse;

                try {
                    /****** Creates a new JSONObject with name/value mappings from JSON string *****/
                    jsonResponse = new JSONObject(content);

                    /****** Returns the value mapped by name if it exists and is a JSONArray ******/
                    /****** returns null otherwise ******/
                    //JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

                    //JSONObject jsonBookingStatusNode = jsonResponse.optJSONObject("status");
                    status = jsonResponse.optString("status");
                    outputData = status;
                    String statusMessage = jsonResponse.optString("status_message");
                    outputData = "Status: " + status + ",/n Status Message: " + statusMessage;
                    String arrayString = jsonResponse.optString("data");
                    JSONArray jsonBookingDataNode = new JSONArray(arrayString);

                    /****** Process each JSON Node ******/
                    int lengthJsonArr = jsonBookingDataNode.length();

                    StringBuilder sb2 = new StringBuilder();
                    User user = User.getInstance();
                    for(int i = 0; i < lengthJsonArr ; i++) {
                        JSONObject jsonChildNode = jsonBookingDataNode.getJSONObject(i);
                        String id = jsonChildNode.optString("soccer_id");
                        String location = jsonChildNode.optString("location");
                        String name = jsonChildNode.optString("name");
                        String timeslot = jsonChildNode.optString("timeslot");
                        int duration = jsonChildNode.optInt("duration");
                        
                        Booking booking = new Booking();
                        booking.setBookingTime(timeslot);
                        booking.setSoccerFieldName(name);
                        booking.setDuration(duration);
                        
                        bookings.add(booking);
                    }
                    
                    user.setMyBookings(bookings);
                    outputData = outputData + sb2.toString();
                    Log.d(TAG, outputData);
                } catch(JSONException je) {
                    Log.e(TAG, je.getMessage());
                }
            }
            Log.d(TAG, "Exiting readJsonData(JsonReader)...");
        }
        
        protected Object readSoccerFieldJsonData() {
        	Log.d(TAG, "Entering readJsonData(JsonReader)...");
            // Close dialog
            //dialog.dismiss();
        	List<SoccerField> soccerFields = new ArrayList<SoccerField>();

            if(error != null) {
                Log.w(TAG, "Output : " + error);
            } else {
                // Show response json on screen
                Log.d(TAG,content);

                /****** Start parse response data ******/
                String outputData = "";
                JSONObject jsonResponse;

                try {
                    /****** Creates a new JSONObject with name/value mappings from JSON string *****/
                    jsonResponse = new JSONObject(content);

                    /****** Returns the value mapped by name if it exists and is a JSONArray ******/
                    /****** returns null otherwise ******/
                    //JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

                    //JSONObject jsonBookingStatusNode = jsonResponse.optJSONObject("status");
                    status = jsonResponse.optString("status");
                    outputData = status;
                    String statusMessage = jsonResponse.optString("status_message");
                    outputData = "Status: " + status + ",/n Status Message: " + statusMessage;
                    String arrayString = jsonResponse.optString("data");
                    JSONArray jsonBookingDataNode = new JSONArray(arrayString);

                    /****** Process each JSON Node ******/
                    int lengthJsonArr = jsonBookingDataNode.length();

                    StringBuilder sb2 = new StringBuilder();
                    //User user = User.getInstance();
                    for(int i = 0; i < lengthJsonArr ; i++) {
                        JSONObject jsonChildNode = jsonBookingDataNode.getJSONObject(i);
                        String id = jsonChildNode.optString("id");
                        String location = jsonChildNode.optString("location");
                        String name = jsonChildNode.optString("name");
                        String size = jsonChildNode.optString("size");
                        String type = jsonChildNode.optString("type");
                        
                        SoccerField field = new SoccerField();
                        field.setId(Integer.parseInt(id));
                        field.setLocation(location);
                        field.setSize(size);
                        field.setName(name);
                        field.setType(type);
                        
                        soccerFields.add(field);
                    }
                    
                    //user.setMyBookings(bookings);
                    outputData = outputData + sb2.toString();
                    Log.d(TAG, outputData);
                } catch(JSONException je) {
                    Log.e(TAG, je.getMessage());
                }
            }
            Log.d(TAG, "Exiting readJsonData(JsonReader)...");
            return soccerFields;
        }

        public int getResponseCode() {
            return responseCode;
        }
		
	}

}
