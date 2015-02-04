package com.bignerdranch.android.hellomoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {

	private AudioPlayer mPlayer = new AudioPlayer();
	Button mPlayButton;
	Button mStopButton;
	Button mPauseButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true); 
		//Retaining a fragment is by default false - meaning that when a user rotates, the hosting activity and the hosted fragment is destroyed and recreated
		//we retain this time to allow audioplayer to keep playing through rotations
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);
		mPlayButton = (Button)v.findViewById(R.id.hellomoon_play);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mPlayer.play(getActivity());
			}
		});

		mPauseButton = (Button)v.findViewById(R.id.hellomoon_pause);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPlayer.pause();
			}
		});

		mStopButton = (Button)v.findViewById(R.id.hellomoon_stop);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mPlayer.stop();
			}
		});
		return v;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPlayer.stop();
	}

}
