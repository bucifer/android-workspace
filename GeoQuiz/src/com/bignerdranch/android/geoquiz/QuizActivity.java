package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {
	
	private Button mTrueButton;
	private Button mFalseButton;
	
	private Button mPrevButton;
	private Button mNextButton;
	
	private Button mCheatButton;
	
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionsBank = new TrueFalse[] {
		new TrueFalse(R.string.question_oceans, true),
		new TrueFalse(R.string.question_mideast, false),
		new TrueFalse(R.string.question_americas, true),
		new TrueFalse(R.string.question_asia, true),
	};
	private int mCurrentIndex = 0;
	private static final String TAG = "TERRY";
	private static final String KEY_INDEX = "index";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        
        //saved instance state is a way of persisting data even when activities get destroyed on device orientation rotation
        if (savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        
        mQuestionTextView = (TextView) findViewById(R.id.question_textview);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
				updateQuestion();
			}
		});
        
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(true);
			}
		});
        
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(false);
			}
		});
        
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
				updateQuestion();
			}
		});
        
        mPrevButton = (Button) findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCurrentIndex = (mCurrentIndex - 1);
				if (mCurrentIndex < 0)
					mCurrentIndex = mQuestionsBank.length-1;
				updateQuestion();
			}
		});
        
        updateQuestion();
        
        setUpCheatButton();
    }
    
    private void setUpCheatButton() {
    	mCheatButton = (Button) findViewById(R.id.cheat_button);
    	mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//the Intent constructor in this case uses (Context, class) parameters.
				//Context refers to the package that the new activity should be found in
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				startActivity(i);
			}
		});
    }
    
    private void updateQuestion() {
		int IdResForNextQuestionString = mQuestionsBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(IdResForNextQuestionString);
    }

    private void checkAnswer(boolean userPressedTrue) {
    	boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isTrueQuestion();
    	int messageResId = 0;
    	if (userPressedTrue == answerIsTrue) {
    		messageResId = R.string.correct_toast;
    	} else {
    		messageResId = R.string.incorrect_toast;
    	}
    	Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    //this override of onSaveInstanceState is necessary to persist questions ordering between device rotations
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(savedInstanceState);
    	Log.i(TAG, "On Save Instance State");
    	savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
