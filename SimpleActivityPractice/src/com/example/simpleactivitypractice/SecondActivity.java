package com.example.simpleactivitypractice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.simpleactivitypractice.MainActivity;


public class SecondActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		TextView myTextViewTwo = (TextView) findViewById(R.id.textView2);
		myTextViewTwo.setText(MainActivity.myString);
	}
}
