 package com.hopen.darts.bean;

import java.util.ArrayList;
import java.util.List;

import com.hopen.darts.common.DataArea;
import com.hopen.darts.common.GameRules;
import com.hopen.darts.constant.Constant;
 
/**
 * @author lpmo
 * 每个玩家的数据对象
 */
public class ScorePlayerBean {

	private List<ScoreRoundBean> RoundList;
	private boolean openDart = false;
	private int startScore = 0;

	public int getStartScore() {
		return startScore;
	}

	public void setStartScore(int startScore) {
		this.startScore = startScore;
	}

	//获取对应游戏类型的回合数，并初始化回合列表。
	public ScorePlayerBean() {
		super();
		// TODO Auto-generated constructor stub

		RoundList = new ArrayList<ScoreRoundBean>();
		int totalRounds = GameModeBean.getInstance().getTotalRounds();
		for (int i = 0; i < totalRounds; i++) {
			ScoreRoundBean RoundScoreBean = new ScoreRoundBean();
			RoundList.add(RoundScoreBean);
		}
	}

	public List<ScoreRoundBean> getList() {

		return RoundList;
	}

	public void setList(List<ScoreRoundBean> RoundList) {
		this.RoundList = RoundList;
	}

	//添加每一回合的分数
	public void addRoundScore(int playerIndex, int roundIndex, int DartIndex,
			int DartArea) {

		//获取到对应位置的回合数据对象
		ScoreRoundBean roundScoreBean = RoundList.get(roundIndex - 1);

		//设置对应位置的玩家顺序标记
		roundScoreBean.setPlayerIndex(playerIndex);
		//集合中剔除当前回合对象
		RoundList.remove(roundScoreBean);
		//获取游戏类型（标准米老鼠）
		if (GameModeBean.getInstance().getGameType()
				.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
			boolean isClose = IsClose(DartArea);
			switch (DartIndex) {

			case 1:
				roundScoreBean.setFirstDartArea(DartArea);
				roundScoreBean.setFirstIsClose(isClose);
				break;
			case 2:
				roundScoreBean.setSecondDartArea(DartArea);
				roundScoreBean.setSecondIsClose(isClose);
				break;
			case 3:
				roundScoreBean.setThirdDartArea(DartArea);
				roundScoreBean.setThirdIsClose(isClose);
				break;
			}
		} else {
			switch (DartIndex) {

			case 1:
				roundScoreBean.setFirstDartArea(DartArea);
				break;
			case 2:
				roundScoreBean.setSecondDartArea(DartArea);
				break;
			case 3:
				roundScoreBean.setThirdDartArea(DartArea);
				break;
			}
		}
		//集合中添加当前回合对象
		RoundList.add(roundIndex - 1, roundScoreBean);
	}

	//获得所删除的回合对象
	public ScoreRoundBean removeRoundScore(int roundIndex) {
		return RoundList.remove(roundIndex - 1);
	}

	//获得指定位置的回合对象
	public ScoreRoundBean getScoreRoundBean(int roundIndex) {

		ScoreRoundBean scorebean = RoundList.get(roundIndex - 1);
		return scorebean;

	}

	
	
	
	//获取对应回合的三镖总分数
	public int getRoundScore(int roundIndex) {

		ScoreRoundBean scorebean = RoundList.get(roundIndex - 1);
		int roundScore = scorebean.getThreeDartScore();
		return roundScore;
	}

	//获取玩家截止到目前回合的总分数
	public int getTotalPlayerScore() {

		int totalScore = 0;
		for (int i = 0; i < RoundList.size(); i++) {
			ScoreRoundBean scorebean = RoundList.get(i);
			totalScore += scorebean.getThreeDartScore();
		}
		return totalScore;
	}
	
	
	

	//获取当前玩家到目前回合的总分数
	public int getTeamPlayerScore(int playerIndex) {

		int totalScore = 0;
		for (int i = 0; i < RoundList.size(); i++) {
			ScoreRoundBean scorebean = RoundList.get(i);
			if (playerIndex == scorebean.getPlayerIndex()) {
				totalScore += scorebean.getThreeDartScore();
			}
		}
		return totalScore;

	}

	//获取当前玩家总的有效镖数
	public int getPlayerRoundSum(int index) {
		int sum = 0;
		for (int i = 0; i < RoundList.size(); i++) {
			ScoreRoundBean scorebean = RoundList.get(i);
			if (index == scorebean.getPlayerIndex()) {
				sum += scorebean.validRoundDarts();

			}
		}
		return sum;
	}

	//获取玩家总的有效镖数
	public int getDartsValidCount() {

		int ValidCount = 0;
		for (int i = 0; i < RoundList.size(); i++) {
			ScoreRoundBean scorebean = RoundList.get(i);
			ValidCount += scorebean.validRoundDarts();
		}
		return ValidCount;

	}

