package com.hopen.darts.view;
import com.hopen.bluetoothdarts.R;
import com.hopen.darts.utils.image.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
public class GameNumberView extends FrameLayout {
	
	private ImageLoader imageLoader;
	private Context context;
	private LinearLayout LinearLayout_game_number;
	private ImageView number1,number2,number3,number4;

	public GameNumberView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		imageLoader = ImageLoader.getInstance(context); 
	}

	public GameNumberView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		imageLoader = ImageLoader.getInstance(context); 
		View view = LayoutInflater.from(context).inflate(R.layout.view_number,null);
		LinearLayout_game_number = (LinearLayout) view.findViewById(R.id.LinearLayout_game_number);
		number1 = (ImageView) view.findViewById(R.id.number1);
		number2 = (ImageView) view.findViewById(R.id.number2);
		number3 = (ImageView) view.findViewById(R.id.number3);
		number4 = (ImageView) view.findViewById(R.id.number4);
		addView(view, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		this.setForegroundGravity(Gravity.CENTER);
	}
	
	public void setVisibility(boolean flag){
		if(flag){
			LinearLayout_game_number.setVisibility(View.VISIBLE);
		}else{
			LinearLayout_game_number.setVisibility(View.GONE);
		}
	}
	
	/**
	 * ��ʾ��Ϸ��������
	 **/
	public void showNumber(int initScore){
		
		switch (initScore) {
		case 301:
			number1.setImageResource(R.drawable.number3);
			number2.setImageResource(R.drawable.number0);
			number3.setImageResource(R.drawable.number1);
			break;
		case 501:
			number1.setImageResource(R.drawable.number5);
			number2.setImageResource(R.drawable.number0);
			number3.setImageResource(R.drawable.number1);
			break;
		case 701:
			number1.setImageResource(R.drawable.number7);
			number2.setImageResource(R.drawable.number0);
			number3.setImageResource(R.drawable.number1);
			break;
		case 901:
			number1.setImageResource(R.drawable.number9);
			number2.setImageResource(R.drawable.number0);
			number3.setImageResource(R.drawable.number1);
			break;
		case 1101:
			number1.setImageResource(R.drawable.number1);
			number2.setImageResource(R.drawable.number1);
			number3.setImageResource(R.drawable.number0);
			number4.setImageResource(R.drawable.number1);
			break;
		case 1501:
			number1.setImageResource(R.drawable.number1);
			number2.setImageResource(R.drawable.number5);
			number3.setImageResource(R.drawable.number0);
			number4.setImageResource(R.drawable.number1);
			break;
		case 0:
			number1.setImageResource(R.drawable.number0);//高分赛初始分数
			
			break;
		default:
			break;
		}
	}
	
	/**������ҵ�ǰ������ʾ**/
	public void setPlayerScore(int score){
		String str = String.valueOf(score);
		char [] stringArr = str.toCharArray(); //ע�ⷵ��ֵ��char����
		for(int i = 0 ;i<stringArr.length;i++){
			switch (i) {
			case 0:
				setImageViewNumber(number1,stringArr[i]);
				number2.setVisibility(View.GONE);
				number3.setVisibility(View.GONE);
				number4.setVisibility(View.GONE);
				break;
			case 1:
				setImageViewNumber(number2,stringArr[i]);
				number2.setVisibility(View.VISIBLE);
				number3.setVisibility(View.GONE);
				number4.setVisibility(View.GONE);
				break;
			case 2:
				setImageViewNumber(number3,stringArr[i]);
				number3.setVisibility(View.VISIBLE);
				number4.setVisibility(View.GONE);
				break;
			case 3:
				setImageViewNumber(number4,stringArr[i]);
				number4.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}
	
	private void setImageViewNumber(ImageView imageView,char c){
		switch (c) {
		case '0':
			imageView.setImageResource(R.drawable.number0);
			break;
		case '1':
			imageView.setImageResource(R.drawable.number1);
			break;
		case '2':
			imageView.setImageResource(R.drawable.number2);
			break;
		case '3':
			imageView.setImageResource(R.drawable.number3);
			break;
		case '4':
			imageView.setImageResource(R.drawable.number4);
			break;
		case '5':
			imageView.setImageResource(R.drawable.number5);
			break;
		case '6':
			imageView.setImageResource(R.drawable.number6);
			break;
		case '7':
			imageView.setImageResource(R.drawable.number7);
			break;
		case '8':
			imageView.setImageResource(R.drawable.number8);
			break;
		case '9':
			imageView.setImageResource(R.drawable.number9);
			break;
		default:
			break;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	@Override
	public void setAnimation(Animation animation) {
		// TODO Auto-generated method stub
		super.setAnimation(animation);
	}

	@Override
	public void setBackgroundResource(int resid) {
		// TODO Auto-generated method stub
		super.setBackgroundResource(resid);
		Bitmap bitmap = getBitmap(resid);
		Drawable drawable = bitmapToDrawable(bitmap);
		if (drawable != null) {
			this.setBackgroundDrawable(drawable);
		}
		bitmap.recycle();
	}

	@Override
	public void setBackgroundColor(int color) {
		// TODO Auto-generated method stub
		super.setBackgroundColor(color);
	}


	private Bitmap getBitmap(int resId) {
		// ���ڴ滺���ȡ
		String str_resId = String.valueOf(resId);
		Bitmap bitmap = imageLoader.memoryCache.getBitmapFromCache(str_resId);
		if (bitmap == null) {
			// �ļ������л�ȡ
			String Imagename = context.getResources().getResourceEntryName(resId);
			bitmap = imageLoader.fileCache.getImageFromFile(Imagename);
			if (bitmap != null) {
				// ��ӵ��ڴ滺��
				imageLoader.memoryCache.addBitmapToCache(str_resId, bitmap);
			} else {
				Log.i("", "��Դ�ļ�res���ص���bitmap");
				bitmap = getBitmapFromRes(resId);

			}
		}
		return bitmap;
	}

	private Drawable bitmapToDrawable(Bitmap bm) {
		BitmapDrawable bd = new BitmapDrawable(bm);
		return bd;
	}

	private Bitmap getBitmapFromRes(int resId) {
		Bitmap bmp = null;
		bmp = ImageLoader.readBitMap(context, resId);
		if (bmp != null) {
			// ��ӵ��ļ�����
			String Imagename = context.getResources().getResourceEntryName(resId);
			imageLoader.fileCache.saveBitmapToFile(bmp, resId, Imagename);
			// ��ӵ��ڴ滺��
			String str_resId = String.valueOf(resId);
			imageLoader.memoryCache.addBitmapToCache(str_resId, bmp);
		}

		return bmp;
	}
}
