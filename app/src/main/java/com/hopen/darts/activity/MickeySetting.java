package com.hopen.darts.activity;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * @author lpmo
 * 
 */
public class MickeySetting {
	public PopupWindow settingPW = null;
	private ImageView backdart, rules, gameover;
	private Context mContext;
	private Handler mHandler;
	private SoundPlayManage soundPlay;

	public MickeySetting(Context mContext, Handler mHandler) {
		super();
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
		initView();
		soundPlay = SoundPlayManage.getInstance(mContext);

	}

	private void initView() {
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view = mInflater.inflate(R.layout.mickeysettingpw, null, false);

		backdart = (ImageView) view.findViewById(R.id.backdart);
		rules = (ImageView) view.findViewById(R.id.rules);
		gameover = (ImageView) view.findViewById(R.id.gameover);

		backdart.setOnClickListener(ClickListener);
		rules.setOnClickListener(ClickListener);
		gameover.setOnClickListener(ClickListener);

		settingPW = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		settingPW.setFocusable(true);
		settingPW.setOutsideTouchable(true);
		settingPW.update();
		settingPW.setBackgroundDrawable(new BitmapDrawable());

	}

	private OnClickListener ClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
			switch (arg0.getId()) {

			case R.id.backdart:
				backdart.setImageResource(R.drawable.backdart_text2);
				msg.what = Constant.BACKDARTS_PW;
				mHandler.sendMessageDelayed(msg, Constant.PW_DELAY);
				break;
			case R.id.rules:
				rules.setImageResource(R.drawable.rules_text2);
				msg.what = Constant.RULES_PW;
				mHandler.sendMessageDelayed(msg, Constant.PW_DELAY);
				break;
			case R.id.gameover:
				gameover.setImageResource(R.drawable.gameover_text2);
				msg.what = Constant.GAMEOVER_PW;
				mHandler.sendMessageDelayed(msg, Constant.PW_DELAY);
				break;
			}

		}

	};
}
