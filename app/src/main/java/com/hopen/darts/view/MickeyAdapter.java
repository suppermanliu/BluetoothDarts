package com.hopen.darts.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.activity.GameMickeyActivity;
import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.common.GameRules;

public class MickeyAdapter extends BaseAdapter {

	private GameMickeyActivity instance;
	private LayoutInflater mInflater;
	private int playerSum = 0;
	private ScorePlayerBean[] playerScoreBean;
	private GameRules 	gameRules;
	public final class ListItemView {
		LinearLayout LinearLayout_player1, LinearLayout_player2,
				LinearLayout_player3, LinearLayout_player4, LinearLayout_line,
				LinearLayout_number;
		ImageView imageView_player1_line, imageView_player2_line,
				imageView_player3_line, imageView_player4_line,
				imageView_player1, imageView_player2, imageView_player3,
				imageView_player4;
	}

	public MickeyAdapter(GameMickeyActivity instance, int playerSum) {
		this.instance = instance;
		this.playerSum = playerSum;
		
		
		gameRules = GameRules.getInstance();
		playerScoreBean =gameRules.getScoreBean();
		this.mInflater = LayoutInflater.from(instance);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = mInflater.inflate(R.layout.listview_game_mickey, null);
			listItemView.LinearLayout_player1 = (LinearLayout) convertView.findViewById(R.id.LinearLayout_player1);
		//	listItemView.LinearLayout_player2 = (LinearLayout) convertView.findViewById(R.id.LinearLayout_player2);
			listItemView.LinearLayout_player3 = (LinearLayout) convertView.findViewById(R.id.LinearLayout_player3);
		//	listItemView.LinearLayout_player4 = (LinearLayout) convertView.findViewById(R.id.LinearLayout_player4);
			listItemView.LinearLayout_line = (LinearLayout) convertView.findViewById(R.id.LinearLayout_line);
			listItemView.LinearLayout_number = (LinearLayout) convertView.findViewById(R.id.LinearLayout_number);
			listItemView.imageView_player1_line = (ImageView) convertView.findViewById(R.id.imageView_player1_line);
	//		listItemView.imageView_player2_line = (ImageView) convertView.findViewById(R.id.imageView_player2_line);
			listItemView.imageView_player3_line = (ImageView) convertView.findViewById(R.id.imageView_player3_line);
		//	listItemView.imageView_player4_line = (ImageView) convertView.findViewById(R.id.imageView_player4_line);
			listItemView.imageView_player1 = (ImageView) convertView.findViewById(R.id.imageView_player1);
		//	listItemView.imageView_player2 = (ImageView) convertView.findViewById(R.id.imageView_player2);
			listItemView.imageView_player3 = (ImageView) convertView.findViewById(R.id.imageView_player3);
	//		listItemView.imageView_player4 = (ImageView) convertView.findViewById(R.id.imageView_player4);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		switch (playerSum) {
		case 2:
			listItemView.LinearLayout_player1.setVisibility(View.VISIBLE);
		//	listItemView.LinearLayout_player2.setVisibility(View.GONE);
			listItemView.LinearLayout_player3.setVisibility(View.VISIBLE);
			//listItemView.LinearLayout_player4.setVisibility(View.GONE);
			listItemView.imageView_player1_line.setImageResource(R.drawable.redline);
			listItemView.imageView_player3_line.setImageResource(R.drawable.yellowline);
			listItemView.imageView_player1.setImageResource(getScoreImg(playerScoreBean,position,1));
			listItemView.imageView_player3.setImageResource(getScoreImg(playerScoreBean,position,2));
			break;
	
		default:
			break;
		}
		if(getValidNumber(position)){
			listItemView.LinearLayout_line.setVisibility(View.VISIBLE);
		}else{
			listItemView.LinearLayout_line.setVisibility(View.GONE);
		}
		listItemView.LinearLayout_number.setBackgroundResource(getNumberImg(position));
		return convertView;
	}

	private boolean getValidNumber(int position) {
		boolean flag = false;
		switch (position) {
		case 0:
			flag = gameRules
					.isFlag_20();
			break;
		case 1:
			flag = gameRules
					.isFlag_19();
			break;
		case 2:
			flag = gameRules
					.isFlag_18();
			break;
		case 3:
			flag = gameRules
					.isFlag_17();
			break;
		case 4:
			flag = gameRules
					.isFlag_16();
			break;
		case 5:
			flag = gameRules
					.isFlag_15();
			break;
		case 6:
			flag = gameRules
					.isFlag_bull();
			break;
		default:
			break;
		}
		return flag;
	}

	private int getScoreImg(ScorePlayerBean[] playerScoreBean,int position, int playerNumber) {
		int resId = 0;
		int ValidSum = 0;
		boolean flag = false;
		switch (position) {
		case 0:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_20();
			flag = gameRules.isFlag_20();
			break;
		case 1:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_19();
			flag =gameRules
					.isFlag_19();
			break;
		case 2:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_18();
			flag = gameRules
					.isFlag_18();
			break;
		case 3:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_17();
			flag = gameRules
					.isFlag_17();
			break;
		case 4:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_16();
			flag = gameRules
					.isFlag_16();
			break;
		case 5:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_15();
			flag = gameRules
					.isFlag_15();
			break;
		case 6:
			ValidSum = playerScoreBean[playerNumber - 1].getNumber_bull();
			flag = gameRules
					.isFlag_bull();
			break;
		default:
			break;
		}
		if (flag) {
			resId = R.drawable.score_gray1;
		} else {
			if (ValidSum == -1) {
				resId = instance.getResources().getColor(R.color.transparent);
			} else if(ValidSum >= 3){
				switch (playerNumber) {
				case 1:
					resId = R.drawable.score_red1;
					break;
				case 2:
					resId = R.drawable.score_yellow1;
					break;
				case 3:
					resId = R.drawable.score_blue1;
					break;
				case 4:
					resId = R.drawable.score_green1;
					break;
				}
			}else{
				switch (ValidSum) {
				case 0:
					switch (playerNumber) {
					case 1:
						resId = R.drawable.score_red4;
						break;
					case 2:
						resId = R.drawable.score_yellow4;
						break;
					case 3:
						resId = R.drawable.score_blue4;
						break;
					case 4:
						resId = R.drawable.score_green4;
						break;
					}
					break;
				case 1:
					switch (playerNumber) {
					case 1:
						resId = R.drawable.score_red3;
						break;
					case 2:
						resId = R.drawable.score_yellow3;
						break;
					case 3:
						resId = R.drawable.score_blue3;
						break;
					case 4:
						resId = R.drawable.score_green3;
						break;
					}
					break;
				case 2:
					switch (playerNumber) {
					case 1:
						resId = R.drawable.score_red2;
						break;
					case 2:
						resId = R.drawable.score_yellow2;
						break;
					case 3:
						resId = R.drawable.score_blue2;
						break;
					case 4:
						resId = R.drawable.score_green2;
						break;
					}
					break;
				}
			}
		}
		return resId;
	}

	private int getNumberImg(int position) {
		int resId = 0;
		switch (position) {
		case 6:
			if (gameRules
					.isFlag_bull()) {
				resId = R.drawable.bull_gray;
			} else {
				resId = R.drawable.bull;
			}
			break;
		case 5:
			if (gameRules
					.isFlag_15()) {
				resId = R.drawable.number15_gray;
			} else {
				resId = R.drawable.number15;
			}
			break;
		case 4:
			if (gameRules
					.isFlag_16()) {
				resId = R.drawable.number16_gray;
			} else {
				resId = R.drawable.number16;
			}
			break;
		case 3:
			if (gameRules
					.isFlag_17()) {
				resId = R.drawable.number17_gray;
			} else {
				resId = R.drawable.number17;
			}
			break;
		case 2:
			if (gameRules
					.isFlag_18()) {
				resId = R.drawable.number18_gray;
			} else {
				resId = R.drawable.number18;
			}
			break;
		case 1:
			if (gameRules
					.isFlag_19()) {
				resId = R.drawable.number19_gray;
			} else {
				resId = R.drawable.number19;
			}
			break;
		case 0:
			if (gameRules
					.isFlag_20()) {
				resId = R.drawable.number20_gray;
			} else {
				resId = R.drawable.number20;
			}
			break;
		default:
			break;
		}
		return resId;
	}

	public void notifyDataChanged() {
		playerScoreBean = null;
		playerScoreBean = gameRules
				.getScoreBean();
		this.notifyDataSetChanged();
	}
}
