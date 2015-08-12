package com.bassambadr.electronicmasbaha.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Bassam on 8/11/2015.
 */
public class Common {

    public SQLiteDatabase mSQLiteDatabase;
    public ElecMasbahaDBHelper mElecMasbahaDBHelper;

    public String  TABLE_NAME;
    public Context mContext;

    public Common(Context context, String TABLE_NAME)
    {
        mElecMasbahaDBHelper = new ElecMasbahaDBHelper(context);
        this.TABLE_NAME = TABLE_NAME;
        this.mContext = context;
    }

    public void openWrite () throws SQLException
    {
        mSQLiteDatabase = mElecMasbahaDBHelper.getWritableDatabase();
    }

    public void openRead () throws SQLException
    {
        mSQLiteDatabase = mElecMasbahaDBHelper.getReadableDatabase();
    }

    public void close ()
    {
        mElecMasbahaDBHelper.close();
    }

    public void removeAll ()
    {
        try
        {
            openWrite();
            mSQLiteDatabase.execSQL("delete from "+ TABLE_NAME);
            close();
        }
        catch (Exception ex)
        {

        }
    }

    public boolean isEmpty()
    {
        Boolean result;
        try
        {
            openRead();

            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = mSQLiteDatabase.rawQuery(query, null);

            if (cursor.getCount() < 1)
                result = true;
            else
                result = false;

            cursor.close();
            close();
        }
        catch (Exception ex)
        {
            result = false;
        }
        return result;
    }
}
