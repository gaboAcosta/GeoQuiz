package com.gaboacosta.geoquiz;

import java.io.Serializable;

public class TrueFalse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private int mQuestion;	
	private boolean mTrueQuestion;
	private boolean mIsCheater = false;
	
	public TrueFalse(int question, boolean trueQuestion){
		mQuestion     = question;
		mTrueQuestion = trueQuestion;
	}
	
	public int getQuestion(){
		return mQuestion;
	}
	
	public void setQuestion(int question){
		mQuestion = question;
	}
	
	public boolean isTrueQuestion(){
		return mTrueQuestion;
	}
	
	public void setTrueQuestion(boolean trueQuestion){
		mTrueQuestion = trueQuestion;
	}
	
	public void setCheated(boolean cheat){
		mIsCheater = cheat;
	}
	
	public boolean userCheated(){
		return mIsCheater;
	}

}
