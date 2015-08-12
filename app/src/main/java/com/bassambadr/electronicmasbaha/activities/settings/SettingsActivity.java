package com.bassambadr.electronicmasbaha.activities.settings;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bassambadr.electronicmasbaha.R;
import com.bassambadr.electronicmasbaha.utils.Functions;
import com.bassambadr.electronicmasbaha.utils.SessionManagment;
import com.google.android.gms.ads.AdView;
import com.rey.material.widget.Switch;

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
public class SettingsActivity extends Activity implements View.OnClickListener{

    // متغير الاعلان
    private AdView mAdView;

    // كلاس الخاص بإفتتاح الجلسة لحفظ وتعديل المتغيرات
    private SessionManagment mSessionManagment;

    // متغيرات الواجهة
    private LinearLayout mRootLL;
    private TextView mTitleTV;

    private TextView mAlarmSettingsTV;

    private TextView mVibrateSettingsTV;
    private Switch mActiveVibrateSW;
    private Switch   mActiveLongVibrateSW;

    private TextView mSoundSettingsTV;
    private Switch   mActiveSoundSW;

    private TextView mAppSettingsTV;
    private RelativeLayout mChangeBackgroundRL;
    private RelativeLayout mResetRL;
    private RelativeLayout mRateRL;
    private RelativeLayout mShareRL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

        // تهيئة الإعلان
        Functions.setupAdmob(mAdView = (AdView) findViewById(R.id.adView));

        // تهيئة المتغيرات
        setupVariables();

        // تهيئة الViews
        setupViews();

        // تهيئة الثيم الخاص بالواجهة
        initTheme(mSessionManagment.getKEY_BACKGROUND_COLOR());
    }

    private void setupVariables()
    {
        mSessionManagment = new SessionManagment(getApplicationContext());
    }

    private void setupViews()
    {
        mRootLL     = (LinearLayout) findViewById(R.id.root_ll);
        mTitleTV    = (TextView) findViewById(R.id.title_tv);

        //mAlarmSettingsTV   = (TextView) findViewById(R.id.alarm_settings_tv);


        mVibrateSettingsTV = (TextView) findViewById(R.id.vibrate_settings_tv);
        mActiveVibrateSW    = (Switch) findViewById(R.id.active_vibrate_sw);
        mActiveVibrateSW.setChecked(mSessionManagment.getKEY_VIBRATE());
        mActiveVibrateSW.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
               mSessionManagment.setKEY_VIBRATE(b);
            }
        });
        mActiveLongVibrateSW = (Switch) findViewById(R.id.active_long_vibrate_sw);
        mActiveLongVibrateSW.setChecked(mSessionManagment.getKEY_LONG_VIBRATE());
        mActiveLongVibrateSW.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                mSessionManagment.setKEY_LONG_VIBRATE(b);
            }
        });

        mSoundSettingsTV   = (TextView) findViewById(R.id.sound_settings_tv);

        mActiveSoundSW = (Switch) findViewById(R.id.active_sound_switch);
        mActiveSoundSW.setChecked(mSessionManagment.getKEY_PLAY_SOUND());
        mActiveSoundSW.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                mSessionManagment.setKEY_PLAY_SOUND(b);
            }
        });

        mAppSettingsTV     = (TextView) findViewById(R.id.app_settings_rl);

        mChangeBackgroundRL = (RelativeLayout) findViewById(R.id.change_bg_rl);
        mChangeBackgroundRL.setOnClickListener(this);
        // mResetRL = (RelativeLayout) findViewById(R.id.reset_settings_rl);
        // mResetRL.setOnClickListener(this);
        mRateRL  = (RelativeLayout) findViewById(R.id.rate_rl);
        mRateRL.setOnClickListener(this);
        mShareRL = (RelativeLayout) findViewById(R.id.share_rl);
        mShareRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        RelativeLayout relativeLayout = (RelativeLayout) v;
        int id = relativeLayout.getId();

        switch (id)
        {
            case R.id.change_bg_rl:
                chooseColorPopup();
                break;

            // case R.id.reset_settings_rl:
               // break;

            case R.id.rate_rl:
                openRate();
                break;

            case R.id.share_rl:
                openShare();
                break;
        }
    }

    private void chooseColorPopup()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window_colors, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView cl = (TextView) popupView.findViewById(R.id.close_tv);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        TextView fi = (TextView) popupView.findViewById(R.id.first_bg_color);
        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSessionManagment
                        .setKEY_BACKGROUND_COLOR(getResources().getColor(R.color.backgroundd_color_1));

                initTheme(getResources().getColor(R.color.backgroundd_color_1));
                popupWindow.dismiss();
            }
        });

        TextView se = (TextView) popupView.findViewById(R.id.second_bg_color);
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSessionManagment
                        .setKEY_BACKGROUND_COLOR(getResources().getColor(R.color.backgroundd_color_2));

                initTheme(getResources().getColor(R.color.backgroundd_color_2));
                popupWindow.dismiss();
            }
        });

        TextView or = (TextView) popupView.findViewById(R.id.orginal_bg_color);
        or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSessionManagment
                        .setKEY_BACKGROUND_COLOR(getResources().getColor(R.color.background_color));

                initTheme(getResources().getColor(R.color.background_color));
                popupWindow.dismiss();
            }
        });

        TextView th = (TextView) popupView.findViewById(R.id.third_bg_color);
        th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSessionManagment
                        .setKEY_BACKGROUND_COLOR(getResources().getColor(R.color.backgroundd_color_3));

                initTheme(getResources().getColor(R.color.backgroundd_color_3));
                popupWindow.dismiss();
            }
        });

        TextView fo = (TextView) popupView.findViewById(R.id.forth_bg_color);
        fo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSessionManagment
                        .setKEY_BACKGROUND_COLOR(getResources().getColor(R.color.backgroundd_color_4));

                initTheme(getResources().getColor(R.color.backgroundd_color_4));
                popupWindow.dismiss();
            }
        });

        TextView fif = (TextView) popupView.findViewById(R.id.fifth_bg_color);
        fif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSessionManagment
                        .setKEY_BACKGROUND_COLOR(getResources().getColor(R.color.backgroundd_color_5));

                initTheme(getResources().getColor(R.color.backgroundd_color_5));
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void openRate()
    {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void openShare()
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=com.bassambadr.electronicmasbaha");
        startActivity(Intent.createChooser(sharingIntent, "إختر التطبيق"));
    }

    private void initTheme(int color)
    {
        mRootLL.setBackgroundColor(color);
        mTitleTV.setTextColor(color);

        //mAlarmSettingsTV.setTextColor(color);
        mVibrateSettingsTV.setTextColor(color);
        mSoundSettingsTV.setTextColor(color);
        mAppSettingsTV.setTextColor(color);
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
}
