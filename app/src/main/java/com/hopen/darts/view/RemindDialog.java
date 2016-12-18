package com.hopen.darts.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;

import pl.droidsonroids.gif.GifDrawable;

public class RemindDialog extends Dialog {

	private static RemindDialog instance;
	private Activity mContext = null;
	private LayoutInflater inflater;
	private RelativeLayout  relt_layout;
	private TextView loadtext;
	public ImageView redkey;
	public GifDrawable gifDrawable1;
	public AnimationDrawable redkeyDrawable;

	public RemindDialog(Activity context) {
		super(context, R.style.Dialog);
		// instance = this;
		this.mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_dialog, null);
		relt_layout =(RelativeLayout) layout.findViewById(R.id.relt_layout);
		loadtext = (TextView) layout.findViewById(R.id.loading_text);
		redkey = (ImageView) layout.findViewById(R.id.imagbut);
		if(GameModeBean.getInstance().getGameType().equalsIgnoreCase(Constant.GAMETYPEMICKEY)){
			relt_layout.setBackgroundResource(R.drawable.toast_green);
		}else{
			relt_layout.setBackgroundResource(R.drawable.toastbg);
		}
	
		try {
			gifDrawable1 = new GifDrawable(mContext.getResources(),
					R.drawable.redkey);
			redkey.setBackgroundDrawable(gifDrawable1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// redkeyDrawable= (AnimationDrawable)
		// redkey.getResources().getDrawable(R.anim.redkey);
		// redkey.setBackground(redkeyDrawable);

		setContentView(layout);
		this.setCancelable(false);

	}

	public synchronized static RemindDialog getRemindDialogInstance(
			Activity context) {
		// if (instance == null)
		instance = new RemindDialog(context);
		return instance;
	}

	public void setLoadText(String msg) {
		/*
		 * if(msg.equals(mContext.getResources().getString(R.string.changecard))
		 * ||
		 * msg.equals(mContext.getResources().getString(R.string.cardbalance))){
		 * mContext.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_PAYFAIL);
		 * }else{ mContext.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_DIALOG);
		 * }
		 */
		SoundPlayManage.getInstance(mContext).PlaySound(SoundPlayManage.ID_SOUND_DIALOG);
		redkey.setVisibility(View.GONE);
		loadtext.setVisibility(View.VISIBLE);
		instance.closeDialog();

		loadtext.setTextColor(mContext.getResources().getColor(R.color.white));
		loadtext.setText(msg);

		WindowManager windowManager = mContext.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenHeight = display.getHeight();

		Window dialogWindow = instance.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();

		lp.y = screenHeight / 5;
		lp.width = lp.FILL_PARENT;
		dialogWindow.setAttributes(lp);

		instance.show();
	}

	public void closeDialog() {
		if (instance.isShowing()) {		
			instance.dismiss();
		}
	}
	
	public void recycled_gifDrawable(){
		if(gifDrawable1 != null){
			Log.i("-------", gifDrawable1+"");
			gifDrawable1.recycle();
			gifDrawable1 = null;
		}
	}
}
