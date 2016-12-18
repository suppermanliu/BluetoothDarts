package com.hopen.darts.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
 * @author lpmo
 *
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView();
		findViewById();
		setListener();

		fillData();
	}

	protected abstract void setContentView();

	protected abstract void findViewById();

	protected void setListener() {
	}

	protected void fillData() {
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}