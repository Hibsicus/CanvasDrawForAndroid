package com.example.user.graffity.CustomWidget.CustomWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.icu.util.MeasureUnit;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.graffity.R;

/**
 * Created by User on 2017/6/8.
 */

public class CanvasDraw extends View implements Runnable{

    public static final int PAINT_COLOR_PAINT = 0;
    public static final int PAINT_ERASER_PAINT = 1;

    private static final String TAG = "CanvasDraw";



    private static final int MIN_MOVE_DIS = 5;
    private static final int CHANGE_PAINT_RADIU_TIME = 1;

    private float preX, preY, dx, dy;
    private float x, y;

    private int NavigationHeight;
    private int ActionBarHeight;

    Paint CurrentPaint;
    Paint mPaint;
    Paint EraserPaint;

    Canvas mCanvas;
    Bitmap mBitmap;
    Path mPath;

    int PaintRadiu;
    int PaintColor;
    Paint SmallRadiuPaint;

    boolean isTouch;
    boolean canChangePaintRadiu;

    int touchSeconds;

    private Bitmap mShaderBitmap;
    private Point mBitmapBrushDimensions;

    public CanvasDraw(Context context) {
        super(context);
    }

    public CanvasDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
      //  setLayerType(LAYER_TYPE_SOFTWARE, null);
//        ActionBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
//        NavigationHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("navigation_bar_height", "dimen", "android"));

        isTouch = false;
        canChangePaintRadiu = false;
        PaintRadiu = 16;
        PaintColor = Color.YELLOW;
        touchSeconds = 0;

        //前景圖 透明(用這張上傳)
        mBitmap =  mBitmap.createBitmap(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.TRANSPARENT);

        mShaderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.light_brush02);
        mBitmapBrushDimensions = new Point(mShaderBitmap.getWidth(), mShaderBitmap.getHeight());

        //畫筆
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(PaintRadiu);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0X00FFFF00));
       // mPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

        //橡皮擦
        EraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        EraserPaint.setColor(Color.TRANSPARENT);
        EraserPaint.setStrokeWidth(PaintRadiu * 5);
        EraserPaint.setStrokeJoin(Paint.Join.ROUND);
        EraserPaint.setStrokeCap(Paint.Cap.ROUND);
        EraserPaint.setStyle(Paint.Style.STROKE);
        EraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        //筆刷大小的圓
        SmallRadiuPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        SmallRadiuPaint.setColor(Color.BLACK);
        SmallRadiuPaint.setStrokeWidth(1);
        SmallRadiuPaint.setStrokeJoin(Paint.Join.ROUND);
        SmallRadiuPaint.setStrokeCap(Paint.Cap.ROUND);
        SmallRadiuPaint.setStyle(Paint.Style.STROKE);

        CurrentPaint = mPaint;
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
        if(isTouch)
            canvas.drawCircle(x,y,PaintRadiu,SmallRadiuPaint);
//        if(isTouch && touchSeconds < CHANGE_PAINT_RADIU_TIME)
//            canvas.drawCircle(x,y,PaintRadiu,SmallRadiuPaint);

//        if(canChangePaintRadiu)
//        {
//            canvas.drawCircle(preX,preY,PaintRadiu,SmallRadiuPaint);
//
//        }else
        if(CurrentPaint == mPaint) {
            mCanvas.drawBitmap(mShaderBitmap,x - mBitmapBrushDimensions.x / 2,y - mBitmapBrushDimensions.y / 2, CurrentPaint);
//            for (int i = 2; i < 5; i++) {
//                if (x % 2 == 0)
//                    mCanvas.drawBitmap(mShaderBitmap, (x - i * 10) - mBitmapBrushDimensions.x / i, (y + i * 10) - mBitmapBrushDimensions.y / i, CurrentPaint);
//                else
//                    mCanvas.drawBitmap(mShaderBitmap, (x + i * 10) - mBitmapBrushDimensions.x / i, (y - i * 10) - mBitmapBrushDimensions.y / i, CurrentPaint);
//                if (y % 2 == 0)
//                    mCanvas.drawBitmap(mShaderBitmap, (x - i * 10) - mBitmapBrushDimensions.x / i, (y - i * 10) - mBitmapBrushDimensions.y / i, CurrentPaint);
//                else
//                    mCanvas.drawBitmap(mShaderBitmap, (x + i * 10) - mBitmapBrushDimensions.x / i, (y + i * 10) - mBitmapBrushDimensions.y / i, CurrentPaint);
//            }
        }
        else
            mCanvas.drawPath(mPath, CurrentPaint);
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
                canChangePaintRadiu = true;
                break;
            case MotionEvent.ACTION_MOVE:
                dx = Math.abs(x - preX);
                dy = Math.abs(y - preY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
//                    if(touchSeconds < CHANGE_PAINT_RADIU_TIME)
//                        canChangePaintRadiu = false;
//
//                    if(canChangePaintRadiu)
//                    {
//                        PaintRadiu = getLength(preX, preY, x, y);
//                        mPath.reset();
//                    }else
//                    {
                        mPath.quadTo(preX, preY, (x + preX) / 2.0f, (y + preY) / 2.0f);
                        
                        preX = x;
                        preY = y;
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mPath.reset();
                isTouch = false;
                canChangePaintRadiu = false;
                touchSeconds = 0;
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
                if (canChangePaintRadiu)
                    touchSeconds++;
                else
                    touchSeconds = 0;

//                CurrentPaint.setStrokeWidth(PaintRadiu);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetPaintColor(int color)
    {
        PaintColor = color;
        mPaint.setColor(color);
        mPath.reset();
    }

    public int GetPaintColor()
    {
        return PaintColor;
    }

    public void ChangePaint(int state)
    {
        switch (state)
        {
            case PAINT_COLOR_PAINT:
                CurrentPaint = mPaint;
                break;
            case PAINT_ERASER_PAINT:
                CurrentPaint = EraserPaint;
                break;
            default:
                break;
        }
        mPath.reset();
    }
}
