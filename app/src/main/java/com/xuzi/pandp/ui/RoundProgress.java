package com.xuzi.pandp.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.xuzi.pandp.R;

/**
 * Created by xuzi on 8/2/2016.
 */
public class RoundProgress extends View {
    private Paint paint = new Paint();
    private int roundColor;
    private int textColor;
    private float textSize;
    private float max;
    private int roundProgressColor;
    private float roundWidth;
    private int progress = 50;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.RED);
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, 15f);
        max = typedArray.getDimension(R.styleable.RoundProgress_max, 100f);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, 5f);
        typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //1、绘制最外层的圆
        paint.setColor(roundColor);
        //设置画笔粗细
        paint.setStrokeWidth(roundWidth);
        //设置为空心样式
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        int center = getWidth() / 2;
        int radius = (int) (center - roundWidth / 2);
        canvas.drawCircle(center, center, radius, paint);

        //2、绘制中间的文本

        float textWidth = paint.measureText(progress + "%");
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        float sx = center - textWidth / 2;
        float sy = center + textSize / 2;
        paint.setStrokeWidth(0);
        canvas.drawText(progress + "%", sx, sy, paint);

        //3、绘制弧形
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(roundWidth);
        canvas.drawArc(oval, 0, 360 * progress / max, false, paint);
    }

    //外部调用重绘
    public void setProgress(int progress) {
        this.progress = progress;
        if(progress>100){
            this.progress = 100;
        }
        //postInvalidate线程安全
        postInvalidate();
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }
}
