package tw.fmbase.app.indicatorviewbar_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by aming on 2016/8/28.
 */
public class Bar {

    private static final String TAG = Bar.class.getName();

    private float mLeftX;

    private float mLength;

    private float mY;

    private int[] mColors;

    private Paint[] mPaints;

    public Bar(Context context,
               float x,
               float y,
               float length,
               float heightPx,
               int color) {
        int[] colors = {color};

        init(context, x, y, length, heightPx, colors);
    }

    public Bar(Context context,
               float x,
               float y,
               float length,
               float heightPx,
               int[] colors) {
        init(context, x, y, length, heightPx, colors);
    }

    public void draw(Canvas canvas) {
        int levelCount = mColors.length;
        float eachSegmentLength = mLength / levelCount;
        float leftX = mLeftX;

        Log.d(TAG, "eachSegmentLength = " + eachSegmentLength);

        for (int index = 0 ; index < levelCount ; index++ ) {
            float rightX = leftX + eachSegmentLength;

            Log.d(TAG, "leftX = " + leftX + ", rightX = " + rightX);

            canvas.drawLine(leftX, mY, rightX, mY, mPaints[index]);

            leftX += eachSegmentLength;

        }
    }

    private void init(Context context,
                      float x,
                      float y,
                      float length,
                      float heightPx,
                      int[] colors) {
        mLeftX = x;
        mLength = length;
        mY = y;
        mColors = colors;

        Log.d(TAG, "mLeftX = " + mLeftX);

        createPaints(heightPx);
    }

    private void createPaints(float heightPx) {
        int count = mColors.length;
        mPaints = new Paint[count];

        for (int i = 0 ; i < count ; i++) {
            Log.d(TAG, "color = " + String.format("#%06X", (0xFFFFFF & mColors[i])));

            mPaints[i] = new Paint();
            mPaints[i].setColor(mColors[i]);
            mPaints[i].setStrokeWidth(heightPx);
            mPaints[i].setAntiAlias(true);
        }
    }
}