	public boolean isOpenDart() {

		return openDart;
	}

	public void setOpenDart(int dartArea) {
		int DartVlue = GameSettingBean.getInstance().getOpenDartVlue();
		int Multiple = DataArea.getMultiple(dartArea);
		if (DartVlue == -1) {
			this.openDart = true;
		} else if (DartVlue == 0) {
			if (Multiple == 2) {
				this.openDart = true;
			} else {
				this.openDart = false;
			}
		} else if (DartVlue == 1) {
			if (Multiple == 3) {
				this.openDart = true;
			} else {
				this.openDart = false;
			}
		} else if (DartVlue == 2) {
			if (Multiple == 2 || Multiple == 3) {
				this.openDart = true;
			} else {
				this.openDart = false;
			}
		}

	}

	public boolean isEndDart(int dartArea) {
		boolean endDart = false;
		int DartVlue = GameSettingBean.getInstance().getOverDartVlue();
		int Multiple = DataArea.getMultiple(dartArea);
		if (DartVlue == -1) {
			endDart = true;
		} else if (DartVlue == 0) {
			if (Multiple == 2) {
				endDart = true;
			} else {
				endDart = false;
			}
		} else if (DartVlue == 1) {
			if (Multiple == 3) {
				endDart = true;
			} else {
				endDart = false;
			}
		} else if (DartVlue == 2) {
			if (Multiple == 2 || Multiple == 3) {
				endDart = true;
			} else {
				endDart = false;
			}
		}

		return endDart;
	}

	public int Number_15 = -1;
	public int Number_16 = -1;
	public int Number_17 = -1;
	public int Number_18 = -1;
	public int Number_19 = -1;
	public int Number_20 = -1;
	public int Number_bull = -1;

	public int ValidScore_15 = -1;
	public int ValidScore_16 = -1;
	public int ValidScore_17 = -1;
	public int ValidScore_18 = -1;
	public int ValidScore_19 = -1;
	public int ValidScore_20 = -1;
	public int ValidScore_bull = -1;

	public int getNumber_15() {
		return Number_15;
	}

	public void setNumber_15(int number_15) {
		if (Number_15 == -1) {
			Number_15 = 0;
		}
		Number_15 = Number_15 + number_15;
		if (Number_15 == 0) {
			Number_15 = -1;
		}
	}

	public int getNumber_16() {
		return Number_16;
	}

	public void setNumber_16(int number_16) {
		if (Number_16 == -1) {
			Number_16 = 0;
		}
		Number_16 = Number_16 + number_16;
		if (Number_16 == 0) {
			Number_16 = -1;
		}
	}

	public int getNumber_17() {
		return Number_17;
	}

	public void setNumber_17(int number_17) {
		if (Number_17 == -1) {
			Number_17 = 0;
		}
		Number_17 = Number_17 + number_17;
		if (Number_17 == 0) {
			Number_17 = -1;
		}
	}

	public int getNumber_18() {
		return Number_18;
	}

	public void setNumber_18(int number_18) {
		if (Number_18 == -1) {
			Number_18 = 0;
		}
		Number_18 = Number_18 + number_18;
		if (Number_18 == 0) {
			Number_18 = -1;
		}
	}

	public int getNumber_19() {
		return Number_19;
	}

	public void setNumber_19(int number_19) {
		if (Number_19 == -1) {
			Number_19 = 0;
		}
		Number_19 = Number_19 + number_19;
		if (Number_19 == 0) {
			Number_19 = -1;
		}
	}

	public int getNumber_20() {
		return Number_20;
	}

	public void setNumber_20(int number_20) {
		if (Number_20 == -1) {
			Number_20 = 0;
		}
		Number_20 = Number_20 + number_20;
		if (Number_20 == 0) {
			Number_20 = -1;
		}
	}

	public int getNumber_bull() {
		return Number_bull;
	}

	public void setNumber_bull(int number_bull) {
		if (Number_bull == -1) {
			Number_bull = 0;
		}
		Number_bull = Number_bull + number_bull;
		if (Number_bull == 0) {
			Number_bull = -1;
		}
	}

	public int getValidScore_15() {
		return ValidScore_15;
	}

	public void setValidScore_15(int validScore_15) {

		if (ValidScore_15 == -1) {
			ValidScore_15 = 0;
		}
		ValidScore_15 = ValidScore_15 + validScore_15;
		if (ValidScore_15 == 0) {
			ValidScore_15 = -1;
		}
	}

	public int getValidScore_16() {
		return ValidScore_16;
	}

	public void setValidScore_16(int validScore_16) {
		if (ValidScore_16 == -1) {
			ValidScore_16 = 0;
		}
		ValidScore_16 = ValidScore_16 + validScore_16;
		if (ValidScore_16 == 0) {
			ValidScore_16 = -1;
		}
	}

