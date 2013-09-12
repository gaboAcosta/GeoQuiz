package com.gaboacosta.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	private Button mShowButton;
	private TextView mAnswerText;
	private boolean mAnswerIsTrue;
	
	
	private static final String ANSWER_KEY = "com.gaboacosta.geoquiz.answer";
	private static final String ANSWER_SHOWN = "com.gaboacosta.geoquiz.answer_shown";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerText = (TextView) findViewById(R.id.quetion_answer);
		mAnswerIsTrue = getIntent().getBooleanExtra(ANSWER_KEY, true);
		
		mShowButton = (Button) findViewById(R.id.show_answer);
		mShowButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAnswerShown(true);
				
				if(mAnswerIsTrue){
					mAnswerText.setText(R.string.true_button);
				} else {
					mAnswerText.setText(R.string.false_button);
				}
				
			}
		});
		setAnswerShown(true);
	}

	
	
	private void setAnswerShown(boolean shown){
		Intent data = new Intent();
		data.putExtra(ANSWER_SHOWN, shown);
		setResult(RESULT_OK, data);
	}

}
