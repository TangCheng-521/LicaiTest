package com.tang.licaidemo;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by tang on 2016/6/24.
 */
public class AlphaPageTransform implements ViewPager.PageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.2f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
//        if (position < -1) {
//            page.setAlpha(mMinAlpha);
//        } else if (position <= 1) { // [-1,1]
//
//            if (position < 0) //[0，-1]
//            {
//                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
//                page.setAlpha(factor);
//            } else//[1，0]
//            {
//                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
//                page.setAlpha(factor);
//            }
//        } else { // (1,+Infinity]
//            page.setAlpha(mMinAlpha);
//        }

//        int pageWidth = page.getWidth();
//
//        if (position < -1) { // [-Infinity,-1)
//            // This page is way off-screen to the left.
//            page.setAlpha(0);
//
//        } else if (position <= 0) { // [-1,0]
//            // Use the default slide transition when moving to the left page
//            page.setAlpha(1);
//            page.setTranslationX(0);
//            page.setScaleX(1);
//            page.setScaleY(1);
//
//        } else if (position <= 1) { // (0,1]
//            // Fade the page out.
//            page.setAlpha(1 - position);
//
//            // Counteract the default slide transition
//            page.setTranslationX(pageWidth * -position);
//
//            // Scale the page down (between MIN_SCALE and 1)
//            float scaleFactor = MIN_SCALE
//                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
//
//        } else { // (1,+Infinity]
//            // This page is way off-screen to the right.
//            page.setAlpha(0);
//        }

        if(position==0){
//            page.bringToFront();
            Log.i("tag",page+"position="+position);
        }
    }
}
