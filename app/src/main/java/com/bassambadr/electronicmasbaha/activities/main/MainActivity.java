package com.bassambadr.electronicmasbaha.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bassambadr.electronicmasbaha.R;
import com.bassambadr.electronicmasbaha.activities.aboutUs.AboutUsActivity;
import com.bassambadr.electronicmasbaha.activities.log.LogActivity;
import com.bassambadr.electronicmasbaha.activities.newSebha.NewSebhaActivity;
import com.bassambadr.electronicmasbaha.activities.settings.SettingsActivity;
import com.bassambadr.electronicmasbaha.utils.Functions;
import com.bassambadr.electronicmasbaha.utils.SessionManagment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/*
        Copyright (C) 2015 Bassam Badr, The Electronic Masbaha Project

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASICS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.

        */

public class MainActivity extends Activity implements View.OnClickListener{

    // متغير الاعلان
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    // متغيرات الواجهة
    private LinearLayout mRootLL;
    private TextView mTitleTV;

    private Button mNewSebhaBtn;
    private Button mLogBtn;
    private Button mSettingsBtn;
    private Button mAboutUsBtn;

    private Intent mIntent;


    // كلاس الخاص بإفتتاح الجلسة لحفظ وتعديل المتغيرات
    private SessionManagment mSessionManagment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);


        // تهيئة الإعلان
        Functions.setupAdmob(mAdView = (AdView) findViewById(R.id.adView));

        // تهبئة الاعلان الكبير
        setupAdmob();


        // تهيئة المتغيرات
        setupVariables();

        // تهيئة الViews
        setupViews();

        // تهيئة الثيم الخاص بالواجهة
        initTheme(mSessionManagment.getKEY_BACKGROUND_COLOR());
    }

    private void setupAdmob()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2338165942707057/3661355268");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startActivity(mIntent);
            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void setupVariables()
    {
        mSessionManagment = new SessionManagment(getApplicationContext());
    }

    private void setupViews()
    {
        mRootLL     = (LinearLayout) findViewById(R.id.root_ll);
        mTitleTV    = (TextView) findViewById(R.id.title_tv);

        mNewSebhaBtn = (Button) findViewById(R.id.new_sebha_btn);
        mNewSebhaBtn.setOnClickListener(this);

        mLogBtn      = (Button) findViewById(R.id.today_log_btn);
        mLogBtn.setOnClickListener(this);

        mSettingsBtn = (Button) findViewById(R.id.settings_btn);
        mSettingsBtn.setOnClickListener(this);

        mAboutUsBtn  = (Button) findViewById(R.id.about_us_button);
        mAboutUsBtn.setOnClickListener(this);
    }

    private void initTheme(int color)
    {
        mRootLL.setBackgroundColor(color);
        mTitleTV.setTextColor(color);
    }

    @Override
    public void onClick(View v)
    {
        Button button = (Button) v;
        int id = button.getId();

        switch (id)
        {
            case R.id.new_sebha_btn:
                mIntent = new Intent(MainActivity.this, NewSebhaActivity.class);
                openNewActivity(1);
                break;

            case R.id.today_log_btn:
                mIntent = new Intent(MainActivity.this, LogActivity.class);
                openNewActivity(2);
                break;

            case R.id.settings_btn:
                mIntent = new Intent(MainActivity.this, SettingsActivity.class);
                openNewActivity(3);
                break;

            case R.id.about_us_button:
                mIntent = new Intent(MainActivity.this, AboutUsActivity.class);
                openNewActivity(4);
                break;
        }
    }

    private void openNewActivity(int request)
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
        else
        {
            startActivityForResult(mIntent, request);
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 3:
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                initTheme(mSessionManagment.getKEY_BACKGROUND_COLOR());
                break;

            case 1:
            case 2:
            case 4:
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                break;
        }
    }

}
