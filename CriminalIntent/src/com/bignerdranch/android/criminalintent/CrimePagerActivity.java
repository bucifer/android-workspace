package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {
	
	//this activity will replace our CrimeActivity (detail view) because we want the "swiping" feature of ViewPager
	//we will be able to swipe between Crime objects using this architecture
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager); //we didn't want to use any new layout file for this because it's a simple viewPager
		//thus, we just create a res/values/ids.xml by hand, add an item (type id) with name viewPager and assign it here
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return mCrimes.size();
			}
			@Override
			public Fragment getItem(int pos) {
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getID());
			}
		});
		
		//this is needed because ViewPager by default starts from index 0 of the array
		//so if you select something from Listview, it will always start from Crime #0
		//to prevent it, we get the extra, loop through crimes, find the right id and display it
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getID().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
		//a listener method for changes in the ViewPager's page being displayed
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageScrollStateChanged(int state) { }
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }
			public void onPageSelected(int pos) {
				Crime crime = mCrimes.get(pos);
				if (crime.getTitle() != null) {
					setTitle(crime.getTitle());
				}
			}
		});
	}
			
}
