package com.tang.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by kingj on 2016/7/13.
 */
public class MainPrdViewPager extends ViewPager {
    public MainPrdViewPager(Context context) {
        super(context);
    }

    public MainPrdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        return super.getChildDrawingOrder(childCount,i);
//        Log.i("MainPrdViewPager",childCount+","+i+","+getCurrentItem());
        int result = 0;
        if(i==getCurrentItem()){
            result=childCount-1;
        }else{
            if(i==childCount-1){
                result = getCurrentItem();
            }else{
                result=i;
            }
        }
//        setChildrenDrawingCacheEnabled(true);
//        Log.d("k", childCount + "," + i + "," + getCurrentItem());
        //在后面滑
//        if (getCurrentItem() >= getOffscreenPageLimit() + 1) {
        //滑到最右边
//        if (getCurrentItem() == getAdapter().getCount() - 1) {
//            result = i;
//        }
//        //滑到第一条
//        else if (getCurrentItem()==0) {
//            if (i == 0) {
//                result = childCount - 1;
//            } else {
//               result=i-1;
//            }
//        }
//        //前部分
//        else if (getCurrentItem() < getOffscreenPageLimit()) {
//            if (i == childCount - getOffscreenPageLimit() - 1) {
//                result = childCount - 1;
//            } else {
//                if (i >childCount - getOffscreenPageLimit() - 1) {
//                    result = i-1;
//                } else {
//                    result = i;
//                }
//            }
//
//        }
//        //后部分
//        else if (getAdapter().getCount() - 1 - getCurrentItem() < getOffscreenPageLimit()) {
//            if (i == getOffscreenPageLimit()) {
//                result = childCount - 1;
//            } else {
//                if (i >getOffscreenPageLimit()) {
//                    result = i-1;
//                } else {
//                    result = i;
//                }
//            }
//
//        }
//        //在中间滑动
//        else {
//            if (i == getOffscreenPageLimit()) {
//                result = childCount - 1;
//            } else {
//                if (i >getOffscreenPageLimit()) {
//                    result = i-1;
//                } else {
//                    result = i;
//                }
//            }
//
//        }
//        }
        //在前面划
//        else {
//            if (i == getCurrentItem()) {
//                result = childCount - 1;
//            } else {
//                if (i == childCount - 1) {
//                    result = getCurrentItem();
//                } else {
//                    result = i;
//                }
//            }
//        }
//        switch (getCurrentItem()) {
//            case 0:
//                switch (i) {
//                    case 0:
//                        result = 2;
//                        break;
//                    case 1:
//                        result = 1;
//                        break;
//                    case 2:
//                        result = 0;
//                        break;
//                }
//                break;
//            case 1:
//                switch (i) {
//                    case 0:
//                        result = 0;
//                        break;
//                    case 1:
//                        result = 2;
//                        break;
//                    case 2:
//                        result = 1;
//                        break;
//                }
//                break;
//            case 2:
//                switch (i) {
//                    case 0:
//                        result = 0;
//                        break;
//                    case 1:
//                        result = 1;
//                        break;
//                    case 2:
//                        result = 2;
//                        break;
//                }
//                break;
//        }
        return result;
    }
}
