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
public class BookingListAdapter extends ArrayAdapter<Booking> {
	
	private final LayoutInflater mInflater;

	public BookingListAdapter(Context context, List<Booking> objects) {
		super(context, -1, objects);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Booking booking = getItem(position);
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.booking_item, parent, false);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.booking_time_item);
		tv.setText(booking.getBookingTime());
		
		return convertView;
	}

}
