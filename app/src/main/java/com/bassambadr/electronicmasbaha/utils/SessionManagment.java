package com.bassambadr.electronicmasbaha.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bassambadr.electronicmasbaha.R;

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
public class SessionManagment {

    // متغيرات للوصول للبيانات وحفظها حتى لو قمنا بإغلاق التطبيق
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    private Context mContext;

    private int PRIVATE_MODE = 0;
    private String PREF_NAME = "ElecMas";

    private String KEY_BACKGROUND_COLOR  = "background_color";
    private String KEY_DATE  = "date";
    private String KEY_VIBRATE = "vibrate";
    private String KEY_LONG_VIBRATE = "long_vibrat";
    private String KEY_PLAY_SOUND = "playSoundEffect";

    public SessionManagment (Context mContext)
    {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mSharedPreferences.edit();
        this.mContext = mContext;
    }

    public int getKEY_BACKGROUND_COLOR() {
        return mSharedPreferences.getInt("background_color",
                mContext.getResources().getColor(R.color.background_color));
    }

    public void setKEY_BACKGROUND_COLOR(int key_background_color) {
        editor.putInt(KEY_BACKGROUND_COLOR, key_background_color);
        editor.commit();
    }

    public String getKEY_DATE() {
        return mSharedPreferences.getString("date", "");
    }

    public void setKEY_DATE(String KEY_DATE_VALUE) {
        editor.putString(KEY_DATE, KEY_DATE_VALUE);
        editor.commit();
    }

    public boolean getKEY_VIBRATE() {
        return mSharedPreferences.getBoolean("vibrate", false);
    }

    public void setKEY_VIBRATE(boolean KEY_VIBRATE) {
        editor.putBoolean("vibrate", KEY_VIBRATE);
        editor.commit();
    }

    public boolean getKEY_LONG_VIBRATE() {
        return mSharedPreferences.getBoolean("long_vibrate", false);
    }

    public void setKEY_LONG_VIBRATE(boolean KEY_LONG_VIBRATE) {
        editor.putBoolean("long_vibrate", KEY_LONG_VIBRATE);
        editor.commit();
    }

    public boolean getKEY_PLAY_SOUND() {
        return mSharedPreferences.getBoolean("playSoundEffect", false);
    }

    public void setKEY_PLAY_SOUND(boolean KEY_PLAY_SOUND) {
        editor.putBoolean("playSoundEffect", KEY_PLAY_SOUND);
        editor.commit();
    }
}
