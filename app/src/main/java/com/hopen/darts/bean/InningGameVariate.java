package com.hopen.darts.bean;

import com.hopen.darts.constant.Constant;

/**
 * @author lpmo
 * 一局游戏中的变量对象
 */
public class InningGameVariate {


	//总回合数
	public int totalRounds = -1;


	//总卡片数
	public int totalCard = -1;
	//总玩家人数
	public int totalPlayNum = -1;
	//当前玩家
	public int currentPlayer = -1;

	//初始分数
	public int initScore;
	//当前回合
	public int currentRound = 0;
	//当前卡片
	public int currentCard = -1;

	
	
	public InningGameVariate() {
		super();
		// TODO Auto-generated constructor stub

	}

	public int getTotalRounds() {

		totalRounds = GameModeBean.getInstance().getTotalRounds();

		return totalRounds;
	}

	//获取总玩家人数
	public int getTotalPlayNum() {

		String totalplayNum = GameModeBean.getInstance().getPlayerType();

		if (totalplayNum.equalsIgnoreCase(Constant.PLAYERNUM2V2)) {

			totalPlayNum = 4;
		} else {
			totalPlayNum = Integer.valueOf(totalplayNum);
		}

		return totalPlayNum;

	}

	//获取总卡片数
	public int getTotalCard() {
		String cardNum = GameModeBean.getInstance().getPlayerType();
		if (cardNum.equalsIgnoreCase(Constant.PLAYERNUM2V2)) {

			totalCard = 2;
		} else {
			totalCard = Integer.valueOf(cardNum);
		}
		return totalCard;

	}

	//判断是否同一队
	public boolean isteam() {

		String cardNum = GameModeBean.getInstance().getPlayerType();
		if (cardNum.equalsIgnoreCase(Constant.PLAYERNUM2V2)) {

			return true;
		}
		return false;

	}

	//获取当前卡片
	public int getCurrentCard() {
		currentCard++;
		if (currentCard == totalCard) {
			currentCard = 0;
		}
		return currentCard;

	}

	//获取当前玩家
	public int getCurrentPlayer() {

		currentPlayer++;
		if (currentPlayer == totalPlayNum) {
			currentPlayer = 0;
		}

		return currentPlayer;

	}

	//获取当前玩家初始分数
	public int getPlayerInitScore() {
		String gameType = GameModeBean.getInstance().getGameType();
		if (gameType.equalsIgnoreCase(Constant.GAMETYPE301)) {
			initScore = Constant.PLAYERSCORE301;
		} else if (gameType.equalsIgnoreCase(Constant.GAMETYPE501)) {
			initScore = Constant.PLAYERSCORE501;
		} else if (gameType.equalsIgnoreCase(Constant.GAMETYPE701)) {
			initScore = Constant.PLAYERSCORE701;
		} else if (gameType.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
			initScore = Constant.PLAYERSCOREMICKEY;
		} else if (gameType.equalsIgnoreCase(Constant.GAMETYPEHIGHSCROE)) { //高分赛初始分数
			initScore = Constant.PLAYERSCOREHIGHSCORE;
		}
		return initScore;

	}

	//获取游戏类型
	public String getGameType() {
		String gameType = GameModeBean.getInstance().getGameType();

		return gameType;

	}

	//获取当前回合
	public int getCurrentRound() {

		
		if(isteam() == true){
			if(currentPlayer ==0 || currentPlayer == 2){
				currentRound++;
			}
		}else{

			if (currentPlayer == 0) {
				currentRound++;
			}
		}
		return currentRound;

	}
}
