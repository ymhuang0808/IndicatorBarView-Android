package tw.fmbase.app.indicatorviewbar_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by aming on 2016/8/28.
 */
public class Bar {

    private static final String TAG = Bar.class.getName();

    private Context mContext;

    private float mStartX;

    private float mY;

    private float mLength;

    private int[] mColors;

    private Paint[] mPaints;

    public Bar(Context context,
               float heightPx,
               int color) {
        int[] colors = {color};

        init(context, heightPx, colors);
    }

    public Bar(Context context,
               float heightPx,
               int[] colors) {
        init(context, heightPx, colors);
    }

    public void draw(Canvas canvas) {
        int levelCount = mColors.length;
        float eachSegmentLength = mLength / levelCount;
        float leftX = mStartX;
        float rightX;

        Log.d(TAG, "eachSegmentLength = " + eachSegmentLength);

        for (int index = 0 ; index < levelCount ; index++ ) {
            rightX = leftX + eachSegmentLength;

            canvas.drawLine(leftX, mY, rightX, mY, mPaints[index]);

            leftX += eachSegmentLength;
        }
    }

    public void setStartX(float startX) {
        mStartX = startX;
    }

    public void setY(float y) {
        mY = y;
    }

    private void init(Context context,
                      float heightPx,
                      int[] colors) {
        mContext = context;
        mColors = colors;

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

    public void setLength(float length) {
        mLength = length;
    }

    public float getLength() {
        return mLength;
    }
}
