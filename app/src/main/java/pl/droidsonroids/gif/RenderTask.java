package pl.droidsonroids.gif;

import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

class RenderTask extends SafeRunnable {

    RenderTask(GifDrawable gifDrawable) {
        super(gifDrawable);
    }

    private final Runnable mNotifyListenersTask = new Runnable() {
        @Override
        public void run() {
            for (AnimationListener listener : mGifDrawable.mListeners)
                listener.onAnimationCompleted();
            
           
        }
    };
    private final Runnable mNotifyListenersTask11 = new Runnable() {
        @Override
        public void run() {
            for (AnimationListener listener : mGifDrawable.mListeners)
             
            listener.FrameIndex7();
            
        }
    };
    
    @Override
    public void doWork() {
        final long invalidationDelay = mGifDrawable.mNativeInfoHandle.renderFrame(mGifDrawable.mBuffer);
        if (invalidationDelay >= 0) {
            mGifDrawable.mNextFrameRenderTime = SystemClock.elapsedRealtime() + invalidationDelay;
            if (mGifDrawable.isVisible()) {
                if (mGifDrawable.mIsRunning && !mGifDrawable.mIsRenderingTriggeredOnDraw) {
                    mGifDrawable.mExecutor.schedule(this, invalidationDelay, TimeUnit.MILLISECONDS);
                }
            }
            if(mGifDrawable.mIsRunning && mGifDrawable.getCurrentFrameIndex() == 15){
          	  if (!mGifDrawable.mListeners.isEmpty()) {
                    mGifDrawable.scheduleSelf(mNotifyListenersTask11, 0L);
                }
          }
           
        } 
        
        else {
            mGifDrawable.mNextFrameRenderTime = Long.MIN_VALUE;
            mGifDrawable.mIsRunning = false;
            if (!mGifDrawable.mListeners.isEmpty()) {
                mGifDrawable.scheduleSelf(mNotifyListenersTask, 0L);
            }
        }
        if (mGifDrawable.isVisible() && !mGifDrawable.mInvalidationHandler.hasMessages(0)) {
            mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(0, 0);
        }
    }
}
