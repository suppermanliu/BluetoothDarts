package com.hopen.darts.activity;

import java.text.DecimalFormat;
import java.util.Random;

import pl.droidsonroids.gif.AnimationListener;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.InningGameVariate;
import com.hopen.darts.bean.ScoreRoundBean;
import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.bluetoothkit.BluetoothLe;
import com.hopen.darts.bluetoothkit.BluetoothUtil;
import com.hopen.darts.common.DataArea;
import com.hopen.darts.common.GameRules;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;
import com.hopen.darts.media.VideoPlayManage;
import com.hopen.darts.utils.anim.MickeyAnim;
import com.hopen.darts.view.BluetoothDialog;
import com.hopen.darts.view.GameNumberView;
import com.hopen.darts.view.GamePlayerView;
import com.hopen.darts.view.MickeyAdapter;
import com.hopen.darts.view.MickeyRoundAdapter;
import com.hopen.darts.view.RemindDialog;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author lpmo
 * 
 */
public class GameMickeyActivity extends Activity {

	// 用来玩家1、2、3、4获取字符串国际化
	public TextView first_player1, second_player2, third_player3,
			fourth_player4;
	public static String myfirst_player1, mysecond_player2, mythird_player3,
			myfourth_player4;
	public static String[] myPlayerName1;

	private InningGameVariate gameVariate;
	private String gameType;
	private int PlayerInitScore;
	private int TotalPlayNum;
	private int TotalCardNum;
	private int TotalRounds;
	private int CurrentPlayer;
	private int CurrentCard;
	private int CurrentRound;
	private GameRules gameRules;

	private MickeyRoundAdapter roundAdapter = null;

	// 混合赛
	private TextView scoreA, scoreB;
	private LinearLayout bigscore;
	private int WinCountA, WinCountB, times;

	private ScorePlayerBean CurrentplayerBean = null;
	private int current_darts_number;
	private String player_name;
	private TextView player_MPR = null, game_title = null;
	private GamePlayerView[] gamePlayerView = new GamePlayerView[4];
	private GameNumberView gameNumberView = null;
	private ListView listView_game_round = null;
	private TextView first_Score, second_Score, third_Score;
	private ImageButton on_offline, setting_but;
	private ImageView rotateView1, rotateView2, rotateView3;
	private LinearLayout LinearLayout_bust;
	private ListView listView;
	private Activity context;
	private RemindDialog remindDialog;
	private boolean isRounding = false;
	private boolean isVaildDarts = false;
	private MickeySetting settingPopupWindow;
	private boolean startGame = false;
	private Dialog FullscreenDialog;
	private MickeyAnim mickeyAnim;
	private boolean WhiteHorse_flag = false;
	private boolean flag_Video = false;
	private MickeyAdapter mickeyAdapter;
	private SoundPlayManage soundPlay;
	private VideoPlayManage ShowVideo;
	private BluetoothLe ble;
	private BluetoothDialog bluetoothDialog;
	private boolean Reconnect = false;
	boolean flag_moreThan200 = false;
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.DARTS_DATA:
				int area_id = (Integer) msg.obj;
				int type = msg.arg1;
				if (type == 2) {
					if (isVaildDarts == true && !remindDialog.isShowing()) {

						current_darts_number++;
						startGame = true;
						notifyDataSetChanged(area_id);

					}
				}
				break;
			case Constant.DARTS_REDKEY:
				int type1 = msg.arg1;
				if (type1 == 3) {
					if (flag_Video) {
						flag_Video = false;
						if (WhiteHorse_flag == false) {
							mickeyAnim.StopAnim_Mickey();
						} else {
							WhiteHorse_flag = false;
							ShowVideo.stop_video();
						}
						if (!isVaildDarts && isRounding) {
							isRounding = false;
							startRound();
						}
					} else if (isVaildDarts == true
							&& !remindDialog.isShowing()) {
						current_darts_number++;
						gameRules.setPlayerScore(CurrentPlayer, CurrentCard,
								CurrentRound, current_darts_number, 0);
						soundPlay.PlaySound(SoundPlayManage.ID_SOUND_MISS);
						notifyDataSetChanged(0);
					} else if (isVaildDarts == false
							&& remindDialog.isShowing()) {
						remindDialog.redkey.setVisibility(View.GONE);
						cancelRoundOver();
					}

				}
				break;
			case Constant.BACKDARTS_PW:
				dimissPopupWindow();
				backDart();
				break;
			case Constant.RULES_PW:
				dimissPopupWindow();
				PopupWindow_help();
				break;
			case Constant.GAMEOVER_PW:
				dimissPopupWindow();
				GameMickeyActivity.this.finish();
				break;
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
		setContentView(R.layout.mickey_game);

