package com.example.customprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

public class CircleProgressBarCustom extends View {

    //Normal dot radius
    private int dotRadius = 12;

    //to get identified in which position dot has to expand its radius
    //   private int dotPosition = 9;

    private int[] colors = {ContextCompat.getColor(getContext(), R.color.start),
            ContextCompat.getColor(getContext(), R.color.centre),
            ContextCompat.getColor(getContext(), R.color.end)};


    private int progress;

    public CircleProgressBarCustom(Context context) {
        super(context);
    }

    public CircleProgressBarCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig(context, attrs);
    }

    public CircleProgressBarCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //Animation called when attaching to the window, i.e to your screen
    }

    private void initConfig(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBarCustom);

        try {
            this.progress = a.getInteger(R.styleable.CircleProgressBarCustom_progress, 0);
        } finally {
            a.recycle();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //take the point to the center of the screen
        canvas.translate(this.getWidth() >> 1, this.getHeight() >> 1);
        float[] positions = {0.0f, 0.1f, 0.4f};
        Paint progressPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        progressPaint.setShader(new LinearGradient(0f, 0f, getWidth(), 0f,
                colors,
                positions,
                Shader.TileMode.CLAMP));
        //call create dot method
        createDotInCircle(canvas, progressPaint);
    }

    private void createDotInCircle(Canvas canvas, Paint progressPaint) {
        //angle for each dot angle = (360/number of dots) i.e  (360/10)
        int angle = 18;

        //specify how many dots you need in a progressbar
        int dotAmount = 20;
        for (int i = 0; i < dotAmount; i++) {

            final double cos = Math.cos((angle * i) * (Math.PI / 180)); // angle should be in radians  i.e formula (angle *(Math.PI / 180))
            final double sin = Math.sin((angle * i) * (Math.PI / 180)); // angle should be in radians  i.e formula (angle *(Math.PI / 180))

            //to get identified in which position dot has to expand its radius
            int dotPosition = (int) Math.round(progress * 0.20);
            //specify the circle radius
            int circleRadius = 120;
            float x = (float) (circleRadius * cos);
            float y = (float) (circleRadius * sin);
            if (i == dotPosition && progress != 100) {
                //Expanded Dot Radius
                int bounceDotRadius = 18;
                canvas.drawCircle(x, y, bounceDotRadius, progressPaint);
            } else {
                if (i > dotPosition) {
                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#E5EDE6"));
                    canvas.drawCircle(x, y, dotRadius, paint);
                } else {
                    canvas.drawCircle(x, y, dotRadius, progressPaint);
                }
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            size = height;
        } else {
            size = width;
        }
        setMeasuredDimension(size, size);
    }

}