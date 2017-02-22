package com.tang.licaidemo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class GuideViewPagerAdapter extends PagerAdapter {
	private List<ImageView> imageViews;

	public GuideViewPagerAdapter(List<ImageView> imageViews) {
		// TODO Auto-generated constructor stub
		this.imageViews = imageViews;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageViews.size();
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(imageViews.get(position), 0);
		return imageViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeView(imageViews.get(position));
	}

}
