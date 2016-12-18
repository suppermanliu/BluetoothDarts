package com.hopen.darts.view;

import java.text.DecimalFormat;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.bean.InningGameVariate;
import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.common.GameRules;
import com.hopen.darts.constant.Constant;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class StatsPlayerView extends FrameLayout {

	private boolean vs_visibility = false;

	private ImageView vs_img, player_head_img, winner;
	private TextView player_name, ppd_text, player_appraise;

	public StatsPlayerView(Context context) {
		super(context);

	}

	public StatsPlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		View view = LayoutInflater.from(context).inflate(R.layout.view_stats,
				null);
		player_head_img = (ImageView) view
				.findViewById(R.id.imageView_stats_player_head);
		winner = (ImageView) view.findViewById(R.id.winner);
		vs_img = (ImageView) view.findViewById(R.id.stats_imageView_vs);
		player_name = (TextView) view.findViewById(R.id.stats_player_name);
		ppd_text = (TextView) view.findViewById(R.id.stats_player_ppd);
		player_appraise = (TextView) view.findViewById(R.id.stats_player_appraise);
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.StatsPlayerView);

		player_name.setTextSize(20);
		ppd_text.setTextSize(30);
		player_appraise.setTextSize(30);

		vs_visibility = a.getBoolean(R.styleable.StatsPlayerView_vs_visibility,
				false);
		if (vs_visibility) {
			winner.setVisibility(View.GONE);
			vs_img.setVisibility(View.VISIBLE);
			if (GameModeBean.getInstance().getGameType()
					.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
				ppd_text.setText(context.getResources().getString(
						R.string.mprstr));
				player_appraise.setText(context.getResources().getString(
						R.string.totalstr));
			} else {
				ppd_text.setText(context.getResources().getString(
						R.string.ppdstr));
				player_appraise.setText(context.getResources().getString(
						R.string.totalstr));
			}
		}
		// startFireWorks();
		addView(view);
		a.recycle();
	}

	@Override
	public void setVisibility(int visibility) {
		// TODO Auto-generated method stub
		super.setVisibility(visibility);
	}

	/**
	 * ͳ����Ϣ��ʾ index �ڼ�λ��һ��ߵڼ������
	 **/
	public void setPlayerMsg1(int index, ScorePlayerBean scoreBean, int Rank) {
		switch (index) {
		case 1:
			player_head_img.setBackgroundResource(R.drawable.player_1);

			player_name.setTextColor(getResources().getColor(R.color.red));

			ppd_text.setTextColor(getResources().getColor(R.color.red));
			player_appraise.setTextColor(getResources().getColor(R.color.red));
			break;
		case 2:
			player_head_img.setBackgroundResource(R.drawable.player_2);

			player_name.setTextColor(getResources().getColor(R.color.yellow));

			ppd_text.setTextColor(getResources().getColor(R.color.yellow));
			player_appraise.setTextColor(getResources()
					.getColor(R.color.yellow));
			break;
		case 3:
			player_head_img.setBackgroundResource(R.drawable.player_3);

			player_name.setTextColor(getResources().getColor(R.color.blue));

			ppd_text.setTextColor(getResources().getColor(R.color.blue));
			player_appraise.setTextColor(getResources().getColor(R.color.blue));
			break;
		case 4:
			player_head_img.setBackgroundResource(R.drawable.player_4);
			player_name.setTextColor(getResources().getColor(R.color.green));

			ppd_text.setTextColor(getResources().getColor(R.color.green));
			player_appraise
					.setTextColor(getResources().getColor(R.color.green));
			break;
		}
		GameRules gameRules = GameRules.getInstance();
		DecimalFormat df = new DecimalFormat("0.00");// ��ʽ��С��
		if (GameModeBean.getInstance().getGameType()
				.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
			// String Rank = gameRules.getPlayerRank_mickey(scoreBean);
			int score = scoreBean.getTotalScore();

			if (Rank == 0) {

				winner.setBackgroundResource(R.drawable.winner);

			} else if (Rank == 1) {

				winner.setBackgroundResource(R.drawable.number_2);

			}

			/*
			 * String mpr = df .format(3 * ((float)
			 * scoreBean.getVaildNumberSum() / scoreBean
			 * .getDartsValidCount()));
			 */
			float mpr_value = 3 * ((float) scoreBean.getVaildNumberSum() / scoreBean
					.getDartsValidCount());
			float mpr = (float) (Math.round(mpr_value * 100)) / 100;
			vs_img.setVisibility(View.GONE);
			player_name.setText(Constant.PlayerName[index - 1]);
			ppd_text.setText(mpr + "");
			player_appraise.setText("" + score);
		} else {
			// String Rank = gameRules.getPlayerRank(scoreBean);

			if (Rank == 0) {

				winner.setBackgroundResource(R.drawable.winner);

			} else if (Rank == 1) {

				winner.setBackgroundResource(R.drawable.number_2);

			}

			float score = scoreBean.getTotalPlayerScore();
			// float CurrentTotalScore = scoreBean.getTotalPlayerScore();
			int VaildCountDarts = scoreBean.getDartsValidCount();
			float ppd_value = score / VaildCountDarts;
			// String ppd = df.format(ppd_value);
			float ppd = (float) (Math.round(ppd_value * 100)) / 100;
			vs_img.setVisibility(View.GONE);
			player_name.setText(Constant.PlayerName[index - 1]);
			ppd_text.setText(ppd + "");

			player_appraise.setText("" + (int) score);

		}
	}

	public void setPlayerMsg(int index, ScorePlayerBean scoreBean) {
		switch (index) {
		case 1:
			player_head_img.setBackgroundResource(R.drawable.player_1);

			player_name.setTextColor(getResources().getColor(R.color.red));

			ppd_text.setTextColor(getResources().getColor(R.color.red));
			player_appraise.setTextColor(getResources().getColor(R.color.red));
			break;
		case 2:
			player_head_img.setBackgroundResource(R.drawable.player_2);

			player_name.setTextColor(getResources().getColor(R.color.yellow));

			ppd_text.setTextColor(getResources().getColor(R.color.yellow));
			player_appraise.setTextColor(getResources()
					.getColor(R.color.yellow));
			break;
		case 3:
			player_head_img.setBackgroundResource(R.drawable.player_3);

			player_name.setTextColor(getResources().getColor(R.color.blue));

			ppd_text.setTextColor(getResources().getColor(R.color.blue));
			player_appraise.setTextColor(getResources().getColor(R.color.blue));
			break;
		case 4:
			player_head_img.setBackgroundResource(R.drawable.player_4);
			player_name.setTextColor(getResources().getColor(R.color.green));

			ppd_text.setTextColor(getResources().getColor(R.color.green));
			player_appraise
					.setTextColor(getResources().getColor(R.color.green));
			break;
		}
		GameRules gameRules = GameRules.getInstance();
		DecimalFormat df = new DecimalFormat("0.00");// ��ʽ��С��
		if (GameModeBean.getInstance().getGameType()
				.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
			String Rank = gameRules.getPlayerRank_mickey(scoreBean);
			int score = scoreBean.getTotalScore();

			if (Rank.equalsIgnoreCase(Constant.NUMBER1)) {

				winner.setBackgroundResource(R.drawable.winner);

			} else if (Rank.equalsIgnoreCase(Constant.NUMBER2)) {

				winner.setBackgroundResource(R.drawable.number_2);

			} else if (Rank.equalsIgnoreCase(Constant.NUMBER3)) {

				winner.setBackgroundResource(R.drawable.number_3);

			} else if (Rank.equalsIgnoreCase(Constant.NUMBER4)) {

				winner.setBackgroundResource(R.drawable.number_4);
			}

			String mpr = df
					.format(3 * ((float) scoreBean.getVaildNumberSum() / scoreBean
							.getDartsValidCount()));

			vs_img.setVisibility(View.GONE);
			player_name.setText(Constant.PlayerName[index - 1]);
			ppd_text.setText(mpr);
			player_appraise.setText("" + score);
		} else {
			String Rank = gameRules.getPlayerRank(scoreBean);

			if (Rank.equalsIgnoreCase(Constant.NUMBER1)) {

				winner.setBackgroundResource(R.drawable.winner);

			} else if (Rank.equalsIgnoreCase(Constant.NUMBER2)) {

				winner.setBackgroundResource(R.drawable.number_2);
			} else if (Rank.equalsIgnoreCase(Constant.NUMBER3)) {

				winner.setBackgroundResource(R.drawable.number_3);
			} else if (Rank.equalsIgnoreCase(Constant.NUMBER4)) {

				winner.setBackgroundResource(R.drawable.number_4);
			}
			if (new InningGameVariate().isteam() == true) {

				float score = scoreBean.getTeamPlayerScore(index - 1);

				int VaildCountDarts = scoreBean.getPlayerRoundSum(index - 1);

				float ppd_value = score / VaildCountDarts;
				String ppd = df.format(ppd_value);
				vs_img.setVisibility(View.GONE);
				player_name.setText(Constant.PlayerName[index - 1]);
				ppd_text.setText(ppd);

				player_appraise.setText("" + (int) score);

			} else {
				float score = scoreBean.getTotalPlayerScore();
				// float CurrentTotalScore = scoreBean.getTotalPlayerScore();
				int VaildCountDarts = scoreBean.getDartsValidCount();
				float ppd_value = score / VaildCountDarts;
				String ppd = df.format(ppd_value);
				vs_img.setVisibility(View.GONE);
				player_name.setText(Constant.PlayerName[index - 1]);
				ppd_text.setText(ppd);

				player_appraise.setText("" + (int) score);
			}

		}
	}

	/**
	 * ���Ż�Ա���̻���Ч
	 * 
	 **/
	public void startFireWorks() {
		// stats_player_level.setBackgroundResource(R.drawable.fireworks2);
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
