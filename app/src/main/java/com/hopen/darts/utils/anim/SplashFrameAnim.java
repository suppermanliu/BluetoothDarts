package com.hopen.darts.utils.anim;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hopen.bluetoothdarts.R;

public class SplashFrameAnim extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {

	// ����״̬
	public boolean mLoop = false;
	// ��ȡ����
	private SurfaceHolder mSurfaceHolder = null;
	// ͼƬ����
	public int mCount = 0;
	// ʱ����
	private int speed;
	public String drawable[];
	private boolean oneshot;
	private static Matrix matrix = new Matrix();
	private Context context;
	//private Bitmap bitmap = null;

//	Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			Destroyed();
//		}
//	};

	public SplashFrameAnim(Context context) {
		super(context);

	}

	public SplashFrameAnim(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;

		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		setZOrderOnTop(true);
		mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);


		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.FrameAnim);

		int drawableIds = typedArray.getResourceId(
				R.styleable.FrameAnim_drawableIds, 0);
		drawable = context.getResources().getStringArray(drawableIds);
		speed = typedArray.getInteger(R.styleable.FrameAnim_duration, 50);

		oneshot = typedArray.getBoolean(R.styleable.FrameAnim_oneshot, true);
		typedArray.recycle();
	}

	private void draw(Bitmap bitmap) {
		Canvas canvas = mSurfaceHolder.lockCanvas();

		if (canvas == null || mSurfaceHolder == null) {
			return;
		}

		try {
			if (bitmap != null) {

				// ����
				canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);

				canvas.drawBitmap(bitmap, matrix, null);
			}
			// ������ʾ
			mSurfaceHolder.unlockCanvasAndPost(canvas);

		} catch (Exception ex) {
			//Log.e("drawImg_Thread", ex.getMessage());
			return;
		} finally {
			// ��Դ����
			if (bitmap != null) {
				bitmap.recycle();
				bitmap = null;
			}
		}
	}
	
	public void stop(){
		if (oneshot == true) {
			mLoop = false;
			mCount = 0;
			//mHandler.sendEmptyMessage(0);
			return;
		}else if(oneshot == false){
			mCount = 0;
		}
	}

	@Override
	public void run() {
		while (mLoop) {
			synchronized (mSurfaceHolder) {
				if(mCount < drawable.length ){
					int resid = context.getResources().getIdentifier(
							drawable[mCount], "drawable", context.getPackageName());
					//System.out.print("-" + drawable[mCount++]);
					Bitmap bitmap = null;
					bitmap = BitmapFactory.decodeStream(context.getResources()
							.openRawResource(resid));
					mCount++;
					draw(bitmap);
				}
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				//Log.e("ImageSurfaceView_Thread", e.getMessage().toString());
			}
		}
	}

	private void Destroyed() {
		//this.setVisibility(View.GONE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	private Thread th = null;
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mLoop = true;// ��ʼ��ͼ
		mCount=0;
		th = new Thread(this);
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			mLoop = false;
			th.interrupt();
		} catch (Exception e) {
			//Log.e("surfaceDestroyed", e.getMessage().toString());
		}
		th = null;
	}

}