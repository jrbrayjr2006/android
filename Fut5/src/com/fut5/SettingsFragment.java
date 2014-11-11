/**
 * 
 */
package com.fut5;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * @author james_r_bray
 *
 */
public class SettingsFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
