package com.tang.licaidemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;

import static com.tang.licaidemo.R.id.viewpager;

public class MainActivity extends AppCompatActivity {
    private  int[] imgRes= {R.mipmap.ic_test_0,R.mipmap.ic_test_1,R.mipmap.ic_test_2,R.mipmap.ic_test_3,R.mipmap.ic_test_4,R.mipmap.ic_test_5,R.mipmap.ic_test_6};
    private  ViewPager id_viewpager;
    private Button btn;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private String mMobile="10086";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_viewpager=(ViewPager)findViewById(R.id.id_viewpager);
//        id_viewpager.setPageMargin(-400);
        id_viewpager.setOffscreenPageLimit(3);
        id_viewpager.setPageTransformer(true,new AlphaPageTransform());
        id_viewpager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float friction = Math.abs(position);
                float scaleFric = 0.6f * friction;
                float alphaFric = 0.95f * friction;
                page.setScaleX(1-scaleFric);
                page.setScaleY(1-scaleFric);
                page.setAlpha(1-alphaFric);

            }
        });
        id_viewpager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView iv=new ImageView(MainActivity.this);
                iv.setImageResource(imgRes[position]);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setAdjustViewBounds(true);
                container.addView(iv);
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);

            }

            @Override
            public int getCount() {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });

        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall(mMobile);
            }
        });

    }

    private void callDirectly(String mobile){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        this.startActivity(intent);
    }

    public void onCall(String mobile){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }else{
                //上面已经写好的拨号方法
                callDirectly(mobile);
            }
        } else {
            //上面已经写好的拨号方法
            callDirectly(mobile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    callDirectly(mMobile);
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "CALL_PHONE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}
