package com.hopen.darts.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.bean.GameSettingBean;
import com.hopen.darts.bean.InningGameVariate;
import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.bluetoothkit.BluetoothLe;
import com.hopen.darts.bluetoothkit.BluetoothUtil;
import com.hopen.darts.common.GameRules;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;
import com.hopen.darts.util.PrefUtils;
import com.hopen.darts.view.BluetoothDialog;
import com.hopen.darts.view.StatsPlayerView;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

public class StatsActivity extends Activity {
	private String mytitle;
	private TextView game_title;
	private ImageView on_offline;
	private TextView first_title_biaozhun;
	private TextView first_title_gaofen;
	public static int times = 0;
	public static int WinCountA = 0, WinCountB = 0;
	private GameModeBean modeBean;
	private GameSettingBean settingBean;

	private Context context;
	public Intent intent;
	public float ppd, ppd1, mpr, mpr1;

	// public static final String APP_ID ="wxb8a205156b7a1163";//�Լ���һ�����

	// 一个keystore签名证书对一个应用程序生成一个固定的MD5.
	// MD5:06:C0:A3:2F:F6:87:3A:8D:E1:BA:E1:0B:E9:17:74:08//06c0a32ff6873a8de1bae10be9177408
	public static final String APP_ID = "wx41f455b48612c16b";// ��

	// MD5:C6:46:F8:2C:E3:4A:1F:4C:8D:FC:1C:E5:0A:F5:AC:6D//c646f82ce34a1f4c8dfc1ce50af5ac6d
	// public static final String APP_ID = "wx9bbc96f209d91c39";// //�Լ���2�����
	// public static final String APP_ID ="wx41f455b48612c16b";//��
	public static final int WXSCENETIMELINE = 0;
	public static final int WXSCENESESSION = 1;
	private static final int THUMB_WIDTH = 400;
	private static final int THUMB_HEIGHT = 240;
	private IWXAPI IWXapi;
	private StatsPlayerView StatsPlayerView_4_Vs = null,
			StatsPlayerView_vs = null, StatsPlayerView_4_1 = null,
			StatsPlayerView_4_2 = null, StatsPlayerView_4_3 = null,
			StatsPlayerView_4_4 = null, StatsPlayerView1 = null,
			StatsPlayerView2 = null,

			StatsPlayerView5 = null, StatsPlayerView6 = null;

	private LinearLayout LinearLayout_4 = null, LinearLayout_4v4 = null;
	/** �Ի����Ƿ���ʾ **/
	public boolean flag = false;

