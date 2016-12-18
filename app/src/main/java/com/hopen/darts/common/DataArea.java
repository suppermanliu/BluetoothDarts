package com.hopen.darts.common;

import com.hopen.darts.bean.GameModeBean;
import com.hopen.darts.bean.GameSettingBean;
import com.hopen.darts.constant.Constant;

/**
 * @author lpmo
 * 
 */
public class DataArea {

	public static int getScore(int areaId) {
		int score = 0;
		GameSettingBean gameSettingBean = GameSettingBean.getInstance();
		int BullVlue = gameSettingBean.getBullVlue();
		switch (areaId) {
		case 0:
			score = 0;
			break;
		case 1:
			score = 25 * 2;
			break;
		case 2:
			if (GameModeBean.getInstance().getGameType()
					.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
				score = 25;
			} else {
				if (BullVlue == 0)
					score = 25;
				else
					score = 50;
			}

			break;
		case 3:

			score = 11 * 1;
			break;
		case 4:

			score = 11 * 3;
			break;
		case 5:

			score = 11 * 1;
			break;
		case 6:

			score = 11 * 2;
			break;
		case 7:

			score = 10 * 1;
			break;
		case 8:

			score = 10 * 3;
			break;
		case 9:

			score = 10 * 1;
			break;
		case 10:

			score = 10 * 2;
			break;
		case 11:

			score = 8 * 1;
			break;
		case 12:

			score = 8 * 3;
			break;
		case 83:

			score = 0;
			break;
		case 13:

			score = 8 * 1;
			break;
		case 14:

			score = 8 * 2;
			break;
		case 15:

			score = 15 * 1;
			break;
		case 16:

			score = 15 * 3;
			break;
		case 17:
			score = 15 * 1;
			break;
		case 84:

			score = 15 * 1;
			break;
		case 18:

			score = 15 * 2;
			break;
		case 85:

			score = 16 * 1;
			break;
		case 19:
			score = 16 * 1;
			break;
		case 20:

			score = 16 * 3;
			break;
		case 21:

			score = 16 * 1;
			break;
		case 22:

			score = 16 * 2;
			break;
		case 23:

			score = 2 * 1;
			break;
		case 24:

			score = 2 * 3;
			break;
		case 25:

			score = 2 * 1;
			break;
		case 26:

			score = 2 * 2;
			break;
		case 27:

			score = 7 * 1;
			break;
		case 28:

			score = 7 * 3;
			break;
		case 29:

			score = 7 * 1;
			break;
		case 30:

			score = 7 * 2;
			break;
		case 31:

			score = 17 * 1;
			break;
		case 32:

			score = 17 * 3;
			break;
		case 33:

			score = 17 * 1;
			break;
		case 34:

			score = 17 * 2;
			break;
		case 35:

			score = 19 * 1;
			break;
		case 36:

			score = 19 * 3;
			break;
		case 37:

			score = 19 * 1;
			break;
		case 38:

			score = 19 * 2;
			break;
		case 39:

			score = 3 * 1;
			break;
		case 40:

			score = 3 * 3;
			break;
		case 41:

			score = 3 * 1;
			break;
		case 42:

			score = 3 * 2;
			break;
		case 43:

			score = 14 * 1;
			break;
		case 44:

			score = 14 * 3;
			break;
		case 45:

			score = 14 * 1;
			break;
		case 46:

			score = 14 * 2;
			break;
		case 47:

			score = 6 * 1;
			break;
		case 48:

			score = 6 * 3;
			break;
		case 49:

			score = 6 * 1;
			break;
		case 50:

			score = 6 * 2;
			break;
		case 51:

			score = 9 * 1;
			break;
		case 52:

			score = 9 * 3;
			break;
		case 53:

			score = 9 * 1;
			break;
		case 54:

			score = 9 * 2;
			break;
		case 55:

			score = 13 * 1;
			break;
		case 56:

			score = 13 * 3;
			break;
		case 57:

			score = 13 * 1;
			break;
		case 58:

			score = 13 * 2;
			break;
		case 59:

			score = 12 * 1;
			break;
		case 60:

			score = 12 * 3;
			break;
		case 61:

			score = 12 * 1;
			break;
		case 62:

			score = 12 * 2;
			break;
		case 63:

			score = 4 * 1;
			break;
		case 64:

			score = 4 * 3;
			break;
		case 65:

			score = 4 * 1;
			break;
		case 66:

			score = 4 * 2;
			break;
		case 67:

			score = 5 * 1;
			break;
		case 68:

			score = 5 * 3;
			break;
		case 69:

			score = 5 * 1;
			break;
		case 70:

			score = 5 * 2;
			break;
		case 71:

			score = 18 * 1;
			break;
		case 72:

			score = 18 * 3;
			break;
		case 73:

			score = 18 * 1;
			break;
		case 74:

			score = 18 * 2;
			break;
		case 75:

			score = 20 * 1;
			break;
		case 76:

			score = 20 * 3;
			break;
		case 77:

			score = 20 * 1;
			break;
		case 78:

			score = 20 * 2;
			break;
		case 79:

			score = 1 * 1;
			break;
		case 80:

			score = 1 * 3;
			break;
		case 81:

			score = 1 * 1;
			break;
		case 82:

			score = 1 * 2;
			break;
		default:
			break;
		}
		return score;
	}

