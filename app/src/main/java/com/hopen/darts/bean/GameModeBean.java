package com.hopen.darts.bean;

import com.hopen.darts.activity.GameModeActivity;
import com.hopen.darts.constant.Constant;
import com.hopen.darts.util.PrefUtils;

/**
 * @author lpmo 游戏模式的数据对象
 * 
 */
public class GameModeBean {

	private static GameModeBean modebean = null;
	private String gameType;// = "301";
	private String playerType;// = "2";
	private int totalRounds;
	private int roundhighscore,roundgame01,roundmickey;

	// private int result_highscore,result_01,result_mickey;

	private GameModeBean() {
		super();
	}

	public static GameModeBean getInstance() {
		if (modebean == null) {
			modebean = new GameModeBean();
		}
		return modebean;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getPlayerType() {
		return playerType;
	}

	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}



	//获取设置后的高分赛回合数
	public int getHighscoreRounds() {
		roundhighscore = PrefUtils.getInt(GameModeActivity.mContext, "ronud_highscore", 8);
		return roundhighscore;
	}

	//获取设置后的01系列回合数
	public int get01gameRounds() {

		roundgame01 = PrefUtils.getInt(GameModeActivity.mContext, "ronud_01", 10);
		return roundgame01;
	}

	//获取设置后的米老鼠回合数
	public int getMickeyRounds() {

		roundmickey = PrefUtils.getInt(GameModeActivity.mContext, "ronud_mickey", 15);
		return roundmickey;
	}

	
	
	
	// 获取游戏回合数
	public int getTotalRounds() {
		setTotalRounds();

		return totalRounds;
	}

	public void setTotalRounds() {
		if (gameType.equals(Constant.GAMETYPE301)) {

//			totalRounds = Constant.GAMEROUNDS301;
			totalRounds = get01gameRounds() + 2;

		} else if (gameType.equals(Constant.GAMETYPE501)) {

//			totalRounds = Constant.GAMEROUNDS501;
			totalRounds = get01gameRounds() + 2;

		} else if (gameType.equals(Constant.GAMETYPE701)) {

//			totalRounds = Constant.GAMEROUNDS701;
			totalRounds = get01gameRounds() + 2;

		} else if (gameType.equals(Constant.GAMETYPEMICKEY)) {

//			totalRounds = Constant.GAMEROUNDSMICKEY;
			totalRounds = getMickeyRounds() + 2;
			
		} else if (gameType.equals(Constant.GAMETYPEHIGHSCROE)) { // 高分赛（暂时写死8回合）

			totalRounds = getHighscoreRounds() + 2;
		}
	}

}
