package com.example.pedometerpixeldungeon.mainsrc.pedometer;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;

import static com.example.pedometerpixeldungeon.mainsrc.pedometer.DBOpenHelper.*;




/**
 * 메인 액티비티 클래스입니다.
 * @author 조용두
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // Sensor
    private SensorManager sensorManager;
    private Sensor stepCountSensor;

    // DB
    private static final String DB_NAME = "MyDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;
    Pedometer pedometer = new Pedometer();

    // UI
    private TextView stepCountView;
    private TextView moneyView;
    private TextView dBView;
    private TextView saveView;
    private Button randomButton;
    private Button rewardButton;

    // custom
    boolean isSaved;                                     // 저장 여부


    /**
     * 권한 요청
     * 센서 연결
     * 센서 존재여부 체크
     * 리셋버튼
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        stepCountView = findViewById(R.id.stepCountView);
        moneyView = findViewById(R.id.moneyView);
        dBView = findViewById(R.id.DBView);
        saveView = findViewById(R.id.saveView);
        randomButton = findViewById(R.id.randomButton);
        rewardButton = findViewById(R.id.rewardButton);

        // DB객체 생성
        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);

        // call DB
        if (openHelper.getReadableDatabase() != null) {
//            displayDB();
        }


        // 활동 퍼미션 체크
        // 권한 요청
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 센서 속도 설정
     */
    @Override
    protected void onResume() {
        super.onResume();
        isSaved = false;
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);
            Log.i("test", "onResume");
        }
        displayDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        writeDB();
        Log.i("test", "onPause");
    }

    /**
     * 걸음 센서 이벤트 발생시 걸음수 증가
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            pedometer.setCount((int) event.values[0]);
            pedometer.setCulCount(pedometer.getCount());

            if (!isSaved) {
                writeDB();
                Log.i("test", "onSensorChanged");
                isSaved = !isSaved;
            }

            // print from UI
            runOnUiThread(() -> {
                stepCountView.setText(pedometer.getCount()+"");
            });

            return;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 화면에 출력
     */
    private void displayDB() {
        StringBuilder builder = new StringBuilder("Saved DB:\n");
        ArrayList<Pedometer> pedometerArr = new ArrayList<Pedometer>();

        Cursor cursor = readDB();
        while (cursor.moveToNext()) {
            // DB -> variable
            int id = cursor.getInt(0);
            long time = cursor.getLong(1);
            int count = cursor.getInt(2);
            int culCount = cursor.getInt(3);

            // variable -> DTO
            Pedometer pedometer = new Pedometer();
            pedometer.setId(id);
            pedometer.setTime(new Date(time));
            pedometer.setCount(count);
            pedometer.setCulCount(culCount);

            // append to DTO Array
            pedometerArr.add(pedometer);
        }

        for (Pedometer p : pedometerArr) {
            builder.append(p.getId()+": ");
            builder.append(p.getTime()+": ");
            builder.append(p.getCount()+": ");
            builder.append(p.getCulCount()+"\n");
        }

        dBView.setText(builder);
        Log.i("test", "displayDB");
    }

    /**
     * 데이터 베이스 읽기
     */
    private Cursor readDB() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String[] from = {_ID, TIME, COUNT, CULCOUNT};
        Cursor cursor = db.query(TABLE_NAME, from, null, null, null, null, TIME + " DESC");
        startManagingCursor(cursor);
        return cursor;
    }

    // 최신 자료
    // 제일 최근 돈 가져오기
    private void readLatestDB() {
        int _money;
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String[] from = {_ID, TIME, COUNT};
        int latestIdx = getCountDB();
        Cursor cursor = db.query(TABLE_NAME,
                from,
                _ID+"=?",
                new String[] {String.valueOf(latestIdx)},
                null, null, null, null);

//                String query = "SELECT count FROM TestDB WHERE _id = (SELECT max(_id) from TestDB);";
//                Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                _money = cursor.getInt(4);
            }
        }
    }

    // 현재 DB에 저장된 column개수를 센다.
    private int getCountDB() {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        return cursor.getCount();
    }

    // 가장 최신의 자료에서 바로 뒤의 자료 가져오기
    private int readCount() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String query = "SELECT count FROM TestDB WHERE _id = (SELECT max(_id) -1 from TestDB);";
        Cursor cursor = db.rawQuery(query, null);

        return cursor.getColumnCount();
    }

    /**
     * DB 저장
     */
    private void writeDB() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TIME, System.currentTimeMillis());
        values.put(COUNT, pedometer.getCount());

        db.insertOrThrow(TABLE_NAME, null, values);
    }

}