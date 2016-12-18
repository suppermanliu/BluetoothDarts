package com.hopen.darts.activity;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameSettingBean;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

/**
 * @author lpmo
 * 
 */
public class GameSetPopupWindow {
	public PopupWindow gameSetPW = null;
	private CheckBox bull50, opendouble, openshi, enddouble,
			 endshi;
	private ImageButton set_ok;
	private Context mContext;
	private Handler mHandler;
	private GameSettingBean gameSettingBean;
	private SoundPlayManage soundPlay;
	public GameSetPopupWindow(Context mContext, Handler mHandler) {
		super();
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mHandler = mHandler;
		initView();
		setListener();
		soundPlay = SoundPlayManage.getInstance(mContext);
		gameSettingBean = GameSettingBean.getInstance();
		getDartVlue();
	}

	private void initView() {
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View view = mInflater.inflate(R.layout.gamesetpw, null, false);

		bull50 = (CheckBox) view.findViewById(R.id.bull50);
		opendouble = (CheckBox) view.findViewById(R.id.opendouble);
		//openthree = (CheckBox) view.findViewById(R.id.openthree);
		openshi = (CheckBox) view.findViewById(R.id.openshi);
		enddouble = (CheckBox) view.findViewById(R.id.enddouble);
		//endthree = (CheckBox) view.findViewById(R.id.endthree);
		endshi = (CheckBox) view.findViewById(R.id.endshi);
		set_ok = (ImageButton) view.findViewById(R.id.set_ok);

		gameSetPW = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		gameSetPW.setFocusable(true);
		gameSetPW.setOutsideTouchable(true);
		gameSetPW.update();
		gameSetPW.setBackgroundDrawable(new BitmapDrawable());
		
		gameSetPW.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(Constant.GAMESETTINGDISMISS_PW);
			}
		});
	}

	private void getDartVlue() {
		int BullVlue = gameSettingBean.getBullVlue();
		int OpenDartVlue = gameSettingBean.getOpenDartVlue();
		int EndDartVlue = gameSettingBean.getOverDartVlue();
		if (BullVlue == 0) {
			bull50.setChecked(true);
		}
		if (OpenDartVlue == 0) {
			opendouble.setChecked(true);
		} else if (OpenDartVlue == 1) {
			//openthree.setChecked(true);
		} else if (OpenDartVlue == 2) {
			openshi.setChecked(true);
		}

		if (EndDartVlue == 0) {
			enddouble.setChecked(true);
		} else if (EndDartVlue == 1) {
			//endthree.setChecked(true);
		} else if (EndDartVlue == 2) {
			endshi.setChecked(true);
		}

	}

	private void setListener() {
		bull50.setOnCheckedChangeListener(CheckedListener);

		opendouble.setOnClickListener(Listener);
		//openthree.setOnClickListener(Listener);
		openshi.setOnClickListener(Listener);
		enddouble.setOnClickListener(Listener);
		//endthree.setOnClickListener(Listener);
		endshi.setOnClickListener(Listener);
		set_ok.setOnClickListener(Listener);
	}

	private OnCheckedChangeListener CheckedListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
			soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
			switch (arg0.getId()) {
			case R.id.bull50:
				if (arg0.isChecked()) {
					gameSettingBean.setBullVlue(0);
			
				} else {
					gameSettingBean.setBullVlue(1);
				
				}
				break;

			}
		}
	};
	private OnClickListener Listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
			switch (arg0.getId()) {
			case R.id.opendouble:
			//case R.id.openthree:
			case R.id.openshi:
				setOpenDart(arg0);
				break;
			case R.id.enddouble:
			//case R.id.endthree:
			case R.id.endshi:
				setEndDart(arg0);
				break;
			case R.id.set_ok:
				gameSetPW.dismiss();
				break;
			}
		}
	};

	private void setOpenDart(View arg0) {
		if (arg0.getId() == R.id.opendouble) {
		//	openthree.setChecked(false);
			openshi.setChecked(false);
			if (opendouble.isChecked()) {
				gameSettingBean.setOpenDartVlue(0);
			} else {
				gameSettingBean.setOpenDartVlue(-1);
			}

		}  else if (arg0.getId() == R.id.openshi) {
			opendouble.setChecked(false);
			//openthree.setChecked(false);
			if (openshi.isChecked()) {
				gameSettingBean.setOpenDartVlue(2);
			} else {

				gameSettingBean.setOpenDartVlue(-1);
			}
		}
		Log.i("---", "44" + gameSettingBean.getOpenDartVlue());
	}

	private void setEndDart(View arg0) {
		if (arg0.getId() == R.id.enddouble) {
		//	endthree.setChecked(false);
			endshi.setChecked(false);
			if (enddouble.isChecked()) {
				gameSettingBean.setOverDartVlue(0);
			} else {
				gameSettingBean.setOverDartVlue(-1);
			}
		} else if (arg0.getId() == R.id.endshi) {
			enddouble.setChecked(false);
			//endthree.setChecked(false);
			if (endshi.isChecked()) {
				gameSettingBean.setOverDartVlue(2);
			} else {
				gameSettingBean.setOverDartVlue(-1);
			}
		}

		Log.i("---", "333" + gameSettingBean.getOverDartVlue());
	}

}
