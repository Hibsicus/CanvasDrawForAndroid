package com.example.user.graffity.CustomWidget.CustomWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.icu.util.MeasureUnit;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 2017/6/8.
 */

public class CanvasDraw extends View {

    private static final int MIN_MOVE_DIS = 5;

    private float preX, preY, dx, dy;

    private int NavigationHeight;
    private int ActionBarHeight;

    Paint mPaint;
    Canvas mCanvas;
    Bitmap mBitmap;
    Path mPath;



    public CanvasDraw(Context context) {
        super(context);
    }

    public CanvasDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        ActionBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
//        NavigationHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("navigation_bar_height", "dimen", "android"));

        mBitmap =  mBitmap.createBitmap(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.BLACK);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(6);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                dx = Math.abs(x - preX);
                dy = Math.abs(y - preY);
                if(dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS)
                {
                    mPath.quadTo(preX, preY, (x + preX) / 2.0f , (y + preY) / 2.0f);
                    preX = x;
                    preY = y;
                }
                break;
        }
        invalidate();
        return true;
    }
}