	/**
	 * ����1-82�������ֵ������ʾ triple double single
	 * 
	 * @authorlpmo
	 * 
	 */
	public static String getScoreString(int areaId) {
		String score = null;
		switch (areaId) {
		case 0:
			score = Constant.MISS;
			break;
		case 1:
			score = Constant.D_BULL;
			break;
		case 2:
			score = Constant.S_BULL;
			break;
		case 3:
			score = Constant.SINGLE + " 11";
			break;
		case 4:
			score = Constant.TRIPLE + " 11";
			break;
		case 5:
			score = Constant.SINGLE + " 11";
			break;
		case 6:
			score = Constant.DOUBLE + " 11";
			break;
		case 7:
			score = Constant.SINGLE + " 10";
			break;
		case 8:
			score = Constant.TRIPLE + " 10";
			break;
		case 9:
			score = Constant.SINGLE + " 10";
			break;
		case 10:
			score = Constant.DOUBLE + " 10";
			break;
		case 11:
			score = Constant.SINGLE + " 8";
			break;
		case 12:
			score = Constant.TRIPLE + " 8";
			break;
		case 83:
			score = Constant.MISS;
			break;
		case 13:
			score = Constant.SINGLE + " 8";
			break;
		case 14:
			score = Constant.DOUBLE + " 8";
			break;
		case 15:
			score = Constant.SINGLE + " 15";
			break;
		case 16:
			score = Constant.TRIPLE + " 15";
			break;
		case 17:
			score = Constant.SINGLE + " 15";
			break;
		case 84:
			score = Constant.SINGLE + " 15";
			break;
		case 18:
			score = Constant.DOUBLE + " 15";
			break;
		case 85:
			score = Constant.SINGLE + " 16";
			break;
		case 19:
			score = Constant.SINGLE + " 16";
			break;
		case 20:
			score = Constant.TRIPLE + " 16";
			break;
		case 21:
			score = Constant.SINGLE + " 16";
			break;
		case 22:
			score = Constant.DOUBLE + " 16";
			break;
		case 23:
			score = Constant.SINGLE + " 2";
			break;
		case 24:
			score = Constant.TRIPLE + " 2";
			break;
		case 25:
			score = Constant.SINGLE + " 2";
			break;
		case 26:
			score = Constant.DOUBLE + " 2";
			break;
		case 27:
			score = Constant.SINGLE + " 7";
			break;
		case 28:
			score = Constant.TRIPLE + " 7";
			break;
		case 29:
			score = Constant.SINGLE + " 7";
			break;
		case 30:
			score = Constant.DOUBLE + " 7";
			break;
		case 31:
			score = Constant.SINGLE + " 17";
			break;
		case 32:
			score = Constant.TRIPLE + " 17";
			break;
		case 33:
			score = Constant.SINGLE + " 17";
			break;
		case 34:
			score = Constant.DOUBLE + " 17";
			break;
		case 35:
			score = Constant.SINGLE + " 19";
			break;
		case 36:
			score = Constant.TRIPLE + " 19";
			break;
		case 37:
			score = Constant.SINGLE + " 19";
			break;
		case 38:
			score = Constant.DOUBLE + " 19";
			break;
		case 39:
			score = Constant.SINGLE + " 3";
			break;
		case 40:
			score = Constant.TRIPLE + " 3";
			break;
		case 41:
			score = Constant.SINGLE + " 3";
			break;
		case 42:
			score = Constant.DOUBLE + " 3";
			break;
		case 43:
			score = Constant.SINGLE + " 14";
			break;
		case 44:
			score = Constant.TRIPLE + " 14";
			break;
		case 45:
			score = Constant.SINGLE + " 14";
			break;
		case 46:
			score = Constant.DOUBLE + " 14";
			break;
		case 47:
			score = Constant.SINGLE + " 6";
			break;
		case 48:
			score = Constant.TRIPLE + " 6";
			break;
		case 49:
			score = Constant.SINGLE + " 6";
			break;
		case 50:
			score = Constant.DOUBLE + " 6";
			break;
		case 51:
			score = Constant.SINGLE + " 9";
			break;
		case 52:
			score = Constant.TRIPLE + " 9";
			break;
		case 53:
			score = Constant.SINGLE + " 9";
			break;
		case 54:
			score = Constant.DOUBLE + " 9";
			break;
		case 55:
			score = Constant.SINGLE + " 13";
			break;
		case 56:
			score = Constant.TRIPLE + " 13";
			break;
		case 57:
			score = Constant.SINGLE + " 13";
			break;
		case 58:
			score = Constant.DOUBLE + " 13";
			break;
		case 59:
			score = Constant.SINGLE + " 12";
			break;
		case 60:
			score = Constant.TRIPLE + " 12";
			break;
		case 61:
			score = Constant.SINGLE + " 12";
			break;
		case 62:
			score = Constant.DOUBLE + " 12";
			break;
		case 63:
			score = Constant.SINGLE + " 4";
			break;
		case 64:
			score = Constant.TRIPLE + " 4";
			break;
		case 65:
			score = Constant.SINGLE + " 4";
			break;
		case 66:
			score = Constant.DOUBLE + " 4";
			break;
		case 67:
			score = Constant.SINGLE + " 5";
			break;
		case 68:
			score = Constant.TRIPLE + " 5";
			break;
		case 69:
			score = Constant.SINGLE + " 5";
			break;
		case 70:
			score = Constant.DOUBLE + " 5";
			break;
		case 71:
			score = Constant.SINGLE + " 18";
			break;
		case 72:
			score = Constant.TRIPLE + " 18";
			break;
		case 73:
			score = Constant.SINGLE + " 18";
			break;
		case 74:
			score = Constant.DOUBLE + " 18";
			break;
		case 75:
			score = Constant.SINGLE + " 20";
			break;
		case 76:
			score = Constant.TRIPLE + " 20";
			break;
		case 77:
			score = Constant.SINGLE + " 20";
			break;
		case 78:
			score = Constant.DOUBLE + " 20";
			break;
		case 79:
			score = Constant.SINGLE + " 1";
			break;
		case 80:
			score = Constant.TRIPLE + " 1";
			break;
		case 81:
			score = Constant.SINGLE + " 1";
			break;
		case 82:
			score = Constant.DOUBLE + " 1";
			break;
		default:
			break;
		}
		return score;
	}

