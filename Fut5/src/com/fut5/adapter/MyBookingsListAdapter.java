/**
 * 
 */
package com.fut5.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
	private TextView mSoccerFieldName;
	private ImageButton mCancelBookingBtn;

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
		mSoccerFieldName = (TextView)convertView.findViewById(R.id.gameSoccerFieldNameValueTextView);
		mCancelBookingBtn = (ImageButton)convertView.findViewById(R.id.cancelBookingButton);
		
		mGameTimeValueTextView.setText(booking.getBookingTime());
		mSoccerFieldName.setText(booking.getSoccerFieldName());
		
		mCancelBookingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return convertView;
	}

}
