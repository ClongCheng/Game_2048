package com.chengl.game_2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mScoreTextView;
	private static MainActivity mActivity = null;
	private int mScore = 0;
	
	public MainActivity() {
		mActivity = this;
	}
	
	public static MainActivity getMainActivity() {
		return mActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mScoreTextView = (TextView) findViewById(R.id.scoreTView);

	}
	
	public void clearScore() {
		mScore = 0;
		showScore();
	}
	
	public void showScore() {
		mScoreTextView.setText(mScore + "");
	}
	
	public void addScore(int s) {
		mScore += s;
		showScore();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