	/**
	 * ����1-82�������ֵ�ı���
	 * 
	 * @authorlpmo
	 * 
	 */
	public static int getMultiple(int areaId) {
		int multiple = 0;
		GameSettingBean gameSettingBean = GameSettingBean.getInstance();
		int BullVlue = gameSettingBean.getBullVlue();
		switch (areaId) {
		case 0:
			multiple = 0;
			break;
		case 1:
			multiple = 2;
			break;
		case 2:
			if (GameModeBean.getInstance().getGameType()
					.equalsIgnoreCase(Constant.GAMETYPEMICKEY)) {
				multiple = 1;
			} else {
				if (BullVlue == 0)
					multiple = 1;
				else
					multiple = 2;
			}
			break;
		case 3:
			multiple = 1;
			break;
		case 4:
			multiple = 3;
			break;
		case 5:
			multiple = 1;
			break;
		case 6:
			multiple = 2;
			break;
		case 7:
			multiple = 1;
			break;
		case 8:
			multiple = 3;
			break;
		case 9:
			multiple = 1;
			break;
		case 10:
			multiple = 2;
			break;
		case 11:
			multiple = 1;
			break;
		case 12:
			multiple = 3;
			break;
		case 83:
			multiple = 0;
			break;
		case 13:
			multiple = 1;
			break;
		case 14:
			multiple = 2;
			break;
		case 15:
			multiple = 1;
			break;
		case 16:
			multiple = 3;
			break;
		case 84:
			multiple = 1;
			break;
		case 17:
			multiple = 1;
			break;
		case 18:
			multiple = 2;
			break;
		case 85:
			multiple = 1;
			break;
		case 19:
			multiple = 1;
			break;
		case 20:
			multiple = 3;
			break;
		case 21:
			multiple = 1;
			break;
		case 22:
			multiple = 2;
			break;
		case 23:
			multiple = 1;
			break;
		case 24:
			multiple = 3;
			break;
		case 25:
			multiple = 1;
			break;
		case 26:
			multiple = 2;
			break;
		case 27:
			multiple = 1;
			break;
		case 28:
			multiple = 3;
			break;
		case 29:
			multiple = 1;
			break;
		case 30:
			multiple = 2;
			break;
		case 31:
			multiple = 1;
			break;
		case 32:
			multiple = 3;
			break;
		case 33:
			multiple = 1;
			break;
		case 34:
			multiple = 2;
			break;
		case 35:
			multiple = 1;
			break;
		case 36:
			multiple = 3;
			break;
		case 37:
			multiple = 1;
			break;
		case 38:
			multiple = 2;
			break;
		case 39:
			multiple = 1;
			break;
		case 40:
			multiple = 3;
			break;
		case 41:
			multiple = 1;
			break;
		case 42:
			multiple = 2;
			break;
		case 43:
			multiple = 1;
			break;
		case 44:
			multiple = 3;
			break;
		case 45:
			multiple = 1;
			break;
		case 46:
			multiple = 2;
			break;
		case 47:
			multiple = 1;
			break;
		case 48:
			multiple = 3;
			break;
		case 49:
			multiple = 1;
			break;
		case 50:
			multiple = 2;
			break;
		case 51:
			multiple = 1;
			break;
		case 52:
			multiple = 3;
			break;
		case 53:
			multiple = 1;
			break;
		case 54:
			multiple = 2;
			break;
		case 55:
			multiple = 1;
			break;
		case 56:
			multiple = 3;
			break;
		case 57:
			multiple = 1;
			break;
		case 58:
			multiple = 2;
			break;
		case 59:
			multiple = 1;
			break;
		case 60:
			multiple = 3;
			break;
		case 61:
			multiple = 1;
			break;
		case 62:
			multiple = 2;
			break;
		case 63:
			multiple = 1;
			break;
		case 64:
			multiple = 3;
			break;
		case 65:
			multiple = 1;
			break;
		case 66:
			multiple = 2;
			break;
		case 67:
			multiple = 1;
			break;
		case 68:
			multiple = 3;
			break;
		case 69:
			multiple = 1;
			break;
		case 70:
			multiple = 2;
			break;
		case 71:
			multiple = 1;
			break;
		case 72:
			multiple = 3;
			break;
		case 73:
			multiple = 1;
			break;
		case 74:
			multiple = 2;
			break;
		case 75:
			multiple = 1;
			break;
		case 76:
			multiple = 3;
			break;
		case 77:
			multiple = 1;
			break;
		case 78:
			multiple = 2;
			break;
		case 79:
			multiple = 1;
			break;
		case 80:
			multiple = 3;
			break;
		case 81:
			multiple = 1;
			break;
		case 82:
			multiple = 2;
			break;
		default:
			break;
		}
		return multiple;
	}

	/** �������ֵ �Ƿ��ѹر� trueΪ�رգ�false��֮ **/
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
