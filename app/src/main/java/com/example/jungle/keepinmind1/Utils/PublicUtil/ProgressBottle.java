package com.example.jungle.keepinmind1.Utils.PublicUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by jungle on 2018/1/29.
 */

public class ProgressBottle extends View {
    private double total = 100;
    private double used = 0;
    private Paint mPaint;
    private int height;
    private int width;
    private double current = 0;
    private RectF r1;
    private RectF r2;
    private double step = used / 50 + 0.5;

    public ProgressBottle(Context context) {
        super(context);
    }

    public ProgressBottle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        r1 = new RectF();
        r2 = new RectF();
    }

    public ProgressBottle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 实现各种绘制功能
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.rgb(171,221,183));
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(100);
        r1.left = 0;
        r1.right = width;
        r1.bottom = height;
        r1.top = 0;
        canvas.drawRoundRect(r1, 10, 10, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.GRAY);
        mPaint.setAlpha(100);
        canvas.drawRoundRect(r1, 10, 10, mPaint);
        change(canvas, used, total);

    }


    public void change(final Canvas canvas, final double target, final double total) {
        double percent = current / total;
        int green = 222 - (int) (122 * percent);
        int red = 100+(int) (155 * percent);
        mPaint.setARGB(120, red, green, 100);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        r2.left = 1;
        r2.right =width-1;
        r2.bottom = height;
        r2.top = (float) (current / total * height);
        if (current < target || current == 0) {
            current = current + step;
        }
        canvas.drawRoundRect(r2, 10, 10, mPaint);
        invalidate();

    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        this.current=0;
        invalidate();
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
        this.current=0;
        invalidate();
    }
    public void set(double i ,double k){
        this.used = i;
        this.total=k;
        this.current=0;
        step = used / 50 + 0.5;
        invalidate();
    }
}
