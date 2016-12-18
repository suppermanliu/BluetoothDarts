package com.hopen.darts.media;

import java.util.HashMap;

import com.hopen.bluetoothdarts.R;
import com.hopen.darts.bean.GameSettingBean;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayManage {

	private SoundPool soundPool;
	private Context context;
	private float volumnRatio = 1;
	private float curVolume = 1;

	private static final int PRIORITY = 1;// �����������ȼ���0Ϊ������ȼ�
	public static final int ID_SOUND_HIT = 1;// �����
	public static final int ID_SOUND_PAGER = 2;// �л�����
	public static final int ID_SOUND_DIALOG = 3;// ��ʾ��
	public static final int ID_SOUND_HITSINGLE = 4;// ���е�����
	public static final int ID_SOUND_HITDOUBLE = 5;// ����˫����
	public static final int ID_SOUND_HITTHREE = 6;// ��������
	public static final int ID_SOUND_HITTHREE20 = 7;// ����20������
	public static final int ID_SOUND_HITEYE = 8;// ������/��ţ��
	public static final int ID_SOUND_MISS = 9;// miss
	public static final int ID_SOUND_BUST = 10; // bust
	public static final int ID_SOUND_UPGRADE = 11; // ͳ�ƽ���ȼ���

	public HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();
	private SharedPreferences sharedPreferences;
	private static SoundPlayManage	soundPlay;
	private SoundPlayManage(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		loadSounds();
		/*
		 * sharedPreferences = context.getSharedPreferences("volumn",
		 * context.MODE_PRIVATE);
		 */
	}

	public static SoundPlayManage getInstance(Context context) {
		if (soundPlay == null) {
			soundPlay = new SoundPlayManage(context);
		}
		return soundPlay;
	}
	/**
	 * װ����Դ�ļ�
	 */
	private void loadSounds() {

		soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 100);// ��ʼ��soundPool
																		// ��������
																		// ����ʹ��OGG��ʽ
		// ����,��һ�������������ж��ٸ�������ͬʱ����,��2����������������,�����������������Ʒ��

		/** ��SD��ȡ��ݣ�OBB��ʱ���ɹ� **/
		// String path = "/storage/external_storage/sdcard1/raw";
		/*
		 * String path = activity.mount_path;
		 * 
		 * int hit_double = soundPool.load(path + "/" + "hit_double.wav",
		 * PRIORITY); int hit_single = soundPool.load(path + "/" +
		 * "hit_single.wav", PRIORITY); int hit_three = soundPool.load(path +
		 * "/" + "hit_three.wav", PRIORITY); int bust = soundPool.load(path +
		 * "/" + "bust.mp3", PRIORITY); int miss = soundPool.load(path + "/" +
		 * "miss.mp3", PRIORITY);
		 * 
		 * int bluekey = soundPool.load(path + "/" + "bluekey.wav", PRIORITY);
		 * int changeview = soundPool.load(path + "/" + "changeview.wav",
		 * PRIORITY); int comegameview = soundPool.load(path + "/" +
		 * "comegameview.wav", PRIORITY); int dialog = soundPool.load(path + "/"
		 * + "dialog.wav", PRIORITY); int directionkey = soundPool.load(path +
		 * "/" + "directionkey.wav", PRIORITY);
		 * 
		 * int eye = soundPool.load(path + "/" + "eye.wav", PRIORITY); int
		 * frozen = soundPool.load(path + "/" + "frozen.wav", PRIORITY); int
		 * hit_three20 = soundPool.load(path + "/" + "hit_three20.wav",
		 * PRIORITY); int redkey = soundPool.load(path + "/" + "redkey.wav",
		 * PRIORITY); int solutionfrozen = soundPool.load(path + "/" +
		 * "solutionfrozen.wav", PRIORITY);
		 * 
		 * int swipecard = soundPool.load(path + "/" + "swipecard.wav",
		 * PRIORITY); int swipecardfail = soundPool.load(path + "/" +
		 * "swipecardfail.wav", PRIORITY); int throwscoins = soundPool.load(path
		 * + "/" + "throwscoins.wav", PRIORITY); int upgrade =
		 * soundPool.load(path + "/" + "upgrade.wav", PRIORITY);
		 */

		int hit_double = soundPool.load(context, R.raw.hit_double, PRIORITY);
		int hit_single = soundPool.load(context, R.raw.hit_single, PRIORITY);
		int hit_three = soundPool.load(context, R.raw.hit_three, PRIORITY);
		int bust = soundPool.load(context, R.raw.bust, PRIORITY);
		int miss = soundPool.load(context, R.raw.miss, PRIORITY);

		int dialog = soundPool.load(context, R.raw.dialog, PRIORITY);

		int eye = soundPool.load(context, R.raw.eye, PRIORITY);

		int hit_three20 = soundPool.load(context, R.raw.hit_three20, PRIORITY);
		int upgrade = soundPool.load(context, R.raw.upgrade, PRIORITY);

		int pager = soundPool.load(context, R.raw.pager, PRIORITY);
		int hit = soundPool.load(context, R.raw.hit, PRIORITY);
		soundPoolMap.put(ID_SOUND_HITSINGLE, hit_single);
		soundPoolMap.put(ID_SOUND_HITDOUBLE, hit_double);
		soundPoolMap.put(ID_SOUND_HITTHREE, hit_three);
		soundPoolMap.put(ID_SOUND_BUST, bust);
		soundPoolMap.put(ID_SOUND_MISS, miss);

		soundPoolMap.put(ID_SOUND_HIT, hit);
		soundPoolMap.put(ID_SOUND_PAGER, pager);

		soundPoolMap.put(ID_SOUND_DIALOG, dialog);

		soundPoolMap.put(ID_SOUND_HITEYE, eye);

		soundPoolMap.put(ID_SOUND_HITTHREE20, hit_three20);

		soundPoolMap.put(ID_SOUND_UPGRADE, upgrade);

	}

	/**
	 * ������Ч
	 */
	public void PlaySound(int typeID) {
		// getVolume();
		soundPool.play(soundPoolMap.get(typeID), curVolume, curVolume, 1, 0,
				volumnRatio);

		/*
		 * ����soundIDָ��Ҫ���ŵ���ƵID����ID����load()����صġ� ����leftVolumeָ���������������ȡֵ��Χ��0.0 �C
		 * 1.0�� ����rightVolumeָ���������������ȡֵ��Χ��0.0 �C 1.0��
		 * ����priorityָ��������Ƶ�����ȼ�����ֵԽ�����ȼ�Խ�ߡ� ����loopָ��ѭ������0Ϊ��ѭ����-1Ϊһֱѭ����
		 * ����rateָ���������ʣ���Ϊ1�����Ϊ0.5�����Ϊ2
		 */
	}

	/**
	 * ֹͣ������Ч
	 */
	public void StopSound(int typeID) {
		soundPool.stop(soundPoolMap.get(typeID));
	}

	/**
	 * �ͷ���Դ�ļ�
	 */
	public void exitSoundPool() {
		if (soundPool != null) {
			soundPool.release();
			soundPool = null;
		}
	}

	public void dartAudio(int multiple) {
		switch (multiple) {
		case 1:
			PlaySound(SoundPlayManage.ID_SOUND_HITSINGLE);
			break;
		case 2:
			PlaySound(SoundPlayManage.ID_SOUND_HITDOUBLE);
			break;
		case 3:
			PlaySound(SoundPlayManage.ID_SOUND_HITTHREE);
			break;
		}
	}

}