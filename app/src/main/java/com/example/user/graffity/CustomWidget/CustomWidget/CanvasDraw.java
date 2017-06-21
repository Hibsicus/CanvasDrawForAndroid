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
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 2017/6/8.
 */

public class CanvasDraw extends View implements Runnable{

    private static final int MIN_MOVE_DIS = 5;

    private float preX, preY, dx, dy;
    private float x, y;

    private int NavigationHeight;
    private int ActionBarHeight;

    Paint mPaint;

    Canvas mCanvas;
    Bitmap mBitmap;
    Path mPath;

    int PaintRadiu;
    Paint SmallRadiuPaint;

    boolean isTouch;

    int touchSeconds;

    public CanvasDraw(Context context) {
        super(context);
    }

    public CanvasDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        ActionBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
//        NavigationHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("navigation_bar_height", "dimen", "android"));

        isTouch = false;
        PaintRadiu = 6;
        touchSeconds = 0;

        mBitmap =  mBitmap.createBitmap(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(PaintRadiu);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        SmallRadiuPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        SmallRadiuPaint.setColor(Color.BLACK);
        SmallRadiuPaint.setStrokeWidth(1);
        SmallRadiuPaint.setStrokeJoin(Paint.Join.ROUND);
        SmallRadiuPaint.setStrokeCap(Paint.Cap.ROUND);
        SmallRadiuPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
        if(isTouch && touchSeconds < 2)
            canvas.drawCircle(x,y,PaintRadiu,SmallRadiuPaint);
        else if(isTouch && touchSeconds > 2)
        {
            canvas.drawCircle(preX,preY,PaintRadiu,SmallRadiuPaint);
        }
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                preX = x;
                preY = y;
                isTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchSeconds < 2) {
                    dx = Math.abs(x - preX);
                    dy = Math.abs(y - preY);
                    if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                        mPath.quadTo(preX, preY, (x + preX) / 2.0f, (y + preY) / 2.0f);
                        preX = x;
                        preY = y;
                    }
                }else{
                    PaintRadiu = getLength(preX, preY, x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }
        invalidate();
        return true;
    }

    private int getLength(float x1, float y1, float x2, float y2)
    {
        return (int)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (isTouch)
                    touchSeconds++;
                else
                    touchSeconds = 0;

                mPaint.setStrokeWidth(PaintRadiu);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