		WinCountA = StatsActivity.WinCountA;
		WinCountB = StatsActivity.WinCountB;
		times = StatsActivity.times;

		initView();
		getGameVariate();
		initPlayBean();
		remindDialog = RemindDialog.getRemindDialogInstance(this);

		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		ShowVideo = new VideoPlayManage(this, windowManager);
		soundPlay = SoundPlayManage.getInstance(this);

		showMickeyView();

		game_title.setText(R.string.GameMickey);
		showPlayerCard();
		scoreA.setText(WinCountA+"");
		scoreB.setText(WinCountB+"");
		// showGameRound();
		// showPlayerMsg();
		startRound();
	}

	private void initView() {

		// 获取玩家1、2、3、4字符串国际化
		first_player1 = (TextView) findViewById(R.id.first_player1);
		second_player2 = (TextView) findViewById(R.id.second_player2);
		third_player3 = (TextView) findViewById(R.id.third_player3);
		fourth_player4 = (TextView) findViewById(R.id.fourth_player4);

		myfirst_player1 = first_player1.getText().toString().trim();
		mysecond_player2 = second_player2.getText().toString().trim();
		mythird_player3 = third_player3.getText().toString().trim();
		myfourth_player4 = fourth_player4.getText().toString().trim();
		myPlayerName1 = new String[] { myfirst_player1, mysecond_player2,
				mythird_player3, myfourth_player4 };

		context = GameMickeyActivity.this;
		listView = (ListView) findViewById(R.id.listView);
		listView.setEnabled(false);
		player_MPR = (TextView) findViewById(R.id.game_ppd);
		player_MPR.setText("0.0");
		game_title = (TextView) findViewById(R.id.textView_title);
		on_offline = (ImageButton) findViewById(R.id.on_offline);
		setting_but = (ImageButton) findViewById(R.id.setting);
		LinearLayout_bust = (LinearLayout) findViewById(R.id.LinearLayout_bust);
		gameNumberView = (GameNumberView) findViewById(R.id.gameNumberView);
		listView_game_round = (ListView) findViewById(R.id.listView_game_round);
		listView_game_round.setEnabled(false);
		gamePlayerView[0] = (GamePlayerView) findViewById(R.id.gamePlayerView1);
		gamePlayerView[1] = (GamePlayerView) findViewById(R.id.gamePlayerView2);
		gamePlayerView[2] = (GamePlayerView) findViewById(R.id.gamePlayerView3);
		gamePlayerView[3] = (GamePlayerView) findViewById(R.id.gamePlayerView4);

		// 混合赛
		bigscore = (LinearLayout) findViewById(R.id.bigscore);
		scoreA = (TextView) findViewById(R.id.scoreA);
		scoreB = (TextView) findViewById(R.id.scoreB);

		rotateView1 = (ImageView) findViewById(R.id.rotateView1);
		rotateView2 = (ImageView) findViewById(R.id.rotateView2);
		rotateView3 = (ImageView) findViewById(R.id.rotateView3);
		first_Score = (TextView) findViewById(R.id.textView_first);
		second_Score = (TextView) findViewById(R.id.textView_second);
		third_Score = (TextView) findViewById(R.id.textView_third);

		setting_but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
				settingPopupWindow = new MickeySetting(context, mHandler);
				settingPopupWindow.settingPW.showAsDropDown(setting_but);
			}
		});
	}

	private void getGameVariate() {
		gameVariate = new InningGameVariate();
		PlayerInitScore = gameVariate.getPlayerInitScore();
		gameType = gameVariate.getGameType();
		TotalPlayNum = gameVariate.getTotalPlayNum();
		TotalCardNum = gameVariate.getTotalCard();
		TotalRounds = gameVariate.getTotalRounds();

		// CurrentRound = gameVariate.getCurrentRound();
	}

	private void initPlayBean() {
		gameRules = GameRules.getInstance();
		gameRules.initPlayerBeanArray(Constant.GAMETYPEMICKEY, TotalCardNum);

	}

	private void getBluetoothData(byte[] data) {
		if (data != null && data.length > 0) {

			final StringBuilder stringBuilder = new StringBuilder(data.length);
			for (byte byteChar : data) {
				stringBuilder.append(String.format("%02X ", byteChar));

			}
			Log.i("GameMickeyActivity", stringBuilder.toString());

			Message msg = new Message();
			msg.what = Constant.DARTS_DATA;
			msg.arg1 = Integer.parseInt(String.valueOf(data[3]).trim());
			msg.obj = Integer.parseInt(String.valueOf(data[4]).trim());
			mHandler.sendMessage(msg);

			Message msg1 = new Message();
			msg1.what = Constant.DARTS_REDKEY;
			msg1.arg1 = Integer.parseInt(String.valueOf(data[3]).trim());
			msg1.obj = Integer.parseInt(String.valueOf(data[4]).trim());
			mHandler.sendMessage(msg1);

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ble = BluetoothLe.getInstance(GameMickeyActivity.this.getApplication());

		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (BluetoothUtil.getInstance().isConnect() == true) {
			on_offline.setBackgroundResource(R.drawable.online);
		} else {
			on_offline.setBackgroundResource(R.drawable.offline);
		}

	}

	public void showMickeyView() {

		mickeyAdapter = new MickeyAdapter(this, 2);

		listView.setAdapter(mickeyAdapter);
	}

	/**
	 * ��ʾ���������Ƭ
	 * 
	 * @author baixc
	 * 
	 */
	private void showPlayerCard() {

		if (TotalPlayNum == TotalCardNum) {

			for (int i = 0; i < TotalCardNum; i++) {
				gamePlayerView[i].setVisibility(View.VISIBLE);
				gamePlayerView[i].showCardView(Constant.PlayerName[i],
						gameType, i + 1);
			}
		}

		if (GameModeActivity.MyCurrentItem == 5) {
			bigscore.setVisibility(View.VISIBLE);
		}

	}

	private void showGameRound() {

		CurrentplayerBean = gameRules.getPlayerBean(CurrentPlayer);

		roundAdapter = new MickeyRoundAdapter(this, CurrentplayerBean,
				CurrentRound, TotalRounds);
		listView_game_round.setAdapter(roundAdapter);
	}

	private void showPlayerMsg() {

		player_name = myPlayerName1[CurrentPlayer];
		// player_name = Constant.PlayerName[CurrentPlayer];

		DecimalFormat df = new DecimalFormat("0.00");// ��ʽ��С��
		String mpr = null;
		if (CurrentRound == 1) {
			if (current_darts_number == 0) {
				mpr = "0.00";
			} else {
				mpr = df.format(3 * ((float) CurrentplayerBean
						.getVaildNumberSum() / current_darts_number));
			}
		} else {
			mpr = df.format(3 * ((float) CurrentplayerBean.getVaildNumberSum() / ((CurrentRound - 1) * 3 + current_darts_number)));
		}
		player_MPR.setText(mpr);
	}

	/** �غϿ�ʼ,��־λ��Ϊtrue,��ʾ���̽��������Ч **/
	private void startRound() {
		first_Score.setText(null);
		second_Score.setText(null);
		third_Score.setText(null);
		rotateView1.setVisibility(View.VISIBLE);
		rotateView2.setVisibility(View.VISIBLE);
		rotateView3.setVisibility(View.VISIBLE);
		CurrentPlayer = gameVariate.getCurrentPlayer();
		CurrentRound = gameVariate.getCurrentRound();
		Log.i("-----", CurrentPlayer + "CurrentPlayer" + CurrentRound
				+ "CurrentRound" + "");
		CurrentCard = gameVariate.getCurrentCard();
		current_darts_number = 0;

		makePlayerCardDark();
		changePlayer();

		remindDialog.setLoadText(context.getString(R.string.roundstr)
				+ CurrentRound);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				remindDialog.closeDialog();
				remindDialog.setLoadText(player_name + " "
						+ context.getString(R.string.plasethrowdart));
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isRounding = true;

						remindDialog.closeDialog();
						isVaildDarts = true;

					}
				}, 2000);
			}
		}, 1500);

	}

	/**
	 * ������ǰ��ҿ�Ƭ��������һҰ� playerNumber Ϊ�ڼ�λ��һ�ڼ������
	 **/
	private void makePlayerCardDark() {
		for (int i = 0; i < gamePlayerView.length; i++) {
			gamePlayerView[i].setCardColor(i + 1);
		}

		gamePlayerView[CurrentPlayer].setCardBig(CurrentPlayer + 1);

	}

	/**
	 * ��һغ��л�
	 * 
	 * @author baixc
	 * 
	 */
	private void changePlayer() {
		showGameRound();
		showPlayerMsg();
	}

	private void stopRound() {
		isVaildDarts = false;

		if (flag_Video) {
			Log.i("", "11111");
			if (ShowVideo.mediaPlayer != null) {
				ShowVideo.mediaPlayer
						.setOnCompletionListener(new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mediaPlayer) {
								// TODO Auto-generated method stub

								cancelVideo();
							}
						});
			}

			else if (mickeyAnim.gifDrawable3 != null) {

				mickeyAnim.gifDrawable3
						.addAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationCompleted() {
								// TODO Auto-generated method stub
								flag_Video = false;

								showStopRound();
							}

							@Override
							public void FrameIndex7() {
								// TODO Auto-generated method stub

							}
						});
			} else {
				flag_Video = false;

				showStopRound();
			}

		} else {

			showStopRound();

		}

	}

	public void cancelVideo() {
		flag_Video = false;
		if (WhiteHorse_flag == false) {
			mickeyAnim.StopAnim_Mickey();
		} else {
			WhiteHorse_flag = false;
			ShowVideo.stop_video();
		}
		showStopRound();

	}

	public void showStopRound() {

		remindDialog.setLoadText(context.getString(R.string.plasegetdart));

		remindDialog.redkey.setVisibility(View.VISIBLE);

		remindDialog.redkey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				cancelRoundOver();
			}
		});

	}

	public void cancelRoundOver() {
		if (!isVaildDarts && isRounding) {
			if (remindDialog.isShowing()) {
				remindDialog.closeDialog();
				isRounding = false;

				startRound();
			}
		}

	}

	private boolean judgeVaildArea(int area_id) {
		boolean area = false;
		if (area_id == 15 || area_id == 16 || area_id == 17 || area_id == 18
				|| area_id == 19 || area_id == 20 || area_id == 21
				|| area_id == 22 || area_id == 31 || area_id == 32
				|| area_id == 33 || area_id == 34 || area_id == 71
				|| area_id == 72 || area_id == 73 || area_id == 74
				|| area_id == 35 || area_id == 36 || area_id == 37
				|| area_id == 38 || area_id == 75 || area_id == 76
				|| area_id == 77 || area_id == 78 || area_id == 2
				|| area_id == 1) {
			area = true;
		} else {
			area = false;
		}
		return area;
	}

	public void notifyDataSetChanged(final int area_id) {
		showDartScore(area_id);

		setScoreArea(CurrentPlayer, area_id);

		if (judgeVaildArea(area_id)) {

			if (moreThan200(CurrentPlayer)) {

				mickeyDataSetChanged(area_id);
			} else {
				setValidScoreBean(CurrentPlayer, area_id);
				mickeyDataSetChanged(area_id);
			}
		} else {
			if (moreThan200(CurrentPlayer)) {// bug23
				mickeyDataSetChanged(area_id);
			} else {
				mickeyDataSetChanged(area_id);
			}
		}
	}

	private void mickeyDataSetChanged(int area_id) {

		playerCardChanged(area_id);
		mickeyAdapter.notifyDataChanged();

		roundAdapter.notifyData(CurrentplayerBean, CurrentRound);

		flag_Video = showMickeyAnim(CurrentRound);
		showPlayerMsg();

		boolean isGameOver = judgeGameOver(area_id);
		if (isGameOver) {
			isVaildDarts = false;
			isRounding = false;

			// saveGameStats();
			remindDialog.setLoadText(context.getString(R.string.gameover));
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					// instance.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_CHANGEVIEW);
					remindDialog.cancel();
					// instance.showMainView(Constant.STATS);
					GameMickeyActivity.this.finish();
					Intent winnerIntent = new Intent(GameMickeyActivity.this,
							StatsActivity.class);
					GameMickeyActivity.this.startActivity(winnerIntent);
				}
			}, 1500);

		} else {
			if (current_darts_number == 3) {
				stopRound();
			}
		}
	}

	private boolean moreThan200(int playerNumber) {
		flag_moreThan200 = false;
		int[] arr = new int[gamePlayerView.length];
		for (int i = 0; i < gamePlayerView.length; i++) {
			arr[i] = gamePlayerView[i].getMickeyPlayerScore();
		}
		int aa = gamePlayerView[0].getMickeyPlayerScore();
		int bb = gamePlayerView[1].getMickeyPlayerScore();
		int currentPlayer = gamePlayerView[playerNumber].getMickeyPlayerScore();
		// arr = sortPlayerScore(arr);
		/*
		 * if(arr[0] - arr[1] > 200 && arr[0] == currentPlayer){
		 * gamePlayerView[playerNumber].setMoreThan200(true); flag_moreThan200 =
		 * true; }else{ gamePlayerView[playerNumber].setMoreThan200(false); }
		 */

		if (aa - bb > 200 && aa == currentPlayer) {
			gamePlayerView[playerNumber].setMoreThan200(true);
			flag_moreThan200 = true;
			showMoreThan200();
		} else if (bb - aa > 200 && bb == currentPlayer) {
			gamePlayerView[playerNumber].setMoreThan200(true);
			flag_moreThan200 = true;
			showMoreThan200();
		} else {
			gamePlayerView[playerNumber].setMoreThan200(false);
		}
		return flag_moreThan200;
	}

	public void showMoreThan200() {
		final Dialog dialog = new Dialog(this, R.style.Dialog);

		ImageView view = new ImageView(this);
		view.setBackgroundResource(R.drawable.mothan200);
		dialog.setContentView(view);
		soundPlay.PlaySound(SoundPlayManage.ID_SOUND_DIALOG);
		dialog.show();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		}, 2000);

	}

	/** �Ե�ǰ��ҷ�ֵ�������� **/
	private int[] sortPlayerScore(int[] arrey) {
		int[] arr = arrey;
		int temp = 0;
		for (int i = 1; i < arr.length; ++i) {
			for (int j = 0; j < arr.length - i; ++j) {
				if (arr[j] < arr[j + 1]) {
					temp = arr[j]; // ����2����
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				} else if (arr[j] == arr[j + 1]) {
					temp = 0; // ����2����
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return arr;
	}

	private void playerCardChanged(int area_score) {
		setScoreBean(CurrentPlayer, area_score);

		setPlayerCard(area_score);
	}

	private void setPlayerCard(int area_score) {
		gamePlayerView[CurrentPlayer].setCardScoreBg(GameMickeyActivity.this,
				area_score);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gamePlayerView[CurrentPlayer].setCardScoreBg(
						GameMickeyActivity.this, 0);
			}
		}, 1500);

		int total_score = CurrentplayerBean.getStartScore();

		if (CurrentplayerBean.getValidScore_20() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_20() - 3) * 20;
		}
		if (CurrentplayerBean.getValidScore_19() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_19() - 3) * 19;
		}
		if (CurrentplayerBean.getValidScore_18() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_18() - 3) * 18;
		}
		if (CurrentplayerBean.getValidScore_17() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_17() - 3) * 17;
		}
		if (CurrentplayerBean.getValidScore_16() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_16() - 3) * 16;
		}
		if (CurrentplayerBean.getValidScore_15() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_15() - 3) * 15;
		}
		if (CurrentplayerBean.getValidScore_bull() > 3) {
			total_score = total_score
					+ (CurrentplayerBean.getValidScore_bull() - 3) * 25;
		}

		gamePlayerView[CurrentPlayer].setMickeyPlayerScore(total_score);

	}

	private void setScoreBean(int playerNumber, int area_score) {

		ScorePlayerBean[] scoreBean = gameRules.getScoreBean();
		ScorePlayerBean playerScoreBean = gameRules
				.getPlayerBean(CurrentPlayer);
		if (area_score == 75 || area_score == 77 || area_score == 78
				|| area_score == 76) {
			if (!gameRules.isFlag_20()) {
				playerScoreBean.setNumber_20(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_20() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_20 = 3;
					gameRules.setFlag_20(true);
				}
			}
		}
		if (area_score == 35 || area_score == 37 || area_score == 38
				|| area_score == 36) {
			if (!gameRules.isFlag_19()) {
				playerScoreBean.setNumber_19(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_19() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_19 = 3;
					gameRules.setFlag_19(true);
				}
			}
		}
		if (area_score == 71 || area_score == 73 || area_score == 74
				|| area_score == 72) {
			if (!gameRules.isFlag_18()) {
				playerScoreBean.setNumber_18(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_18() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_18 = 3;
					gameRules.setFlag_18(true);
				}
			}
		}
		if (area_score == 31 || area_score == 33 || area_score == 34
				|| area_score == 32) {
			if (!gameRules.isFlag_17()) {
				playerScoreBean.setNumber_17(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_17() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_17 = 3;
					gameRules.setFlag_17(true);
				}
			}
		}
		if (area_score == 19 || area_score == 21 || area_score == 22
				|| area_score == 20) {
			if (!gameRules.isFlag_16()) {
				playerScoreBean.setNumber_16(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_16() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_16 = 3;
					gameRules.setFlag_16(true);
				}
			}
		}
		if (area_score == 17 || area_score == 15 || area_score == 18
				|| area_score == 16) {
			if (!gameRules.isFlag_15()) {
				playerScoreBean.setNumber_15(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_15() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_15 = 3;
					gameRules.setFlag_15(true);
				}
			}
		}
		if (area_score == 2 || area_score == 1) {
			if (!gameRules.isFlag_bull()) {
				playerScoreBean
						.setNumber_bull(DataArea.getMultiple(area_score));
				boolean flag_close = true;
				for (int i = 0; i < scoreBean.length; i++) {
					if (scoreBean[i].getNumber_bull() < 3) {
						flag_close = false;
					}
				}
				if (flag_close) {
					playerScoreBean.Number_bull = 3;
					gameRules.setFlag_bull(true);
				}
			}
		}
	}

	private void setValidScoreBean(int playerNumber, int area_score) {

		ScorePlayerBean playerScoreBean = gameRules
				.getPlayerBean(CurrentPlayer);
		if (area_score == 75 || area_score == 77 || area_score == 78
				|| area_score == 76) {
			if (!gameRules.isFlag_20()) {
				playerScoreBean.setValidScore_20(DataArea
						.getMultiple(area_score));

			}
		}
		if (area_score == 35 || area_score == 37 || area_score == 38
				|| area_score == 36) {
			if (!gameRules.isFlag_19()) {
				playerScoreBean.setValidScore_19(DataArea
						.getMultiple(area_score));

			}
		}
		if (area_score == 71 || area_score == 73 || area_score == 74
				|| area_score == 72) {
			if (!gameRules.isFlag_18()) {
				playerScoreBean.setValidScore_18(DataArea
						.getMultiple(area_score));

			}
		}
		if (area_score == 31 || area_score == 33 || area_score == 34
				|| area_score == 32) {
			if (!gameRules.isFlag_17()) {
				playerScoreBean.setValidScore_17(DataArea
						.getMultiple(area_score));

			}
		}
		if (area_score == 19 || area_score == 21 || area_score == 22
				|| area_score == 20) {
			if (!gameRules.isFlag_16()) {
				playerScoreBean.setValidScore_16(DataArea
						.getMultiple(area_score));

			}
		}
		if (area_score == 17 || area_score == 15 || area_score == 18
				|| area_score == 16) {
			if (!gameRules.isFlag_15()) {
				playerScoreBean.setValidScore_15(DataArea
						.getMultiple(area_score));

			}
		}
		if (area_score == 2 || area_score == 1) {
			if (!gameRules.isFlag_bull()) {
				playerScoreBean.setValidScore_bull(DataArea
						.getMultiple(area_score));

			}
		}
	}

	private boolean judgeGameOver(int area_id) {
		boolean isOver = false;

		if (CurrentRound == TotalRounds) {

			if (CurrentPlayer == TotalPlayNum - 1) {// CurrentPlayer 0��
				if (current_darts_number == 3) {
					isOver = true;
				}
			}

		}
		if (gameRules.isFlag_15() && gameRules.isFlag_16()
				&& gameRules.isFlag_17() && gameRules.isFlag_18()
				&& gameRules.isFlag_19() && gameRules.isFlag_20()
				&& gameRules.isFlag_bull()) {
			isOver = true;
		} else {
			ScorePlayerBean playerScoreBean1 = gameRules.getScoreBean()[0];
			ScorePlayerBean playerScoreBean2 = gameRules.getScoreBean()[1];
			int score_player1 = playerScoreBean1.getTotalScore();
			int score_player2 = playerScoreBean2.getTotalScore();
			if (CurrentPlayer == 0) {
				if (score_player1 >= score_player2) {
					if (playerScoreBean1.judgeOver()) {
						isOver = true;
					}
				}
			} else {
				if (score_player1 <= score_player2) {
					if (playerScoreBean2.judgeOver()) {
						isOver = true;
					}
				}
			}
		}
		return isOver;
	}

	/** ��ʾÿ�ڷ�ֵ **/
	private void showDartScore(int areaId) {
		switch (current_darts_number) {
		case 1:
			rotateView1.setVisibility(View.INVISIBLE);
			first_Score.setText(DataArea.getScoreString(areaId));

			break;
		case 2:
			rotateView2.setVisibility(View.INVISIBLE);
			second_Score.setText(DataArea.getScoreString(areaId));

			break;
		case 3:
			rotateView3.setVisibility(View.INVISIBLE);
			third_Score.setText(DataArea.getScoreString(areaId));

			break;
		default:
			break;
		}
	}

	public void backDart() {
		if (current_darts_number == 0) {
			return;
		}

		vaildBackDart(CurrentPlayer);
		setScoreArea(CurrentPlayer, -1);
		cardViewChange();
		current_darts_number--;
		mickeyAdapter.notifyDataChanged();
		roundAdapter.notifyData(CurrentplayerBean, CurrentRound);
		switch (current_darts_number) {
		case 0:

			rotateView1.setVisibility(View.VISIBLE);
			first_Score.setText(null);
			break;
		case 1:
			rotateView2.setVisibility(View.VISIBLE);
			second_Score.setText(null);
			break;
		case 2:
			rotateView3.setVisibility(View.VISIBLE);
			third_Score.setText(null);
			break;
		}
		showPlayerMsg();
	}

	private void vaildBackDart(int index) {
		/*
		 * playerScoreBean =
		 * GameRulesManage.getGameRulesManageInstance(instance)
		 * .getPlayerBean(index, playertype);
		 */
		ScoreRoundBean RoundScoreBean = CurrentplayerBean
				.getScoreRoundBean(CurrentRound);
		int scoreArea = 0;
		switch (current_darts_number) {
		case 1:
			scoreArea = RoundScoreBean.getFirstDartArea();
			break;
		case 2:
			scoreArea = RoundScoreBean.getSecondDartArea();
			break;
		case 3:
			scoreArea = RoundScoreBean.getThirdDartArea();
			break;
		}
		// GameRulesManage rulesManage =
		// GameRulesManage.getGameRulesManageInstance(instance);
		if (scoreArea == 75 || scoreArea == 77 || scoreArea == 78
				|| scoreArea == 76) {
			if (!gameRules.isFlag_20()) {
				CurrentplayerBean
						.setNumber_20(-DataArea.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_20(-DataArea
							.getMultiple(scoreArea));
				}
			}
		} else if (scoreArea == 35 || scoreArea == 37 || scoreArea == 38
				|| scoreArea == 36) {
			if (!gameRules.isFlag_19()) {
				CurrentplayerBean
						.setNumber_19(-DataArea.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_19(-DataArea
							.getMultiple(scoreArea));
				}
			}
		} else if (scoreArea == 71 || scoreArea == 73 || scoreArea == 74
				|| scoreArea == 72) {
			if (!gameRules.isFlag_18()) {
				CurrentplayerBean
						.setNumber_18(-DataArea.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_18(-DataArea
							.getMultiple(scoreArea));
				}
			}
		} else if (scoreArea == 31 || scoreArea == 33 || scoreArea == 34
				|| scoreArea == 32) {
			if (!gameRules.isFlag_17()) {
				CurrentplayerBean
						.setNumber_17(-DataArea.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_17(-DataArea
							.getMultiple(scoreArea));
				}
			}
		} else if (scoreArea == 85 || scoreArea == 21 || scoreArea == 22
				|| scoreArea == 20) {
			if (!gameRules.isFlag_16()) {
				CurrentplayerBean
						.setNumber_16(-DataArea.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_16(-DataArea
							.getMultiple(scoreArea));
				}
			}
		} else if (scoreArea == 84 || scoreArea == 15 || scoreArea == 18
				|| scoreArea == 16) {
			if (!gameRules.isFlag_15()) {
				CurrentplayerBean
						.setNumber_15(-DataArea.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_15(-DataArea
							.getMultiple(scoreArea));
				}
			}
		} else if (scoreArea == 2 || scoreArea == 1) {
			if (!gameRules.isFlag_bull()) {
				CurrentplayerBean.setNumber_bull(-DataArea
						.getMultiple(scoreArea));
				if (flag_moreThan200 == false) {
					CurrentplayerBean.setValidScore_bull(-DataArea
							.getMultiple(scoreArea));
				}
			}
		}
	}

	private void cardViewChange() {

		setPlayerCard(0);
	}

	private void setScoreArea(int CurrentPlayer, int area_id) {

		gameRules.setPlayerScore(CurrentPlayer, CurrentCard, CurrentRound,
				current_darts_number, area_id);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("------", "onPause");
	}

	private boolean showMickeyAnim(int roundIndex) {
		if (current_darts_number == 3) {

			ScoreRoundBean roundScoreBean = CurrentplayerBean
					.getScoreRoundBean(roundIndex);
			int first = vaildMultiple(roundScoreBean.getFirstDartArea());
			int second = vaildMultiple(roundScoreBean.getSecondDartArea());
			int third = vaildMultiple(roundScoreBean.getThirdDartArea());
			int multipleSum = first + second + third;

			mickeyAnim = new MickeyAnim(this);

			switch (multipleSum) {
			case 9:// qqq
				if (roundScoreBean.judgeThree()) {// ����
					WhiteHorse_flag = true;
					ShowVideo.play_video(VideoPlayManage.horse_ID);
				} else {// qqq
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QQQ);
				}
				break;
			case 8:// qqc
				mickeyAnim.PlayAnim_Mickey(MickeyAnim.QQC);
				break;
			case 7:// qcc��qqg
				if (first == 1 || second == 1 || third == 1) {// qqg
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QQG);
				} else {// qcc
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QCC);
				}
				break;
			case 6:// qqh��qcg��ccc
				if (first == 1 || second == 1 || third == 1) {// qcg
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QCG);
				} else if (first == second && first == third) {// ccc
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.CCC);
				} else {// qqh
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QQH);
				}
				break;
			case 5:// qch

				if (first == 0 || second == 0 || third == 0) {
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QCH);
				} else if (first == 3 || second == 3 || third == 3) {// qgg
					mickeyAnim.PlayAnim_Mickey(MickeyAnim.QGG);
				}

				break;
			default:
				break;
			}
			if (multipleSum == 5 || multipleSum == 6 || multipleSum == 7
					|| multipleSum == 8 || multipleSum == 9) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	private int vaildMultiple(int areaId) {
		int vaildmultiple = 0;

		if (areaId == 75 || areaId == 77 || areaId == 78 || areaId == 76
				|| areaId == 35 || areaId == 37 || areaId == 38 || areaId == 36
				|| areaId == 71 || areaId == 73 || areaId == 74 || areaId == 72
				|| areaId == 31 || areaId == 33 || areaId == 34 || areaId == 32
				|| areaId == 85 || areaId == 21 || areaId == 22 || areaId == 20
				|| areaId == 84 || areaId == 15 || areaId == 18 || areaId == 16
				|| areaId == 2 || areaId == 1) {

			vaildmultiple = DataArea.getMultiple(areaId);
		}
		return vaildmultiple;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("------", "onStop");
		Reconnect = false;
		if (mGattUpdateReceiver != null) {
			unregisterReceiver(mGattUpdateReceiver);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		remindDialog.recycled_gifDrawable();
	}

	private void dimissPopupWindow() {
		if (settingPopupWindow.settingPW != null
				&& settingPopupWindow.settingPW.isShowing()) {
			settingPopupWindow.settingPW.dismiss();
		}
	}

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLe.ACTION_GATT_CONNECTED.equals(action)) {
				Reconnect = false;
				BluetoothUtil.getInstance().setConnect(true);
				on_offline.setBackgroundResource(R.drawable.online);
				bluetoothDialog.closeDialog();
			} else if (BluetoothLe.ACTION_GATT_DISCONNECTED.equals(action)) {
				BluetoothUtil.getInstance().setConnect(false);
				on_offline.setBackgroundResource(R.drawable.offline);

				bluetoothDialog = BluetoothDialog
						.getRemindDialogInstance(GameMickeyActivity.this);
				bluetoothDialog.setLoadText();

				Reconnect = true;

				mHandler.sendEmptyMessageDelayed(Constant.RECONNET_SERVICE,
						2000);
			} else if (BluetoothLe.ACTION_DATA_AVAILABLE.equals(action)) {

				byte[] data_available = intent
						.getByteArrayExtra(BluetoothLe.EXTRA_DATA);
				getBluetoothData(data_available);
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
		intentFilter.addAction(BluetoothLe.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	private void PopupWindow_help() {
		LayoutInflater mInflater = LayoutInflater.from(this);
		View view = mInflater.inflate(R.layout.helppw, null, false);

		PopupWindow helpPW = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				1000, true);
		helpPW.setFocusable(true);
		helpPW.setOutsideTouchable(true);
		helpPW.update();
		helpPW.setBackgroundDrawable(new BitmapDrawable());

		helpPW.showAtLocation(player_MPR, Gravity.CENTER, 0, 0);
	}
}
