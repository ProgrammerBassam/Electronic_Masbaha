package com.bassambadr.electronicmasbaha.activities.log;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bassambadr.electronicmasbaha.R;
import com.bassambadr.electronicmasbaha.database.adapters.TasbehatsAdapter;
import com.bassambadr.electronicmasbaha.database.tables.TasbehatsTable;
import com.bassambadr.electronicmasbaha.utils.Functions;
import com.bassambadr.electronicmasbaha.utils.SessionManagment;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

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

public class LogActivity extends Activity {

    // متغير الاعلان
    private AdView mAdView;

    // كلاس الخاص بإفتتاح الجلسة لحفظ وتعديل المتغيرات
    private SessionManagment mSessionManagment;
    private TasbehatsAdapter mTasbehatsAdapter;

    // متغيرات الواجهة
    private LinearLayout mRootLL;
    private TextView mTitleTV;

    private ListView mLogsLV;
    private LogAdapter mLogsLA;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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
        mTasbehatsAdapter = new TasbehatsAdapter(getApplicationContext(), TasbehatsTable.TABLE_NAME);
    }

    private void setupViews()
    {
        mRootLL     = (LinearLayout) findViewById(R.id.root_ll);
        mTitleTV    = (TextView) findViewById(R.id.title_tv);

        mLogsLV = (ListView) findViewById(R.id.logs_lv);


        mLogsLA = new LogAdapter(this, R.layout.log_item, mTasbehatsAdapter.getTasbehhats
                (Locale.getDefault().getDisplayLanguage().toString().equals("العربية")));
        mLogsLV.setAdapter(mLogsLA);
    }

    private void initTheme(int color)
    {
        mRootLL.setBackgroundColor(color);
        mTitleTV.setTextColor(color);
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
}
