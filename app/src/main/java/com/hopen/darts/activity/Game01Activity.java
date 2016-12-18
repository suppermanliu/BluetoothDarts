package com.hopen.darts.activity;

import java.text.DecimalFormat;
import java.util.Random;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameSettingBean;
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
import com.hopen.darts.view.BluetoothDialog;
import com.hopen.darts.view.GameNumberView;
import com.hopen.darts.view.GamePlayerView;
import com.hopen.darts.view.RemindDialog;
import com.hopen.darts.view.RoundAdapter;

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
import android.widget.Toast;

/**
 * @author lpmo
 * 
 */

public class Game01Activity extends Activity {

	// 用来获取玩家1、2、3、4字符串用来国际化
	public TextView first_player, second_player, third_player, fourth_player;
	public static String myfirst_player, mysecond_player, mythird_player,
			myfourth_player;
	public static String[] myPlayerName;

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

	// 混合赛
	private TextView scoreA, scoreB;
	private LinearLayout bigscore;
	private int WinCountA, WinCountB, times;

	private RoundAdapter roundAdapter = null;

	private ScorePlayerBean CurrentplayerBean = null;
	private int current_darts_number;
	private String player_name;
	private TextView player_ppd = null, game_title = null;
	private GamePlayerView[] gamePlayerView = new GamePlayerView[4];
	private GameNumberView gameNumberView = null;
	private ListView listView_game_round = null;
	private TextView first_Score, second_Score, third_Score;
	private ImageButton setting_but;
	private ImageView on_offline;
	private ImageView rotateView1, rotateView2, rotateView3;
	private LinearLayout LinearLayout_bust;
	private Activity context;
	private RemindDialog remindDialog;
	private boolean bustflag = false;
	private boolean isRounding = false; // ��ʼ������غϼ�Ϊtrue
	private boolean isVaildDarts = false;// ����Ч��dialogΪ��Ч false
	private SettingPopupWindow settingPopupWindow;
	private boolean startGame = false;
	private Dialog FullscreenDialog;
	private GameSetPopupWindow gameset;
	private ImageView openEndicon1, openEndicon2, openEndicon3;
	private VideoPlayManage ShowVideo;
	private SoundPlayManage soundPlay;
	private boolean IsVideo;
	private BluetoothDialog bluetoothDialog;
	private boolean Reconnect = false;
	private BluetoothLe ble;
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.DARTS_DATA:
				int area_id = (Integer) msg.obj;
				int type = msg.arg1;
				if (type == 2) {
					if (isVaildDarts == true && !remindDialog.isShowing()
							&& bustflag == false) {

						current_darts_number++;
						startGame = true;
						notifyDataSetChanged(area_id);

					}
				}
				break;
			case Constant.DARTS_REDKEY:
				int type1 = msg.arg1;
				if (type1 == 3) {
					if (IsVideo) {
						IsVideo = false;
						ShowVideo.stop_video();
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
			case Constant.GAMESETTING_PW:
				dimissPopupWindow();
				if (startGame == false) {
					gameset = new GameSetPopupWindow(context, mHandler);
					gameset.gameSetPW.showAtLocation(player_ppd,
							Gravity.CENTER, 0, 0);

				} else {
					Toast.makeText(context,
							context.getResources().getString(R.string.gameset),
							Toast.LENGTH_LONG).show();
				}
				break;
			case Constant.GAMESETTINGDISMISS_PW:
				ShowOpen_End();
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
				/*
				 * Game01Activity.this.startActivity(new Intent(
				 * Game01Activity.this, StatsActivity.class));
				 */
				Game01Activity.this.finish();

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
		setContentView(R.layout.include_game);

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
		Log.i("------", screenWidth + "onCreate" + screenHeight);

		game_title.setText(gameType);
		gameNumberView.showNumber(PlayerInitScore);
		showPlayerCard();

		scoreA.setText(WinCountA + "");
		scoreB.setText(WinCountB + "");
		// showGameRound();
		// showPlayerMsg();
		ShowOpen_End();
		startRound();
	}

	private void initView() {
		context = Game01Activity.this;

		// 用来获取玩家1、2、3、4字符串用来国际化
		first_player = (TextView) findViewById(R.id.first_player);
		second_player = (TextView) findViewById(R.id.second_player);
		third_player = (TextView) findViewById(R.id.third_player);
		fourth_player = (TextView) findViewById(R.id.fourth_player);

		myfirst_player = first_player.getText().toString().trim();
		mysecond_player = second_player.getText().toString().trim();
		mythird_player = third_player.getText().toString().trim();
		myfourth_player = fourth_player.getText().toString().trim();
		myPlayerName = new String[] { myfirst_player, mysecond_player,
				mythird_player, myfourth_player };

		player_ppd = (TextView) findViewById(R.id.game_ppd);
		player_ppd.setText("0.0");
		game_title = (TextView) findViewById(R.id.textView_title);
		on_offline = (ImageView) findViewById(R.id.on_offline);
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

		// 三个飞镖
		rotateView1 = (ImageView) findViewById(R.id.rotateView1);
		rotateView2 = (ImageView) findViewById(R.id.rotateView2);
		rotateView3 = (ImageView) findViewById(R.id.rotateView3);
		// 三个飞镖射出后的标记SINGLE 1
		first_Score = (TextView) findViewById(R.id.textView_first);
		second_Score = (TextView) findViewById(R.id.textView_second);
		third_Score = (TextView) findViewById(R.id.textView_third);

		openEndicon1 = (ImageView) findViewById(R.id.openEndicon1);
		openEndicon2 = (ImageView) findViewById(R.id.openEndicon2);
		openEndicon3 = (ImageView) findViewById(R.id.openEndicon3);

		player_ppd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (isVaildDarts == true && !remindDialog.isShowing()) {

					Message msg = new Message();
					msg.what = Constant.DARTS_DATA;
					msg.arg1 = 2;
					msg.obj = new Random().nextInt(84) + 0;
					// mHandler.sendMessage(msg);
				}

			}
		});
		setting_but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
				settingPopupWindow = new SettingPopupWindow(context, mHandler);
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
		gameRules.initPlayerBeanArray("", TotalCardNum);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ble = BluetoothLe.getInstance(Game01Activity.this.getApplication());

		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (BluetoothUtil.getInstance().isConnect() == true) {
			on_offline.setBackgroundResource(R.drawable.online);
		} else {
			on_offline.setBackgroundResource(R.drawable.offline);
		}
		Log.i("------", "onResume");

		/*
		 * game_title.setText(gameType + "GAME");
		 * gameNumberView.showNumber(PlayerInitScore); showPlayerCard(); //
		 * showGameRound(); // showPlayerMsg(); ShowOpen_End(); startRound();
		 */

	}

	private void getBluetoothData(byte[] data) {

		if (data != null && data.length > 0) {

			final StringBuilder stringBuilder = new StringBuilder(data.length);
			for (byte byteChar : data) {
				stringBuilder.append(String.format("%02X ", byteChar));

			}
			Log.i("Game01Activity", stringBuilder.toString());

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

			if (GameModeActivity.MyCurrentItem == 5) {
				bigscore.setVisibility(View.VISIBLE);
			}
			
		} else {

			gamePlayerView[0].setVisibility(View.VISIBLE);
			gamePlayerView[1].setVisibility(View.VISIBLE);
			String[] name_1 = { Constant.PlayerName[0], Constant.PlayerName[2] };
			String[] name_2 = { Constant.PlayerName[1], Constant.PlayerName[3] };
			gamePlayerView[0].showCardView(name_1, gameType, 1);
			gamePlayerView[1].showCardView(name_2, gameType, 2);
		}

	}

	private void showGameRound() {
		if (gameVariate.isteam() == true) {
			if (CurrentPlayer == 0 || CurrentPlayer == 2) {
				CurrentplayerBean = gameRules.getPlayerBean(0);
			} else {
				CurrentplayerBean = gameRules.getPlayerBean(1);
			}
		} else {
			CurrentplayerBean = gameRules.getPlayerBean(CurrentPlayer);

		}

		roundAdapter = new RoundAdapter(this, CurrentplayerBean, CurrentRound,
				TotalRounds);
		listView_game_round.setAdapter(roundAdapter);
	}

	private void showPlayerMsg() {
		// player_name.setText(Constant.PlayerName[CurrentPlayer]);
		// myfirst_player = first_player.getText().toString().trim();
		// mysecond_player = second_player.getText().toString().trim();
		// mythird_player = third_player.getText().toString().trim();
		// myfourth_player = fourth_player.getText().toString().trim();

		player_name = myPlayerName[CurrentPlayer];
		// player_name = Constant.PlayerName[CurrentPlayer];

		DecimalFormat df = new DecimalFormat("0.00");// ��ʽ��С��
		String ppd;

		if (gameVariate.isteam()) {

			float CurrentTotalScore = CurrentplayerBean
					.getTeamPlayerScore(CurrentPlayer);

			int VaildCountDarts = CurrentplayerBean
					.getPlayerRoundSum(CurrentPlayer);

			if (VaildCountDarts != 0) {
				float ppd_value = CurrentTotalScore / VaildCountDarts;
				ppd = df.format(ppd_value);
				player_ppd.setText(ppd);
			}

		} else {
			float CurrentTotalScore = CurrentplayerBean.getTotalPlayerScore();
			int VaildCountDarts = CurrentplayerBean.getDartsValidCount();

			if (VaildCountDarts != 0) {
				float ppd_value = CurrentTotalScore / VaildCountDarts;
				ppd = df.format(ppd_value);
				player_ppd.setText(ppd);
			}

		}

	}

	private void startRound() {
		first_Score.setText(null);
		second_Score.setText(null);
		third_Score.setText(null);
		rotateView1.setVisibility(View.VISIBLE);
		rotateView2.setVisibility(View.VISIBLE);
		rotateView3.setVisibility(View.VISIBLE);
		CurrentPlayer = gameVariate.getCurrentPlayer();
		CurrentRound = gameVariate.getCurrentRound();

		CurrentCard = gameVariate.getCurrentCard();
		current_darts_number = 0;
		bustflag = false;

		makePlayerCardDark();
		changePlayer();
		// animation_Darts.initDarts();
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
						// DartsNative.setRoundingLight(0);
						// DartsNative.setKeyLight(0);
					}
				}, 2000);
			}
		}, 1500);

	}

	private void makePlayerCardDark() {
		for (int i = 0; i < gamePlayerView.length; i++) {
			gamePlayerView[i].setCardColor(i + 1);
		}

		if (gameVariate.isteam() == true) {
			if (CurrentPlayer == 0 || CurrentPlayer == 2) {
				gamePlayerView[1].setCardColor(2);
				gamePlayerView[0].setCardBig(1);

				gameNumberView.setPlayerScore(gamePlayerView[0]
						.get01PlayerScore());
			} else {
				gamePlayerView[0].setCardColor(1);
				gamePlayerView[1].setCardBig(2);
				gameNumberView.setPlayerScore(gamePlayerView[1]
						.get01PlayerScore());
			}
		} else {

			// gamePlayerView[CurrentPlayer].setCardDark(false);
			gamePlayerView[CurrentPlayer].setCardBig(CurrentPlayer + 1);
			gameNumberView.setPlayerScore(gamePlayerView[CurrentPlayer]
					.get01PlayerScore());

		}
	}

	private void changePlayer() {
		showGameRound();
		player_name = myPlayerName[CurrentPlayer];
		// player_name = Constant.PlayerName[CurrentPlayer];

		if (CurrentRound == 1) {
			if (current_darts_number == 0) {
				String ppd = "0.00";
				player_ppd.setText(ppd);
			}
		} else {
			showPlayerMsg();
		}

	}

	private void stopRound() {
		isVaildDarts = false;
		if (IsVideo) {
			ShowVideo.mediaPlayer
					.setOnCompletionListener(new OnCompletionListener() {
						// instance.videoPlay.videoView.setOnCompletionListener(new
						// OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mediaPlayer) {
							// TODO Auto-generated method stub
							cancelVideo();
						}
					});
		} else {

			showStopRound();
		}
	}

	// public void cancelVideo(MediaPlayer mediaPlayer){
	public void cancelVideo() {
		IsVideo = false;
		ShowVideo.stop_video();
		showStopRound();

	}

	public void showStopRound() {
		remindDialog.setLoadText(context.getString(R.string.plasegetdart));
		// instance.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_DIALOG);
		// DartsNative.setRoundingLight(getCurrentPlayer()+1);
		// DartsNative.setKeyLight(1);
		remindDialog.redkey.setVisibility(View.VISIBLE);
		remindDialog.redkey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				remindDialog.redkey.setVisibility(View.GONE);
				cancelRoundOver();
			}
		});

	}

	public void cancelRoundOver() {
		if (!isVaildDarts && isRounding) {
			if (remindDialog.isShowing()) {
				remindDialog.closeDialog();
				isRounding = false;
				// startNextPlayer();
				startRound();
			}
		}

	}

	private boolean showVideoView() {
		if (!bustflag && current_darts_number == 3) {
			ScoreRoundBean roundScoreBean = CurrentplayerBean
					.getScoreRoundBean(CurrentRound);
			int first_Area = roundScoreBean.getFirstDartArea();
			int second_Area = roundScoreBean.getSecondDartArea();
			int third_Area = roundScoreBean.getThirdDartArea();
			// GameRulesManage rulerManage =
			// GameRulesManage.getGameRulesManageInstance(activity);
			// int multiple = rulerManage.optionFromArea(first_Area);
			int multiple = DataArea.getMultiple(first_Area);
			// int total_sum = getScoreBean(0).getRoundSum(roundNumber-1);
			int total_sum = CurrentplayerBean.getRoundScore(CurrentRound);
			if (first_Area == 1 || first_Area == 2) {
				if (second_Area == 1 || second_Area == 2) {
					if (third_Area == 1 || third_Area == 2) {
						if (first_Area == 1 && second_Area == 1
								&& third_Area == 1) {
							// �����ţ��
							ShowVideo.play_video(VideoPlayManage.bull_ID);
							return true;
						} else {
							// �������ţ��
							ShowVideo.play_video(VideoPlayManage.maozi_ID);
							return true;
						}
					}
				}
			}
			if (total_sum < 151 && total_sum > 99) {// 99-120
				// �Ͷ�
				ShowVideo.play_video(VideoPlayManage.lowton_ID);
				return true;
			} else if (total_sum < 180 && total_sum > 150) {// 119-180
				// �߶�
				ShowVideo.play_video(VideoPlayManage.highton_ID);
				return true;
			} else if (total_sum == 180) {
				// һ���ְ�ʮ
				ShowVideo.play_video(VideoPlayManage.ton80_ID);
				return true;
			} else if (total_sum < 180 && first_Area > 0
					&& first_Area == second_Area && first_Area == third_Area
					&& (multiple == 2 || multiple == 3)) {
				// ������
				ShowVideo.play_video(VideoPlayManage.three_ID);
				return true;
			}
		}
		return false;
	}

	public void notifyDataSetChanged(int area_id) {
		/*
		 * flag_StartDarts = list_startDarts.get(getCurrentPlayer() - 1);
		 * showDartScore(area_id); if (!flag_StartDarts) { flag_StartDarts =
		 * judgeStartDarts(getCurrentPlayer(), area_id);
		 * list_startDarts.remove(getCurrentPlayer() - 1);
		 * list_startDarts.add(getCurrentPlayer() - 1, flag_StartDarts); }
		 */
		// if (flag_StartDarts) {// ��Ͽ���Ҫ��
		showDartScore(area_id);

		if (CurrentplayerBean.isOpenDart() == false) {
			CurrentplayerBean.setOpenDart(area_id);

		}
		if (CurrentplayerBean.isOpenDart()) {
			setScoreArea(CurrentPlayer, area_id);
			int index = 0;

			if (gameVariate.isteam() == true) {
				if (CurrentPlayer == 0 || CurrentPlayer == 2) {
					index = 0;
				} else {
					index = 1;
				}
			} else {
				index = CurrentPlayer;
			}

			playerCardChanged(index, area_id);

			roundAdapter.notifyData(CurrentplayerBean, CurrentRound);

			showPlayerMsg();

			boolean isGameOver = judgeGameOver(area_id);
			if (isGameOver) {
				isVaildDarts = false;
				isRounding = false;
				// saveGameStats();
				// DartsNative.setDartsLight(0, 26);
				if (bustflag) {
					soundPlay.PlaySound(SoundPlayManage.ID_SOUND_BUST);
					Bustdialog();
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							FullscreenDialog.dismiss();
							remindDialog.setLoadText(context
									.getString(R.string.gameover));
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									remindDialog.cancel();
									// instance.showMainView(Constant.STATS);
									Game01Activity.this.finish();
									Intent winnerIntent = new Intent(
											Game01Activity.this,
											StatsActivity.class);
									Game01Activity.this
											.startActivity(winnerIntent);
								}
							}, 2000);
						}
					}, 1500);
				} else {
					remindDialog.setLoadText(context
							.getString(R.string.gameover));
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							// DartsNative.setDartsLight(0, 0);
							// instance.soundPlay.PlaySound(SoundPlayManage.ID_SOUND_CHANGEVIEW);
							remindDialog.cancel();
							// instance.showMainView(Constant.STATS);
							Game01Activity.this.finish();
							Intent winnerIntent = new Intent(
									Game01Activity.this, StatsActivity.class);
							Game01Activity.this.startActivity(winnerIntent);
						}
					}, 1500);
				}
			} else {
				IsVideo = showVideoView();
				if (bustflag) {
					soundPlay.PlaySound(SoundPlayManage.ID_SOUND_BUST);
					Bustdialog();
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub

							FullscreenDialog.dismiss();
							stopRound();

						}
					}, 2000);
				} else {
					if (current_darts_number == 3) {
						stopRound();
					}
				}
			}
		} else {
			if (bustflag) {
				soundPlay.PlaySound(SoundPlayManage.ID_SOUND_BUST);
				Bustdialog();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub

						FullscreenDialog.dismiss();
						stopRound();
					}
				}, 2000);
			} else {
				if (current_darts_number == 3) {
					stopRound();
				}
			}
		}

	}

	private void Bustdialog() {
		if (FullscreenDialog == null) {
			FullscreenDialog = new Dialog(context, R.style.Dialog);
		}
		ImageView img = new ImageView(context);
		img.setBackgroundResource(R.drawable.bust);
		FullscreenDialog.setContentView(img);
		WindowManager windowManager = context.getWindowManager();
		Display d = windowManager.getDefaultDisplay(); // ��ȡ��Ļ�?����
		WindowManager.LayoutParams p = FullscreenDialog.getWindow()
				.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		p.height = (d.getHeight());
		p.width = (d.getWidth());
		FullscreenDialog.getWindow().setAttributes(p);
		FullscreenDialog.show();

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

	private boolean judgeGameOver(int area_id) {
		boolean isOver = false;
		int gameItem_Sum = PlayerInitScore;
		int player_TotalScore = CurrentplayerBean.getTotalPlayerScore();
		/** �غϽ����ж�,�غ�,����,���һλ��Ҷ����� **/
		/** ��ֵ�����ж� **/
		if (CurrentRound == TotalRounds && CurrentCard == TotalCardNum - 1
				&& (current_darts_number == 3 || bustflag)) {

			// if (CurrentCard == TotalCardNum - 1) {
			// if (current_darts_number == 3 || bustflag) {
			isOver = true;
			// }

			// }
		} else if (player_TotalScore == PlayerInitScore) {

			int index = 0;

			if (gameVariate.isteam() == true) {
				if (CurrentPlayer == 0 || CurrentPlayer == 2) {
					index = 0;
				} else {
					index = 1;
				}
			} else {
				index = CurrentPlayer;
			}
			boolean isEndDart = CurrentplayerBean.isEndDart(area_id);
			if (isEndDart) {
				isOver = true;
			} else {
				// ����Ϊ����Ҫ��û����ʱ������Ϊ����
				bustflag = true;

				gameRules.setBustPlayerScore(index, CurrentCard, CurrentRound,
						current_darts_number, 0);
				roundAdapter.notifyData(CurrentplayerBean, CurrentRound);
				cardViewChange();

				if (CurrentRound == TotalRounds
						&& CurrentCard == TotalCardNum - 1
						&& (current_darts_number == 3 || bustflag)) {

					isOver = true;
				}
			}
		}
		return isOver;
	}

	public void backDart() {
		if (current_darts_number == 0) {
			return;
		}
		setScoreArea(CurrentPlayer, -1);

		roundAdapter.notifyData(CurrentplayerBean, CurrentRound);
		cardViewChange();
		current_darts_number--;
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
		bustflag = false;
		showPlayerMsg();
	}

	/** ����֮��Ƭ��gameNumberView����ʾ,playerNumberΪ�ڼ�λ��һ��ߵڼ������ **/

	private void cardViewChange() {
		// playerScoreBean = getScoreBean(0);
		// int startScore = playerScoreBean.getStartScore();
		/*
		 * for(int i = 0;i<gamePlayerView.length;i++){ if(getCurrentPlayer()-1
		 * == i){ //gamePlayerView[i].setBustScore(startScore -
		 * playerScoreBean.getSum());
		 * 
		 * gamePlayerView[playerNumber].setBustScore(PlayerInitScore -
		 * CurrentplayerBean.getTotalPlayerScore());
		 * gameNumberView.setPlayerScore
		 * (gamePlayerView[playerNumber].get01PlayerScore()); break; } }
		 */

		gamePlayerView[CurrentPlayer].setBustScore(PlayerInitScore
				- CurrentplayerBean.getTotalPlayerScore());
		gameNumberView.setPlayerScore(gamePlayerView[CurrentPlayer]
				.get01PlayerScore());
	}

	/** �洢ÿ�ڵ�����ֵ **/
	private void setScoreArea(int CurrentPlayer, int area_id) {

		gameRules.setPlayerScore(CurrentPlayer, CurrentCard, CurrentRound,
				current_darts_number, area_id);
	}

	/**
	 * ��ݵ�ǰ���ÿһ�ڵķ�ֵ���¿�Ƭ��playerNumberΪ�ڼ����ڼ�λ��ң� area_scoreΪ��������ֵ
	 **/
	private void playerCardChanged(final int playerNumber, int area_score) {
		// int score = getDartScore(area_score);
		int score = DataArea.getScore(area_score);
		gamePlayerView[playerNumber].setCardScoreBg(context, area_score);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gamePlayerView[playerNumber].setCardScoreBg(context, 0);
			}
		}, 1500);

		if (gamePlayerView[playerNumber].change01PlayerScore(score)
				|| residueBust(playerNumber)) {
			bustflag = true;
			/*
			 * GameRulesManage.getGameRulesManageInstance(instance)
			 * .setBustPlayerScore(playerNumber, roundNumber,
			 * instance.darts_number, 0);
			 */
			gameRules.setBustPlayerScore(CurrentPlayer, CurrentCard,
					CurrentRound, current_darts_number, 0);
			// roundAdapter.notifyData(CurrentplayerBean, CurrentRound);
			// int startScore = CurrentplayerBean.getStartScore();
			gamePlayerView[playerNumber].setBustScore(PlayerInitScore
					- CurrentplayerBean.getTotalPlayerScore());
		}
		gameNumberView.setPlayerScore(gamePlayerView[playerNumber]
				.get01PlayerScore());
	}

	/** ���û�б��ڣ��жϱ���֮��ʣ���ֵ�Ƿ�����������Ҫ�󣬲�����ʱҲ�㱬�� **/
	private boolean residueBust(int playerNumber) {

		boolean overFlag = false;

		int residue = PlayerInitScore - CurrentplayerBean.getTotalPlayerScore();
		int DartVlue = GameSettingBean.getInstance().getOverDartVlue();

		if (residue > 0) {

			switch (DartVlue) {
			case -1:
				overFlag = false;
				break;
			case 0:// ˫��
				if (residue < 2)
					overFlag = true;
				break;
			case 1:// ��
				if (residue < 3)
					overFlag = true;
				break;
			case 2:// ��ʦ
				if (residue < 2)
					overFlag = true;
				break;
			default:
				break;
			}

		}
		return overFlag;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

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
		remindDialog.recycled_gifDrawable();
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

		helpPW.showAtLocation(player_ppd, Gravity.CENTER, 0, 0);
	}

	private void dimissPopupWindow() {
		if (settingPopupWindow.settingPW != null
				&& settingPopupWindow.settingPW.isShowing()) {
			settingPopupWindow.settingPW.dismiss();
		}
	}

	private void ShowOpen_End() {
		if (GameSettingBean.getInstance().getBullVlue() == 0) {
			openEndicon1.setVisibility(View.VISIBLE);
			openEndicon1.setBackgroundResource(R.drawable.bull50_icon);
		} else if (GameSettingBean.getInstance().getBullVlue() == 1) {
			openEndicon1.setVisibility(View.GONE);
		}
		int openvalue = GameSettingBean.getInstance().getOpenDartVlue();
		if (openvalue == -1) {
			openEndicon2.setVisibility(View.GONE);
		} else if (openvalue == 0) {
			openEndicon2.setVisibility(View.VISIBLE);
			openEndicon2.setBackgroundResource(R.drawable.double_open_icon);
		} else if (openvalue == 2) {
			openEndicon2.setVisibility(View.VISIBLE);
			openEndicon2.setBackgroundResource(R.drawable.dashi_open_icon);
		}
		int overValue = GameSettingBean.getInstance().getOverDartVlue();
		if (overValue == -1) {
			openEndicon3.setVisibility(View.GONE);
		} else if (overValue == 0) {
			openEndicon3.setVisibility(View.VISIBLE);
			openEndicon3.setBackgroundResource(R.drawable.double_end_icon);
		} else if (overValue == 2) {
			openEndicon3.setVisibility(View.VISIBLE);
			openEndicon3.setBackgroundResource(R.drawable.dashi_end_icon);
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
				if (bluetoothDialog != null) {
					bluetoothDialog.closeDialog();
				}

			} else if (BluetoothLe.ACTION_GATT_DISCONNECTED.equals(action)) {
				BluetoothUtil.getInstance().setConnect(false);
				on_offline.setBackgroundResource(R.drawable.offline);
				bluetoothDialog = BluetoothDialog
						.getRemindDialogInstance(Game01Activity.this);
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

					/*
					 * new
					 * BluetoothLeService().connect(BluetoothUtil.getInstance()
					 * .getmDeviceAddress());
					 */

					Log.i("-----------", BluetoothUtil.getInstance()
							.getmDeviceAddress() + "");
					/*
					 * Intent gattServiceIntent = new
					 * Intent(Game01Activity.this, BluetoothLeService.class);
					 * gattServiceIntent
					 * .putExtra(Constant.EXTRAS_DEVICE_ADDRESS,
					 * BluetoothUtil.getInstance().getmDeviceAddress());
					 * 
					 * Game01Activity.this.startService(gattServiceIntent);
					 */
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
}
