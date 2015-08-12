package com.bassambadr.electronicmasbaha.activities.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bassambadr.electronicmasbaha.R;
import com.bassambadr.electronicmasbaha.activities.main.MainActivity;
import com.bassambadr.electronicmasbaha.database.adapters.TasbehatsAdapter;
import com.bassambadr.electronicmasbaha.database.tables.TasbehatsTable;
import com.bassambadr.electronicmasbaha.utils.Functions;
import com.bassambadr.electronicmasbaha.utils.SessionManagment;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class SplashActivity extends Activity {

    // متغير الاعلان
    private AdView mAdView;


    // كلاس الخاص بإفتتاح الجلسة لحفظ وتعديل المتغيرات
    private SessionManagment mSessionManagment;

    // كلاس العمليات الجدول tb_tasbehats على القاعجة
    private TasbehatsAdapter mTasbehatsAdapter;

    // متغيرات الواجهة
    private RelativeLayout mRootLL;
    private TextView mLogoTV;

    private TextView mProgressTV;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // تهيئة الإعلان
        Functions.setupAdmob(mAdView = (AdView) findViewById(R.id.adView));

        // تهيئة المتغيرات
        setupVariables();

        // تهيئة الViews
        setupViews();

        // تهيئة الثيم الخاص بالواجهة
        initTheme(mSessionManagment.getKEY_BACKGROUND_COLOR());

        /*
            هذه الدالة لتهيئة قاعدة البيانات عند الدخول لأول مره
            حيث تقوم بعمل مقارنة اذا ماكان تاريخ اليو مختلف عن تاريخ اخر مره قمت بالدخول فيه إلى التطبيق
            اذا كان اول مره يقوم بإضافة التسحبيحات للقاعدة وتهيئتها بالقيمة صفر
         */
        initDatabase();

        /*
         وظيفة الدالة تقوم بالإنتظار لمدة 5 ثواني يقوم مؤشر التحميل بالتحرك وعند الإنتهاء يتم فتح الواجهة الرئيسية
         */
        gettingReady();
    }

    private void setupVariables()
    {
        mSessionManagment = new SessionManagment(getApplicationContext());
        mTasbehatsAdapter = new TasbehatsAdapter(getApplicationContext(), TasbehatsTable.TABLE_NAME);
    }

    private void setupViews()
    {
        mRootLL     = (RelativeLayout) findViewById(R.id.root_ll);
        mLogoTV     = (TextView) findViewById(R.id.logo_tv);
        mProgressTV = (TextView) findViewById(R.id.progress_tv);

        mProgressBar = (ProgressBar) findViewById(R.id.ready_status_pb);
    }

    private void initTheme(int color)
    {
        mRootLL.setBackgroundColor(color);
        mLogoTV.setTextColor(color);
        mProgressTV.setTextColor(color);
    }

    private void initDatabase()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        String current = dateFormat.format(cal.getTime()) + "";

        if (!mSessionManagment.getKEY_DATE().equals(current))
        {
            mSessionManagment.setKEY_DATE(current);

            if (!mTasbehatsAdapter.isEmpty())
            {
                mTasbehatsAdapter.update("استغفر الله",
                        Locale.getDefault().getDisplayLanguage().toString().equals("العربية"));
                mTasbehatsAdapter.update("سبحان الله",
                        Locale.getDefault().getDisplayLanguage().toString().equals("العربية"));
                mTasbehatsAdapter.update("الحمد لله",
                        Locale.getDefault().getDisplayLanguage().toString().equals("العربية"));
                mTasbehatsAdapter.update("الله اكبر",
                        Locale.getDefault().getDisplayLanguage().toString().equals("العربية"));
            }
            else
            {
                mTasbehatsAdapter.insert("استغفر الله", "استغفر الله", 0);
                mTasbehatsAdapter.insert("سبحان الله", "سبحان الله", 0);
                mTasbehatsAdapter.insert("الحمد لله", "الحمد لله", 0);
                mTasbehatsAdapter.insert("الله اكبر", "الله اكبر", 0);
            }
        }
    }

    private void gettingReady()
    {
        final Intent mIntent = new Intent(this, MainActivity.class);

        new Thread(new Runnable()
        {
            int progressBarStatus = 0;
            public void run() {
                while (progressBarStatus < 100) {

                    progressBarStatus += 20;

                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mProgressBar.post(new Runnable() {
                        public void run() {
                            mProgressBar.setProgress(progressBarStatus);
                        }
                    });
                }

                if (progressBarStatus >= 100) {

                    startActivity(mIntent);

                    finish();
                }
            }
        }).start();
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
    }
}
