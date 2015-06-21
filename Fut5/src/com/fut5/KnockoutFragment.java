/**
 * 
 */
package com.fut5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author james_r_bray
 *
 */
public class KnockoutFragment extends  CoreBookingFragment {
	
	private static final String TAG = "KnockoutFragment";
	private Button joinTheListBtn;
	
	private ListView mListViewKnockout;
	
	private TextView mNumberOfPlayers;
	
	private Activity mActivity;
	
	
	/**
	 * List of players for display - flesh out getPlayers() method for production
	 */
	private List<String> players = new ArrayList<String>();
	
	private ArrayAdapter<String> aaKnockoutListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_knockout, root, false);
		
		joinTheListBtn = (Button)v.findViewById(R.id.buttonJoinTheList);
		joinTheListBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Opening JoinKnockoutListDialogFragment...");
				openJoinKnockoutDialog() ;
			}});
		
		//TODO replace dummy list with data call
		
		String[] p = getResources().getStringArray(R.array.dummy_players);
		players = new ArrayList<String>(Arrays.asList(p));
		int count = p.length;
		
		aaKnockoutListAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, players);
		
		mListViewKnockout = (ListView)v.findViewById(R.id.listViewKnockout);
		mListViewKnockout.setAdapter(aaKnockoutListAdapter);
		
		mNumberOfPlayers = (TextView)v.findViewById(R.id.textViewNumberOfPlayers);
		mNumberOfPlayers.setText(count + " players");
		
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	/**
	 * Open DialogFragment
	 */
	private void openJoinKnockoutDialog() {
		DialogFragment joinKnockoutListDialog = new JoinKnockoutListDialogFragment();
		joinKnockoutListDialog.show(getActivity().getFragmentManager(), TAG);
	}
	
	/**
	 * Get the players on the knockout list
	 * @return
	 */
	private List<String> getPlayers() {
		//TODO populate list from server database
		return players;
	}

}
