package com.fut5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends FragmentActivity implements LoginFragment.OnLoginButtonClickedListener, CoreBookingFragment.BookingAppCallbackListener {
	
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
        
        fragmentManager = getSupportFragmentManager();
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
            return true;
        }
        if (id == R.id.my_bookings) {
        	goToMyBookings();
        }
        if(id == R.id.bookings) {
        	goToBookings();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Replace one fragment with another
     */
	@Override
	public void onLoginButtonClicked() {
		if(fragmentManager == null) {
			fragmentManager = getSupportFragmentManager();
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
			fragmentManager = getSupportFragmentManager();
		}
		
		myBookingsFragment = new MyBookingsFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, myBookingsFragment).commit();
		setTitle(getResources().getString(R.string.action_my_bookings));
	}
	
	private void goToBookings() {
		if(fragmentManager == null) {
			fragmentManager = getSupportFragmentManager();
		}
		
		myBookingsFragment = new BookingFragment();
		fragmentManager.beginTransaction().replace(R.id.fragmentContainer, myBookingsFragment).commit();
		setTitle(getResources().getString(R.string.action_bookings));
	}
}
