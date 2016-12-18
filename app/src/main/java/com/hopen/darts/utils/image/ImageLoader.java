package com.hopen.darts.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

	private static ImageLoader instance;
	private Context context;

	private ExecutorService executorService;
	public ImageMemoryCache memoryCache;
	public ImageFileCache fileCache;
	private Map<String, ImageView> taskMap;
	private boolean allowLoad = true;

	private ImageLoader(Context context) {
		this.context = context;
		int cpuNums = Runtime.getRuntime().availableProcessors();
		this.executorService = Executors.newFixedThreadPool(cpuNums + 1);

		this.memoryCache = new ImageMemoryCache(context);
		this.fileCache = new ImageFileCache();
		this.taskMap = new HashMap<String, ImageView>();
	}

	public static ImageLoader getInstance(Context context) {
		if (instance == null)
			instance = new ImageLoader(context.getApplicationContext());
		return instance;
	}

	public void restore() {
		this.allowLoad = true;
	}

	public void lock() {
		this.allowLoad = false;
	}

	public void unlock() {
		this.allowLoad = true;
		doTask();
	}

	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public void addTask(String url, ImageView img) {
		if (url.startsWith("ResId:")) {
			int resId = Integer.parseInt(url.substring(6));
			// img.setImageResource(resId);
			img.setImageBitmap(readBitMap(context, resId));
			return;
		}
		Bitmap bitmap = memoryCache.getBitmapFromCache(url);
		if (bitmap != null) {
			img.setImageBitmap(bitmap);
		} else {
			synchronized (taskMap) {
				img.setTag(url);
				taskMap.put(Integer.toString(img.hashCode()), img);
			}
			if (allowLoad) {
				doTask();
			}
		}
	}

	private void doTask() {
		synchronized (taskMap) {
			Collection<ImageView> con = taskMap.values();
			for (ImageView i : con) {
				if (i != null) {
					if (i.getTag() != null) {
						loadImage((String) i.getTag(), i);
					}
				}
			}
			taskMap.clear();
		}
	}

	private void loadImage(String url, ImageView img) {
		this.executorService.submit(new TaskWithResult(
				new TaskHandler(url, img), url));
	}

	private Bitmap getBitmap(String url) {
		Bitmap result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			result = fileCache.getImage(url);
			if (result == null) {
				result = ImageGetFromHttp.downloadBitmap(url);
				if (result != null) {
					fileCache.saveBitmap(result, url);
					memoryCache.addBitmapToCache(url, result);
				}
			} else {
				memoryCache.addBitmapToCache(url, result);
			}
		}
		return result;
	}

	private class TaskWithResult implements Callable<String> {
		private String url;
		private Handler handler;

		public TaskWithResult(Handler handler, String url) {
			this.url = url;
			this.handler = handler;
		}

		@Override
		public String call() throws Exception {
			Message msg = new Message();
			msg.obj = getBitmap(url);
			if (msg.obj != null) {
				handler.sendMessage(msg);
			}
			return url;
		}
	}

	private class TaskHandler extends Handler {
		String url;
		ImageView img;

		public TaskHandler(String url, ImageView img) {
			this.url = url;
			this.img = img;
		}

		@Override
		public void handleMessage(Message msg) {
			if (img.getTag().equals(url)) {
				if (msg.obj != null) {
					Bitmap bitmap = (Bitmap) msg.obj;
					img.setImageBitmap(bitmap);
				}
			}
		}
	}

}