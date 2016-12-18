package com.hopen.darts.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.bean.GameSettingBean;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;
import com.hopen.darts.util.PrefUtils;

public class BigScoreActivity extends Activity {

	private ImageView go;
	private TextView player1_bigscore, player2_bigscore, title;
	private Context context;
	private GameModeBean modeBean;
	private GameSettingBean settingBean;
	private SoundPlayManage soundPlay;
	private Dialog AskDialog_Win, AskDialog_Choose;
	private int WinCountA, WinCountB, times;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_score);
		context = BigScoreActivity.this;
		modeBean = GameModeBean.getInstance();
		settingBean = GameSettingBean.getInstance();
		soundPlay = SoundPlayManage.getInstance(context);

		// Intent intent = getIntent();
		// WinCountA = intent.getIntExtra("wincountA", 0);
		// WinCountB = intent.getIntExtra("wincountB", 0);
		// times = intent.getIntExtra("times", 0);

		// WinCountA = PrefUtils.getInt(getApplicationContext(), "wincountA",
		// 0);
		// WinCountB = PrefUtils.getInt(getApplicationContext(), "wincountB",
		// 0);
		// times = PrefUtils.getInt(getApplicationContext(), "times", 0);

		WinCountA = StatsActivity.WinCountA;
		WinCountB = StatsActivity.WinCountB;
		times = StatsActivity.times;

		findViewById();
		judgeGame();

	}

	private void findViewById() {
		go = (ImageView) findViewById(R.id.go);
		go.setOnClickListener(ClickListener);

		player1_bigscore = (TextView) findViewById(R.id.player1_bigscore);
		player2_bigscore = (TextView) findViewById(R.id.player2_bigscore);
		title = (TextView) findViewById(R.id.Mix_title);
		player1_bigscore.setTextColor(getResources().getColor(R.color.red));
		player2_bigscore.setTextColor(getResources().getColor(R.color.yellow));
		title.setTextColor(getResources().getColor(R.color.green));
	}

	private void judgeGame() {
		// 三局两胜
		if (GameModeActivity.MyCurrentItem == 5
				&& GameModeActivity.MyCurrentItem2 == 0) {
			// 第一次进入大比分界面，还没开始游戏
			if (times == 0) {
				player1_bigscore.setText(0 + "");
				player2_bigscore.setText(0 + "");
				// 一局游戏结束后，进入大比分界面
			} else if (times == 1) {
				player1_bigscore.setText(WinCountA + "");
				player2_bigscore.setText(WinCountB + "");
				// 两局游戏结束后，进入大比分界面
			} else if (times == 2) {
				if (WinCountA == 2 || WinCountB == 2) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);

				} else {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
				}
				// 三局游戏结束后，进入大比分页面
			} else if (times == 3) {
				if (WinCountA >= 2 || WinCountB >= 2) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);

				}
			}

			// 五局三胜
		} else if (GameModeActivity.MyCurrentItem == 5
				&& GameModeActivity.MyCurrentItem2 == 1) {
			// 第一次进入大比分界面，还没开始游戏
			if (times == 0) {
				player1_bigscore.setText(0 + "");
				player2_bigscore.setText(0 + "");
				// 一局游戏结束后，进入大比分界面
			} else if (times == 1) {
				player1_bigscore.setText(WinCountA + "");
				player2_bigscore.setText(WinCountB + "");
				// 两局游戏结束后，进入大比分界面
			} else if (times == 2) {
				player1_bigscore.setText(WinCountA + " ");
				player2_bigscore.setText(WinCountB + " ");
				// 三局游戏结束后，进入大比分页面
			} else if (times == 3) {
				if (WinCountA == 3 || WinCountB == 3) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				} else {
					player1_bigscore.setText(WinCountA + " ");
					player2_bigscore.setText(WinCountB + " ");
				}
				// 四局游戏结束后，进入大比分页面
			} else if (times == 4) {
				if (WinCountA >= 3 || WinCountB >= 3) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				} else {
					player1_bigscore.setText(WinCountA + " ");
					player2_bigscore.setText(WinCountB + " ");
				}
				// 五局游戏结束后，进入大比分页面
			} else if (times == 5) {
				if (WinCountA >= 3 || WinCountB >= 3) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				} 

			}

			// 七局四胜
		} else if (GameModeActivity.MyCurrentItem == 5
				&& GameModeActivity.MyCurrentItem2 == 2) {
			// 第一次进入大比分界面，还没开始游戏
			if (times == 0) {
				player1_bigscore.setText(0 + "");
				player2_bigscore.setText(0 + "");
				// 一局游戏结束后，进入大比分界面
			} else if (times == 1) {
				player1_bigscore.setText(WinCountA + "");
				player2_bigscore.setText(WinCountB + "");
				// 两局游戏结束后，进入大比分界面
			} else if (times == 2) {
				player1_bigscore.setText(WinCountA + " ");
				player2_bigscore.setText(WinCountB + " ");
				// 三局游戏结束后，进入大比分页面
			} else if (times == 3) {
				player1_bigscore.setText(WinCountA + " ");
				player2_bigscore.setText(WinCountB + " ");
				// 四局游戏结束后，进入大比分页面
			} else if (times == 4) {
				if (WinCountA == 4 || WinCountB == 4) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				} else {
					player1_bigscore.setText(WinCountA + " ");
					player2_bigscore.setText(WinCountB + " ");
				}
				// 五局游戏结束后，进入大比分页面
			} else if (times == 5) {
				if (WinCountA >= 4 || WinCountB >= 4) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				} else {
					player1_bigscore.setText(WinCountA + " ");
					player2_bigscore.setText(WinCountB + " ");
				}
				// 六局游戏结束后，进入大比分页面
			}  else if (times == 6) {
				if (WinCountA >= 4 || WinCountB >= 4) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				} else {
					player1_bigscore.setText(WinCountA + " ");
					player2_bigscore.setText(WinCountB + " ");
				}
				// 七局游戏结束后，进入大比分页面
			}  else if (times == 7) {
				if (WinCountA >= 4 || WinCountB >= 4) {
					player1_bigscore.setText(WinCountA + "");
					player2_bigscore.setText(WinCountB + "");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							AskDialog_Win();
						}
					}, 2000);
				}
			}
			
		}
	}

	OnClickListener ClickListener = new OnClickListener() {

		
		@Override
		public void onClick(View arg0) {
			
			soundPlay.PlaySound(SoundPlayManage.ID_SOUND_HIT);
			
			switch (arg0.getId()) {
			case R.id.ok_mix:
				AskDialog_Win.dismiss();
				AskDialog_Win = null;
				break;

			case R.id.cancle_mix:
				AskDialog_Win.dismiss();
				BigScoreActivity.this.finish();
				startActivity(new Intent(BigScoreActivity.this,
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

			case R.id.go:
				//三局两胜
				if (GameModeActivity.MyCurrentItem == 5
						&& GameModeActivity.MyCurrentItem2 == 0) {
					if (times == 0) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOverDartVlue(0);
						startActivity(new Intent(context, Game01Activity.class));
						
						// 打完一局游戏后大比分展示
					} else if (times == 1) { 
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						startActivity(new Intent(context,
								GameMickeyActivity.class));
					} else if (times == 2) {
						// new Handler().postDelayed(new Runnable() {
						//
						// @Override
						// public void run() {
						// AskDialog_Choose();
						// }
						// }, 2000);
						 AskDialog_Choose();
//						AskDialog_Win();
//						modeBean.setGameType(Constant.GAMETYPE301);
//						modeBean.setPlayerType("2");
//						settingBean.setOpenDartVlue(0);
//						settingBean.setOverDartVlue(0);
//						startActivity(new Intent(context, Game01Activity.class));
					} else if (times == 3) {
						BigScoreActivity.this.finish();
						startActivity(new Intent(context,
								GameModeActivity.class));
					}

					//五局三胜
				} else if (GameModeActivity.MyCurrentItem == 5
						&& GameModeActivity.MyCurrentItem2 == 1) {
					if (times == 0) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOverDartVlue(0);
						startActivity(new Intent(context, Game01Activity.class));

						// 打完一局游戏后大比分展示
					} else if (times == 1) {
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						startActivity(new Intent(context,
								GameMickeyActivity.class));

						// 打完两局游戏后大比分展示
					} else if (times == 2) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOpenDartVlue(0);
						settingBean.setOverDartVlue(0);
						startActivity(new Intent(context, Game01Activity.class));

						// 打完三局游戏后大比分展示
					} else if (times == 3) {
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						startActivity(new Intent(context,
								GameMickeyActivity.class));

						// 打完四局游戏后大比分展示
					} else if (times == 4) {
						
						AskDialog_Choose();

						// 打完五局游戏后大比分展示
					} else if (times == 5) {
						BigScoreActivity.this.finish();
						startActivity(new Intent(context,
								GameModeActivity.class));
					}

					//七局四胜
				} else if (GameModeActivity.MyCurrentItem == 5
						&& GameModeActivity.MyCurrentItem2 == 2) {
					if (times == 0) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOverDartVlue(0);
						startActivity(new Intent(context, Game01Activity.class));

						// 打完一局游戏后大比分展示
					} else if (times == 1) {
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						startActivity(new Intent(context,
								GameMickeyActivity.class));

						// 打完两局游戏后大比分展示
					} else if (times == 2) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOpenDartVlue(0);
						settingBean.setOverDartVlue(0);
						startActivity(new Intent(context, Game01Activity.class));

						// 打完三局游戏后大比分展示
					} else if (times == 3) {
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						startActivity(new Intent(context,
								GameMickeyActivity.class));

						// 打完四局游戏后大比分展示
					} else if (times == 4) {
						modeBean.setGameType(Constant.GAMETYPE301);
						modeBean.setPlayerType("2");
						settingBean.setOpenDartVlue(0);
						settingBean.setOverDartVlue(0);
						startActivity(new Intent(context, Game01Activity.class));

						// 打完五局游戏后大比分展示
					} else if (times == 5) {
						modeBean.setGameType(Constant.GAMETYPEMICKEY);
						modeBean.setPlayerType("2");
						startActivity(new Intent(context,
								GameMickeyActivity.class));
						// 打完六局游戏后大比分展示
					} else if (times == 6) {
						
						AskDialog_Choose();
						
						// 打完七局游戏后大比分展示
					} else if (times == 7) {
						BigScoreActivity.this.finish();
						startActivity(new Intent(context,
								GameModeActivity.class));
						
					}

				}
				break;
			}
		}

	};
	

	private void AskDialog_Win() {
		AskDialog_Win = new Dialog(BigScoreActivity.this, R.style.Dialog);
		AskDialog_Win.setContentView(R.layout.askdialog_mix);
		AskDialog_Win.setCanceledOnTouchOutside(false);
		Window dialogWindow = AskDialog_Win.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		TextView winner = (TextView) AskDialog_Win.findViewById(R.id.winner);
		ImageView ok_mix = (ImageView) AskDialog_Win.findViewById(R.id.ok_mix);
		ImageView cancle_mix = (ImageView) AskDialog_Win
				.findViewById(R.id.cancle_mix);

		if(WinCountA>WinCountB){
			winner.setTextColor(context.getResources().getColor(R.color.blue));
			winner.setText(context.getResources().getString(R.string.Ask1));
		}else{
			winner.setTextColor(context.getResources().getColor(R.color.blue));
			winner.setText(context.getResources().getString(R.string.Ask2));
		}
		ok_mix.setOnClickListener(ClickListener);
		cancle_mix.setOnClickListener(ClickListener);
		if (!AskDialog_Win.isShowing()) {
			SoundPlayManage.getInstance(this).PlaySound(
					SoundPlayManage.ID_SOUND_DIALOG);
			AskDialog_Win.show();
		}
	}

	private void AskDialog_Choose() {
		AskDialog_Choose = new Dialog(BigScoreActivity.this, R.style.Dialog);
		AskDialog_Choose.setContentView(R.layout.askdialog_choose);
		AskDialog_Choose.setCanceledOnTouchOutside(false);
		Window dialogWindow = AskDialog_Choose.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);

		TextView game01 = (TextView) AskDialog_Choose.findViewById(R.id.game01);
		TextView gamehighscore = (TextView) AskDialog_Choose.findViewById(R.id.gamehighscore);
		TextView gamemickey = (TextView) AskDialog_Choose.findViewById(R.id.gamemickey);
		
		game01.setTextColor(context.getResources().getColor(R.color.blue));
		gamehighscore.setTextColor(context.getResources().getColor(R.color.blue));
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

}
