package com.hopen.darts.constant;


/**
 * @author lpmo
 *
 */
public class Constant {

	public static final String GAMETYPE301 = "301";
	public static final String GAMETYPE501 = "501";
	public static final String GAMETYPE701 = "701";
	public static final String GAMETYPEMICKEY = "标准米老鼠";
	public static final String GAMETYPEHIGHSCROE = "高分赛";
	public static final String GAMETYPEMIX = "混合赛";

	

	public static final int GAMEROUNDS301 = 10;
	public static final int GAMEROUNDS501 = 15;
	public static final int GAMEROUNDS701 = 20;
	public static final int GAMEROUNDSMICKEY = 15;
//	public static final int GAMETYPEHIGHSCROE_5 = 5;
//	public static final int GAMETYPEHIGHSCROE_8 = 8;
//	public static final int GAMETYPEHIGHSCROE_15 = 15;
	public static final int GAMEROUNDSHIGHSCROE = 8;
	public static final int GAMEROUNDSMIX = 8;

	public static final String[] GameTypeArray = new String[] { GAMETYPE301,GAMETYPE501, GAMETYPE701, GAMETYPEMICKEY, GAMETYPEHIGHSCROE,GAMETYPEMIX };
	public static final Integer[] GameRoundsArray = new Integer[] {GAMEROUNDS301, GAMEROUNDS501, GAMEROUNDS701, GAMEROUNDSMICKEY,GAMEROUNDSHIGHSCROE,GAMEROUNDSMIX};

	
	public static final String PLAYERNUM1 = "1";
	public static final String PLAYERNUM2 = "2";
	public static final String PLAYERNUM3 = "3";
	public static final String PLAYERNUM4 = "4";
	public static final String PLAYERNUM2V2 = "2V2";
	public static final String PLAYERNUMMICKEY = "2";
	
	public static final int PLAYERSCORE301 = 301;
	public static final int PLAYERSCORE501 = 501;
	public static final int PLAYERSCORE701 = 701;
	public static final int PLAYERSCOREMICKEY = 0;
	public static final int PLAYERSCOREHIGHSCORE = 0;
	
    
    public static final String SINGLE = "SINGLE";
    public static final String DOUBLE = "DOUBLE";
    public static final String TRIPLE = "TRIPLE";
    public static final String D_BULL = "D-BULL";
    public static final String S_BULL = "S-BULL";
    public static final String MISS = "MISS";
    
    public static final String NUMBER1 = "第一名";
    public static final String NUMBER2 = "第二名";
    public static final String NUMBER3 = "第三名";
    public static final String NUMBER4 = "第四名";
    
    public static final String[] PlayerName = new String[] {"player1","player2","player3","player4"};
    
    public static final int DARTS_DATA = 0;
    public static final int GAMESETTING_PW = 1;
    public static final int BACKDARTS_PW = 2;
    public static final int RULES_PW = 3;
    public static final int GAMEOVER_PW = 4;
    public static final int GAMESETTINGDISMISS_PW = 5;
    
    public static final int DARTS_REDKEY = 6;
    public static final int RECONNET_SERVICE = 7;
    public static final int PW_DELAY = 200;
    
    public static final int GATT_CONNECTED = 10;
    public static final int GATT_DISCONNECTED = 11;
    public static final int GATT_SERVICES_DISCOVERED = 12;
    public static final int DATA_AVAILABLE = 13;
    
    
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
}
