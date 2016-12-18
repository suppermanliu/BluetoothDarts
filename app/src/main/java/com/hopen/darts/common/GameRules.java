package com.hopen.darts.common;

import java.util.List;

import com.hopen.darts.bean.ScorePlayerBean;
import com.hopen.darts.constant.Constant;

/**
 * @author lpmo
 * 
 */
public class GameRules {

	private static GameRules gameRules = null;
	private ScorePlayerBean[] playerScoreBean = null;

	/**
	 * 
	 */
	private GameRules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public synchronized static GameRules getInstance() {
		if (gameRules == null)
			gameRules = new GameRules();
		return gameRules;
	}

	public ScorePlayerBean[] getScoreBean() {
		return playerScoreBean;
	}

	/** ������� **/
	public String getPlayerRank(ScorePlayerBean scoreBean) {
		String rank = null;

		int[] arr = new int[playerScoreBean.length];
		int temp = 0;
		for (int i = 0; i < playerScoreBean.length; i++) {
			arr[i] = playerScoreBean[i].getTotalPlayerScore();
		}
		// ð������ÿ�ΰ����ķŵ����N-i����Ϊ��i������֮��
		// ����arr�����i�����Ѿ��ǰ��մ�С˳����ˣ����Բ���Ҫ��������
		// �����һ������֮�����һ����϶������ģ���һ��ֻ��Ҫ��ǰ9��������
		if (playerScoreBean.length > 1) {
			for (int i = 1; i < playerScoreBean.length; ++i) {
				for (int j = 0; j < playerScoreBean.length - i; ++j) {
					// ���ǰ�����Ⱥ���Ĵ����ǰ���˳��ģ����Ҫ����
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
			// �ó�����ҵ���β�����String
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == scoreBean.getTotalPlayerScore()) {

					switch (i) {
					case 0:
						rank = Constant.NUMBER1;
						break;
					case 1:
						rank = Constant.NUMBER2;
						break;
					case 2:
						rank = Constant.NUMBER3;
						break;
					case 3:
						rank = Constant.NUMBER4;
						break;
					}
					return rank;
				}
			}
		} else {
			rank = Constant.NUMBER1;
		}
		return rank;
	}
	/** ������� **/
	public String getPlayerRank_mickey(ScorePlayerBean scoreBean) {
		String rank = null;

		int[] arr = new int[playerScoreBean.length];
		int temp = 0;
		for (int i = 0; i < playerScoreBean.length; i++) {
			arr[i] = playerScoreBean[i].getTotalScore();
		}
		// ð������ÿ�ΰ����ķŵ����N-i����Ϊ��i������֮��
		// ����arr�����i�����Ѿ��ǰ��մ�С˳����ˣ����Բ���Ҫ��������
		// �����һ������֮�����һ����϶������ģ���һ��ֻ��Ҫ��ǰ9��������
		if (playerScoreBean.length > 1) {
			for (int i = 1; i < playerScoreBean.length; ++i) {
				for (int j = 0; j < playerScoreBean.length - i; ++j) {
					// ���ǰ�����Ⱥ���Ĵ����ǰ���˳��ģ����Ҫ����
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
			// �ó�����ҵ���β�����String
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == scoreBean.getTotalScore()) {

					switch (i) {
					case 0:
						rank = Constant.NUMBER1;
						break;
					case 1:
						rank = Constant.NUMBER2;
						break;
					case 2:
						rank = Constant.NUMBER3;
						break;
					case 3:
						rank = Constant.NUMBER4;
						break;
					}
					return rank;
				}
			}
		} else {
			rank = Constant.NUMBER1;
		}
		return rank;
	}
	/** ȥ�������е��ظ�ֵ **/
	private Integer[] getDistinct(int num[]) {
		List<Integer> list = new java.util.ArrayList<Integer>();
		for (int i = 0; i < num.length; i++) {
			if (!list.contains(num[i])) {// ���list���鲻����num[i]�е�ֵ�Ļ����ͷ���true��
				list.add(num[i]); // ��list�����м���num[i]��ֵ���Ѿ����˹�
			}
		}
		return list.toArray(new Integer[0]);
	}

