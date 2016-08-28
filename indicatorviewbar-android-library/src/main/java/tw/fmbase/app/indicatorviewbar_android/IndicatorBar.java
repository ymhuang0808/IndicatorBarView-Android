package tw.fmbase.app.indicatorviewbar_android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        createSingleColorBar();

        mBar.draw(canvas);
    }

    public IndicatorBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IndicatorBar,
                0,
                0);

        initIndicatorBar(context, typedArray);
    }

    private void initIndicatorBar(Context context, TypedArray typedArray) {
        final float DEFAULT_HEIGHT_PX = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_BAR_HEIGHT_DP,
                context.getResources().getDisplayMetrics()
        );

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
    }

    private void createSingleColorBar() {
        mBar = new Bar(getContext(),
                getMarginLeft(),
                getYPosition(),
                getBarLength(),
                getBarHeightDp(),
                mBarColor
                );

        invalidate();
    }

    private void createMultColorsBar() {
        mBar = new Bar(getContext(),
                getMarginLeft(),
                getYPosition(),
                getBarLength(),
                getBarHeightDp(),
                mBarLevelColors
        );

        invalidate();
    }

    private float getMarginLeft() {
        return 0;
    }

    private float getYPosition() {
        return (getHeight() - getResources().getDimensionPixelSize(R.dimen.default_bar_padding_bottom));
    }

    private float getBarHeightDp() {
        return mBarHeightPx;
    }

    private float getBarLength() {
        return getWidth() - getMarginLeft() * 2;
    }
}
