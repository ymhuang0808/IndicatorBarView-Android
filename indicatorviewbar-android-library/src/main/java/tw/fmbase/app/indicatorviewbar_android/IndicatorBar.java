package tw.fmbase.app.indicatorviewbar_android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by aming on 2016/8/28.
 */
public class IndicatorBar extends View {

    private static final String TAG = IndicatorBar.class.getName();

    // Default values

    private static final int DEFAULT_MIN_VALUE = 0;

    private static final int DEFAULT_MAX_VALUE = 100;

    private static final float DEFAULT_BAR_HEIGHT_DP = 3;

    private static final boolean DEFAULT_IS_MULTIPLE_LEVEL_COLORS = false;

    private static final int DEFAULT_BAR_LEVEL_COLORS_RESOURCE_ID = R.array.default_bar_leve_colors;

    private static final int DEFAULT_BAR_COLOR = Color.parseColor("#2196F3");

    private static final boolean DEFAULT_IS_PIN_COLO_WITH_BAR_COLOR = true;

    private static final int DEFAULT_PIN_COLOR = Color.parseColor("#2196F3");

    // Members

    private int mMinValue;

    private int mMaxValue;

    private float mBarHeightPx;

    private boolean mIsMultipleLevelColors;

    private int[] mBarLevelColors;

    private int mBarColor;

    private boolean mIsPinColorWithBarColor;

    private int mPinColor;

    private Bar mBar;

    private Pin mPin;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        initBar();
        createPin();

//        mBar.draw(canvas);
        mPin.draw(canvas);
    }

    public IndicatorBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            initIndicatorBar(context, attrs);
        }
    }


    public IndicatorBar(Context context) {
        super(context);
    }

    public IndicatorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            initIndicatorBar(context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        if (!isInEditMode()) {
            initIndicatorBar(context, attrs);
        }
    }

    private void initIndicatorBar(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.IndicatorBar,
                0,
                0);

        try {
            // Get default bar height
            final float DEFAULT_HEIGHT_PX = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    DEFAULT_BAR_HEIGHT_DP,
                    context.getResources().getDisplayMetrics()
            );

            // Get the values of the attributes.
            // If the attributes are not set, it will get default values
            mMinValue = typedArray.getInt(R.styleable.IndicatorBar_minValue, DEFAULT_MIN_VALUE);
            mMaxValue = typedArray.getInt(R.styleable.IndicatorBar_maxValue, DEFAULT_MAX_VALUE);
            mBarHeightPx = typedArray.getDimension(
                    R.styleable.IndicatorBar_barHeight,
                    DEFAULT_HEIGHT_PX
            );
            mIsMultipleLevelColors = typedArray.getBoolean(
                    R.styleable.IndicatorBar_isMultipleLevelColors,
                    DEFAULT_IS_MULTIPLE_LEVEL_COLORS
            );
            mIsPinColorWithBarColor = typedArray.getBoolean(
                    R.styleable.IndicatorBar_isPinColorWithBarColor,
                    DEFAULT_IS_PIN_COLO_WITH_BAR_COLOR
            );

            if (mIsMultipleLevelColors) {
                int barLevelColorsResourceId = typedArray.getResourceId(
                        R.styleable.IndicatorBar_barLevelColors,
                        DEFAULT_BAR_LEVEL_COLORS_RESOURCE_ID);

                mBarLevelColors = context.getResources().getIntArray(barLevelColorsResourceId);
            } else {
                mBarColor = typedArray.getColor(R.styleable.IndicatorBar_barColor, DEFAULT_BAR_COLOR);
            }

            if (mIsPinColorWithBarColor) {
                if (!mIsMultipleLevelColors) {
                    mPinColor = mBarColor;
                }
            } else {
                if (!mIsMultipleLevelColors) {
                    mPinColor = typedArray.getColor(
                            R.styleable.IndicatorBar_pinColor,
                            DEFAULT_PIN_COLOR
                    );
                }
            }
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            initBar();
        }

        super.setEnabled(enabled);
    }

    private void initBar() {
        if (mIsMultipleLevelColors) {
            createMultColorsBar();
        } else {
            createSingleColorBar();
        }
    }

    private void createSingleColorBar() {
        mBar = new Bar(getContext(),
                getMarginLeft(),
                getYPosition(),
                getBarLength(),
                getBarHeightDp(),
                mBarColor
                );
    }

    private void createMultColorsBar() {
        mBar = new Bar(getContext(),
                getMarginLeft(),
                getYPosition(),
                getBarLength(),
                getBarHeightDp(),
                mBarLevelColors
        );
    }

    private void createPin() {
        mPin = new Pin(getContext(), mPinColor, 0, 0);
    }

    private float getMarginLeft() {
        return 0;
    }

    private float getYPosition() {
        float defaultPaddingBottomPx = getResources()
                .getDimensionPixelSize(R.dimen.default_bar_padding_bottom);
        float yPosition = getHeight() - defaultPaddingBottomPx;

        Log.d(TAG, "getHeight() = " + getHeight());
        Log.d(TAG, "defaultPaddingBottomPx = " + defaultPaddingBottomPx);
        Log.d(TAG, "yPosition = " + yPosition);

        return yPosition;
    }

    private float getBarHeightDp() {
        return mBarHeightPx;
    }

    private float getBarLength() {
        return getWidth() - getMarginLeft() * 2;
    }
}
