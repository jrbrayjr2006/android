/**
 * 
 */
package com.fut5.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fut5.MainActivity;
import com.fut5.R;
import com.fut5.helper.NetworkHelper;
import com.fut5.model.Booking;

/**
 * @author james_r_bray
 *
 */
public class MyBookingsListAdapter extends ArrayAdapter<Booking> {
	
	private static final String TAG = "MyBookingsListAdapter";
	public static final String TRANSACTION = "cancelBooking";
	private final LayoutInflater mInflater;
	private TextView mGameTimeValueTextView;
	private TextView mSoccerFieldName;
	private ImageButton mCancelBookingBtn;
	private List<NameValuePair> nameValuePairs;
	private NetworkHelper networkHelper;
	private Context mContext;
	private OnCancelBookingCallbackListener mCallback;
	
	public interface OnCancelBookingCallbackListener {
		public void refreshMyBookings();
	}

	public MyBookingsListAdapter(Context context, List<Booking> objects) {
		super(context, -1, objects);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		MainActivity activity = (MainActivity)context;
		FragmentManager fm = activity.getFragmentManager();
		Fragment currentFragment = fm.findFragmentByTag(MainActivity.MY_BOOKINGS_FRAGMENT_TAG);
		mCallback = (OnCancelBookingCallbackListener)currentFragment;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Booking booking = getItem(position);
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.list_my_bookings_row, parent, false);
		}
		
		mGameTimeValueTextView = (TextView)convertView.findViewById(R.id.gameTimeValueTextView);
		mSoccerFieldName = (TextView)convertView.findViewById(R.id.gameSoccerFieldNameValueTextView);
		mCancelBookingBtn = (ImageButton)convertView.findViewById(R.id.cancelBookingButton);
		
		mGameTimeValueTextView.setText(booking.getBookingTime());
		mSoccerFieldName.setText(booking.getSoccerFieldName());
		
		mCancelBookingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				nameValuePairs = new ArrayList<NameValuePair>();
				//nameValuePairs.add(new BasicNameValuePair("userId", String.valueOf(user.getUserId())));
				nameValuePairs.add(new BasicNameValuePair("userIdForBooking", String.valueOf(booking.getUserId())));
				nameValuePairs.add(new BasicNameValuePair("bookingTimeId", String.valueOf(booking.getBookingTimeId())));
				nameValuePairs.add(new BasicNameValuePair("soccerId", String.valueOf(booking.getSoccerFieldId())));
				// Send request to delete booking record
				deleteBookingSelected(nameValuePairs);
				mCallback.refreshMyBookings();
			}
			
		});
		
		return convertView;
	}
	
	private void deleteBookingSelected(List<NameValuePair> _nameValuePairs) {
		Log.d(TAG, "Entering deleteBookingSelected(List<NameValuePair>)");
		
		networkHelper = new NetworkHelper();
		networkHelper.sendData(_nameValuePairs, TRANSACTION);
		
		Log.d(TAG, "Exiting deleteBookingSelected(List<NameValuePair>)");
	}
	
	private void refreshFragment() {
		// Refresh Fragment
		Activity activity = (MainActivity)mContext.getApplicationContext();
		FragmentManager fm = activity.getFragmentManager();
		Fragment currentFragment = fm.findFragmentByTag(MainActivity.MY_BOOKINGS_FRAGMENT_TAG);
		final FragmentTransaction ft = fm.beginTransaction();
		ft.detach(currentFragment);
		ft.attach(currentFragment);
		ft.commit();
	}

}
