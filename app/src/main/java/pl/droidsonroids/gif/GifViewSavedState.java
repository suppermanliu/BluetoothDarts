package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

class GifViewSavedState extends View.BaseSavedState {

    final long[][] mStates;

    GifViewSavedState(Parcelable superState, Drawable... drawables) {
        super(superState);
        mStates = new long[drawables.length][4];
        for (int i = 0; i < drawables.length; i++) {
            Drawable drawable = drawables[i];
            if (drawable instanceof GifDrawable) {
                mStates[i] = ((GifDrawable) drawable).mNativeInfoHandle.getSavedState();
            } else {
                mStates[i] = null;
            }
        }
    }

    private GifViewSavedState(Parcel in) {
        super(in);
        mStates = new long[in.readInt()][4];
        for (long[] mState : mStates)
            in.readLongArray(mState);
    }

    public GifViewSavedState(Parcelable superState, long[] savedState) {
        super(superState);
        mStates = new long[1][3];
        mStates[0] = savedState;
    }

    //@NonNull
    @Override
    public void writeToParcel( Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mStates.length);
        for (long[] mState : mStates)
            dest.writeLongArray(mState);
    }

    public static final Creator<GifViewSavedState> CREATOR = new Creator<GifViewSavedState>() {

        @Override
		public GifViewSavedState createFromParcel(Parcel in) {
            return new GifViewSavedState(in);
        }

        @Override
		public GifViewSavedState[] newArray(int size) {
            return new GifViewSavedState[size];
        }
    };

    void restoreState(Drawable drawable, int i) {
        if (mStates[i] != null && drawable instanceof GifDrawable) {
            final GifDrawable gifDrawable = (GifDrawable) drawable;
            gifDrawable.startAnimation(gifDrawable.mNativeInfoHandle.restoreSavedState(mStates[i], gifDrawable.mBuffer));
        }
    }
}
