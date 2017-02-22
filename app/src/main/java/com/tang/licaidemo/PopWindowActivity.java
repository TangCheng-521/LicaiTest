package com.tang.licaidemo;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tang.view.LoadMoreListView;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PopWindowActivity extends AppCompatActivity {

    private static final String TAG ="PopWindowActivity" ;
    private PopupWindow pop;
    private TextView button;
    private View v_shadow;
    private String [] array=new String[]{"净值类","非净值类","定制类","净值类","非净值类","净值类","非净值类","定制类","净值类","非净值类" };
    private List<String> list=new ArrayList<>();
    private MyAdapter adapter;
    private EditText et;
    private EditText et1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window);
        button = (TextView) findViewById(R.id.button);
        v_shadow = findViewById(R.id.v_shadow);

        String a=null;
        String b=a+"b";
        Log.i("tag",b);
        getUserLifeTimeId1();
//        initPopWindow();
    }

    private void getUserLifeTimeId(){
        String deviceId = "";
        String macAddress = "";
        String androidId = "";
        String serialNum = "";
        String simSerialNum = "";

        //需要READ_PHONE_STATE权限
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        if(checkPermission(this, Manifest.permission.READ_PHONE_STATE)){
            deviceId = tm.getDeviceId();
            simSerialNum = tm.getSimSerialNumber();
        }

        //需要ACCESS_WIFI_STATE权限
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        macAddress = wifi.getConnectionInfo().getMacAddress();

        androidId = android.provider.Settings.Secure.getString(this.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        serialNum = Build.SERIAL;

        String deviceManufacturer = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        String deviceBrand = Build.BRAND;
        String device = Build.DEVICE;

        //==============
        Log.i("Identifier_Device_ID", validate(deviceId));
        Log.i("Identifier_Mac_Address", validate(macAddress));
        Log.i("Identifier_Android_ID", validate(androidId));
        Log.i("Identifier_Serial_Num", validate(serialNum));
        Log.i("Identifier_Sim_SN", validate(simSerialNum));

        Log.i("Identifier_Manufacturer", validate(deviceManufacturer));
        Log.i("Identifier_Model", validate(deviceModel));
        Log.i("Identifier_Brand", validate(deviceBrand));
        Log.i("Identifier_Device", validate(device));

        UUID deviceUserLifetimeUUID = new UUID(validate(androidId).hashCode(), ((long)validate(deviceId).hashCode() << 32) | validate(simSerialNum).hashCode());
        String deviceUserLifetimeId = deviceUserLifetimeUUID.toString();

        String deviceHardwareId = md5(validate(serialNum)  + validate(deviceId) + validate(macAddress));


        Log.e("deviceUserLifetimeId", deviceUserLifetimeId);
        Log.e("deviceHardwareId", deviceHardwareId);
    }

    public String getUserLifeTimeId1(){
        String deviceId = "";
        String macAddress = "";
        String androidId = "";
        String serialNum = "";
        String simSerialNum = "";

        //需要READ_PHONE_STATE权限
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        if(checkPermission(this, Manifest.permission.READ_PHONE_STATE)){
            deviceId = tm.getDeviceId();
            simSerialNum = tm.getSimSerialNumber();
        }

        //需要ACCESS_WIFI_STATE权限
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        macAddress = wifi.getConnectionInfo().getMacAddress();

        androidId = android.provider.Settings.Secure.getString(this.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        serialNum = Build.SERIAL;

        String deviceManufacturer = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        String deviceBrand = Build.BRAND;
        String device = Build.DEVICE;

        if(deviceId==null){
            deviceId="";
        }
        if(simSerialNum==null){
            simSerialNum="";
        }
        if(androidId==null){
            androidId="";
        }
        Log.i("deviceId",deviceId);
        Log.i("simSerialNum",simSerialNum);
        Log.i("androidId",androidId);
        UUID deviceUserLifetimeUUID = new UUID(androidId.hashCode(), ((long)deviceId.hashCode() << 32) | simSerialNum.hashCode());
        String deviceUserLifetimeId = deviceUserLifetimeUUID.toString();
        Log.i("deviceUserLifetimeId",deviceUserLifetimeId);
        return deviceUserLifetimeId;
    }
    //检查权限，READ_PHONE_STATE在API>=23需要用户手动赋予权限
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    // MD5加密，32位小写
    public static String md5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        md5.update(str.getBytes());
        byte[] md5Bytes = md5.digest();
        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    //判空
    private String validate(String value) {
        if(value == null) {
            return "";
        }
        return value;
    }

    private void initPopWindow() {
        Collections.addAll(list,array);
        View contentView;
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        contentView = mLayoutInflater.inflate(R.layout.popwindow_test,
                null);
        pop = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LoadMoreListView listview = (LoadMoreListView) contentView.findViewById(R.id.listview);
        adapter=new MyAdapter();
        listview.setAdapter(adapter);
        listview.setItemChecked(0,true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemClick",position+"");
            }
        });
        listview.setLoadMoreListen(new LoadMoreListView.OnLoadMore() {
            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(list.size()>=60){
                            listview.setNoMore(true);
                        }
                        addDatas();
                        adapter.notifyDataSetChanged();
                        listview.onLoadComplete();
                    }
                },3000);
            }
        });
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                setShadowViewVisiable(v_shadow, false);
            }

        });

        et=(EditText) findViewById(R.id.et);
        et1=(EditText) findViewById(R.id.et1);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "onFocusChange: "+hasFocus+","+v.getId());
                if(et.getText().toString().length()>=6&&hasFocus==false){
                    Log.i(TAG, "request: ");
                }
            }
        });
    }

    private void request(){
        Log.i(TAG, "request: ");
    }
    private void addDatas(){
        Collections.addAll(list,array);
    }

    public void setShadowViewVisiable(final View shadowView, final boolean visiable) {
        float[] alphas;
        if(visiable){
            alphas=new float[]{0f,1f};
        }else{
            alphas=new float[]{1f,0f};
        }
        ValueAnimator anim = ValueAnimator.ofFloat(alphas);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shadowView.setAlpha(value);
                shadowView.invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if(visiable){
                    shadowView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!visiable) {
                    shadowView.setVisibility(View.GONE);
                }
            }
        });
        anim.start();
    }

    public void doClick(View v) {
        setShadowViewVisiable(v_shadow, true);
        pop.showAsDropDown(button);
        test();
    }

    public void test(){
        String j1="{\"List\":[{\"Amt\":\"200\",\"acqRefno\":\"a\",\"currCd\":\"CNY\",\"acctNo\":\"00000000000000084160\",\"postDate\":\"2016-07-30\",\"tranSn\":\"20010729002747697333\",\"billAmt\":\"200\",\"cardNo\":\"6251292802727970\",\"transDate\":\"2016-07-29\",\"Mcc\":\"5542\",\"mercName\":\"中国石油天然气股份有限公司江苏常州石油分\",\"ipmFlag\":\"Y\",\"subaccNo\":\"1\",\"payDate\":\"2016-08-20\",\"approvalCd\":\"\",\"excessLmtInd\":\"N\",\"descTrans\":\"银联POS消费\",\"ageCd\":\"0\"},{\"Amt\":\"-40\",\"acqRefno\":\"a\",\"currCd\":\"CNY\",\"acctNo\":\"00000000000000084160\",\"postDate\":\"2016-08-10\",\"tranSn\":\"10010810083433535609\",\"billAmt\":\"-40\",\"cardNo\":\"6251292802727970\",\"transDate\":\"2016-08-10\",\"Mcc\":\"0\",\"mercName\":\"\",\"ipmFlag\":\"N\",\"subaccNo\":\"91\",\"payDate\":\"2016-08-20\",\"approvalCd\":\"\",\"excessLmtInd\":\"N\",\"descTrans\":\"本行其它转账还款\",\"ageCd\":\"0\"},{\"Amt\":\"200\",\"acqRefno\":\"a\",\"currCd\":\"CNY\",\"acctNo\":\"00000000000000084160\",\"postDate\":\"2016-08-11\",\"tranSn\":\"20010810183434002083\",\"billAmt\":\"200\",\"cardNo\":\"6251292802727970\",\"transDate\":\"2016-08-10\",\"Mcc\":\"5542\",\"mercName\":\"中国石油天然气股份有限公司江苏常州石油分\",\"ipmFlag\":\"Y\",\"subaccNo\":\"1\",\"payDate\":\"2016-08-20\",\"approvalCd\":\"\",\"excessLmtInd\":\"N\",\"descTrans\":\"银联POS消费\",\"ageCd\":\"0\"}],\"_RejCode\":\"000000\",\"totalPage\":\"1\"}";
        String jsonStr=getString(R.string.credit_test1);
//        jsonStr=jsonStr.replaceAll("-","");
        try{
            JSONObject jsonObject=new JSONObject(j1);
            String list=jsonObject.optString("List");
            Log.i("tag",list);
        }catch (Exception e){
            Log.i("tag",e.getMessage());
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return array[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=LayoutInflater.from(PopWindowActivity.this).inflate(R.layout.item_licai_order_product_type,null);
            TextView tv= (TextView) convertView.findViewById(R.id.tv);
            tv.setText(list.get(position));
            return convertView;
        }

    }
}
