package com.hopen.darts.bean;

/**
 * @author lpmo
 * 游戏设置对象（开镖、结镖、牛眼设置）
 */
public class GameSettingBean {

	private static GameSettingBean settingbean = null;
	private int openDartVlue = -1;//Ĭ��-1  ���⿪�ڣ�  0 ˫�� ,1 Ϊ��, 2 ��ʦ
	private int overDartVlue = -1;//Ĭ��-1  �������
	private int bullVlue = 1;//Ĭ�� 0 Ϊ 25/50     1Ϊ50/50

	private GameSettingBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static GameSettingBean getInstance() {
		if (settingbean == null) {
			settingbean = new GameSettingBean();
		}
		return settingbean;
	}

	
	public int getOpenDartVlue() {
		return openDartVlue;
	}

	public void setOpenDartVlue(int openDartVlue) {
		this.openDartVlue = openDartVlue;
	}

	
	public int getOverDartVlue() {
		return overDartVlue;
	}

	public void setOverDartVlue(int overDartVlue) {
		this.overDartVlue = overDartVlue;
	}


	public int getBullVlue() {
		return bullVlue;
	}

	public void setBullVlue(int bullVlue) {
		this.bullVlue = bullVlue;
	}
}
