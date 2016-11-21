package com.skynetprojectapp.android.skynetprojectapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;



/**
 * This custom class allows to customize and create the view that shows the details of the reservation.
 * Created by Bruce
 */
public class Reservation extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private String roomNumber,day,hours, location, presentation;

    private boolean presTool= false;
    private int mExampleColor = Color.WHITE; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private Paint mPaint;
    private float mTextWidth;
    private float mTextHeight;


    public Reservation(Context context) {
        super(context);
        init(null, 0);
    }

    public Reservation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Reservation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Reservation, defStyle, 0);

        //get the default values from the atttibute file
        mExampleString = a.getString(R.styleable.Reservation_exampleString);
        roomNumber = a.getString(R.styleable.Reservation_room);
        day = a.getString(R.styleable.Reservation_day);
        hours = a.getString(R.styleable.Reservation_hours);
        location = a.getString(R.styleable.Reservation_location);
        presentation = a.getString(R.styleable.Reservation_presentation);

//        mExampleColor = a.getColor(
//                R.styleable.Reservation_exampleColor,
//                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.Reservation_exampleDimension,
                mExampleDimension);
        a.recycle();

        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(Color.WHITE);

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(getResources().getColor(R.color.colorPrimary));
//        mTextPaint.setColor(Color.WHITE);

        mTextPaint.setTextSize(10);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

//
        int adjust=20;
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawRoundRect(adjust,paddingTop+ adjust,getWidth()-paddingRight-adjust,getHeight()-paddingBottom-adjust,15,15,mPaint);

        mTextPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));

        mTextPaint.setTextSize(50);

        // Draw the text.
        int textPadding= 50;

        canvas.drawText(roomNumber,paddingLeft,textPadding + paddingTop + (contentHeight + mTextHeight) / 10, mTextPaint);

        canvas.drawText(day, paddingLeft, textPadding + paddingTop + 7*(contentHeight + mTextHeight) / 10, mTextPaint);

        canvas.drawText(hours,paddingLeft + (contentWidth /3), textPadding + paddingTop + 7*(contentHeight + mTextHeight) / 10, mTextPaint);

        canvas.drawText(location, paddingLeft, textPadding + paddingTop + 4*(contentHeight + mTextHeight) / 10, mTextPaint);

        canvas.drawText(presentation,paddingLeft, textPadding + paddingTop + 10*(contentHeight + mTextHeight) / 10, mTextPaint);


        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }


    public boolean isPresTool() {
        return presTool;
    }

    public void setPresTool(boolean presTool) {
        this.presTool = presTool;
        invalidateTextPaintAndMeasurements();

    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        invalidateTextPaintAndMeasurements();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        invalidateTextPaintAndMeasurements();

    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
        invalidateTextPaintAndMeasurements();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        invalidateTextPaintAndMeasurements();
    }
}
