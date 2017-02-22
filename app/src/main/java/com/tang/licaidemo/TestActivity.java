package com.tang.licaidemo;

import android.graphics.drawable.ClipDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    String s="\"a\",\"c\",\"d\"";
    private static final int a = 250;
    private ProgressBar pb1;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        pb1= (ProgressBar) findViewById(R.id.progressBar);
        iv= (ImageView) findViewById(R.id.iv);
        iv.getDrawable().setLevel(40*100);
        Log.i("tag",(int)iv.getTag()+"");
        final ViewTreeObserver vto=pb1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pb1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp=  pb1.getLayoutParams();
                lp.height=14;
                pb1.setLayoutParams(lp);
            }
        });


                String a[]=s.split(",");
        for(int i=0;i<a.length;i++){
            Log.i("tag",a[i]);
        }
        ArrayList<String> k=new ArrayList<>();
        ArrayList<String> b=new ArrayList<>();
        k.addAll(b);
        Log.i("tag",'#'+"abc");
    }
}
