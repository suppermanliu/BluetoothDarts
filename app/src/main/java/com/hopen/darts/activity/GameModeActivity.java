package com.hopen.darts.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.bean.GameSettingBean;
import com.hopen.darts.bluetoothkit.BluetoothLe;
import com.hopen.darts.bluetoothkit.BluetoothUtil;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;
import com.hopen.darts.view.BluetoothDialog;
import com.umeng.update.UmengUpdateAgent;

/**
 * @author lpmo
 * 
 */
public class GameModeActivity extends Activity {

	public static Context mContext;
	private ViewPager viewPager;
	private ImageButton left_imagebut, right_imagebut, help, backbut,
			roundsetting;
	private ImageView online;
	public int CurrentItem;
	public static int MyCurrentItem,MyCurrentItem2;
	private Activity activity;
	// 当前类型
	private int CurrentMode = 0;
	// 游戏类型
	private static final int GameMode = 1;
	// 玩家类型
	private static final int PlayerMode = 2;
	private GameModeBean modeBean;
	private GameSettingBean settingBean;
	private GamePagerAdapter viewPagerAdapter;
	// private String[] Photo;
	private int[] Photo;

	// // 游戏类型中加入高分赛模式
	// private String[] GamePhotoArray = new String[] { "game301", "game501",
	// "game701", "gamemickey", "gamehighscore" };
	// // 01系列玩家类型
	// private String[] playerPhotoArray = new String[] { "player1", "player2",
	// "player3", "player4", "player2v2" };
	// // 米老鼠玩家类型
	// private String[] player_mickeyPhotoArray = new String[] { "player2" };
	// // 01系列玩家类型
	// private String[] player_highscorePhotoArray = new String[] { "player1",
	// "player2", "player3", "player4" };

