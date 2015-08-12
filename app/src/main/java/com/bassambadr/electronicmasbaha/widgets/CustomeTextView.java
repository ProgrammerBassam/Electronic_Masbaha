package com.bassambadr.electronicmasbaha.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.bassambadr.electronicmasbaha.R;

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
public class CustomeTextView extends TextView {

    public CustomeTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);

    }

    public CustomeTextView(Context context)
    {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs)
    {
        if (attrs!=null)
        {
            TypedArray a;
            String fontName;

            if (!Locale.getDefault().getDisplayLanguage().toString().equals("العربية"))
            {
                a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomeTextView);
                fontName = a.getString(R.styleable.CustomeTextView_zrnic);
            }
            else
            {
                a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomeArabicTextView);
                fontName = a.getString(R.styleable.CustomeArabicTextView_DroidSansArabic);
            }

            if (fontName!=null)
            {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}
