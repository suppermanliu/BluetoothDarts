package com.hopen.darts.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.bean.ScoreRoundBean;

public class RoundAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<ScoreRoundBean> list;
	private ScorePlayerBean playerScoreBean;
	private int roundNumber, round;
	private int list_size = 0;
	
	public final class ListItemView {
		// �Զ���ؼ�����
		private LinearLayout LinearLayout_game_round, LinearLayout_round;
		private TextView textView_game_round, textView_round_score;
	}

	/** playerScoreBean��ҷ�ֵ��roundNumber�ڼ��غϣ�round�ܻغ��� **/
	public RoundAdapter(Context context, ScorePlayerBean playerScoreBean,
			int roundNumber, int round) {
		this.context = context;
		this.playerScoreBean = playerScoreBean;
		this.roundNumber = roundNumber;
		this.round = round;
		list = playerScoreBean.getList();//返回一个回合的集合
		this.mInflater = LayoutInflater.from(context); // ������ͼ����������������
		list_size = list.size() + 1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return list_size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return position;
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
			convertView = mInflater.inflate(R.layout.listview_game_round, null);
			listItemView.textView_game_round = (TextView) convertView
					.findViewById(R.id.textView_game_round);
			listItemView.textView_round_score = (TextView) convertView
					.findViewById(R.id.textView_round_score);
			listItemView.LinearLayout_game_round = (LinearLayout) convertView
					.findViewById(R.id.LinearLayout_game_round);
			listItemView.LinearLayout_round = (LinearLayout) convertView
					.findViewById(R.id.LinearLayout_round);
			// ���ÿؼ�����convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.LinearLayout_round.setBackground(context.getResources()
				.getDrawable(R.drawable.game_round));

		
		//设置第一行为：ROUND  1/10
		
		//position(0-9)
		if (position == 0) {           
		
			listItemView.textView_game_round.setText("ROUND");
			listItemView.textView_round_score.setText("" + roundNumber + "/"+ round);
		}
		else {
			
			//回合数：1-8
			if (playerScoreBean.getList().size() <= 8) {
				int sum = playerScoreBean.getRoundScore(position);    //获取对应回合的3镖总分
			
				listItemView.textView_game_round.setText("R" + position);
				listItemView.textView_round_score.setText("" + sum);
			} 
			
			//回合数：9-16
			else if (playerScoreBean.getList().size() > 8    
					&& playerScoreBean.getList().size() <= 16) {
				if (playerScoreBean.getList().get(8).validRoundDarts() == 0) { //第9回合未开始投镖
					if (position <= 8) {
						int sum = playerScoreBean.getRoundScore(position);
						listItemView.textView_game_round
								.setText("R" + position);
						listItemView.textView_round_score.setText("" + sum);
					} 
				} else {                                                       //第9回合已开始投镖
					if (position <= 8) {
						int index = position
								+ (playerScoreBean.getList().size() - 8);
						int sum = playerScoreBean.getRoundScore(index);
						listItemView.textView_game_round.setText("R" + index);
						listItemView.textView_round_score.setText("" + sum);
					} 
				}
			}
			
			//回合数：17-20
			else {
				if (playerScoreBean.getList().get(8).validRoundDarts() == 0) {
					if (position <= 8) {
						int sum = playerScoreBean.getRoundScore(position);
						listItemView.textView_game_round
								.setText("R" + position);
						listItemView.textView_round_score.setText("" + sum);
					} 
				} else if (playerScoreBean.getList().get(16).validRoundDarts() == 0) {
					if (position <= 8) {
						int index = position + 8;
						int sum = playerScoreBean.getRoundScore(index);
						listItemView.textView_game_round.setText("R" + index);
						listItemView.textView_round_score.setText("" + sum);
					} 
				} else {
					if (position <= 8) {
						int index = position + 12;
						int sum = playerScoreBean.getRoundScore(index);
						listItemView.textView_game_round.setText("R" + index);
						listItemView.textView_round_score.setText("" + sum);
					} 
				}
			}
		}
		return convertView;
	}

	public void notifyData(ScorePlayerBean playerScoreBean, int roundNumber) {
		this.playerScoreBean = playerScoreBean;
		this.roundNumber = roundNumber;
		list = playerScoreBean.getList();

		this.notifyDataSetChanged();
	
	}
}