	// 游戏类型中加入高分赛模式
	private int[] GamePhotoArray = new int[] { R.drawable.game301,
			R.drawable.game501, R.drawable.game701, R.drawable.gamemickey,
			R.drawable.gamehighscore, R.drawable.gamemix};
	// 01系列玩家类型
	private int[] playerPhotoArray = new int[] { R.drawable.player1,
			R.drawable.player2, R.drawable.player3, R.drawable.player4,
			R.drawable.player2v2, };
	// 米老鼠玩家类型
	private int[] player_mickeyPhotoArray = new int[] { R.drawable.player2 };
	// 高分赛系列玩家类型
	private int[] player_highscorePhotoArray = new int[] { R.drawable.player1,
			R.drawable.player2, R.drawable.player3, R.drawable.player4 };
	// 混合赛回合选择
	private int[] round_mix = new int[] { R.drawable.game2,
			R.drawable.game3, R.drawable.game4 };
	private SoundPlayManage soundPlay;
	private BluetoothDialog bluetoothDialog;
	private boolean Reconnect = false;
	private BluetoothLe ble;
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.RECONNET_SERVICE:
				ReConnect();
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.gamemode);
		mContext = this;
		modeBean = GameModeBean.getInstance();
		settingBean = GameSettingBean.getInstance();
		fillData();
		findViewById();
		setListener();
		// 获取声音对象
		soundPlay = SoundPlayManage.getInstance(this);

		UmengUpdateAgent.update(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		StatsActivity.WinCountA=0;
		StatsActivity.WinCountB=0;
		StatsActivity.times=0;

		ble = BluetoothLe.getInstance(GameModeActivity.this.getApplication());

		Log.i("---------", "onResume");
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

		CurrentMode = GameMode;
		viewPagerAdapter = new GamePagerAdapter();
		showGameMode();
		if (BluetoothUtil.getInstance().isConnect() == true) {
			online.setBackgroundResource(R.drawable.online);
		} else {
			online.setBackgroundResource(R.drawable.offline);
		}
		if (modeBean.getGameType() != null) {
			if (modeBean.getGameType().equals(Constant.GAMETYPE301)) {
				viewPager.setCurrentItem(0);
			} else if (modeBean.getGameType().equals(Constant.GAMETYPE501)) {
				viewPager.setCurrentItem(1);
			} else if (modeBean.getGameType().equals(Constant.GAMETYPE701)) {
				viewPager.setCurrentItem(2);
			} else if (modeBean.getGameType().equals(Constant.GAMETYPEMICKEY)) {
				viewPager.setCurrentItem(3);
			} else if (modeBean.getGameType()
					.equals(Constant.GAMETYPEHIGHSCROE)) {
				viewPager.setCurrentItem(4);
			} else if (modeBean.getGameType().equals(Constant.GAMETYPEMIX)) {
				viewPager.setCurrentItem(5);
			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		Reconnect = false;
		if (mGattUpdateReceiver != null) {
			unregisterReceiver(mGattUpdateReceiver);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	private void findViewById() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) this.findViewById(R.id.viewpager);
		left_imagebut = (ImageButton) this.findViewById(R.id.left_imagebut);
		right_imagebut = (ImageButton) this.findViewById(R.id.right_imagebut);
		online = (ImageView) this.findViewById(R.id.online);

		help = (ImageButton) this.findViewById(R.id.help);
		backbut = (ImageButton) this.findViewById(R.id.backbut);
		roundsetting = (ImageButton) this.findViewById(R.id.roundsetting);

	}

	private void setListener() {
		viewPager.setOnPageChangeListener(PageChangeListener);
		left_imagebut.setOnClickListener(listener);
		right_imagebut.setOnClickListener(listener);
		backbut.setOnClickListener(listener);
		help.setOnClickListener(listener);
		roundsetting.setOnClickListener(listener);
	}

	private void fillData() {
		activity = this;

	};

	// 显示游戏类型页面
	private void showGameMode() {
		CurrentItem = 0;
		backbut.setVisibility(View.GONE);
		roundsetting.setVisibility(View.VISIBLE);
		Photo = GamePhotoArray;
		viewPager.setAdapter(viewPagerAdapter);

	}

	// 显示玩家类型页面
	private void showPlayerMode() {
		CurrentItem = 0;
		backbut.setVisibility(View.VISIBLE);
		roundsetting.setVisibility(View.GONE);
		if (modeBean.getGameType().equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
			Photo = player_mickeyPhotoArray;
		} else if (modeBean.getGameType().equalsIgnoreCase(
				Constant.GAMETYPEHIGHSCROE)) {
			Photo = player_highscorePhotoArray;
		} else if (modeBean.getGameType().equalsIgnoreCase( // 加入混合赛的3、5、7选择设置
				Constant.GAMETYPEMIX)) {
			Photo = round_mix;
		} else {
			Photo = playerPhotoArray;
		}
		viewPager.setAdapter(viewPagerAdapter);

	}

	// 为向左、向右、返回键、怎么玩添加点击事件
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			switch (arg0.getId()) {
			case R.id.left_imagebut:
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_PAGER);
				if (CurrentItem != 0) {
					CurrentItem--;
					viewPager.setCurrentItem(CurrentItem);
				}
				break;
			case R.id.right_imagebut:
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_PAGER);
				if (CurrentItem != Photo.length - 1) {
					CurrentItem++;
					viewPager.setCurrentItem(CurrentItem);
				}
				break;
			case R.id.backbut:
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
				CurrentMode = GameMode;
				// showGameMode();
				backbut.setVisibility(View.GONE);
				roundsetting.setVisibility(View.VISIBLE);
				Photo = GamePhotoArray;
				viewPager.setAdapter(viewPagerAdapter);
				// //返回键时返回之前选择玩家类型时对应的position所对应的游戏类型
				// viewPager.setCurrentItem(CurrentItem);
				// 返回键时返回之前选择的页面，而不是总是返回到301
				viewPager.setCurrentItem(MyCurrentItem);
				break;
			case R.id.help:
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
				PopupWindow_help();
				break;
			case R.id.roundsetting:
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);// 游戏回合设置按钮
				// PopupWindow_roundsetting();
				activity.startActivity(new Intent(activity,
						RoundSettingActivity.class));
				break;
			}
		}
	};

	// 怎么玩按钮
	private void PopupWindow_help() {
		LayoutInflater mInflater = LayoutInflater.from(this);
		View view = mInflater.inflate(R.layout.helppw, null, false);

		PopupWindow helpPW = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				1000, true);
		helpPW.setFocusable(true);
		helpPW.setOutsideTouchable(true);
		helpPW.update();
		helpPW.setBackgroundDrawable(new BitmapDrawable());

		helpPW.showAtLocation(backbut, Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL, 0, 0);
	}

	// 为viewpager添加滑动监听事件
	private OnPageChangeListener PageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			CurrentItem = arg0;
			soundPlay.PlaySound(SoundPlayManage.ID_SOUND_PAGER);
		}
	};

	// 给viewpager填充数据
	private class GamePagerAdapter extends PagerAdapter {


		public GamePagerAdapter() {

		}

		@Override
		public int getCount() {
			return Photo != null ? Photo.length : 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view.equals(obj);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(activity, R.layout.activity_game_item,
					null);
			ImageView iv = (ImageView) view.findViewById(R.id.photo_item_iv);

			// int resid = activity.getResources().getIdentifier(
			// Photo[position].trim(), "drawable",
			// activity.getPackageName());
			// iv.setBackgroundResource(resid);
			// container.addView(view);

			iv.setBackgroundResource(Photo[position]);
			container.addView(view);

			final int pos;
			pos = position;
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					ViewPagerListener(pos);
				}
			});

			return view;
		}

		// 为viewpager设置点击事件，进入具体游戏前确定游戏类型，玩家类型，牛眼设置，开镖结镖设置
		private void ViewPagerListener(int position) {
			soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);

			// 如果当前模式为游戏类型viewpager，点击后进入相对应的游戏类型
			if (CurrentMode == GameMode) {

				switch (position) {
				case 0:
					modeBean.setGameType(Constant.GAMETYPE301);
					break;
				case 1:
					modeBean.setGameType(Constant.GAMETYPE501);
					break;
				case 2:
					modeBean.setGameType(Constant.GAMETYPE701);
					break;
				case 3:
					modeBean.setGameType(Constant.GAMETYPEMICKEY);
					break;
				case 4:
					modeBean.setGameType(Constant.GAMETYPEHIGHSCROE);
					break;
				case 5:
					modeBean.setGameType(Constant.GAMETYPEMIX);
					break;
				}
				// 获取当前选择的游戏模式的position
				MyCurrentItem = viewPager.getCurrentItem();
				// 设置当前模式为玩家类型选择
				CurrentMode = PlayerMode;
				// 显示玩家类型viewpager的页面
				showPlayerMode();

				// 如果当前页面为玩家类型选择的页面
			} else if (CurrentMode == PlayerMode) {

				if (!modeBean.getGameType().equalsIgnoreCase(
						Constant.GAMETYPEMICKEY)) {
					GameSettingBean.getInstance().setBullVlue(1);
					GameSettingBean.getInstance().setOpenDartVlue(-1);
					GameSettingBean.getInstance().setOverDartVlue(-1);
				}
				switch (position) {
				case 0:
					if (modeBean.getGameType().equalsIgnoreCase(
							Constant.GAMETYPEMICKEY)) {
						modeBean.setPlayerType("2");
						activity.startActivity(new Intent(activity,
								GameMickeyActivity.class));
					} else if (modeBean.getGameType().equalsIgnoreCase( // 进入高分赛单人模式
							Constant.GAMETYPEHIGHSCROE)) {
						modeBean.setPlayerType("1");
						activity.startActivity(new Intent(activity,
								GameHighScoreActivity.class));
					} else if (modeBean.getGameType().equalsIgnoreCase( // 进入混合赛（三局两胜）
							Constant.GAMETYPEMIX)) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOverDartVlue(0);
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					} else {
						modeBean.setPlayerType("1");
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					}
					break;
				case 1:
					if (modeBean.getGameType().equalsIgnoreCase( // 进入高分赛2人模式
							Constant.GAMETYPEHIGHSCROE)) {
						modeBean.setPlayerType("2");
						activity.startActivity(new Intent(activity,
								GameHighScoreActivity.class));
					} else if (modeBean.getGameType().equalsIgnoreCase( // 进入混合赛（五局三胜）
							Constant.GAMETYPEMIX)) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOverDartVlue(0);
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					} else {
						modeBean.setPlayerType("2");
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					}
					break;
				case 2:
					if (modeBean.getGameType().equalsIgnoreCase( // 进入高分赛3人模式
							Constant.GAMETYPEHIGHSCROE)) {
						modeBean.setPlayerType("3");
						activity.startActivity(new Intent(activity,
								GameHighScoreActivity.class));
					} else if (modeBean.getGameType().equalsIgnoreCase( // 进入混合赛（七局四胜）
							Constant.GAMETYPEMIX)) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOverDartVlue(0);
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					} else {
						modeBean.setPlayerType("3");
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					}
					break;
				case 3:
					if (modeBean.getGameType().equalsIgnoreCase( // 进入高分赛4人模式
							Constant.GAMETYPEHIGHSCROE)) {
						modeBean.setPlayerType("4");
						activity.startActivity(new Intent(activity,
								GameHighScoreActivity.class));
					} else {
						modeBean.setPlayerType("4");
						activity.startActivity(new Intent(activity,
								Game01Activity.class));
					}
					break;
				case 4:
					modeBean.setPlayerType("2v2");
					activity.startActivity(new Intent(activity,
							Game01Activity.class));
					break;
				}
				
				MyCurrentItem2 = viewPager.getCurrentItem();

			}
		}
	}

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLe.ACTION_GATT_CONNECTED.equals(action)) {
				Reconnect = false;
				BluetoothUtil.getInstance().setConnect(true);
				online.setBackgroundResource(R.drawable.online);
				if (bluetoothDialog != null && bluetoothDialog.isShowing()) {
					bluetoothDialog.closeDialog();
				}

			} else if (BluetoothLe.ACTION_GATT_DISCONNECTED.equals(action)) {
				BluetoothUtil.getInstance().setConnect(false);
				online.setBackgroundResource(R.drawable.offline);

				bluetoothDialog = BluetoothDialog
						.getRemindDialogInstance(GameModeActivity.this);
				bluetoothDialog.setLoadText();

				Reconnect = true;

				mHandler.sendEmptyMessageDelayed(Constant.RECONNET_SERVICE,
						2000);
			}
		}
	};

	private void ReConnect() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (Reconnect == true) {

					Log.i("-----------", BluetoothUtil.getInstance()
							.getmDeviceAddress() + "");

					ble.connect(BluetoothUtil.getInstance().getmDeviceAddress());
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLe.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLe.ACTION_GATT_DISCONNECTED);

		return intentFilter;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			/*
			 * final Intent intent = new Intent(
			 * BluetoothLeService.ACTION_CLOSE_SERVICE); sendBroadcast(intent);
			 * ;
			 */
			ble.disconnect();
			this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}
