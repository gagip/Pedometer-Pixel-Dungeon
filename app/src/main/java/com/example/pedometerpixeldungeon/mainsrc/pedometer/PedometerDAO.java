package com.example.pedometerpixeldungeon.mainsrc.pedometer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import static com.example.pedometerpixeldungeon.mainsrc.pedometer.DBOpenHelper.*;

public class PedometerDAO {

    private static PedometerDAO instance = null;

    private static final String DB_NAME = "MyDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;


    private PedometerDAO() {
        openHelper = null;
    }

    public  static PedometerDAO getInstance() {
        if (instance == null) {
            instance = new PedometerDAO();
        }
        return instance;
    }

    public void setOpenHelper(DBOpenHelper openHelper) {
        this.openHelper = openHelper;
    }


    public void insertPedometer(Pedometer pedometer) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TIME, System.currentTimeMillis());
        values.put(COUNT, pedometer.getCount());
        values.put(CULCOUNT, pedometer.getCulCount());

        db.insertOrThrow(TABLE_NAME, null, values);
    }

    public Pedometer selectLeastPedometer() {
        Pedometer pedometer = null;

        SQLiteDatabase db = openHelper.getReadableDatabase();
        String sql = String.format( "SELECT * FROM %s " +
                                    "WHERE _id = (SELECT max(_id) -1 from %s);",
                                    TABLE_NAME, TABLE_NAME);
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            pedometer = new Pedometer();
            pedometer.setId(cursor.getInt(0));
            pedometer.setTime(new Date(cursor.getLong(1)));
            pedometer.setCount(cursor.getInt(2));
            pedometer.setCulCount(cursor.getInt(3));
        }

        return pedometer;
    }
}
