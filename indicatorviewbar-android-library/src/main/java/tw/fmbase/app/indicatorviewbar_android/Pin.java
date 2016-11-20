package tw.fmbase.app.indicatorviewbar_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
/**
 * Created by aming on 2016/8/29.
 */
public class Pin {

    private static final String TAG = Pin.class.getName();

    // Default values
    public final static int DEFAULT_RECTANGLE_WIDTH_DP = 50;

    public final static int DEFAULT_RECTANGLE_HEIGHT_DP = 40;

    public final static int DEFAULT_TOP_OFFSET = 8;

    // Members

    private int mColor;

    private DisplayMetrics mDisplayMetrics;

    private float mY;

    private float mX;

    public Pin(Context context, int color, float x, float y) {
        Log.d(TAG, "x = " + x);
        Log.d(TAG, "y = " + y);

        mX = x;
        mY = y;

        init(context, color);

        Log.d(TAG, "Pin()");
        Log.d(TAG, "mX = " + mX);
        Log.d(TAG, "mY = " + mY);
    }

    public void draw(Canvas canvas) {
        drawRectangle(canvas);
        drawTriangle(canvas);

        Log.d(TAG, "draw()");
    }

    public void setColor(int color) {
        // TODO: set the color into mColor and re-render
    }

    private void init(Context context, int color) {
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mColor = color;

        mY += getTopOffset();

        Log.d(TAG, "Pin color = " + String.format("#%06X", (0xFFFFFF & mColor)));
    }

    private void drawRectangle(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
//        paint.setAntiAlias(true);

        float left = getRectangleLeft();
        float top = getRectangleTop();
        float right = getRectangleRight();
        float bottom = getRectangleBottom();

        Log.d(TAG, "left = " + left);
        Log.d(TAG, "top = " + top);
        Log.d(TAG, "right = " + right);
        Log.d(TAG, "bottom = " + bottom);

        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawTriangle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        float eachSegmentLength = getWidthPx() / 6;
        float leftTopX = mX - eachSegmentLength;
        float rightTopX = mX + eachSegmentLength;
        float topY = mY - getHeightPx() / 4 ;
        float bottomX = mX;
        float bottomY = mY;

        Path path = new Path();
        path.moveTo(leftTopX, topY);
        path.lineTo(rightTopX, topY);
        path.lineTo(bottomX, bottomY);
        path.close();

        canvas.drawPath(path, paint);
    }

    private float getWidthPx() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_RECTANGLE_WIDTH_DP,
                mDisplayMetrics
        );
    }

    private float getHeightPx() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_RECTANGLE_HEIGHT_DP,
                mDisplayMetrics
        );
    }

    private float getTopOffset() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_TOP_OFFSET,
                mDisplayMetrics
        );
    }

    private float getRectangleTop() {
        return getRectangleBottom() - getHeightPx();
    }

    private float getRectangleBottom() {
        return mY - getHeightPx() / 4;
    }

    private float getRectangleLeft() {
        float segment = getWidthPx() / 2;

        return mX - segment;
    }

    private float getRectangleRight() {
        float segment = getWidthPx() / 2;

        return mX + segment;
    }
}