	/** ����Ҹ����ʼ����Ӧÿλ��һ���ÿ����ҵ�ScorePlayerBean���� **/
	//初始化玩家数据数组
	public void initPlayerBeanArray(String gameType, int TotalCardNum) {

		if (gameType.equals(Constant.GAMETYPEMICKEY)) {
			boolean flag = false;
			for (int i = 0; i < Constant.GAMEROUNDSMICKEY; i++) {

				flag = true;
				flag_15 = false;
				flag_16 = false;
				flag_17 = false;
				flag_18 = false;
				flag_19 = false;
				flag_20 = false;
				flag_bull = false;
			}
		}

		playerScoreBean = new ScorePlayerBean[TotalCardNum];
		switch (TotalCardNum) {
		case 1:
			playerScoreBean[0] = new ScorePlayerBean();
			break;
		case 2:
			playerScoreBean[0] = new ScorePlayerBean();
			playerScoreBean[1] = new ScorePlayerBean();
			break;
		case 3:
			playerScoreBean[0] = new ScorePlayerBean();
			playerScoreBean[1] = new ScorePlayerBean();
			playerScoreBean[2] = new ScorePlayerBean();
			break;
		case 4:
			playerScoreBean[0] = new ScorePlayerBean();
			playerScoreBean[1] = new ScorePlayerBean();
			playerScoreBean[2] = new ScorePlayerBean();
			playerScoreBean[3] = new ScorePlayerBean();
			break;

		}

	}

	/** ��ݵڼ�λ��ҷ��ظ���ҵ�ScorePlayerBean **/
	public ScorePlayerBean getPlayerBean(int playerIndex) {
		return playerScoreBean[playerIndex];
	}

	/**
	 * ��ڼ��غϵڼ��ڵ� ��ֵ
	 * 
	 * @param cardIndex
	 *            �ڼ�����һ� �ڼ����Ŷ�
	 * @param roundIndex
	 *            �ڼ����غ�
	 * @param DartIndex
	 *            �ڼ���
	 * @param DartArea
	 *            ��ֵ
	 */
	
	//设置玩家分数
	public void setPlayerScore(int playerIndex, int cardIndex, int roundIndex,
			int DartIndex, int DartArea) {

		playerScoreBean[cardIndex].addRoundScore(playerIndex, roundIndex,
				DartIndex, DartArea);

	}

	
	public void setBustPlayerScore(int playerIndex, int cardIndex,
			int roundIndex, int DartIndex, int DartArea) {

		for (int i = 1; i <= DartIndex; i++) {
			playerScoreBean[cardIndex].addRoundScore(playerIndex, roundIndex,
					i, DartArea);
		}
	}

	
	private boolean flag_15 = false;
	private boolean flag_16 = false;
	private boolean flag_17 = false;
	private boolean flag_18 = false;
	private boolean flag_19 = false;
	private boolean flag_20 = false;
	private boolean flag_bull = false;

	public boolean isFlag_15() {
		return flag_15;
	}

	public void setFlag_15(boolean flag_15) {
		this.flag_15 = flag_15;
	}

	public boolean isFlag_16() {
		return flag_16;
	}

	public void setFlag_16(boolean flag_16) {
		this.flag_16 = flag_16;
	}

	public boolean isFlag_17() {
		return flag_17;
	}

	public void setFlag_17(boolean flag_17) {
		this.flag_17 = flag_17;
	}

	public boolean isFlag_18() {
		return flag_18;
	}

	public void setFlag_18(boolean flag_18) {
		this.flag_18 = flag_18;
	}

	public boolean isFlag_19() {
		return flag_19;
	}

	public void setFlag_19(boolean flag_19) {
		this.flag_19 = flag_19;
	}

	public boolean isFlag_20() {
		return flag_20;
	}

	public void setFlag_20(boolean flag_20) {
		this.flag_20 = flag_20;
	}

	public boolean isFlag_bull() {
		return flag_bull;
	}

	public void setFlag_bull(boolean flag_bull) {
		this.flag_bull = flag_bull;
	}


}
