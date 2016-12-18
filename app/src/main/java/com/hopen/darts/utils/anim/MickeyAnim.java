package com.hopen.darts.utils.anim;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.hopen.bluetoothdarts.R;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;

public class MickeyAnim {

	public static final int QQQ = 1;
	public static final int QQC = 2;
	public static final int QCC = 3;
	public static final int QQG = 4;
	public static final int QQH = 5;
	public static final int QCG = 6;
	public static final int QCH = 7;
	public static final int CCC = 8;
	public static final int QGG = 9;
	private static final int PLAY_FRIST_PIC = 1;
	private static final int PLAY_SECOND_PIC = 2;
	private static final int PLAY_THREE_PIC = 3;
	private GifDrawable gifDrawable1, gifDrawable2;
	public GifDrawable  gifDrawable3;
	private ImageView imageView1, imageView2, imageView3;
	private Dialog Anim_Dialog;
	private Context context;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case PLAY_FRIST_PIC:
				imageView1.setBackgroundDrawable(gifDrawable1);
				handler.sendEmptyMessageDelayed(PLAY_SECOND_PIC, 500);

			case PLAY_SECOND_PIC:
				imageView2.setBackgroundDrawable(gifDrawable2);
				handler.sendEmptyMessageDelayed(PLAY_THREE_PIC, 500);

				break;
			case PLAY_THREE_PIC:
				imageView3.setBackgroundDrawable(gifDrawable3);
				break;

			}
		}
	};

	/**
	 * @param context
	 */
	public MickeyAnim(Context context) {
		super();
		this.context = context;
	}

	private void AnimationListener() {
		gifDrawable1.addAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationCompleted() {
				// TODO Auto-generated method stub
			}
			@Override
			public void FrameIndex7() {
				// TODO Auto-generated method stub
				gifDrawable1.stop();
			}
			
		});
		gifDrawable2.addAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationCompleted() {
				// TODO Auto-generated method stub
			}
			@Override
			public void FrameIndex7() {
				// TODO Auto-generated method stub
				gifDrawable2.stop();
			}
			
		});
		gifDrawable3.addAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationCompleted() {
				// TODO Auto-generated method stub
				StopAnim_Mickey();
			}
			@Override
			public void FrameIndex7() {
				// TODO Auto-generated method stub
				gifDrawable1.start();
				gifDrawable2.start();
			}
			
		});
	}


	public void PlayAnim_Mickey(int type) {
		try {
			PlayType(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.sendEmptyMessageDelayed(PLAY_FRIST_PIC, 10);

	}


	public void StopAnim_Mickey() {
		if (Anim_Dialog != null && Anim_Dialog.isShowing())
			Anim_Dialog.dismiss();
			gifDrawable1.recycle();
			gifDrawable2.recycle();
			gifDrawable3.recycle();
	}

	private void initView() {
		Anim_Dialog = new Dialog(context, R.style.Dialog);
		Anim_Dialog.setContentView(R.layout.dialog_mi);
		imageView1 = (ImageView) Anim_Dialog.findViewById(R.id.imageview1);
		imageView2 = (ImageView) Anim_Dialog.findViewById(R.id.imageview2);
		imageView3 = (ImageView) Anim_Dialog.findViewById(R.id.imageview3);
		Anim_Dialog.setCancelable(false);
	}

	private void PlayType(int dex) throws Exception {
		initView();
		switch (dex) {
		case QQQ:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable3 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			break;
		case QQC:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable3 = new GifDrawable(context.getResources(), R.drawable.x);
			break;
		case QCC:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(), R.drawable.x);
			gifDrawable3 = new GifDrawable(context.getResources(), R.drawable.x);
			break;
		case QQG:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable3 = new GifDrawable(context.getResources(),
					R.drawable.xiegang);
			break;
		case QQH:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable3 = new GifDrawable(context.getResources(),
					R.drawable.heng);
			break;

		case QCG:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(), R.drawable.x);
			gifDrawable3 = new GifDrawable(context.getResources(),
					R.drawable.xiegang);
			break;
		case QCH:
			gifDrawable1 = new GifDrawable(context.getResources(),
					R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(), R.drawable.x);
			gifDrawable3 = new GifDrawable(context.getResources(),
					R.drawable.heng);
			break;
		case CCC:
			gifDrawable1 = new GifDrawable(context.getResources(), R.drawable.x);
			gifDrawable2 = new GifDrawable(context.getResources(), R.drawable.x);
			gifDrawable3 = new GifDrawable(context.getResources(), R.drawable.x);
			
			break;
		case QGG:
			gifDrawable1 = new GifDrawable(context.getResources(), R.drawable.ox);
			gifDrawable2 = new GifDrawable(context.getResources(), R.drawable.xiegang);
			gifDrawable3 = new GifDrawable(context.getResources(), R.drawable.xiegang);
			
			break;
		}

		AnimationListener();
		Anim_Dialog.show();
	}
}
