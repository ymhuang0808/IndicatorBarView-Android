package tw.fmbase.app.indicatorviewbar_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by aming on 2016/8/29.
 */
public class Pin extends View {

    private static final String TAG = Pin.class.getName();

    // Default values
    private final static int DEFAULT_RECTANGLE_X_LENGTH_DP = 70;

    private final static int DEFAULT_RECTANGLE_Y_LENGTH_DP = 50;

    // Members

    private int mColor;

    private DisplayMetrics mDisplayMetrics;

//    private LayerDrawable mRectangle;
//
//    private LayerDrawable mTriangle;

    private LayerDrawable mPin;

    private LightingColorFilter mLightingColorFilter;

    private Rect mRect;

    private int mTopBound;

    private int mLeftBound;

    public Pin(Context context, int color, int top, int left) {
        super(context);

        mTopBound = top;
        mLeftBound = left;

        init(context, color);

        Log.d(TAG, "Pin()");
    }

    @Override
    public void draw(Canvas canvas) {
//        mRectangle.draw(canvas);
//        mTriangle.draw(canvas);

//        Log.d(TAG, "mTriangle bounds = " + mTriangle.getBounds().toString());

        mPin.draw(canvas);

        super.draw(canvas);

        Log.d(TAG, "draw()");
    }

    private void init(Context context, int color) {
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mColor = color;
        mLightingColorFilter = new LightingColorFilter(mColor, mColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPin = (LayerDrawable) context.getResources()
                    .getDrawable(R.drawable.pin, context.getTheme());
        } else {
            mPin = (LayerDrawable) context.getResources().getDrawable(R.drawable.pin);
        }

        mPin.setColorFilter(mLightingColorFilter);

        int rightBound = getRightBound();
        int bottomBound = getBottomBound();

        Log.d(TAG, "rightBound = " + rightBound + ", bottomBound = " + bottomBound);

        mPin.setBounds(mLeftBound, mTopBound, rightBound, bottomBound);
        RotateDrawable triangle = (RotateDrawable) mPin.findDrawableByLayerId(R.id.pin_triangle);
    }

    private int getRightBound() {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_RECTANGLE_X_LENGTH_DP,
                mDisplayMetrics
        );
    }

    private int getBottomBound() {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_RECTANGLE_Y_LENGTH_DP,
                mDisplayMetrics
        );
    }
}
