package com.fut5;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity implements LoginFragment.OnLoginButtonClickedListener, CoreBookingFragment.BookingAppCallbackListener {
	
	private DrawerLayout mDrawerLayout;
	private String[] mNavigationArray;
	private ListView mNavigationList;
	private FragmentManager fragmentManager;
	Fragment loginFragment;
	Fragment bookingFragment;
	Fragment myBookingsFragment;

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
		bookingFragment = fragmentManager.findFragmentById(R.id.booking_fragment);
		bookingFragment = new BookingFragment();
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
		
		myBookingsFragment = new MyBookingsFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, myBookingsFragment).commit();
		setTitle(getResources().getString(R.string.action_my_bookings));
	}
	
	/**
	 * Navigation to booking screen
	 */
	private void goToBookings() {
		if(fragmentManager == null) {
			fragmentManager = getFragmentManager();
		}
		
		myBookingsFragment = new BookingFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, myBookingsFragment).commit();
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
}
