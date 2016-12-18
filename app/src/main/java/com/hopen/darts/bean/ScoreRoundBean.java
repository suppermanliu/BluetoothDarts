package com.hopen.darts.bean;

import com.hopen.darts.common.DataArea;

/**
 * @author lpmo
 * 每一回合的数据对象
 */
public class ScoreRoundBean {

	public int firstDartArea = -1;
	public int secondDartArea = -1;
	public int thirdDartArea = -1;
	private boolean validFirstDart = false;
	private boolean validSecondDart = false;
	private boolean validThirdDart = false;

	private boolean bustflag = false;
	private int playerIndex = -1;

	private static int ZERO = 0;

	public boolean firstIsClose = false;
	public boolean secondIsClose = false;
	public boolean thirdIsClose = false;

	public ScoreRoundBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public boolean isBustflag() {
		return bustflag;
	}

	public void setBustflag(boolean bustflag) {
		this.bustflag = bustflag;
	}

	//获取3镖总分
	public int getThreeDartScore() {
		int ThreeDartScore;

		ThreeDartScore = getFirstDartScore() + getSecondDartScore()
				+ getThirdDartScore();

		return ThreeDartScore;

	}

	//获取第一镖分数
	public int getFirstDartScore() {

		return DataArea.getScore(getFirstDartArea());

	}

	//获取第二镖分数
	public int getSecondDartScore() {

		return DataArea.getScore(getSecondDartArea());

	}

	//获取第三镖分数
	public int getThirdDartScore() {

		return DataArea.getScore(getThirdDartArea());

	}

	//获取第一镖区域
	public int getFirstDartArea() {
		int DartScore = 0;
		if (firstDartArea == -1) {
			DartScore = ZERO;
		} else {
			DartScore = firstDartArea;
		}
		return DartScore;
	}

	public void setFirstDartArea(int firstDartArea) {
		this.firstDartArea = firstDartArea;
	}

	//获取第二镖区域
	public int getSecondDartArea() {
		int DartScore = 0;
		if (secondDartArea == -1) {
			DartScore = ZERO;
		} else {
			DartScore = secondDartArea;
		}
		return DartScore;
	}

	public void setSecondDartArea(int secondDartArea) {
		this.secondDartArea = secondDartArea;
	}

	//获取第三镖区域
	public int getThirdDartArea() {
		int DartScore = 0;
		if (thirdDartArea == -1) {
			DartScore = ZERO;
		} else {
			DartScore = thirdDartArea;
		}
		return DartScore;
	}

	public void setThirdDartArea(int thirdDartArea) {
		this.thirdDartArea = thirdDartArea;
	}

	//判断是否是有效的第一镖
	public boolean isValidFirstDart() {
		if (firstDartArea != -1) {
			validFirstDart = true;
		}
		return validFirstDart;
	}

	//判断是否是有效的第二镖
	public boolean isValidSecondDart() {
		if (secondDartArea != -1) {
			validSecondDart = true;
		}
		return validSecondDart;
	}

	//判断是否是有效的第三镖
	public boolean isValidThirdDart() {
		if (thirdDartArea != -1) {
			validThirdDart = true;
		}
		return validThirdDart;
	}

	/*
	 * public int validRoundDarts(){ int valid_round_darts = 0; if(firstDartArea
	 * > -1){ //valid_round_darts = 1; valid_round_darts++; if(secondDartArea >
	 * -1){ // valid_round_darts = 2; valid_round_darts++; if(thirdDartArea >
	 * -1){ //valid_round_darts = 3; valid_round_darts++; } } } return
	 * valid_round_darts; }
	 */
	
	//获取每一回合的有效镖数
	public int validRoundDarts() {
		int valid_round_darts = 0;
		if (firstDartArea > -1) {
			// valid_round_darts = 1;
			valid_round_darts++;
		}

		if (secondDartArea > -1) {
			// valid_round_darts = 2;
			valid_round_darts++;
		}
		if (thirdDartArea > -1) {
			// valid_round_darts = 3;
			valid_round_darts++;
		}

		return valid_round_darts;
	}

	public boolean isFirstIsClose() {
		return firstIsClose;
	}

	public void setFirstIsClose(boolean firstIsClose) {
		this.firstIsClose = firstIsClose;
	}

	public boolean isSecondIsClose() {
		return secondIsClose;
	}

	public void setSecondIsClose(boolean secondIsClose) {
		this.secondIsClose = secondIsClose;
	}

	public boolean isThirdIsClose() {
		return thirdIsClose;
	}

	public void setThirdIsClose(boolean thirdIsClose) {
		this.thirdIsClose = thirdIsClose;
	}

	//判断3镖位置是否属于同一区域
	public boolean judgeThree() {
		boolean flag = false;
		if (firstDartArea != secondDartArea && firstDartArea != thirdDartArea
				&& secondDartArea != thirdDartArea) {
			flag = true;
		}
		return flag;
	}
}
