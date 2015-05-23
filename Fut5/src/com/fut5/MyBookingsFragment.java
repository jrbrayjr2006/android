/**
 * 
 */
package com.fut5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fut5.adapter.MyBookingsListAdapter;
import com.fut5.helper.NetworkHelper;
import com.fut5.model.Booking;
import com.fut5.model.User;

/**
 * @author james_r_bray
 *
 */
public class MyBookingsFragment extends CoreBookingFragment {
	
	private static final String TAG = "MyBookingsFragment";
	public static final String TRANSACTION = "my-bookings";
	
	private TextView mMyBookingsMessageTextView;
	private ListView mMyBookingsListView;
	private List<Booking> mBookingArray = new ArrayList<Booking>();
	private MyBookingsListAdapter mBookingListAdapter;
	private List<NameValuePair> nameValuePairs;
	private NetworkHelper networkHelper;
	private User user;
	
	public MyBookingsFragment(User _user) {
		this.user = _user;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		super.onCreateView(inflater, parent, savedInstanceState);
		View v = inflater.inflate(R.layout.my_booking_fragment, parent, false);
		
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userId", String.valueOf(user.getUserId()))); //TODO remove hard coded value after testing complete
		//retrieveMyBookings();  // dummy list only
		
		mMyBookingsMessageTextView = (TextView)v.findViewById(R.id.myBookingsMessageTextView);
		mBookingArray = retrieveMyBookings(nameValuePairs);;
		if(mBookingArray.size() == 0) {
			Log.d(TAG, "No bookings retrieved so make message visible");
			mMyBookingsMessageTextView.setVisibility(View.VISIBLE);
		}
		mBookingListAdapter = new MyBookingsListAdapter(getActivity(), mBookingArray);
		mMyBookingsListView = (ListView)v.findViewById(R.id.myBookingsListView);
		mMyBookingsListView.setAdapter(mBookingListAdapter);
		return v;
	}
	
	/**
	 * Get the bookings for the user and put them into Booking objects in an ArrayList
	 * @return
	 */
	private List<Booking> retrieveMyBookings() {
		Log.d(TAG, "Entering retrieveMyBookings()");
		List<Booking> mBookings = new ArrayList<Booking>();
		
		//TODO remove dummy bookings when complete
		Booking mybooking1 = new Booking();
		mybooking1.setAvailable(false);
		mybooking1.setBookingTime("2:00 PM");
		mybooking1.setDateTime(new Date());
		mybooking1.setDuration(2);
		//TODO use NetworkHelper to get data for listing
		networkHelper = new NetworkHelper();
		mBookings.add(mybooking1);
		
		Log.d(TAG, "Exiting retrieveMyBookings()");
		return mBookings;
	}
	
	private List<Booking> retrieveMyBookings(List<NameValuePair> _nameValuePairs) {
		Log.d(TAG, "Entering retrieveMyBookings(List<NameValuePair>)");
		List<Booking> mBookings = new ArrayList<Booking>();
		networkHelper = new NetworkHelper();
		networkHelper.sendData(_nameValuePairs, TRANSACTION);
		
		//TODO populate return values with bookings
		mBookings = user.getMyBookings();
		
		Log.d(TAG, "Exiting retrieveMyBookings(List<NameValuePair>)");
		return mBookings;
	}

}
