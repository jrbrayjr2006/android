/**
 * 
 */
package com.fut5;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fut5.helper.NetworkHelper;
import com.fut5.model.Booking;


/**
 * <pre>
 * Create a dialog with the hours selection for booking
 * <ul>
 * <li>1 hour</li>
 * <li>2 hours</li>
 * </ul>
 * </pre>
 * 
 * @author james_r_bray
 *
 */
public class TimeSelectionDialogFragment extends DialogFragment {
	
	private static final String TAG = "TimeSelectionDialogFragment";
	private static final String TRANSACTION = "book_time";
	
	Booking mBooking;
	List<NameValuePair> nameValuePairs;
	NetworkHelper networkHelper;
	
	String mBookingInfoMessage;
	
	public TimeSelectionDialogFragment(Booking _booking){
		super();
		mBooking = _booking;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getResources().getString(R.string.booking_select_duration));
		builder.setItems(getResources().getStringArray(R.array.booking_times), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int duration) {
				// TODO Auto-generated method stub
				boolean bookedFieldAndTime = false;
				switch(duration) {
				case 0:
					mBooking.setDuration(duration + 1);
					bookedFieldAndTime = saveSelectedTimeInBookingEngine(mBooking);
					break;
				case 1:
					mBooking.setDuration(duration + 1);
					bookedFieldAndTime = saveSelectedTimeInBookingEngine(mBooking);
					break;
				default:
					Toast.makeText(getActivity(), "Nothing selected!", Toast.LENGTH_SHORT).show();
					break;
				}
				
				if(bookedFieldAndTime) {
					mBookingInfoMessage = getResources().getString(R.string.press_ok_message);
					DialogFragment bookingInfoDialogFragment = new BookingInfoDialogFragment(mBookingInfoMessage);
					bookingInfoDialogFragment.show(getFragmentManager(), TAG);
				} else {
					mBookingInfoMessage = getResources().getString(R.string.unable_to_book_message);
					DialogFragment bookingInfoDialogFragment = new BookingInfoDialogFragment(mBookingInfoMessage);
					bookingInfoDialogFragment.show(getFragmentManager(), TAG);
				}
			}
		});
		
		return builder.create();
	}
	
	/**
	 * Use the network helper to record the booking
	 */
	private boolean saveSelectedTimeInBookingEngine(Booking _booking) {
		nameValuePairs = new ArrayList<NameValuePair>();
		//TODO make a call to booking engine
		boolean result = true;  //TODO Set to false by default and only reset to true when booking confirmed
    	networkHelper = new NetworkHelper();
    	nameValuePairs.add(new BasicNameValuePair("timeslot", mBooking.getBookingTime()));
    	nameValuePairs.add(new BasicNameValuePair("datetime", mBooking.getDateTime().toString()));
    	nameValuePairs.add(new BasicNameValuePair("duration", Integer.toString(_booking.getDuration())));
    	networkHelper.sendData(nameValuePairs, TRANSACTION);
    	String message = "Booked " + _booking.getDuration() + " hour(s) at " + _booking.getBookingTime();
    	Log.d(TAG, message);
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		Log.d(TAG, "Exiting saveSelectedTimeInBookingEngine...");
		return result;
	}

}
