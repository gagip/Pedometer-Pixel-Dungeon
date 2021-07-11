package com.example.pedometerpixeldungeon.mainsrc.pedometer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "TestDB";
    public static final String _ID = "_id";
    public static final String TIME = "time";
    public static final String CUR_COUNT = "curCount";
    public static final String PRE_COUNT = "preCount";


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s INTEGER, " +
                        "%s INTEGER, " +
                        "%s INTEGER); ",
                TABLE_NAME, _ID, TIME, CUR_COUNT, PRE_COUNT));

        db.execSQL(String.format(   "INSERT INTO %s (%s, %s, %s) " +
                                    "VALUES (%d, 0, 0);",
                                    TABLE_NAME, TIME, CUR_COUNT, PRE_COUNT,
                                    System.currentTimeMillis()));
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

