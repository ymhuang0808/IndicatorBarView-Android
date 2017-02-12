package tw.fmbase.app.indicatorviewbar_android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class IndicatorBar extends View {

    private static final String TAG = IndicatorBar.class.getName();

    // Default values

    private static final int DEFAULT_MIN_VALUE = 0;

    private static final int DEFAULT_MAX_VALUE = 100;

    private static final int DEFAULT_VALUE = 0;

    private static final float DEFAULT_BAR_HEIGHT_DP = 3;

    private static final boolean DEFAULT_IS_MULTIPLE_LEVEL_COLORS = false;

    private static final int DEFAULT_BAR_LEVEL_COLORS_RESOURCE_ID = R.array.default_bar_leve_colors;

    private static final int DEFAULT_BAR_COLOR = Color.parseColor("#2196F3");

    private static final boolean DEFAULT_IS_PIN_COLO_WITH_BAR_COLOR = true;

    private static final int DEFAULT_PIN_COLOR = Color.parseColor("#2196F3");

    // Members

    private int mMinValue;

    private int mMaxValue;

    private int mValue;

    private float mBarHeightPx;

    private boolean mIsMultipleLevelColors;

    private int[] mBarLevelColors;

    private int mBarColor;

    private boolean mIsPinColorWithBarColor;

    private int mPinColor;

    private Bar mBar;

    private Pin mPin;

    private DisplayMetrics mDisplayMetrics;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBar(canvas);
        drawPin(canvas);
    }

    /**
     * Constructor for setting attributes
     *
     * @param context
     * @param attrs
     */
    public IndicatorBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }


    public IndicatorBar(Context context) {
        super(context);
    }

    public IndicatorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Initialize the indicator bar
     *
     * It helps assigning the attributes into global properties
     *
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet) {
        mDisplayMetrics = context.getResources().getDisplayMetrics();
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

            mValue = typedArray.getInt(R.styleable.IndicatorBar_value, DEFAULT_VALUE);
        } finally {
            typedArray.recycle();
        }

        initBar();
        createPin();
    }

    @Override
    public void setEnabled(boolean enabled) {
        initBar();
        createPin();

        super.setEnabled(enabled);
    }

    private void initBar() {
        if (mIsMultipleLevelColors) {
            createMultiColorsBar();
        } else {
            createSingleColorBar();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Create a single color bar
     */
    private void createSingleColorBar() {
        Context context = getContext();
        float height = getBarHeightPx();

        mBar = new Bar(context, height, mBarColor);
    }

    /**
     * Create a multiple colors bar
     */
    private void createMultiColorsBar() {
        Context context = getContext();
        float height = getBarHeightPx();

        mBar = new Bar(context, height, mBarLevelColors);
    }

    /**
     * Prepare the properties and draw the Bar
     */
    private void drawBar(Canvas canvas) {
        float startX = getMarginLeft() + getPaddingLeft();
        float yPosition = getYPosition();
        float length = getBarLength();

        mBar.setStartX(startX);
        mBar.setY(yPosition);
        mBar.setLength(length);

        mBar.draw(canvas);
    }

    /**
     * Create a Pin object
     */
    private void createPin() {
        mPinColor = getPinColorByValue();

        mPin = new Pin(getContext(), mPinColor);
    }

    private void drawPin(Canvas canvas) {
        float x = transformValueToPx();
        float y = getYPosition() - getBarHeightPx();

        mPin.setX(x);
        mPin.setY(y);
        mPin.setText(String.valueOf(mValue));

        mPin.draw(canvas);
    }

    /**
     * Calculating the margin left distance
     *
     * @return
     */
    private float getMarginLeft() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                Pin.DEFAULT_RECTANGLE_WIDTH_DP,
                mDisplayMetrics
        ) / 2;
    }

    private float getYPosition() {
        float defaultPaddingBottomPx = getResources()
                .getDimensionPixelSize(R.dimen.default_bar_padding_bottom);
        float yPosition = getHeight() - defaultPaddingBottomPx - getPaddingTop() - getPaddingBottom();

        Log.d(TAG, "getHeight() = " + getHeight());
        Log.d(TAG, "defaultPaddingBottomPx = " + defaultPaddingBottomPx);
        Log.d(TAG, "yPosition = " + yPosition);

        return yPosition;
    }

    private float getBarHeightPx() {
        return mBarHeightPx;
    }

    private float getBarLength() {
        float marginLeft = getMarginLeft();
        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        int width = getWidth();

        return width - (marginLeft * 2) - paddingLeft - paddingRight;
    }

    private float transformValueToPx() {
        float scale = ((mMaxValue - mMinValue)) / getBarLength();

        return (mValue / scale) + getMarginLeft();
    }

    /**
     * Get the pin color by value
     * @return
     */
    private int getPinColorByValue() {
        int color = 0;

        if (mIsMultipleLevelColors) {
            int segmentNumber = mBarLevelColors.length;
            int base = (mMaxValue - mMinValue) / segmentNumber;
            int segment = mValue / base;

            if (segment > segmentNumber - 1) {
                segment = segmentNumber - 1;
            }

            color = mBarLevelColors[segment];
        }

        return color;
    }

}
