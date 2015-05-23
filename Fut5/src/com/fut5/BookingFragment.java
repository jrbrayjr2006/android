/**
 * 
 */
package com.fut5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fut5.adapter.BookingListAdapter;
import com.fut5.model.Booking;
import com.fut5.model.SoccerField;
import com.fut5.model.User;

/**
 * @author james_r_bray
 *
 */
public class BookingFragment extends CoreBookingFragment implements FieldSelectionDialogFragment.SoccerFieldDialogCallbackInterface {
	
	private static final String TAG = "BookingFragment";
	
	private ListView mBookingListView;
	private TextView mDateTextView;
	private Button mFieldSelectButton;
	
	//private ArrayAdapter<String> mArrayAdapter;
	
	private BookingListAdapter mBookingListAdapter;
	
	//private ArrayList<String> bookingArrayList;
	
	private String[] bookingTimeArray = {"10:00 AM", "11:00 AM","1:00 PM","2:00 PM","3:00 PM","5:00 PM","6:00 PM", "7:00 PM", "8:00 PM"};
	private List<Booking> bookingArray = new ArrayList<Booking>();
	
	private String mSoccerFieldName; 
	
	private User user;
	
	private SoccerField mSelectedSoccerField;
	
	static final String[] PROJECTION = new String[] {};
	
	public static final String TRANSACTION = "book_time";
	
	public interface SoccerFieldDialogCallbackInterface {
		public String getSoccerFieldName();
	}
	
	BookingAppCallbackListener mCallback;
	
	public BookingFragment(User _user) {
		this.user = _user;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_booking, container, false);
		
		bookingArray = initializeBookingsList();
		mDateTextView = (TextView)v.findViewById(R.id.dateTextView);
		mDateTextView.setText(user.getFirstname() + " - " + formatTodaysDate());
		mFieldSelectButton = (Button)v.findViewById(R.id.fieldSelectButton);
		mFieldSelectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openFieldSelectionDialog();
				
			}});
		
		mBookingListAdapter = new BookingListAdapter(getActivity(),bookingArray);
		mBookingListView = (ListView)v.findViewById(android.R.id.list);
		mBookingListView.setAdapter(mBookingListAdapter);
		
		mBookingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Booking bookThis = bookingArray.get(position);
				
				openBookingDialog(bookThis);
				//Toast.makeText(getActivity(), "Time Selected..." + position, Toast.LENGTH_SHORT).show();
			}
			
		});
		
		mBookingListView.setEnabled(false);
		
		getActivity().setTitle(getActivity().getResources().getString(R.string.booking) + " " + formatTodaysDate());
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
    private List<Booking> initializeBookingsList() {
    	List<Booking> b = new ArrayList<Booking>();
        //SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        //try {
    	for(String bTime: bookingTimeArray) {
    		Booking book = new Booking();
    		book.setDateTime(new Date());
    		book.setBookingTime(bTime);
    		b.add(book);
    	}
        //} catch(ParseException pe) {
        	//Log.e(TAG, pe.getMessage());
        //}
    	
    	return b;
    }
    
    public void openBookingDialog(Booking _booking) {
    	DialogFragment dmBookingTimeSelection = new TimeSelectionDialogFragment(_booking);
    	dmBookingTimeSelection.show(getFragmentManager(), getResources().getString(R.string.booking_select_duration));
    	
    }
    
    /**
     * Enable the list of times after the soccer field has been selected
     */
    public void openFieldSelectionDialog() {
    	Log.d(TAG, "Entering openFieldSelectionDialog()...");
    	DialogFragment dmFieldSelection = new FieldSelectionDialogFragment();
    	dmFieldSelection.setTargetFragment(this, 0);
    	dmFieldSelection.show(getFragmentManager(), getResources().getString(R.string.booking_field_names));
    	mBookingListView.setEnabled(true);
    	Log.d(TAG, "Exiting openFieldSelectionDialog()...");
    }
    
    private String formatTodaysDate() {
		String formattedDate = null;
		Date rawDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		formattedDate = sdf.format(rawDate);
		
		return formattedDate;
	}

	@Override
	public String getSoccerFieldName(String name) {
		mSoccerFieldName = name;
		mFieldSelectButton.setText(mSoccerFieldName);
		return mSoccerFieldName;
	}


}
