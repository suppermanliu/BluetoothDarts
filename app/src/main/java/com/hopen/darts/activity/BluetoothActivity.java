/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hopen.darts.activity;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bluetoothkit.BluetoothLe;
import com.hopen.darts.bluetoothkit.BluetoothUtil;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.view.BluetoothDialog;
import com.hopen.darts.view.RemindDialog;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
@SuppressLint("NewApi")
public class BluetoothActivity extends Activity {
	private ImageView bar_img;
	private GifDrawable barGif;
	private boolean Connect = false;
	private BluetoothAdapter mBluetoothAdapter;
	private boolean mScanning;
	private BluetoothDialog bluetoothDialog;
	private static final int REQUEST_ENABLE_BT = 1;
	// 10���ֹͣ��������.
	private static final long SCAN_PERIOD = 60000;

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLe.ACTION_GATT_CONNECTED.equals(action)) {
				BluetoothUtil.getInstance().setConnect(true);
				Connect = true;

				if (!barGif.isRunning() && bluetoothDialog != null) {
					bluetoothDialog.closeDialog();
					Log.i("BluetoothActivity ",
							"true---Connect  ----barGif.isPlaying()----"
									+ barGif.isPlaying());
					barGif.start();
				}

			} else if (BluetoothLe.ACTION_GATT_DISCONNECTED.equals(action)) {
				Log.i("BluetoothActivity ", "---------Connect  false");
				Connect = false;
				scanLeDevice(true);
				BluetoothUtil.getInstance().setConnect(false);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.bluetooth);

		initbar();

		// 判断是否支持蓝牙
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
			finish();
		}

		// 判断是否支持蓝牙
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		Log.i("BluetoothActivity ",
				"onCreate---barGif.isPlaying()--" + barGif.isPlaying());
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("BluetoothActivity ",
				"onResume---barGif.isPlaying()--" + barGif.isPlaying());
		// 判断蓝牙是否激活
		if (!mBluetoothAdapter.isEnabled()) {

			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}

		scanLeDevice(true);
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (!barGif.isRunning()) {
			barGif.start();
		}

	}
 
	private void initbar() {
		bar_img = (ImageView) this.findViewById(R.id.splash_progressBar);
		try {
			barGif = new GifDrawable(this.getResources(), R.drawable.bar);
			bar_img.setBackgroundDrawable(barGif);
			if (!barGif.isRunning()) {
				barGif.start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		barGif.addAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationCompleted() {
				// TODO Auto-generated method stub
				Log.i("---------", Connect + "Connect---onAnimationCompleted--");
				if (Connect == true) {
					
					Intent intent1 = new Intent(BluetoothActivity.this,
							GameModeActivity.class);
					BluetoothActivity.this.startActivity(intent1);
					BluetoothActivity.this.finish();
				}
			}

			@Override
			public void FrameIndex7() {
				// TODO Auto-generated method stub
				Log.i("---------", Connect + "---FrameIndex7--");
				if (Connect == false) {
					barGif.pause();
					bluetoothDialog = BluetoothDialog
							.getRemindDialogInstance(BluetoothActivity.this);
					bluetoothDialog.setLoadText();
				}
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_CANCELED) {
			finish();
			return;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("BluetoothActivity ",
				"onPause----barGif.isPlaying()--" + barGif.isPlaying());
		scanLeDevice(false);
		barGif.pause();
		

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		Log.i("BluetoothActivity ",
				"onStop---barGif.isPlaying()--" + barGif.isPlaying());

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("BluetoothActivity ",
				"onDestroy---barGif.isPlaying()--" + barGif.isPlaying());
		barGif.recycle();
		if (mGattUpdateReceiver != null) {
			unregisterReceiver(mGattUpdateReceiver);
		}
	
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {

			// Stops scanning after a pre-defined scan period.
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					if (Connect == false) {
						Log.i("BluetoothActivity",
								" scanLeDevice  Connect == false ");
						scanLeDevice(true);
					}
				}
			}, SCAN_PERIOD);

			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);

		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}

	}

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					final String deviceName = device.getName();
					if (deviceName != null && deviceName.length() > 0
							&& deviceName.equalsIgnoreCase("ble darts")) {

						String mDeviceName = device.getName();
						String mDeviceAddress = device.getAddress();
						BluetoothUtil.getInstance().setmDeviceAddress(
								mDeviceAddress);
						Log.i("mDeviceAddress==========", mDeviceAddress + "");

						BluetoothLe bluetoothLe = BluetoothLe
								.getInstance(BluetoothActivity.this
										.getApplication());
						bluetoothLe.connect(mDeviceAddress);
						if (mScanning) {

							mBluetoothAdapter.stopLeScan(mLeScanCallback);
							mScanning = false;
						}

					}
				}
			});
		}
	};

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLe.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLe.ACTION_GATT_DISCONNECTED);

		return intentFilter;
	}
}