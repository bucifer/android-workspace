package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN =
			"com.bignerdranch.android.geoquiz.answer_shown";
	
	private boolean mAnswerIsTrue;
	
	private TextView mAnswerTextView;
	private Button mShowButton;
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	//setResult method takes an (int resultCode) as a parameter which is a predefined constant
	//Result_OK and Result_Canceled are options
	//setResult is optional because it's set to "Result_Cancelled" by default but it's a way to distinguish and send different data
	//based on how the user closes out of child activity ex) send different data when user does one thing (OK) or just backs out (Cancel)
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowButton = (Button) findViewById(R.id.showAnswerButton);
		
		mShowButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mAnswerIsTrue)
					mAnswerTextView.setText(R.string.true_button);
				else
					mAnswerTextView.setText(R.string.false_button);
				setAnswerShownResult(true);
			}
		});
		
	}
	
}