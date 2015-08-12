package com.bassambadr.electronicmasbaha.activities.newSebha;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bassambadr.electronicmasbaha.R;
import com.bassambadr.electronicmasbaha.activities.log.LogAdapter;
import com.bassambadr.electronicmasbaha.database.adapters.TasbehatsAdapter;
import com.bassambadr.electronicmasbaha.database.tables.TasbehatsTable;
import com.bassambadr.electronicmasbaha.utils.Functions;
import com.bassambadr.electronicmasbaha.utils.SessionManagment;
import com.bassambadr.electronicmasbaha.widgets.CustomeButton;
import com.bassambadr.electronicmasbaha.widgets.CustomeEditText;
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

public class NewSebhaActivity extends Activity {

    // متغير الاعلان
    private AdView mAdView;

    // كلاس الخاص بإفتتاح الجلسة لحفظ وتعديل المتغيرات
    private SessionManagment mSessionManagment;

    // متغيرات الواجهة
    private LinearLayout mRootLL;
    // private TextView mTitleTV;
    // private View mLogoView;
    private TextView mCurrentTasbehaTV;

    private LinearLayout mCounterLL;
    private TextView     mCounterTV;

    private Button mNewBtn;
    private Button mNightModeBtn;

    private Vibrator mVibrator;
    private AudioManager mAudioManager;

    private TasbehatsAdapter mTasbehatsAdapter;

    private String tasName;
    private int counter = 0;
    private int tasCouners = 0;

    private Boolean mNaightMode = false;
    private boolean isEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sebha);
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

    private void setupViews()
    {
        mVibrator     = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mRootLL           = (LinearLayout) findViewById(R.id.root_ll);
        // mTitleTV          = (TextView) findViewById(R.id.title_tv);
        // mLogoView         = (View) findViewById(R.id.top_bar);
        mCurrentTasbehaTV = (TextView) findViewById(R.id.tas_title_tv);

        mCounterLL = (LinearLayout) findViewById(R.id.count_ll);
        mCounterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEnabled) {
                    mTasbehatsAdapter.update(tasName,
                            Locale.getDefault().getDisplayLanguage().toString().equals("العربية"));
                    if (counter++ == tasCouners - 1) {
                        if (mSessionManagment.getKEY_LONG_VIBRATE())
                            mVibrator.vibrate(1000);

                        mNewBtn.setEnabled(true);
                        isEnabled = !isEnabled;
                        tasCouners = 0;
                        counter = 0;
                        mCurrentTasbehaTV.setText(getString(R.string.new_string));
                        mCounterTV.setText("0");
                    } else {
                        mCounterTV.setText(counter + "");

                        if (mSessionManagment.getKEY_VIBRATE())
                            mVibrator.vibrate(100);

                        if (mSessionManagment.getKEY_PLAY_SOUND()) {
                            mAudioManager.playSoundEffect(SoundEffectConstants.CLICK);
                        } else {
                            mCounterLL.setSoundEffectsEnabled(false);
                        }
                    }
                }
            }
        });

        mCounterTV = (TextView) findViewById(R.id.counter_tv);

        mNewBtn       = (Button) findViewById(R.id.new_btn);
        mNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTasbeeha();
            }
        });

        mNightModeBtn = (Button) findViewById(R.id.night_mode_btn);
        mNightModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mNaightMode) {
                    changeVis(View.VISIBLE, mSessionManagment.getKEY_BACKGROUND_COLOR());
                } else {
                    changeVis(View.INVISIBLE, getResources().getColor(R.color.backgroundd_color_5));
                }
            }
        });
    }

    private void initTheme(int color)
    {
        mRootLL.setBackgroundColor(color);
        // mTitleTV.setTextColor(color);
        mCurrentTasbehaTV.setTextColor(color);
    }

    private void setupVariables()
    {
        mSessionManagment = new SessionManagment(getApplicationContext());
        mTasbehatsAdapter = new TasbehatsAdapter(getApplicationContext(), TasbehatsTable.TABLE_NAME);

        mVibrator     = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private void changeVis(int vis, int color)
    {
        mNewBtn.setVisibility(vis);
        mAdView.setVisibility(vis);
        mCurrentTasbehaTV.setVisibility(vis);
        // mLogoView.setVisibility(vis);
        mRootLL.setBackgroundColor(color);
        mNaightMode = !mNaightMode;
    }

    private void createTasbeeha()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window_choose_tasbeha, null);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mTasbehatsAdapter.getNames());

        final AutoCompleteTextView autoCompleteTextView =
                (AutoCompleteTextView) popupView.findViewById(R.id.tasbeha_name_et);

        autoCompleteTextView.setAdapter(adapter);

        final CustomeEditText customeEditText =
                (CustomeEditText) popupView.findViewById(R.id.counter_et);

        CustomeButton button = (CustomeButton) popupView.findViewById(R.id.start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tasName = autoCompleteTextView.getText().toString();
                String tasCoun = customeEditText.getText().toString();

                if (TextUtils.isEmpty(tasName) || TextUtils.isEmpty(tasCoun))
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_empty),
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    tasCouners = Integer.parseInt(tasCoun);
                    isEnabled = true;
                    mNewBtn.setEnabled(false);
                    mCurrentTasbehaTV.setText(tasName + " - " + tasCoun);
                    popupWindow.dismiss();


                    mTasbehatsAdapter.insert(tasName, tasName, 0);
                }
            }
        });

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0, 0);
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
