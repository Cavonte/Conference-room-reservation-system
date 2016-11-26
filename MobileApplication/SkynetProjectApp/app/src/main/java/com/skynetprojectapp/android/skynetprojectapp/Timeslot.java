package com.skynetprojectapp.android.skynetprojectapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


/**
 * This custom class defines the custom view that will be shown in the scheduler.
 * Created by Bruce
 */
public class Timeslot extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private String status;

    private int passed = Color.BLACK;
    private int avail = Color.GREEN;
    private int taken = Color.RED;

    private String index="";

    private int mExampleColor = Color.WHITE; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private Paint mPaint;
    private float mTextWidth;
    private float mTextHeight;
    private String timeSlotText="";


    public Timeslot(Context context) {
        super(context);
        init(null, 0);
    }

    public Timeslot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Timeslot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Reservation, defStyle, 0);

        //get the default values from the atttibute file
        mExampleString = a.getString(R.styleable.Reservation_exampleString);
        status = a.getString(R.styleable.Timeslot_status);


        avail = a.getColor(R.styleable.Reservation_exampleColor, avail);
        taken = a.getColor(R.styleable.Reservation_exampleColor, taken);
        passed = a.getColor(R.styleable.Reservation_exampleColor, passed);
        mExampleColor = a.getColor(R.styleable.Reservation_exampleColor, mExampleColor);

        mExampleDimension = a.getDimension(R.styleable.Reservation_exampleDimension, mExampleDimension);
        a.recycle();

        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(Color.WHITE);

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        //mTextPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mTextPaint.setColor(Color.GRAY);

        mTextPaint.setTextSize(5);


        //passed = getResources().getColor(R.color.colorPrimary);
        passed = ContextCompat.getColor(getContext(), R.color.colorPrimary);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
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

        int adjust = 20;
        canvas.drawColor(Color.WHITE);
        //mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setColor(passed);
        canvas.drawRoundRect(adjust, paddingTop + adjust, getWidth() - paddingRight - adjust, getHeight() - paddingBottom - adjust, 15, 15, mPaint);

        mTextPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        //mTextPaint.setColor(Color.GRAY);

        mTextPaint.setTextSize(25);



        //canvas.drawText(timeSlotText,(contentWidth)/2 - mTextWidth/2, contentHeight/2+mTextHeight, mTextPaint);
        canvas.drawText(timeSlotText,(contentWidth)/2-mTextWidth/4, contentHeight/2, mTextPaint);


        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getExampleString() {
        return mExampleString;
    }

    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }


    public int getExampleColor() {
        return mExampleColor;
    }


    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    public void setTimeSlotText(String t){
        timeSlotText = t;
    }

    public float getExampleDimension() {
        return mExampleDimension;
    }


    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }


    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }


    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public int getTaken() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }
}

