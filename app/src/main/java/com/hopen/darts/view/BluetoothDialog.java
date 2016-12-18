package com.hopen.darts.view;

import pl.droidsonroids.gif.GifDrawable;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.media.SoundPlayManage;

public class BluetoothDialog extends Dialog {

	private static BluetoothDialog instance;
	private Activity mContext = null;
	private LayoutInflater inflater;
	private RelativeLayout  relt_layout;
	private TextView loadtext;
	public ImageView redkey;
	public GifDrawable gifDrawable1;
	public AnimationDrawable redkeyDrawable;

	public BluetoothDialog(Activity context) {
		super(context, R.style.Dialog);
		// instance = this;
		this.mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.bluetooth_dialog, null);
		

		setContentView(layout);
		this.setCancelable(false);// ���õ����ĻDialog����ʧ

	}

	public synchronized static BluetoothDialog getRemindDialogInstance(
			Activity context) {
		// if (instance == null)
		instance = new BluetoothDialog(context);
		return instance;
	}

	/** ��ʾ�Ի��� **/
	public void setLoadText() {
		/*
		 * if(msg.equals(mContext.getResources().getString(R.string.changecard))
		 * ||
		 * msg.equals(mContext.getResources().getString(R.string.cardbalance))){
		 * mContext.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_PAYFAIL);
		 * }else{ mContext.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_DIALOG);
		 * }
		 */
		SoundPlayManage.getInstance(mContext).PlaySound(SoundPlayManage.ID_SOUND_DIALOG);
		WindowManager windowManager = mContext.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenHeight = display.getHeight();

		Window dialogWindow = instance.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();

		lp.y = screenHeight / 3;
		lp.width = lp.FILL_PARENT;
		dialogWindow.setAttributes(lp);

		instance.show();
	}

	public void closeDialog() {
		if (instance.isShowing()) {		
			instance.dismiss();

		}
	}
	
	
}
