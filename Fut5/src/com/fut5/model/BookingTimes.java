/**
 * 
 */
package com.fut5.model;

import java.util.ArrayList;

import android.content.Context;

/**
 * @author james_r_bray
 *
 */
public class BookingTimes {
	
	private static BookingTimes sBookingTimes;
	private ArrayList<Booking> mBookings;
	private Context mAppContext;
	
	private BookingTimes(Context context) {
		mAppContext = context;
		mBookings = new ArrayList<Booking>();
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static BookingTimes getInstance(Context context) {
		if(sBookingTimes == null) {
			sBookingTimes = new BookingTimes(context.getApplicationContext());
		}
		return sBookingTimes;
	}

	public ArrayList<Booking> getmBookings() {
		//TODO remove this development code before release
		String[] times = {"12:00 PM", "1:00 PM", "2:00 PM"};
		int i = 0;
		while(i < times.length) {
			Booking b = new Booking();
			b.setBookingTime(times[i]);
			mBookings.add(b);
			i++;
		}
		return mBookings;
	}

	public void setmBookings(ArrayList<Booking> mBookings) {
		this.mBookings = mBookings;
	}

	public Context getmAppContext() {
		return mAppContext;
	}

	public void setmAppContext(Context mAppContext) {
		this.mAppContext = mAppContext;
	}

}
