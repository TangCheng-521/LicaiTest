package com.tang.licaidemo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tang.view.MainPrdViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.viewpager)
    MainPrdViewPager viewpager;
    @BindView(R.id.rl)
    RelativeLayout viewPagerContainer;
    private List<View> views = new ArrayList<View>();
    private List<ImageView> imgList = new ArrayList<>();
    private int[] imgRes = new int[]{R.drawable.ic_test_0, R.drawable.ic_test_1, R.drawable.ic_test_2, R.drawable.ic_test_3,
            R.drawable.ic_test_4, R.drawable.ic_test_5, R.drawable.ic_test_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        for (int i = 0; i < imgRes.length; i++) {
            View item = View.inflate(this, R.layout.item_viewpager, null);
            TextView tv_Value= (TextView) item.findViewById(R.id.tv_value);
            SpannableString msp = new SpannableString(tv_Value.getText());
            msp.setSpan(new RelativeSizeSpan(2.5f), 0, msp.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_Value.setText(msp);
            tv_Value.setTag(i);
            tv_Value.setOnClickListener(this);
            views.add(item);
        }
        viewpager.setOffscreenPageLimit(imgRes.length - 1);
        viewpager.setPageMargin(-420);
        viewpager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Log.i("tag", position + "");
                float fric = Math.abs(position);
                float scaleFric = 0.6f * fric;
                float alphaFric = 0.9f * fric;
                page.setScaleY(1 - scaleFric);
                page.setScaleX(1 - scaleFric);
                page.setAlpha(1 - alphaFric);
            }
        });
        viewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewpager.dispatchTouchEvent(event);
            }
        });
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        });
    }

    @OnClick({R.id.viewpager,R.id.rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewpager:
                break;
            case R.id.rl:
                break;
            case R.id.tv_value:
                int index= (int) view.getTag();
                TextView tv= (TextView) view;
                Toast.makeText(this,index+","+tv.getText().toString(),Toast.LENGTH_LONG).show();
                break;
        }
    }
}
