package com.tang.licaidemo;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tang.view.RingProgressBar;

public class ColorMatrixActivity extends AppCompatActivity {
    private ImageView iv;
    private TextView tv_progress;
    private RingProgressBar ringProgress;
    private int percent=0;

    class MyColorMatrix{
        private float r1,r2,r3,r4,r5;
        private float g1,g2,g3,g4,g5;
        private float b1,b2,b3,b4,b5;
        private float a1,a2,a3,a4,a5;

        private float[] rgba={r1,r2,r3,r4,r5
        ,g1,g2,g3,g4,g5
        ,b1,b2,b3,b4,b5
        ,a1,a2,a3,a4,a5};


    }

    private ValueAnimator animFilter;

    public class MatrixEvaluator implements TypeEvaluator{

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            float[] start=(float[])startValue;
            float[] end=(float[])endValue;
            float[] result=new float[start.length];
            for(int i=0;i<start.length;i++){
                result[i]=start[i]+fraction*(end[i]-start[i]);
            }
            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        iv= (ImageView) findViewById(R.id.iv);
        animFilter=initFilterAnim(iv);
        animFilter.start();

        ringProgress= (RingProgressBar) findViewById(R.id.ringProgress);
        tv_progress= (TextView) findViewById(R.id.tv_progress);
        setPregressText();

    }

    private void setPregressText(){
        String percent=ringProgress.getPercent()+"%";
        //金额斜体加粗
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(percent);
//        spanBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, percent.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体
        spanBuilder.setSpan(new RelativeSizeSpan(1.5f), 0, percent.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体放大1.5倍
        ringProgress.setText(spanBuilder);
    }

    /**
     * 初始化滤镜动画
     */
    public ValueAnimator initFilterAnim(final ImageView iv){
        //起始矩阵，设置后滤镜效果是灰色
        float[] grayMatrix={
                0.3f, 0.59f, 0.11f, 0, 0,
                0.3f, 0.59f, 0.11f, 0, 0,
                0.3f, 0.59f, 0.11f, 0, 0,
                0, 0, 0, 1, 0 };
        //终点矩阵，设置后颜色不变
        float[] originMatrix={1,0,0,0,0
                ,0,1,0,0,0
                ,0,0,1,0,0
                ,0,0,0,1,0};

        //设置起始滤镜
        final ColorMatrix clrMtx =new ColorMatrix();
        clrMtx.set(grayMatrix);
        iv.setColorFilter(new ColorMatrixColorFilter(clrMtx));

        //矩阵属性动画
        ValueAnimator animFilter =ValueAnimator.ofObject(new MatrixEvaluator(),grayMatrix,originMatrix);
        animFilter.setDuration(5000);
        animFilter.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] value= (float[]) animation.getAnimatedValue();
                clrMtx.set(value);
                iv.setColorFilter(new ColorMatrixColorFilter(clrMtx));
            }
        });
        return animFilter;
    }
    public void doClick(View view){
        switch (view.getId()){
            case R.id.btn_change:
                animFilter.start();
                break;
            case R.id.rl_progress:
                percent+=10;
                if(percent>100){
                    percent=0;
                }
                ringProgress.setProgress(percent);
                setPregressText();
                break;
        }
    }
}
