package com.example.user.graffity.CustomWidget.CustomWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.graffity.R;

/**
 * Created by PIPOLE_VR19 on 2017/6/20.
 */

public class ColorPicker extends View {
    private Context context;
    private int BigCircle; //外圈半徑
    private int CenterRadius; //小圓半徑
    private int CenterColor; //小圓顏色
    private Bitmap BackgroundBitmap; //背景圖片
    private Paint mPaint; //背景畫筆
    private Paint CenterPaint; //小圓畫筆

    private Point CenterPoint; //中心
    private Point SelectPosition; //小圓位置
    private OnColorChangedListener listener; //監聽事件
    private int length; //小圓到中心的距離

    public void setOnColorChangedListener(OnColorChangedListener listener){
        this.listener = listener;
    }

    public ColorPicker(Context context) {
        super(context);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //獲取自定義屬性
        TypedArray types = context.obtainStyledAttributes(attrs,
                R.styleable.ColorPicker);
        try{
            BigCircle = types.getDimensionPixelOffset(R.styleable.ColorPicker_circle_radius, 100);
            CenterRadius = types.getDimensionPixelOffset(R.styleable.ColorPicker_center_radius, 10);
            CenterColor = types.getColor(R.styleable.ColorPicker_center_color, Color.WHITE);
        }finally {
            types.recycle();
        }
        //設置背景大小
        BackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.piccolor);
        BackgroundBitmap = Bitmap.createScaledBitmap(BackgroundBitmap, BigCircle * 2, BigCircle * 2, false);

        //中心位置
        CenterPoint = new Point(BigCircle, BigCircle);
        SelectPosition = new Point(CenterPoint);

        //初始化背景畫筆跟小球
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        CenterPaint = new Paint();
        CenterPaint.setColor(CenterColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //畫背景圖
        canvas.drawBitmap(BackgroundBitmap, 0, 0 , mPaint);

        //畫小球
        canvas.drawCircle(SelectPosition.x, SelectPosition.y, CenterRadius, CenterPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN://按下
                length = getLength(event.getX(), event.getY(), CenterPoint.x, CenterPoint.y);
                if(length > BigCircle - CenterRadius)
                    return true;
                break;
            case MotionEvent.ACTION_MOVE: //移動
                length = getLength(event.getX(), event.getY(), CenterPoint.x, CenterPoint.y);
                if(length <= BigCircle - CenterRadius){
                    SelectPosition.set((int)event.getX(), (int)event.getY());
                }else{
                    SelectPosition = getBorderPoint(CenterPoint, new Point((int)event.getX(), (int)event.getY()), BigCircle - CenterRadius);
                }
                listener.onColorChanged(BackgroundBitmap.getPixel(SelectPosition.x, SelectPosition.y));
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //大小設成直徑
        setMeasuredDimension(BigCircle * 2, BigCircle * 2);
    }

    //計算距離
    public int getLength(float x1, float y1, float x2, float y2){
        return (int)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    //如果觸摸超過小圓範圍時，設置小圓邊緣
    public Point getBorderPoint(Point a, Point b, int cutRadius)
    {
       float radian = getRadian(a,b);
        return new Point(a.x + (int)(cutRadius * Math.cos(radian)), a.x + (int)(cutRadius * Math.sin(radian)));
    }

    //觸碰點跟中心的夾角
    public float getRadian(Point a, Point b)
    {
        float lenA = b.x - a.x;
        float lenB = b.y - a.y;
        float lenC = (float)Math.sqrt(Math.pow(lenA, 2) + Math.pow(lenB, 2));
        float ang = (float)Math.acos(lenA / lenC);
        ang = ang * (b.y < a.y ? -1 : 1);
        return ang;
    }

    public interface OnColorChangedListener{
        void onColorChanged(int color);
    }
}
