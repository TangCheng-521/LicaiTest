package com.tang.licaidemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LicaiActivity extends AppCompatActivity implements LicaiFragment.OnFragmentInteractionListener{
    private LicaiFragment licaiFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licai);

        licaiFrag=LicaiFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().replace(R.id.content,licaiFrag).commitAllowingStateLoss();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
