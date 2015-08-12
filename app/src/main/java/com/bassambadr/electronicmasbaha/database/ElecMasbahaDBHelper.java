package com.bassambadr.electronicmasbaha.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bassambadr.electronicmasbaha.database.tables.TasbehatsTable;

/**
 * Created by Bassam on 8/11/2015.
 */
public class ElecMasbahaDBHelper extends SQLiteOpenHelper {

    public ElecMasbahaDBHelper(Context context) {
        super(context, "ElecTasbeehat.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TasbehatsTable.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
