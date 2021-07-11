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


    /**
     * 데이터 삽입
     * @param pedometer
     */
    public void insertPedometer(Pedometer pedometer) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TIME, System.currentTimeMillis());
        values.put(CUR_COUNT, pedometer.getCurCount());
        values.put(PRE_COUNT, pedometer.getPreCount());

        db.insertOrThrow(TABLE_NAME, null, values);
    }

    /**
     * 최신 데이터 추출
     * @return
     */
    public Pedometer selectLeastPedometer() {
        Pedometer pedometer = null;

        SQLiteDatabase db = openHelper.getReadableDatabase();
        String sql = String.format( "SELECT * FROM %s " +
                                    "ORDER BY %s DESC LIMIT 1;",
                                    TABLE_NAME, TIME);
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            pedometer = new Pedometer();
            pedometer.setId(cursor.getInt(0));
            pedometer.setTime(new Date(cursor.getLong(1)));
            pedometer.setCurCount(cursor.getInt(2));
            pedometer.setPreCount(cursor.getInt(3));
        }

        return pedometer;
    }

    /**
     * 최근 데이터 추출
     */
    public void insertPedometer() {
        Pedometer pedometer = selectLeastPedometer();

        pedometer.setPreCount(pedometer.getCurCount());
        insertPedometer(pedometer);
    }
}
