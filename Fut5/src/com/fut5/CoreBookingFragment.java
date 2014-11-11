/**
 * 
 */
package com.fut5;

import android.app.Fragment;

/**
 * <pre>
 * This class should never be used by itself.  It is designed to be extended.
 * </pre>
 * @author james_r_bray
 *
 */
public abstract class CoreBookingFragment extends Fragment {
	
	public interface BookingAppCallbackListener {
		//TODO add method signatures here
		public void onBookingItemClicked();
	}

}
