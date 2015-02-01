package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


//this is the Fragment responsible for the ListView and each cell of the listview
//It's "HOSTED" by the CrimeListActivity
public class CrimeListFragment extends ListFragment {

	private ArrayList<Crime> mCrimes;
	private static final String TAG = "TERRY";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		//array adapter is the controller between a ListView (aka tableview) and an array data source
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		//array adapter finds a layout, inflates it, iterates through each Crime object in the array and then calls
		//toString() on each object to populate the cells
		
		setListAdapter(adapter);	
		//this will now create a listview but the view of the cell is populated below at View getView
		
		//this will display the memory address of Crime objects hex values which is not good
		//thus, override toString() in Crime class	
	}
	
	
	//you need a subclass of arrayAdapter that knows how to deal with Crime objects 
	//This is like laying out your custom cell
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			//this is almost like cellForRowAtIndexPath
			
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_crime, null);
			}
			// Configure the view for this Crime
			Crime c = getItem(position);
			TextView titleTextView =
					(TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			TextView dateTextView =
					(TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			CheckBox solvedCheckBox =
					(CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			return convertView;
		}
		
	}
	
	//this is like didSelectRowAtIndexPath counterpart for Android
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getID());
		startActivity(i);
	}
	
	//need to refresh Listview data when detail view (crime activity) pops off the stack
	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	
}
