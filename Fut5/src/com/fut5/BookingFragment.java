/**
 * 
 */
package com.fut5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fut5.adapter.BookingListAdapter;

/**
 * @author james_r_bray
 *
 */
public class BookingFragment extends Fragment {
	
	private ListView mBookingListView;
	
	private BookingListAdapter mBookingListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_booking, container, false);
		
		mBookingListView = (ListView)v.findViewById(R.id.bookingListView);
		getActivity().setTitle(R.string.booking);
		return v;
	}

}
