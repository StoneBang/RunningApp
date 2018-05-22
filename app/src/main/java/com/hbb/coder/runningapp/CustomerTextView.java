package com.hbb.coder.runningapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/5/17.
 */

public class CustomerTextView extends AppCompatTextView {

    private Paint mBorderPaint;

    public CustomerTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CustomerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CustomerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        if (null == mBorderPaint) {
            mBorderPaint = new Paint();
        }
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mBorderPaint.setStrokeWidth(1);
        mBorderPaint.setAntiAlias(true);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int paddingLeft = getCompoundPaddingLeft();
        int paddingTop = getCompoundPaddingTop();
        int paddingRight = getCompoundPaddingRight();
        int paddingBottom = getCompoundPaddingBottom();

        int left = paddingLeft -10;
        int top = paddingTop -10;
        int right = measuredWidth - paddingRight + 10;
        int bottom = measuredHeight - paddingBottom+ 10;


        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, 6, 6, mBorderPaint);
        super.onDraw(canvas);
        measure(0,0);
    }
}