	private Dialog shareDialog, AskDialog, AskDialog_Choose;
	private int width, height;
	private String imgPath;
	private boolean windowFocus_flag = false;
	private boolean IsWXAppInstalledAndSupported;
	private BluetoothDialog bluetoothDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.include_stats);

		modeBean = GameModeBean.getInstance();
		settingBean = GameSettingBean.getInstance();

		context = StatsActivity.this;
		// intent = new Intent();

		// 获取结果页面标题
		game_title = (TextView) findViewById(R.id.game_title);
		first_title_biaozhun = (TextView) findViewById(R.id.first_title_biaozhun);
		first_title_gaofen = (TextView) findViewById(R.id.first_title_gaofen);

		on_offline = (ImageView) findViewById(R.id.on_offline);
		StatsPlayerView_4_Vs = (StatsPlayerView) findViewById(R.id.StatsPlayerView_4_Vs);
		StatsPlayerView_vs = (StatsPlayerView) findViewById(R.id.StatsPlayerView_Vs);
		StatsPlayerView_4_1 = (StatsPlayerView) findViewById(R.id.StatsPlayerView_4_1);
		StatsPlayerView_4_2 = (StatsPlayerView) findViewById(R.id.StatsPlayerView_4_2);
		StatsPlayerView_4_3 = (StatsPlayerView) findViewById(R.id.StatsPlayerView_4_3);
		StatsPlayerView_4_4 = (StatsPlayerView) findViewById(R.id.StatsPlayerView_4_4);
		StatsPlayerView1 = (StatsPlayerView) findViewById(R.id.StatsPlayerView1);
		StatsPlayerView2 = (StatsPlayerView) findViewById(R.id.StatsPlayerView2);

		StatsPlayerView5 = (StatsPlayerView) findViewById(R.id.StatsPlayerView5);
		StatsPlayerView6 = (StatsPlayerView) findViewById(R.id.StatsPlayerView6);

		LinearLayout_4 = (LinearLayout) findViewById(R.id.LinearLayout_4);
		LinearLayout_4v4 = (LinearLayout) findViewById(R.id.LinearLayout_4v4);

		flag = false;

		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();

		registerApp();

		SoundPlayManage.getInstance(this).PlaySound(
				SoundPlayManage.ID_SOUND_UPGRADE);

		// 如果是混合赛，则不显示分享对话框
		if (GameModeActivity.MyCurrentItem == 5) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() { // TODO Auto-generated method stub
					AskDialog();
				}
			}, 3000);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() { // TODO Auto-generated method stub
					ShareDialog();
				}
			}, 3000);
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

		if (BluetoothUtil.getInstance().isConnect() == true) {
			on_offline.setBackgroundResource(R.drawable.online);
		} else {
			on_offline.setBackgroundResource(R.drawable.offline);
		}
		if (flag == true) {
			AskDialog();
		}
		showCardView();

		Log.i("onResume----", "//////////////onResume-----------------------");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (shareDialog != null && shareDialog.isShowing()) {
			shareDialog.dismiss();
		}
		Log.i("onPause----", "///////////onPause-----------------------");
	}

	private void registerApp() {
		IWXapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
		IsWXAppInstalledAndSupported = IWXapi.isWXAppInstalled()
				&& IWXapi.isWXAppSupportAPI();

		if (IsWXAppInstalledAndSupported) {
			IWXapi.registerApp(APP_ID);
		}

		Log.i("---weixin-/////////---", IWXapi.registerApp(APP_ID) + "");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		Log.i("onStop----", "//////////////onStop-----------------------");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (mGattUpdateReceiver != null) {
			unregisterReceiver(mGattUpdateReceiver);
		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		if (windowFocus_flag == false) {
			Log.i("onWindowFocusChanged----", "");
			saveBitmap(takeScreenShot(this));
			windowFocus_flag = true;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ��ʾ���ͳ��
	 * 
	 * @author xcbai
	 * 
	 */
	protected void showCardView() {

		InningGameVariate Variate = new InningGameVariate();
		String gameType = Variate.getGameType();
		GameRules gameRules = GameRules.getInstance();
		ScorePlayerBean[] ScorePlayerBean = gameRules.getScoreBean();

		if (gameType.contains("标准米老鼠")) {
			mytitle = first_title_biaozhun.getText().toString().trim();
			game_title.setText(mytitle);
		} else if (gameType.contains("高分赛")) {
			mytitle = first_title_gaofen.getText().toString().trim();
			game_title.setText(mytitle);
		} else {
			game_title.setText(gameType);
		}

		int PlayNum = Variate.getTotalPlayNum();

		switch (PlayNum) {
		case 1:
			LinearLayout_4.setVisibility(View.VISIBLE);
			LinearLayout_4v4.setVisibility(View.GONE);
			StatsPlayerView_4_1.setVisibility(View.VISIBLE);
			StatsPlayerView_4_2.setVisibility(View.GONE);
			StatsPlayerView_4_3.setVisibility(View.GONE);
			StatsPlayerView_4_4.setVisibility(View.GONE);
			StatsPlayerView_4_1.setPlayerMsg(1, ScorePlayerBean[0]);
			break;
		case 2:
			LinearLayout_4.setVisibility(View.VISIBLE);
			LinearLayout_4v4.setVisibility(View.GONE);
			StatsPlayerView_4_1.setVisibility(View.VISIBLE);
			StatsPlayerView_4_2.setVisibility(View.VISIBLE);
			StatsPlayerView_4_3.setVisibility(View.GONE);
			StatsPlayerView_4_4.setVisibility(View.GONE);

			if (GameModeBean.getInstance().getGameType()
					.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
				String Rank = gameRules
						.getPlayerRank_mickey(ScorePlayerBean[0]);
				String Rank1 = gameRules
						.getPlayerRank_mickey(ScorePlayerBean[1]);

				if (Rank.equalsIgnoreCase(Rank1)
						&& Rank.equalsIgnoreCase(Constant.NUMBER1)) {
					DecimalFormat df = new DecimalFormat("0.00");// ��ʽ��С��

					float mpr_value = 3 * ((float) ScorePlayerBean[0]
							.getVaildNumberSum() / ScorePlayerBean[0]
							.getDartsValidCount());

					float mpr_value1 = 3 * ((float) ScorePlayerBean[1]
							.getVaildNumberSum() / ScorePlayerBean[1]
							.getDartsValidCount());
					mpr = (float) (Math.round(mpr_value * 100)) / 100;
					mpr1 = (float) (Math.round(mpr_value1 * 100)) / 100;

					if (mpr >= mpr1) {

						StatsPlayerView_4_1.setPlayerMsg1(1,
								ScorePlayerBean[0], 0);
						StatsPlayerView_4_2.setPlayerMsg1(2,
								ScorePlayerBean[1], 1);
						WinCountA++;
						// intent.putExtra("wincountA", WinCountA);
					} else if (mpr == mpr1) {

						StatsPlayerView_4_1.setPlayerMsg1(1,
								ScorePlayerBean[0], 0);
						StatsPlayerView_4_2.setPlayerMsg1(2,
								ScorePlayerBean[1], 1);
						WinCountA++;
						// intent.putExtra("wincountA", WinCountA);
					} else {

						StatsPlayerView_4_1.setPlayerMsg1(1,
								ScorePlayerBean[0], 1);
						StatsPlayerView_4_2.setPlayerMsg1(2,
								ScorePlayerBean[1], 0);
						WinCountB++;
						// intent.putExtra("wincountB", WinCountB);
					}
				} else {
					StatsPlayerView_4_1.setPlayerMsg(1, ScorePlayerBean[0]);
					StatsPlayerView_4_2.setPlayerMsg(2, ScorePlayerBean[1]);

					// 用来判断player1和Player2的输赢
					if (ScorePlayerBean[0].getTotalScore() > ScorePlayerBean[1]
							.getTotalScore()) {
						WinCountA++;
						// PrefUtils.putInt(getApplicationContext(),
						// "wincountA", WinCountA);
						// intent.putExtra("wincountA", WinCountA);
					} else {
						WinCountB++;
						// PrefUtils.putInt(getApplicationContext(),
						// "wincountB", WinCountB);
						// intent.putExtra("wincountB", WinCountB);
					}
				}
			} else {

				String Rank = gameRules.getPlayerRank(ScorePlayerBean[0]);
				String Rank1 = gameRules.getPlayerRank(ScorePlayerBean[1]);
				if (Rank.equalsIgnoreCase(Rank1)
						&& Rank.equalsIgnoreCase(Constant.NUMBER1)) {
					int score = ScorePlayerBean[0].getTotalPlayerScore();
					int score1 = ScorePlayerBean[1].getTotalPlayerScore();
					// float CurrentTotalScore =
					// scoreBean.getTotalPlayerScore();
					int VaildCountDarts = ScorePlayerBean[0]
							.getDartsValidCount();
					int VaildCountDarts1 = ScorePlayerBean[1]
							.getDartsValidCount();
					float ppd_value = score / VaildCountDarts;

					float ppd_value1 = score1 / VaildCountDarts1;

					ppd = (float) (Math.round(ppd_value * 100)) / 100;
					ppd1 = (float) (Math.round(ppd_value1 * 100)) / 100;

					if (ppd > ppd1) {
						StatsPlayerView_4_1.setPlayerMsg1(1,
								ScorePlayerBean[0], 0);
						StatsPlayerView_4_2.setPlayerMsg1(2,
								ScorePlayerBean[1], 1);
						WinCountA++;
						// intent.putExtra("wincountA", WinCountA);
					} else if (ppd == ppd1) {
						StatsPlayerView_4_1.setPlayerMsg1(1,
								ScorePlayerBean[0], 0);
						StatsPlayerView_4_2.setPlayerMsg1(2,
								ScorePlayerBean[1], 1);
						WinCountA++;
						// intent.putExtra("wincountA", WinCountA);
					} else {
						StatsPlayerView_4_1.setPlayerMsg1(1,
								ScorePlayerBean[0], 1);
						StatsPlayerView_4_2.setPlayerMsg1(2,
								ScorePlayerBean[1], 0);
						WinCountB++;
						// intent.putExtra("wincountB", WinCountB);
					}
				} else {
					StatsPlayerView_4_1.setPlayerMsg(1, ScorePlayerBean[0]);
					StatsPlayerView_4_2.setPlayerMsg(2, ScorePlayerBean[1]);

					// 用来判断player1和Player2的输赢
					if (ScorePlayerBean[0].getTotalPlayerScore() > ScorePlayerBean[1]
							.getTotalPlayerScore()) {
						WinCountA++;
						// PrefUtils.putInt(getApplicationContext(),
						// "wincountA", WinCountA);
						// intent.putExtra("wincountA", WinCountA);
					} else {
						WinCountB++;
						// PrefUtils.putInt(getApplicationContext(),
						// "wincountB", WinCountB);
						// intent.putExtra("wincountB", WinCountB);
					}
				}
			}
			break;
		case 3:
			LinearLayout_4.setVisibility(View.VISIBLE);
			LinearLayout_4v4.setVisibility(View.GONE);
			StatsPlayerView_4_1.setVisibility(View.VISIBLE);
			StatsPlayerView_4_2.setVisibility(View.VISIBLE);
			StatsPlayerView_4_3.setVisibility(View.VISIBLE);
			StatsPlayerView_4_4.setVisibility(View.GONE);
			StatsPlayerView_4_1.setPlayerMsg(1, ScorePlayerBean[0]);
			StatsPlayerView_4_2.setPlayerMsg(2, ScorePlayerBean[1]);
			StatsPlayerView_4_3.setPlayerMsg(3, ScorePlayerBean[2]);
			break;
		case 4:
			if (Variate.isteam() == false) {
				LinearLayout_4.setVisibility(View.VISIBLE);
				LinearLayout_4v4.setVisibility(View.GONE);
				StatsPlayerView_4_1.setVisibility(View.VISIBLE);
				StatsPlayerView_4_2.setVisibility(View.VISIBLE);
				StatsPlayerView_4_3.setVisibility(View.VISIBLE);
				StatsPlayerView_4_4.setVisibility(View.VISIBLE);
				StatsPlayerView_4_1.setPlayerMsg(1, ScorePlayerBean[0]);
				StatsPlayerView_4_2.setPlayerMsg(2, ScorePlayerBean[1]);
				StatsPlayerView_4_3.setPlayerMsg(3, ScorePlayerBean[2]);
				StatsPlayerView_4_4.setPlayerMsg(4, ScorePlayerBean[3]);
			} else {
				LinearLayout_4.setVisibility(View.GONE);
				LinearLayout_4v4.setVisibility(View.VISIBLE);
				StatsPlayerView1.setVisibility(View.VISIBLE);
				StatsPlayerView2.setVisibility(View.VISIBLE);

				StatsPlayerView5.setVisibility(View.VISIBLE);
				StatsPlayerView6.setVisibility(View.VISIBLE);

				StatsPlayerView1.setPlayerMsg(1, ScorePlayerBean[0]);
				StatsPlayerView2.setPlayerMsg(3, ScorePlayerBean[0]);
				StatsPlayerView5.setPlayerMsg(2, ScorePlayerBean[1]);
				StatsPlayerView6.setPlayerMsg(4, ScorePlayerBean[1]);
			}
			break;

		default:
			break;
		}
	}

	private void ShareDialog() {

		shareDialog = new Dialog(this, R.style.Dialog);
		shareDialog.setContentView(R.layout.share_dialog);
		shareDialog.setCanceledOnTouchOutside(false);
		Window dialogWindow = shareDialog.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		ImageView close = (ImageView) shareDialog.findViewById(R.id.close);
		ImageView weixin_img = (ImageView) shareDialog
				.findViewById(R.id.weixin_img);
		ImageView friends_img = (ImageView) shareDialog
				.findViewById(R.id.friends_img);
		close.setOnClickListener(ClickListener);
		weixin_img.setOnClickListener(ClickListener);
		friends_img.setOnClickListener(ClickListener);
		if (!shareDialog.isShowing()) {
			SoundPlayManage.getInstance(this).PlaySound(
					SoundPlayManage.ID_SOUND_DIALOG);
			shareDialog.show();
		}

	}

	private void AskDialog_Choose() {
		AskDialog_Choose = new Dialog(context, R.style.Dialog);
		AskDialog_Choose.setContentView(R.layout.askdialog_choose);
		AskDialog_Choose.setCanceledOnTouchOutside(false);
		Window dialogWindow = AskDialog_Choose.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);

		TextView game01 = (TextView) AskDialog_Choose.findViewById(R.id.game01);
		TextView gamehighscore = (TextView) AskDialog_Choose
				.findViewById(R.id.gamehighscore);
		TextView gamemickey = (TextView) AskDialog_Choose
				.findViewById(R.id.gamemickey);

		game01.setTextColor(context.getResources().getColor(R.color.blue));
		gamehighscore.setTextColor(context.getResources()
				.getColor(R.color.blue));
		gamemickey.setTextColor(context.getResources().getColor(R.color.blue));

		game01.setOnClickListener(ClickListener);
		gamehighscore.setOnClickListener(ClickListener);
		gamemickey.setOnClickListener(ClickListener);
		if (!AskDialog_Choose.isShowing()) {
			SoundPlayManage.getInstance(this).PlaySound(
					SoundPlayManage.ID_SOUND_DIALOG);
			AskDialog_Choose.show();
		}
	}

	private void AskDialog() {
		// ScreenShot_Bitmap = takeScreenShot(this);

		AskDialog = new Dialog(this, R.style.Dialog);
		AskDialog.setContentView(R.layout.ask_dialog);
		AskDialog.setCanceledOnTouchOutside(false);
		Window dialogWindow = AskDialog.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		ImageView ok_img = (ImageView) AskDialog.findViewById(R.id.ok_img);
		ImageView cancel_img = (ImageView) AskDialog
				.findViewById(R.id.cancel_img);
		TextView ask = (TextView) AskDialog.findViewById(R.id.ask_dialog);

		if ((GameModeActivity.MyCurrentItem == 5 && GameModeActivity.MyCurrentItem2 == 0)) {
			if (times == 2) {
				if (WinCountA >= 2 || WinCountB >= 2) {
					cancel_img.setVisibility(View.GONE);
					if (WinCountA > WinCountB) {
						ask.setText(context.getResources().getString(
								R.string.Ask11));
					} else {
						ask.setText(context.getResources().getString(
								R.string.Ask22));
					}
				} else {
					ask.setText(context.getResources().getString(
							R.string.restart));
				}
			} else if (times == 1 && WinCountA == WinCountB) {
				ask.setText(context.getResources().getString(R.string.Ask3));
			} else {
				if (WinCountA >= 2 || WinCountB >= 2) {
					if (WinCountA > WinCountB) {
						ask.setText(context.getResources().getString(
								R.string.Ask1));
					} else {
						ask.setText(context.getResources().getString(
								R.string.Ask2));
					}
				} else {
					ask.setText(context.getResources().getString(
							R.string.restart));
				}
			}
		} else if (GameModeActivity.MyCurrentItem == 5
				&& GameModeActivity.MyCurrentItem2 == 1) {
			if (times == 4) {
				if (WinCountA >= 3 || WinCountB >= 3) {
					cancel_img.setVisibility(View.GONE);
					if (WinCountA > WinCountB) {
						ask.setText(context.getResources().getString(
								R.string.Ask11));
					} else {
						ask.setText(context.getResources().getString(
								R.string.Ask22));
					}
				} else {
					ask.setText(context.getResources().getString(
							R.string.restart));
				}
			} else if (times == 3 && WinCountA == WinCountB) {
				ask.setText(context.getResources().getString(R.string.Ask3));
			} else {
				if (WinCountA >= 3 || WinCountB >= 3) {
					if (WinCountA > WinCountB) {
						ask.setText(context.getResources().getString(
								R.string.Ask1));
					} else {
						ask.setText(context.getResources().getString(
								R.string.Ask2));
					}
				} else {
					ask.setText(context.getResources().getString(
							R.string.restart));
				}
			}
		} else if (GameModeActivity.MyCurrentItem == 5
				&& GameModeActivity.MyCurrentItem2 == 2) {
			if (times == 6) {
				if (WinCountA >= 4 || WinCountB >= 4) {
					cancel_img.setVisibility(View.GONE);
					if (WinCountA > WinCountB) {
						ask.setText(context.getResources().getString(
								R.string.Ask11));
					} else {
						ask.setText(context.getResources().getString(
								R.string.Ask22));
					}
				} else {
					ask.setText(context.getResources().getString(
							R.string.restart));
				}
			} else if (times == 5 && WinCountA == WinCountB) {
				ask.setText(context.getResources().getString(R.string.Ask3));
			} else {
				if (WinCountA >= 4 || WinCountB >= 4) {
					if (WinCountA > WinCountB) {
						ask.setText(context.getResources().getString(
								R.string.Ask1));
					} else {
						ask.setText(context.getResources().getString(
								R.string.Ask2));
					}
				} else {
					ask.setText(context.getResources().getString(
							R.string.restart));
				}
			}
		} else {
			ask.setText(context.getResources().getString(R.string.restart));
		}

		ok_img.setOnClickListener(ClickListener);
		cancel_img.setOnClickListener(ClickListener);

		if (!AskDialog.isShowing()) {
			SoundPlayManage.getInstance(this).PlaySound(
					SoundPlayManage.ID_SOUND_DIALOG);
			AskDialog.show();
		}

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	OnClickListener ClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.close:
				if (shareDialog.isShowing()) {
					shareDialog.dismiss();
					shareDialog = null;
					AskDialog();
				}
				break;
			case R.id.weixin_img:
				shareDialog.dismiss();
				flag = true;
				if (IsWXAppInstalledAndSupported) {
					WXSceneSession();
				} else {
					Toast.makeText(StatsActivity.this, "�밲װ΢�ſͻ���",
							Toast.LENGTH_LONG).show();
					AskDialog();
				}
				break;
			case R.id.friends_img:
				shareDialog.dismiss();
				flag = true;
				if (IsWXAppInstalledAndSupported) {
					WXSceneTimeline();
				} else {
					Toast.makeText(StatsActivity.this, "�밲װ΢�ſͻ���",
							Toast.LENGTH_LONG).show();
					AskDialog();
				}
				break;
			case R.id.ok_img:
				AskDialog.dismiss();

				times++;
				// 当前为混合赛（三局两胜）
				if (GameModeActivity.MyCurrentItem == 5
						&& GameModeActivity.MyCurrentItem2 == 0) {
					if (times == 1) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameMickeyActivity.class));
					} else if (times == 2) {
						AskDialog_Choose();
					} else if (times == 3) {
						StatsActivity.this.finish();
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameModeActivity.class));
					}

					// 五局三胜
				} else if (GameModeActivity.MyCurrentItem == 5
						&& GameModeActivity.MyCurrentItem2 == 1) {

					if (times == 1) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameMickeyActivity.class));
					} else if (times == 2) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOpenDartVlue(0);
						settingBean.setOverDartVlue(0);
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, Game01Activity.class));

					} else if (times == 3) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameMickeyActivity.class));
					} else if (times == 4) {
						AskDialog_Choose();
					} else if (times == 5) {
						StatsActivity.this.finish();
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameModeActivity.class));
					}
					// 七局四胜
				} else if (GameModeActivity.MyCurrentItem == 5
						&& GameModeActivity.MyCurrentItem2 == 2) {

					if (times == 1) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameMickeyActivity.class));
					} else if (times == 2) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOpenDartVlue(0);
						settingBean.setOverDartVlue(0);
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, Game01Activity.class));

					} else if (times == 3) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameMickeyActivity.class));
					} else if (times == 4) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOpenDartVlue(0);
						settingBean.setOverDartVlue(0);
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, Game01Activity.class));
					} else if (times == 5) {
						StatsActivity.this.finish();
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameMickeyActivity.class));
					} else if (times == 6) {
						AskDialog_Choose();
					} else if (times == 7) {
						StatsActivity.this.finish();
						StatsActivity.this.startActivity(new Intent(
								StatsActivity.this, GameModeActivity.class));
					}
				} else if (GameModeBean.getInstance().getGameType()
						.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
					StatsActivity.this.finish();
					StatsActivity.this.startActivity(new Intent(
							StatsActivity.this, GameMickeyActivity.class));
				} else {

					StatsActivity.this.finish();
					StatsActivity.this.startActivity(new Intent(
							StatsActivity.this, Game01Activity.class));
				}
				break;
			case R.id.cancel_img:
				AskDialog.dismiss();
				StatsActivity.this.finish();
				StatsActivity.this.startActivity(new Intent(StatsActivity.this,
						GameModeActivity.class));
				break;

			case R.id.game01:
				AskDialog_Choose.dismiss();
				modeBean.setGameType(Constant.GAMETYPE301);
				modeBean.setPlayerType("2");
				settingBean.setOpenDartVlue(0);
				settingBean.setOverDartVlue(0);
				startActivity(new Intent(context, Game01Activity.class));
				break;

			case R.id.gamehighscore:
				AskDialog_Choose.dismiss();
				modeBean.setGameType(Constant.GAMETYPEHIGHSCROE);
				modeBean.setPlayerType("2");
				settingBean.setOpenDartVlue(0);
				settingBean.setOverDartVlue(0);
				startActivity(new Intent(context, GameHighScoreActivity.class));
				break;

			case R.id.gamemickey:
				AskDialog_Choose.dismiss();
				modeBean.setGameType(Constant.GAMETYPEMICKEY);
				modeBean.setPlayerType("2");
				startActivity(new Intent(context, GameMickeyActivity.class));
				break;

			}
		}
	};

	private void WXSceneSession() {
		/*
		 * String text = "�������ڻ�"; // ��ʼ��һ��WXTextObject���� WXTextObject
		 * textObj = new WXTextObject(); textObj.text = text;
		 * 
		 * // ��WXTextObject�����ʼ��һ��WXMediaMessage���� WXMediaMessage msg =
		 * new WXMediaMessage(); msg.mediaObject = textObj; //
		 * �����ı����͵���Ϣʱ��title�ֶβ������� // msg.title = "Will be ignored";
		 * msg.description = text;
		 * 
		 * // ����һ��Req SendMessageToWX.Req req = new SendMessageToWX.Req();
		 * req.transaction = buildTransaction("text"); //
		 * transaction�ֶ�����Ψһ��ʶһ������ req.message = msg; req.scene =
		 * SendMessageToWX.Req.WXSceneSession;
		 * 
		 * // ����api�ӿڷ�����ݵ�΢�� IWXapi.sendReq(req);
		 */

		File imgFile = new File(imgPath);
		Bitmap bmp = decodeFile_WX(imgFile);
		imgFile.delete();
		WXImageObject imgObj = new WXImageObject(bmp);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_WIDTH,
				THUMB_HEIGHT, true);

		msg.thumbData = bmpToByteArray(thumbBmp, true); // ��������ͼ
		int imageSize = msg.thumbData.length / 1024;
		Log.i("imageSize", "p-----------------------" + imageSize);

		if (imageSize > 32) {
			Toast.makeText(StatsActivity.this, "������ͼƬ���",
					Toast.LENGTH_SHORT).show();

		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;

		IWXapi.sendReq(req);
		bmp.recycle();

	}

	private void WXSceneTimeline() {

		File imgFile = new File(imgPath);
		Bitmap bmp = decodeFile(imgFile);
		imgFile.delete();
		WXImageObject imgObj = new WXImageObject(bmp);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_WIDTH,
				THUMB_HEIGHT, true);

		msg.thumbData = bmpToByteArray(thumbBmp, true); // ��������ͼ
		int imageSize = msg.thumbData.length / 1024;
		Log.i("imageSize", "p-----------------------" + imageSize);

		if (imageSize > 32) {
			Toast.makeText(StatsActivity.this, "������ͼƬ���",
					Toast.LENGTH_SHORT).show();

		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;

		IWXapi.sendReq(req);
		bmp.recycle();
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 20, output);

		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();

		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			// The new size we want to scale to
			final int REQUIRED_HEIGHT = 800;
			final int REQUIRED_WIDTH = 480;
			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;

			System.out.println(width_tmp + "  " + height_tmp);
			Log.w("===", (width_tmp + "  " + height_tmp));

			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_WIDTH
						&& height_tmp / 2 < REQUIRED_HEIGHT)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;

				Log.w("===", scale + "''" + width_tmp + "  " + height_tmp);
			}
			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	private Bitmap decodeFile_WX(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			// The new size we want to scale to
			final int REQUIRED_HEIGHT = 800;
			final int REQUIRED_WIDTH = 480;
			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;

			System.out.println(width_tmp + "  " + height_tmp);
			Log.w("===", (width_tmp + "  " + height_tmp));

			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_WIDTH
						&& height_tmp / 2 < REQUIRED_HEIGHT)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;

				Log.w("===", scale + "''" + width_tmp + "  " + height_tmp);
			}
			Log.w("===", scale + "");
			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	private Bitmap takeScreenShot(StatsActivity context) {

		View view = context.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		Rect frame = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		Log.i("TAG", "" + statusBarHeight);

		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);

		view.destroyDrawingCache();

		return b;
	}

	private String saveBitmap(Bitmap mBitmap) {
		if (mBitmap == null) {
			return null;
		}
		try {
			String sdCardPath = "";

			sdCardPath = Environment.getExternalStorageDirectory().getPath();
			String filePath = sdCardPath + "/" + "MicroShare/";

			Date date = new Date(System.currentTimeMillis());

			SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");// ʱ���ʽ-��ʾ��ʽ

			imgPath = filePath + sdf.format(date) + ".png";

			File file = new File(filePath);

			if (!file.exists()) {
				file.mkdirs();
			}
			File imgFile = new File(imgPath);

			if (!imgFile.exists()) {
				imgFile.createNewFile();
			}

			FileOutputStream fOut = new FileOutputStream(imgFile);

			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

			fOut.flush();

			if (fOut != null) {

				fOut.close();
			}
			Log.i("imgPath----", "-----------------------" + imgPath);
			return imgPath;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLe.ACTION_GATT_CONNECTED.equals(action)) {
				BluetoothUtil.getInstance().setConnect(true);
				on_offline.setBackgroundResource(R.drawable.online);
				bluetoothDialog.closeDialog();
			} else if (BluetoothLe.ACTION_GATT_DISCONNECTED.equals(action)) {
				BluetoothUtil.getInstance().setConnect(false);
				on_offline.setBackgroundResource(R.drawable.offline);

				bluetoothDialog = BluetoothDialog
						.getRemindDialogInstance(StatsActivity.this);
				bluetoothDialog.setLoadText();
			}
		}
	};

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLe.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLe.ACTION_GATT_DISCONNECTED);

		return intentFilter;
	}

}
