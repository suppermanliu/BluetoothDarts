package com.hopen.darts.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.bean.ScoreRoundBean;
import com.hopen.darts.common.DataArea;

public class MickeyRoundAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<ScoreRoundBean> list;
	private ScorePlayerBean playerScoreBean;
	private int roundNumber, round;

	public final class ListItemView {
		// �Զ���ؼ�����
		private LinearLayout LinearLayout_game_round;
		private TextView textView_game_round;
		private ImageView imageView_first, imageView_second, imageView_third;
	}

	/** playerScoreBean��ҷ�ֵ��roundNumber�ڼ��غϣ�round�ܻغ��� **/
	public MickeyRoundAdapter(Context context, ScorePlayerBean playerScoreBean,
			int roundNumber, int round) {
		this.context = context;
		this.playerScoreBean = playerScoreBean;
		this.roundNumber = roundNumber;
		this.round = round;
		list = playerScoreBean.getList();
		this.mInflater = LayoutInflater.from(context); // ������ͼ����������������
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
			convertView = mInflater.inflate(R.layout.listview_mickeygame_round,
					null);
			listItemView.textView_game_round = (TextView) convertView
					.findViewById(R.id.textView_game_round);
			listItemView.LinearLayout_game_round = (LinearLayout) convertView
					.findViewById(R.id.LinearLayout_game_round);
			listItemView.imageView_first = (ImageView) convertView
					.findViewById(R.id.imageView_first);
			listItemView.imageView_second = (ImageView) convertView
					.findViewById(R.id.imageView_second);
			listItemView.imageView_third = (ImageView) convertView
					.findViewById(R.id.imageView_third);
			// ���ÿؼ�����convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		listItemView.imageView_first.setImageResource(context.getResources()
				.getColor(R.color.transparent));
		listItemView.imageView_second.setImageResource(context.getResources()
				.getColor(R.color.transparent));
		listItemView.imageView_third.setImageResource(context.getResources()
				.getColor(R.color.transparent));
		if (position == 0) {
			listItemView.textView_game_round.setText("ROUND" + "  "
					+ roundNumber + "/" + round);
			listItemView.imageView_first.setVisibility(View.GONE);
			listItemView.imageView_second.setVisibility(View.GONE);
			listItemView.imageView_third.setVisibility(View.GONE);
		} else {
			int index = 0;
			ScoreRoundBean roundScoreBean = null;

			if (playerScoreBean.getList().size() > 8
					&& playerScoreBean.getList().size() <= 16) {
				if (playerScoreBean.getList().get(8).validRoundDarts() == 0) {
					if (position <= 8) {
						index = position;
						roundScoreBean = list.get(position - 1);
					}
				} else {
					if (position <= 8) {
						index = position
								+ (playerScoreBean.getList().size() - 8);
						roundScoreBean = list.get(index - 1);
					}
				}
			}

			if (roundScoreBean != null) {
				int first = roundScoreBean.firstDartArea;
				int second = roundScoreBean.secondDartArea;
				int third = roundScoreBean.thirdDartArea;

				boolean firstIsClose = roundScoreBean.isFirstIsClose();
				boolean secondIsClose = roundScoreBean.isSecondIsClose();
				boolean thirdIsClose = roundScoreBean.isThirdIsClose();
				listItemView.textView_game_round.setText("R" + index);
				listItemView.imageView_first
						.setImageResource(getRoundImg(vaildMultiple(first,
								firstIsClose)));
				listItemView.imageView_second
						.setImageResource(getRoundImg(vaildMultiple(second,
								secondIsClose)));
				listItemView.imageView_third
						.setImageResource(getRoundImg(vaildMultiple(third,
								thirdIsClose)));
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

	/** areaId ��������ֵ **/
	private int vaildMultiple(int areaId, boolean flag) {
		int vaildmultiple = -1;

		if (areaId >= 0) {
			if ((areaId == 75 || areaId == 77 || areaId == 78 || areaId == 76)
					&& !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else if ((areaId == 35 || areaId == 37 || areaId == 38 || areaId == 36)
					&& !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else if ((areaId == 71 || areaId == 73 || areaId == 74 || areaId == 72)
					&& !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else if ((areaId == 31 || areaId == 33 || areaId == 34 || areaId == 32)
					&& !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else if ((areaId == 19 || areaId == 21 || areaId == 22 || areaId == 20)
					&& !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else if ((areaId == 17 || areaId == 15 || areaId == 18 || areaId == 16)
					&& !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else if ((areaId == 2 || areaId == 1) && !flag) {
				vaildmultiple = DataArea.getMultiple(areaId);
			} else {
				vaildmultiple = 0;
			}
		} else {
			vaildmultiple = areaId;
		}

		return vaildmultiple;
	}

	/** ��ȡ��һغ�ÿ�ڷ�ֵ��Ӧ��ͼ�� **/
	private int getRoundImg(int areaId) {
		int resID = R.drawable.round_score4;
		if (areaId == -1) {
			resID = context.getResources().getColor(R.color.transparent);
		} else {
			switch (areaId) {
			case 0:
				resID = R.drawable.round_score4;
				break;
			case 3:
				resID = R.drawable.round_score1;
				break;
			case 2:
				resID = R.drawable.round_score2;
				break;
			case 1:
				resID = R.drawable.round_score3;
				break;
			default:
				break;
			}
		}
		return resID;
	}
}
