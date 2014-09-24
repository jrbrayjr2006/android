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
		String[] times = {"12:00 PM"};
		Booking b = new Booking();
		b.setBookingTime("12:00 PM");
		mBookings.add(new Booking());
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
