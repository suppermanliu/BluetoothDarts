package com.hopen.darts.media;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hopen.bluetoothdarts.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VideoPlayManage implements SurfaceHolder.Callback {
	private WindowManager windowManager;
	private Context context;
	public MediaPlayer mediaPlayer;
	private SurfaceView surfaceView;
	private SurfaceHolder holder;
	private FrameLayout frameLayout;
	private float curVolume = 1;
	private Uri uri_bull, uri_maozi, uri_three, uri_ton80, uri_highton,
			uri_lowton, uri_winner, uri_horse;
	public static final int maozi_ID = 0;
	public static final int three_ID = 1;
	public static final int ton80_ID = 2;
	public static final int highton_ID = 3;
	public static final int lowton_ID = 4;
	public static final int winner_ID = 5;
	public static final int bull_ID = 6;
	public static final int horse_ID = 7;

	private Dialog Video_Dialog = null;

	public VideoPlayManage(Context context, WindowManager windowManager) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.windowManager = windowManager;
		Init();
	}

	@SuppressWarnings("deprecation")
	private void Init() {
		frameLayout = new FrameLayout(context);
		// View view =
		// LayoutInflater.from(context).inflate(R.layout.video_dialog, null);
		// videoView = (VideoView) view.findViewById(R.id.video);
		Video_Dialog = new Dialog(context, R.style.Dialog);
		Video_Dialog.setContentView(frameLayout);

		Display d = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams p = Video_Dialog.getWindow().getAttributes();
		p.height = (d.getHeight());
		p.width = (d.getWidth());
		Video_Dialog.getWindow().setAttributes(p);

		// context.mount_path = "/storage/external_storage/sdcard1/raw";
		/*
		 * uri_bull = Uri.parse(context.mount_path + "/" + "bull.mp4");
		 * uri_maozi = Uri.parse(context.mount_path + "/" + "hattrick.mp4");
		 * uri_three = Uri.parse(context.mount_path + "/" + "three.mp4");
		 * uri_ton80 = Uri.parse(context.mount_path + "/" + "lowton.mp4");
		 * uri_highton = Uri.parse(context.mount_path + "/" + "highton.mp4");
		 * uri_lowton = Uri.parse(context.mount_path + "/" + "lowton.mp4");
		 * uri_winner = Uri.parse(context.mount_path + "/" + "win.mp4");
		 * uri_horse = Uri.parse(context.mount_path + "/" + "highton.mp4");
		 */

		uri_bull = Uri.parse("android.resource://" + context.getPackageName()
				+ "/" + R.raw.bullmov);
		uri_maozi = Uri.parse("android.resource://" + context.getPackageName()
				+ "/" + R.raw.hatmov);
		uri_three = Uri.parse("android.resource://" + context.getPackageName()
				+ "/" + R.raw.inthebed3mov);
		uri_ton80 = Uri.parse("android.resource://" + context.getPackageName()
				+ "/" + R.raw.ton80mov);
		uri_highton = Uri.parse("android.resource://"
				+ context.getPackageName() + "/" + R.raw.highton);
		uri_lowton = Uri.parse("android.resource://" + context.getPackageName()
				+ "/" + R.raw.lowton);

		uri_horse = Uri.parse("android.resource://" + context.getPackageName()
				+ "/" + R.raw.whitehorse);
	}

	private void getVolume() {
		// curVolume = sharedPreferences.getFloat("pool", 10);
		mediaPlayer.setVolume(curVolume, curVolume);
	}

	static MediaPlayer getMediaPlayer(Context context) {
		MediaPlayer mediaplayer = new MediaPlayer();
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
			return mediaplayer;
		}
		try {
			Class<?> cMediaTimeProvider = Class
					.forName("android.media.MediaTimeProvider");
			Class<?> cSubtitleController = Class
					.forName("android.media.SubtitleController");
			Class<?> iSubtitleControllerAnchor = Class
					.forName("android.media.SubtitleController$Anchor");
			Class<?> iSubtitleControllerListener = Class
					.forName("android.media.SubtitleController$Listener");

			Constructor<?> constructor = cSubtitleController
					.getConstructor(new Class[] { Context.class,
							cMediaTimeProvider, iSubtitleControllerListener });

			Object subtitleInstance = constructor.newInstance(context, null,
					null);

			Field f = cSubtitleController.getDeclaredField("mHandler");

			f.setAccessible(true);
			try {
				f.set(subtitleInstance, new Handler());
			} catch (IllegalAccessException e) {
				return mediaplayer;
			} finally {
				f.setAccessible(false);
			}

			Method setsubtitleanchor = mediaplayer.getClass().getMethod(
					"setSubtitleAnchor", cSubtitleController,
					iSubtitleControllerAnchor);

			setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
			// Log.e("", "subtitle is setted :p");
		} catch (Exception e) {

		}
		return mediaplayer;
	}

	private int Id = -1;

	// public VideoView videoView = null;
	 public void play_video(int play_Id) {
		Id = play_Id;
		surfaceView = new SurfaceView(context);
		holder = surfaceView.getHolder();
		holder.addCallback(this);
		// mediaPlayer = new MediaPlayer();
		mediaPlayer = getMediaPlayer(context);
		// mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
		// @Override
		// public void onCompletion(MediaPlayer arg0) {
		// // TODO Auto-generated method stub
		// stop_video(arg0);
		// }
		// });
		getVolume();
		frameLayout.removeAllViews();
		frameLayout.addView(surfaceView);
		Video_Dialog.show();

		/*
		 * switch (Id) { case bull_ID: videoView.setVideoURI(uri_bull); break;
		 * case maozi_ID: videoView.setVideoURI(uri_maozi); break; case
		 * three_ID: videoView.setVideoURI(uri_three); break; case ton80_ID:
		 * videoView.setVideoURI(uri_ton80); break; case highton_ID:
		 * videoView.setVideoURI(uri_highton); break; case lowton_ID:
		 * videoView.setVideoURI(uri_lowton); break; case winner_ID:
		 * videoView.setVideoURI(uri_winner); break; case horse_ID:
		 * videoView.setVideoURI(uri_horse); break; }
		 * videoView.setOnPreparedListener(new OnPreparedListener() {
		 * 
		 * @Override public void onPrepared(MediaPlayer mp) {
		 * mp.setVideoScalingMode
		 * (MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
		 * mp.setLooping(false); mp.start(); } });
		 * videoView.setOnCompletionListener(new OnCompletionListener() {
		 * 
		 * @Override public void onCompletion(MediaPlayer mp) { stop_video(); }
		 * }); videoView.setOnErrorListener(new OnErrorListener() {
		 * 
		 * @Override public boolean onError(MediaPlayer arg0, int arg1, int
		 * arg2) { return true; } }); videoView.start(); Video_Dialog.show();
		 */
	}

	// public void stop_video(MediaPlayer mediaPlayer11) {
	public void stop_video() {
		Id = -1;
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			Release(mediaPlayer);
			frameLayout.removeAllViews();
			if (Video_Dialog.isShowing()) {
				Video_Dialog.dismiss();
			}
			mediaPlayer = null;// 1210
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		try {
			mediaPlayer.setDisplay(holder);
			switch (Id) {
			case bull_ID:
				mediaPlayer.setDataSource(context, uri_bull);
				break;
			case maozi_ID:
				mediaPlayer.setDataSource(context, uri_maozi);
				break;
			case three_ID:
				mediaPlayer.setDataSource(context, uri_three);
				break;
			case ton80_ID:
				mediaPlayer.setDataSource(context, uri_ton80);
				break;
			case highton_ID:
				mediaPlayer.setDataSource(context, uri_highton);
				break;
			case lowton_ID:
				mediaPlayer.setDataSource(context, uri_lowton);
				break;

			case horse_ID:
				mediaPlayer.setDataSource(context, uri_horse);
				break;
			}
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mp.start();
				}
			});
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		// if (mediaPlayer != null) {
		// if (mediaPlayer.isPlaying()) {
		// mediaPlayer.stop();
		// }
		// Release();
		// }
	}

	public void Release(MediaPlayer mediaPlayer) {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
		}
	}
}