	public int getValidScore_17() {
		return ValidScore_17;
	}

	public void setValidScore_17(int validScore_17) {
		if (ValidScore_17 == -1) {
			ValidScore_17 = 0;
		}
		ValidScore_17 = ValidScore_17 + validScore_17;
		if (ValidScore_17 == 0) {
			ValidScore_17 = -1;
		}
	}

	public int getValidScore_18() {
		return ValidScore_18;
	}

	public void setValidScore_18(int validScore_18) {
		if (ValidScore_18 == -1) {
			ValidScore_18 = 0;
		}
		ValidScore_18 = ValidScore_18 + validScore_18;
		if (ValidScore_18 == 0) {
			ValidScore_18 = -1;
		}
	}

	public int getValidScore_19() {
		return ValidScore_19;
	}

	public void setValidScore_19(int validScore_19) {
		if (ValidScore_19 == -1) {
			ValidScore_19 = 0;
		}
		ValidScore_19 = ValidScore_19 + validScore_19;
		if (ValidScore_19 == 0) {
			ValidScore_19 = -1;
		}
	}

	public int getValidScore_20() {
		return ValidScore_20;
	}

	public void setValidScore_20(int validScore_20) {
		if (ValidScore_20 == -1) {
			ValidScore_20 = 0;
		}
		ValidScore_20 = ValidScore_20 + validScore_20;
		if (ValidScore_20 == 0) {
			ValidScore_20 = -1;
		}
	}

	public int getValidScore_bull() {
		return ValidScore_bull;
	}

	public void setValidScore_bull(int validScore_bull) {
		if (ValidScore_bull == -1) {
			ValidScore_bull = 0;
		}
		ValidScore_bull = ValidScore_bull + validScore_bull;
		if (ValidScore_bull == 0) {
			ValidScore_bull = -1;
		}
	}

	public int getVaildNumberSum() {
		int number = 0;
		if (Number_15 != -1) {
			number = Number_15;
		}
		if (Number_16 != -1) {
			number = number + Number_16;
		}
		if (Number_17 != -1) {
			number = number + Number_17;
		}
		if (Number_18 != -1) {
			number = number + Number_18;
		}
		if (Number_19 != -1) {
			number = number + Number_19;
		}
		if (Number_20 != -1) {
			number = number + Number_20;
		}
		if (Number_bull != -1) {
			number = number + Number_bull;
		}
		return number;
	}

	private boolean IsClose(int areaId) {
		boolean flag = false;
		if ((areaId == 75 || areaId == 77 || areaId == 78 || areaId == 76)
				&& GameRules.getInstance().isFlag_20()) {
			flag = true;
		} else if ((areaId == 35 || areaId == 37 || areaId == 38 || areaId == 36)
				&& GameRules.getInstance().isFlag_19()) {
			flag = true;
		} else if ((areaId == 71 || areaId == 73 || areaId == 74 || areaId == 72)
				&& GameRules.getInstance().isFlag_18()) {
			flag = true;
		} else if ((areaId == 31 || areaId == 33 || areaId == 34 || areaId == 32)
				&& GameRules.getInstance().isFlag_17()) {
			flag = true;
		} else if ((areaId == 19 || areaId == 21 || areaId == 22 || areaId == 20)
				&& GameRules.getInstance().isFlag_16()) {
			flag = true;
		} else if ((areaId == 17 || areaId == 15 || areaId == 18 || areaId == 16)
				&& GameRules.getInstance().isFlag_15()) {
			flag = true;
		} else if ((areaId == 2 || areaId == 1)
				&& GameRules.getInstance().isFlag_bull()) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public int getTotalScore() {
		int total_score = getStartScore();
		if (getValidScore_20() > 3) {
			total_score = total_score + (getValidScore_20() - 3) * 20;
		}
		if (getValidScore_19() > 3) {
			total_score = total_score + (getValidScore_19() - 3) * 19;
		}
		if (getValidScore_18() > 3) {
			total_score = total_score + (getValidScore_18() - 3) * 18;
		}
		if (getValidScore_17() > 3) {
			total_score = total_score + (getValidScore_17() - 3) * 17;
		}
		if (getValidScore_16() > 3) {
			total_score = total_score + (getValidScore_16() - 3) * 16;
		}
		if (getValidScore_15() > 3) {
			total_score = total_score + (getValidScore_15() - 3) * 15;
		}
		if (getValidScore_bull() > 3) {
			total_score = total_score + (getValidScore_bull() - 3) * 25;
		}
		return total_score;
	}

	public boolean judgeOver() {
		boolean flag = false;
		if (Number_15 >= 3 && Number_16 >= 3 && Number_17 >= 3
				&& Number_18 >= 3 && Number_19 >= 3 && Number_20 >= 3
				&& Number_bull >= 3) {
			flag = true;
		}
		return flag;
	}
}
