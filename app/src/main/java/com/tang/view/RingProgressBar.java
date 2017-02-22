package com.tang.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.tang.licaidemo.R;

/**
 * Created by tang on 2016/7/25.
 */
public class RingProgressBar extends TextView {
    public static final String TAG="RingProgressBar";
    public static final int STROKE=0;
    public static final int FILL=1;

    private int ringColor;
    private int ringProgressColor;
    private int max;
    private int progress;
    private int style;

    private Paint ringPaint;
    private Paint proPaint;

    private float cx;
    private float cy;
    private float radiums;
    private float ringWidth;

    private boolean isOpenAnim =false;
    private ValueAnimator animator;
    private long animTime=1800;
    private int animProgress=0;

    public RingProgressBar(Context context) {
        super(context);
    }

    public RingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RingProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RingProgressBar);
        ringColor = ta.getColor(R.styleable.RingProgressBar_ringColor_RPB, Color.parseColor("#f6f6f7"));
        ringProgressColor = ta.getColor(R.styleable.RingProgressBar_ringProgressColor_RPB, Color.parseColor("#3cacfa"));
        max=ta.getInteger(R.styleable.RingProgressBar_max_RPB,100);
        progress=ta.getInteger(R.styleable.RingProgressBar_progress_RPB,0);
        isOpenAnim=ta.getBoolean(R.styleable.RingProgressBar_isAnim_RPB,false);
        style=ta.getInt(R.styleable.RingProgressBar_style_RPB,0);

        ta.recycle();

        ringPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        ringWidth=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics());
        ringPaint.setStrokeWidth(ringWidth);
        ringPaint.setColor(ringColor);
        ringPaint.setStyle(Paint.Style.STROKE);

        proPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        proPaint.setStrokeWidth(ringWidth);
        proPaint.setColor(ringProgressColor);
        if(style==STROKE){
            proPaint.setStyle(Paint.Style.STROKE);
        }else if(style==FILL){
            proPaint.setStyle(Paint.Style.FILL);
        }

        initAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG,"onDraw---"+canvas);
        super.onDraw(canvas);

        cx =getWidth()/2.0f;
        cy =getHeight()/2.0f;
        radiums=(cx < cy ? cx : cy -ringWidth/2);

        canvas.drawCircle(cx, cy,radiums,ringPaint);

        RectF oval=new RectF();
        oval.left=cx-radiums;
        oval.right=cx+radiums;
        oval.top=cy-radiums;
        oval.bottom=cy+radiums;
        if(isOpenAnim){
            if(animator!=null&&!animator.isStarted()&&animProgress==0&&animProgress!=progress){
                animator.start();
            }else{
                canvas.drawArc(oval,-90,((float)animProgress/max)*360,false,proPaint);
            }
        }else{
            canvas.drawArc(oval,-90,((float)progress/max)*360,false,proPaint);
        }
    }

    private void initAnim(){
        animator =ValueAnimator.ofInt(animProgress,progress);
        long duration= (long) (((float)progress/max)*animTime);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animProgress= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public void setProgress(int progress){
        this.progress=progress;
        animProgress=0;
        initAnim();
        invalidate();
    }

    public int getPercent(){
        int percent= (int) (((float)progress/max)*100);
        return percent;
    }
}
