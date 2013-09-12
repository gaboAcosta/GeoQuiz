package com.gaboacosta.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private ImageButton mBackButton;
	private ImageButton mNextButton;
	private TextView mQuestionTextView;
	
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX ="currentIndex";
	private static final String KEY_CHEATED ="com.gaboacosta.geoquiz.cheatedList"; 
	private static final String ANSWER_KEY = "com.gaboacosta.geoquiz.answer";
	private static final String ANSWER_SHOWN = "com.gaboacosta.geoquiz.answer_shown";

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
        new TrueFalse(R.string.question_oceans, true),
        new TrueFalse(R.string.question_mideast, false),
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true),
    };
    
    
    private int mCurrentIndex = 0;
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(KEY_CHEATED, mQuestionBank);
	}
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
			mQuestionBank = (TrueFalse[]) savedInstanceState.getSerializable(KEY_CHEATED);
		}
		
		Log.d(TAG,"onCreate called");
		
		setContentView(R.layout.activity_quiz);
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this,CheatActivity.class);
				boolean temp = checkAnswerIsTrue();
				i.putExtra(ANSWER_KEY, temp);
				startActivityForResult(i, 0);
			}
		});
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		updateQuestion();
		
		
		mTrueButton  = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
				
			}
		});
		
		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
				
			}
		});
		
		mBackButton = (ImageButton) findViewById(R.id.back_button);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = mCurrentIndex == 0 ? mQuestionBank.length -1 : mCurrentIndex - 1;				
				updateQuestion();				
			}
		});
		
		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
				
			}
		});
		
		
	}
	
	private void updateQuestion(){
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private boolean checkAnswerIsTrue(){
		return mQuestionBank[mCurrentIndex].isTrueQuestion();
	}
	
	private void checkAnswer(boolean userPressedTrue){
		boolean answerIsTrue = checkAnswerIsTrue();
		int messageResId = 0;
		if(mQuestionBank[mCurrentIndex].userCheated()){
			messageResId = R.string.cheat_toast;
		} else {
			if(answerIsTrue == userPressedTrue){
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}
		
		
		
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data == null) {
			return;
		}
		if(data.getBooleanExtra(ANSWER_SHOWN, false)){
			mQuestionBank[mCurrentIndex].setCheated(true);
		}
		
	}

}
