package tw.fmbase.app.indicatorviewbar_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * Created by aming on 2016/8/28.
 */
public class Bar {

    private float mLeftX;

    private float mLength;

    private float mY;

    private int[] mColors;

    private Paint mPaint;

    public Bar(Context context,
               float x,
               float y,
               float length,
               float heighPx,
               int color) {
        int[] colors = {color};

        init(context, x, y, length, heighPx, colors);
    }

    public Bar(Context context,
               float x,
               float y,
               float length,
               float heighPx,
               int[] colors) {
        init(context, x, y, length, heighPx, colors);
    }

    public void draw(Canvas canvas) {
        int levelCount = mColors.length;
        float eachSegmentLength = mLength / levelCount;
        float leftX = mLeftX;

        for (int color:mColors) {
            float rightX = leftX + eachSegmentLength;
            leftX += eachSegmentLength;

            mPaint.setColor(color);

            canvas.drawLine(mLeftX, mY, rightX, mY, mPaint);
        }
    }

    private void init(Context context,
                      float x,
                      float y,
                      float length,
                      float heighPx,
                      int[] colors) {
        mLeftX = x;
        mLength = length;
        mY = y;
        mColors = colors;

        mPaint = new Paint();
        mPaint.setStrokeWidth(heighPx);
        mPaint.setAntiAlias(true);
    }
}
