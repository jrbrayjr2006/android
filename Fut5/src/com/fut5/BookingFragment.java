/**
 * 
 */
package com.fut5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fut5.adapter.BookingListAdapter;
import com.fut5.model.Booking;

/**
 * @author james_r_bray
 *
 */
public class BookingFragment extends CoreBookingFragment {
	
	private ListView mBookingListView;
	
	private ArrayAdapter<String> mArrayAdapter;
	
	private BookingListAdapter mBookingListAdapter;
	
	private ArrayList<String> bookingArrayList;
	
	private String[] bookingTimeArray = {"10:00 AM", "11:00 AM","1:00 PM","2:00 PM","3:00 PM","5:00 PM","6:00 PM", "7:00 PM", "8:00 PM"};
	private List<Booking> bookingArray = new ArrayList<Booking>();
	
	static final String[] PROJECTION = new String[] {};
	
	BookingAppCallbackListener mCallback;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_booking, container, false);
		
		//int[] toViews = {android.R.id.text1};
		
		int layoutID = android.R.layout.simple_list_item_1;
		int bookingLayoutID = R.layout.booking_item;
		
		mArrayAdapter = new ArrayAdapter<String>(getActivity(),layoutID,bookingTimeArray); //TODO to be removed
		bookingArray = populateDummyBookings();
		mBookingListAdapter = new BookingListAdapter(getActivity(),bookingArray);
		mBookingListView = (ListView)v.findViewById(android.R.id.list);
		//bookingArrayList = new ArrayList<String>(Arrays.asList(bookingTimeArray));
		
		//mBookingListView.setAdapter(mArrayAdapter); //TODO to be removed
		mBookingListView.setAdapter(mBookingListAdapter);
		
		mBookingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Booking bookThis = bookingArray.get(position);
				
				openBookingDialog(bookThis);
				//mCallback.onBookingItemClicked();
				//Toast.makeText(getActivity(), "Time Selected..." + position, Toast.LENGTH_SHORT).show();
			}
			
		});
		//mBookingListView.setId(android.R.id.list);
		getActivity().setTitle(R.string.booking);
		return v;
	}
	
	// Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(getActivity());
    }

    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        //TODO
    }
    
    
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	try {
    		mCallback = (BookingAppCallbackListener)activity;
    	} catch(ClassCastException cce) {
    		Log.e("ERROR","Class cast " + cce.getMessage());
    	}
    }
    
    /**
     * This method is for testing and demo purposes
     * @return
     */
    private List<Booking> populateDummyBookings() {
    	List<Booking> b = new ArrayList<Booking>();

    	for(String bTime: bookingTimeArray) {
    		Booking book = new Booking();
    		book.setBookingTime(bTime);
    		b.add(book);
    	}
    	
    	return b;
    }
    
    public void openBookingDialog(Booking _booking) {
    	DialogFragment dmBookingTimeSelection = new TimeSelectionDialogFragment(_booking);
    	dmBookingTimeSelection.show(getFragmentManager(), getResources().getString(R.string.booking_select_duration));
    	
    }
    
    
    private static class BookingListInnerAdapter extends ArrayAdapter<Booking> {
    	
    	private final LayoutInflater mInflater;

		public BookingListInnerAdapter(Context context, List<Booking> objects) {
			super(context, -1, objects);
			mInflater = LayoutInflater.from(context);
			// TODO Auto-generated constructor stub
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


}
