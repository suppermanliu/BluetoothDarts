package com.hopen.darts.bluetoothkit;

/**
 * @author lpmo
 * 
 */
public class BluetoothUtil {

	private static BluetoothUtil Util = null;
	
	private boolean isConnect;
	private String  mDeviceAddress;
	private BluetoothUtil() {
		super();
	}

	public static BluetoothUtil getInstance() {
		if (Util == null) {
			Util = new BluetoothUtil();
		}
		return Util;
	}

	

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public String getmDeviceAddress() {
		return mDeviceAddress;
	}

	public void setmDeviceAddress(String mDeviceAddress) {
		this.mDeviceAddress = mDeviceAddress;
	}

}
