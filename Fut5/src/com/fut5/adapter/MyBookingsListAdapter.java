/**
 * 
 */
package com.fut5.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fut5.R;
import com.fut5.model.Booking;

/**
 * @author james_r_bray
 *
 */
public class MyBookingsListAdapter extends ArrayAdapter<Booking> {
	
	private final LayoutInflater mInflater;
	private TextView mGameTimeValueTextView;

	public MyBookingsListAdapter(Context context, List<Booking> objects) {
		super(context, -1, objects);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Booking booking = getItem(position);
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.list_my_bookings_row, parent, false);
		}
		
		mGameTimeValueTextView = (TextView)convertView.findViewById(R.id.gameTimeValueTextView);
		mGameTimeValueTextView.setText(booking.getBookingTime());
		
		return convertView;
	}

}
