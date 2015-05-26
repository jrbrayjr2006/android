package com.fut5;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.fut5.model.Booking;
import com.fut5.model.User;


public class MainActivity extends Activity implements LoginFragment.OnLoginButtonClickedListener, CoreBookingFragment.BookingAppCallbackListener {
	
	private boolean loggedin = false;
	private DrawerLayout mDrawerLayout;
	private String[] mNavigationArray;
	private ListView mNavigationList;
	private FragmentManager fragmentManager;
	private User user;
	
	public static final String MY_BOOKINGS_FRAGMENT_TAG = "MY_BOOKINGS_FRAGMENT";
	
	/**
	 * List of bookings the user currently has
	 */
	private List<Booking> myBookings;
	
	Fragment loginFragment;
	Fragment bookingFragment;
	Fragment myBookingsFragment;
	Fragment mRegisterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mDrawerLayout = (DrawerLayout)findViewById(R.layout.booking_drawer_fragment);
        
        fragmentManager = getFragmentManager();
        loginFragment = fragmentManager.findFragmentById(R.id.login_fragment);
        if(loginFragment == null) {
        	loginFragment = new LoginFragment();
        	fragmentManager.beginTransaction().add(R.id.fragmentContainer,loginFragment).commit();
        }
        setTitle(getResources().getString(R.string.app_name));
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
    	if(loggedin == false) {
            loginFragment = fragmentManager.findFragmentById(R.id.login_fragment);
            if(loginFragment == null) {
            	loginFragment = new LoginFragment();
            	fragmentManager.beginTransaction().add(R.id.fragmentContainer,loginFragment).commit();
            }
    	} else {
    		myBookingsFragment = new BookingFragment(user);
    		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, myBookingsFragment).commit();
    		setTitle(getResources().getString(R.string.action_bookings));
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home) {
        	Toast.makeText(this, "Home Button Clicked!", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_settings) {
        	goToSettings();
            return true;
        }
        if (id == R.id.my_bookings) {
        	goToMyBookings();
        }
        if(id == R.id.bookings) {
        	goToBookings();
        }
        if(id == R.id.knockout) {
        	goToKnockoutList();
        }
        switch(id) {
        case android.R.id.home:
        	Toast.makeText(this, "Home Button Clicked!", Toast.LENGTH_SHORT).show();
        	break;
        case R.id.action_settings:
        	goToSettings();
        	break;
        case R.id.my_bookings:
        	goToMyBookings();
        	break;
        case R.id.bookings:
        	goToBookings();
        	break;
        case R.id.knockout:
        	goToKnockoutList();
        	break;
        case R.id.logout:
        	logout(this);
        	break;
        default:
        	break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Replace one fragment with another
     */
	@Override
	public void onLoginButtonClicked() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		if(user == null) {
			user = User.getInstance();
		}
		loggedin = true;
		bookingFragment = fragmentManager.findFragmentById(R.id.booking_fragment);
		bookingFragment = new BookingFragment(user);
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, bookingFragment).commit();
	}


	@Override
	public void onBookingItemClicked() {
		// TODO Auto-generated method stub
		goToMyBookings();
	}
	
	private void goToMyBookings() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		
		myBookingsFragment = new MyBookingsFragment(user);
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, myBookingsFragment, MY_BOOKINGS_FRAGMENT_TAG).commit();
		setTitle(getResources().getString(R.string.action_my_bookings));
	}
	
	/**
	 * Navigation to booking screen
	 */
	private void goToBookings() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		
		bookingFragment = new BookingFragment(user);
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, bookingFragment).commit();
		setTitle(getResources().getString(R.string.action_bookings));
	}
	
	/**
	 * Navigation to knockout list screen
	 */
	private void goToKnockoutList() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		Fragment knockoutFragment = new KnockoutFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, knockoutFragment).commit();
		setTitle(getResources().getString(R.string.knockout_list));
	}
	
	private void goToSettings() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		Fragment settingsFragment = new SettingsFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, settingsFragment).commit();
		setTitle(getResources().getString(R.string.knockout_list));
	}
	
	private void logout(Activity activity) {
		Toast.makeText(this, R.string.logout_message, Toast.LENGTH_SHORT).show();
		activity.finish();
		
	}


	@Override
	public void onRegisterTouch() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		
		mRegisterFragment = new RegisterFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, mRegisterFragment).commit();
		setTitle(getResources().getString(R.string.register));
		
	}
	
	private String formatTodaysDate() {
		String formattedDate = null;
		Date rawDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.US);
		formattedDate = sdf.format(rawDate);
		
		return formattedDate;
	}
}
