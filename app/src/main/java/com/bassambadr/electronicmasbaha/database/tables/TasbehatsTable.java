package com.bassambadr.electronicmasbaha.database.tables;

/**
 * Created by Bassam on 8/11/2015.
 */
public class TasbehatsTable {

    public static final String TABLE_NAME = "tb_tasbehat";

    public static final String COLUMN_TASBEHA_ID              = "tasbeha_id";
    public static final String COLUMN_tasbeha_ARABIC_NAME     = "tasbeha_arabic_name";
    public static final String COLUMN_tasbeha_ENGLISH_NAME    = "tasbeha_english_name";
    public static final String COLUMN_COUNT                   = "tasbeha_count";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_TASBEHA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COLUMN_tasbeha_ARABIC_NAME + " TEXT," +
                    COLUMN_tasbeha_ENGLISH_NAME + " TEXT," +
                    COLUMN_COUNT + " INTEGER" + ")";
}
