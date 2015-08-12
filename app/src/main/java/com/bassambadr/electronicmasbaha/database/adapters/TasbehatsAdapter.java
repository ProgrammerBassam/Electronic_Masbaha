package com.bassambadr.electronicmasbaha.database.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bassambadr.electronicmasbaha.activities.log.LogItem;
import com.bassambadr.electronicmasbaha.database.Common;
import com.bassambadr.electronicmasbaha.database.ElecMasbahaDBHelper;
import com.bassambadr.electronicmasbaha.database.tables.TasbehatsTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bassam on 8/11/2015.
 */
public class TasbehatsAdapter  extends Common {

    private String[] allColumns =
            {
                    TasbehatsTable.COLUMN_TASBEHA_ID,
                    TasbehatsTable.COLUMN_tasbeha_ARABIC_NAME,
                    TasbehatsTable.COLUMN_tasbeha_ENGLISH_NAME,
                    TasbehatsTable.COLUMN_COUNT
            };

    public TasbehatsAdapter(Context context, String TABLE_NAME) {
        super(context, TABLE_NAME);
    }

    public long insert(String arabic, String englih, int count)
    {
        long result = 0;

        if (!getValues(arabic, true).equals(""))
            return -1;

        if (!getValues(englih, false).equals(""))
            return -1;

        try
        {
            openWrite();

            ContentValues values = new ContentValues();

            values.put(allColumns[1], arabic);
            values.put(allColumns[2], englih);
            values.put(allColumns[3], count);

            result = mSQLiteDatabase.insert(TABLE_NAME, null, values);

            close();
        } catch (Exception ex) {

        }
        return result;
    }

    public ArrayList<LogItem> getTasbehhats(boolean isArabic)
    {
        ArrayList<LogItem> logItems = new ArrayList<>();
        try {
            openRead();

            Cursor mCursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            mCursor.moveToFirst();

            do
            {
                if (isArabic)
                    logItems.add(new LogItem(mCursor.getString(mCursor.getColumnIndex(allColumns[1])),
                            mCursor.getInt(mCursor.getColumnIndex(allColumns[3])) + ""));
                else
                    logItems.add(new LogItem(mCursor.getString(mCursor.getColumnIndex(allColumns[2])),
                            mCursor.getInt(mCursor.getColumnIndex(allColumns[3])) + ""));
            }
            while (mCursor.moveToNext());

            mCursor.close();
            close();

        }
        catch (Exception ex)
        {
        }
        return logItems;
    }

    public void update(String name, boolean isArabic)
    {
        String[] valuess   = getValues(name, isArabic).split("-");

        try
        {
            openWrite();

            ContentValues values = new ContentValues();

            values.put(allColumns[0], Integer.parseInt(valuess[0]));
            values.put(allColumns[1], valuess[1]);
            values.put(allColumns[2], valuess[2]);
            values.put(allColumns[3], Integer.parseInt(valuess[3]) + 1);

            mSQLiteDatabase.update(TABLE_NAME, values, allColumns[0]
                    + " = " + Integer.parseInt(valuess[0]), null);

            close();
        }
        catch (Exception ex)
        {

        }
    }

    public String getValues(String name, boolean isArabic)
    {
        String result = "";

        try
        {
            openRead();

            Cursor mCursor;

            if (isArabic)
                mCursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE " + allColumns[1] + " LIKE '%" + name + "%'", null);
            else
                mCursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE " + allColumns[2] + " LIKE '%" + name + "%'", null);

            mCursor.moveToFirst();

            if (mCursor.getCount() > 0)
            {
                result = mCursor.getInt(mCursor.getColumnIndex(allColumns[0]))
                        + "-"
                        + mCursor.getString(mCursor.getColumnIndex(allColumns[1]))
                        + "-"
                        + mCursor.getString(mCursor.getColumnIndex(allColumns[2]))
                        + "-"
                        + mCursor.getInt(mCursor.getColumnIndex(allColumns[3]));
            }

            mCursor.close();
            close();

            close();
        }
        catch (Exception ex)
        {

        }

        return result;
    }

    public String[] getNames()
    {
        List<String> stringList = new ArrayList<>();

        try {
            openRead();

            Cursor mCursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            mCursor.moveToFirst();

            do
            {
                stringList.add(mCursor.getString(mCursor.getColumnIndex(allColumns[1])));
            }
            while (mCursor.moveToNext());

            mCursor.close();
            close();

        }
        catch (Exception ex)
        {
        }

        String[] s = new String[stringList.size()];

        return stringList.toArray(s);
    }
}
