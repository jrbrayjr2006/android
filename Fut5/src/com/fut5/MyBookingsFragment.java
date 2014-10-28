/**
 * 
 */
package com.fut5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author james_r_bray
 *
 */
public class MyBookingsFragment extends CoreBookingFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.my_booking_fragment, parent, false);;
		
		return v;
	}

}
