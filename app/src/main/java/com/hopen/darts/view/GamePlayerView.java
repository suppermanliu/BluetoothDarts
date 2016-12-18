package com.hopen.darts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.common.DataArea;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.media.SoundPlayManage;

public class GamePlayerView extends FrameLayout {

	private GamePlayerView gamePlayerView;
	private Context context;
	// private Drawable cardbg = null;
	// private int textcolor = 0;
	private LinearLayout LinearLayout_score;
	private LinearLayout playerCard_darkbg, playerCard_frozen, game_player,
			game_4v4_player;
	private LinearLayout game_4_player;
	private TextView textView1, textView2, textView3, textView4,
			textView_score;
	public boolean isKill;

	public GamePlayerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public GamePlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		gamePlayerView = this;
		this.context = context;
		this.setClickable(true);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_game_player, null);
		findViewID(view);
		// TypedArray a =
		// context.obtainStyledAttributes(attrs,R.styleable.GamePlayerView);
		// getAttribute(a);
		addView(view, new FrameLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		// a.recycle();
	}

	/**
	 * ��ȡ��������ֵ
	 **/
	// public void getAttribute(TypedArray a){
	// cardbg = a.getDrawable(R.styleable.GamePlayerView_cardbg);
	// textcolor = a.getColor(R.styleable.GamePlayerView_textcolor,Color.BLUE);
	// game_player.setBackgroundDrawable(cardbg);
	// textView1.setTextColor(textcolor);
	// textView2.setTextColor(textcolor);
	// textView3.setTextColor(textcolor);
	// textView4.setTextColor(textcolor);
	// }

	/**
	 * ʵ�����
	 **/
	public void findViewID(View view) {
		playerCard_darkbg = (LinearLayout) view
				.findViewById(R.id.playerCard_darkbg);
		playerCard_frozen = (LinearLayout) view
				.findViewById(R.id.playerCard_frozen);
		game_player = (LinearLayout) view.findViewById(R.id.game_player);
		game_4v4_player = (LinearLayout) view
				.findViewById(R.id.game_4v4_player);
		game_4_player = (LinearLayout) view.findViewById(R.id.game_4_player);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView3 = (TextView) view.findViewById(R.id.textView3);
		textView4 = (TextView) view.findViewById(R.id.textView4);
		textView_score = (TextView) view.findViewById(R.id.textView_score);
		LinearLayout_score = (LinearLayout) view
				.findViewById(R.id.LinearLayout_score);
	}

	private Handler h = null;

	/**
	 * ��˫ţ��3��20������ʾ
	 **/
	// D-BULL、S-BULL、THREE20
	public void setCardScoreBg(Context instance, int area_score) {
		h = new Handler();

		if (area_score == 2) {
			SoundPlayManage.getInstance(instance).PlaySound(
					SoundPlayManage.ID_SOUND_HITEYE);
			LinearLayout_score.setVisibility(View.VISIBLE);
			LinearLayout_score.setBackgroundResource(R.drawable.s_bull);

		} else if (area_score == 1) {
			SoundPlayManage.getInstance(instance).PlaySound(
					SoundPlayManage.ID_SOUND_HITEYE);
			LinearLayout_score.setVisibility(View.VISIBLE);
			LinearLayout_score.setBackgroundResource(R.drawable.d_bull);

		} else if (area_score == 76) {
			SoundPlayManage.getInstance(instance).PlaySound(
					SoundPlayManage.ID_SOUND_HITTHREE20);
			LinearLayout_score.setVisibility(View.VISIBLE);
			LinearLayout_score.setBackgroundResource(R.drawable.triple_20);
			/*
			 * h.postDelayed(new Runnable() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * LinearLayout_score.setVisibility(View.GONE); } }, 1500);
			 */
		} else {
			LinearLayout_score.setVisibility(View.GONE);

		}

		if (area_score != 2 && DataArea.getMultiple(area_score) == 1) {
			SoundPlayManage.getInstance(instance).PlaySound(
					SoundPlayManage.ID_SOUND_HITSINGLE);
		} else if (area_score != 1 && DataArea.getMultiple(area_score) == 2) {
			SoundPlayManage.getInstance(instance).PlaySound(
					SoundPlayManage.ID_SOUND_HITDOUBLE);

		} else if (area_score != 76 && DataArea.getMultiple(area_score) == 3) {

			SoundPlayManage.getInstance(instance).PlaySound(
					SoundPlayManage.ID_SOUND_HITTHREE);
		}
	}

	public void setMoreThan200(boolean flag) {
		if (flag == true) {
			playerCard_frozen.setVisibility(View.VISIBLE);
			playerCard_frozen.setBackground(context.getResources().getDrawable(
					R.drawable.kill));

		} else {

			// playerCard_frozen.setBackground(null);
			playerCard_frozen.setVisibility(View.GONE);
		}
	}

	// 设置放大卡片
	public void setCardBig(int index) {
		textView1.setTextSize(20);
		textView2.setTextSize(20);
		textView3.setTextSize(20);
		textView4.setTextSize(20);
		textView_score.setTextSize(35);
		switch (index) {
		case 1:
			game_player.setBackgroundResource(R.drawable.card_big_red);
			textView1.setTextColor(getResources().getColor(R.color.red));
			textView2.setTextColor(getResources().getColor(R.color.red));
			textView3.setTextColor(getResources().getColor(R.color.red));
			textView4.setTextColor(getResources().getColor(R.color.red));
			break;
		case 2:
			game_player.setBackgroundResource(R.drawable.card_big_yel);
			textView1.setTextColor(getResources().getColor(R.color.yellow));
			textView2.setTextColor(getResources().getColor(R.color.yellow));
			textView3.setTextColor(getResources().getColor(R.color.yellow));
			textView4.setTextColor(getResources().getColor(R.color.yellow));
			break;
		case 3:
			game_player.setBackgroundResource(R.drawable.card_big_blue);
			textView1.setTextColor(getResources().getColor(R.color.blue));
			textView2.setTextColor(getResources().getColor(R.color.blue));
			textView3.setTextColor(getResources().getColor(R.color.blue));
			textView4.setTextColor(getResources().getColor(R.color.blue));
			break;
		case 4:
			game_player.setBackgroundResource(R.drawable.card_big_green);
			textView1.setTextColor(getResources().getColor(R.color.green));
			textView2.setTextColor(getResources().getColor(R.color.green));
			textView3.setTextColor(getResources().getColor(R.color.green));
			textView4.setTextColor(getResources().getColor(R.color.green));
			break;
		}
	}

	// 设置卡片颜色
	public void setCardColor(int index) {
		textView1.setTextSize(15);
		textView2.setTextSize(15);
		textView3.setTextSize(15);
		textView4.setTextSize(15);
		textView_score.setTextSize(30);
		switch (index) {
		case 1:
			game_player.setBackgroundResource(R.drawable.card_red);
			textView1.setTextColor(getResources().getColor(R.color.red));
			textView2.setTextColor(getResources().getColor(R.color.red));
			textView3.setTextColor(getResources().getColor(R.color.red));
			textView4.setTextColor(getResources().getColor(R.color.red));
			break;
		case 2:
			game_player.setBackgroundResource(R.drawable.card_yel);
			textView1.setTextColor(getResources().getColor(R.color.yellow));
			textView2.setTextColor(getResources().getColor(R.color.yellow));
			textView3.setTextColor(getResources().getColor(R.color.yellow));
			textView4.setTextColor(getResources().getColor(R.color.yellow));
			break;
		case 3:
			game_player.setBackgroundResource(R.drawable.card_blue);
			textView1.setTextColor(getResources().getColor(R.color.blue));
			textView2.setTextColor(getResources().getColor(R.color.blue));
			textView3.setTextColor(getResources().getColor(R.color.blue));
			textView4.setTextColor(getResources().getColor(R.color.blue));
			break;
		case 4:
			game_player.setBackgroundResource(R.drawable.card_green);
			textView1.setTextColor(getResources().getColor(R.color.green));
			textView2.setTextColor(getResources().getColor(R.color.green));
			textView3.setTextColor(getResources().getColor(R.color.green));
			textView4.setTextColor(getResources().getColor(R.color.green));
			break;
		}
	}

	/**
	 * 1-4λ��Ҹ��ģʽѡ����ʾ��ͬ�����card
	 **/
	public void showCardView(String name, String gameItem, int index) {
		setCardColor(index);
		initStartScore(gameItem);
		game_4v4_player.setVisibility(View.GONE);
		game_4_player.setVisibility(View.VISIBLE);
		this.setPlayer4Name(name);
	}

	/**
	 * ��ݾ�����Ϸitem��ʾ��ʼ��ֵ
	 **/
	// 初始化高分赛卡片分数
	public void initStartScore(String gameitem) {
		if (gameitem.equals(Constant.GAMETYPEMICKEY)
				|| gameitem.equals(Constant.GAMETYPEHIGHSCROE)) {
			textView_score.setText("0");
		} else {
			textView_score.setText(gameitem);
		}

	}

	/**
	 * 2v2,3v3,4v4���ģʽѡ����ʾ��ͬ�����card
	 **/
	public void showCardView(String name[], String gameItem, int index) {
		setCardColor(index);
		initStartScore(gameItem);
		switch (name.length) {
		case 2:
			game_4v4_player.setVisibility(View.VISIBLE);
			game_4_player.setVisibility(View.VISIBLE);
			textView3.setVisibility(View.GONE);
			textView4.setVisibility(View.GONE);
			this.setPlayer1Name(name[0]);
			this.setPlayer2Name(name[1]);
			break;
		case 3:
			game_4_player.setVisibility(View.VISIBLE);
			game_4v4_player.setVisibility(View.VISIBLE);
			textView4.setVisibility(View.GONE);
			this.setPlayer1Name(name[0]);
			this.setPlayer2Name(name[1]);
			this.setPlayer3Name(name[2]);
			break;
		default:
			break;
		}
	}

	/** ��ȡ��ǰʣ���ֵ **/
	public int get01PlayerScore() {
		int last_score = 0;
		String str = textView_score.getText().toString();
		if (str != null && !str.equals("")) {
			last_score = Integer.parseInt(str);
		}
		return last_score;
	}

	/** ������ϵ�����÷�ֵ **/
	public void setMickeyPlayerScore(int score) {
		textView_score.setText(String.valueOf(score));
	}

	/** ������ϵ�л�ȡ��ֵ **/
	public int getMickeyPlayerScore() {
		int score = 0;
		if (textView_score.getText() != null
				&& !textView_score.getText().toString().equals("")) {
			score = Integer.parseInt(textView_score.getText().toString());
		}
		return score;
	}

	/**
	 * ������ҷ�ֵ,��������ֵ�������֮��ķ�ֵ ����trueΪbust
	 **/

	// 针对高分赛大改动！！！！！！
	// 改变01游戏玩家分数
	public boolean change01PlayerScore(int score) {
		int totle_score = 0;
		String str = textView_score.getText().toString();
		if (str != null && !str.equals("")) {
			int last_score = Integer.parseInt(str);

			// 针对高分赛大改动！！！！！！
			if (GameModeBean.getInstance().getGameType()
					.equals(Constant.GAMETYPEHIGHSCROE)) {
				totle_score = last_score + score;
				textView_score.setText("" + totle_score);
				return false;
				
			// 01系列分数改动
			} else if (last_score >= score) {
				totle_score = last_score - score;
				textView_score.setText("" + totle_score);
				return false;
			} else {
				return true;
			}
		} else {
			totle_score = score;
			textView_score.setText("" + totle_score);
			return false;
		}
	}

	/**
	 * ������BUSTʱ�����÷�ֵ
	 **/
	public void setBustScore(int score) {
		textView_score.setText("" + score);
	}

	/**
	 * ����������
	 **/
	public void setPlayer1Name(String name) {
		textView1.setText(name);
	}

	/**
	 * ����������
	 **/
	public void setPlayer2Name(String name) {
		textView2.setText(name);
	}

	/**
	 * ����������
	 **/
	public void setPlayer3Name(String name) {
		textView3.setText(name);
	}

	/**
	 * ����������
	 **/
	public void setPlayer4Name(String name) {
		textView4.setText(name);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	@Override
	public void setAnimation(Animation animation) {
		// TODO Auto-generated method stub
		super.setAnimation(animation);
	}

	@Override
	public void setBackgroundResource(int resid) {
		// TODO Auto-generated method stub
		super.setBackgroundResource(resid);
	}
}
