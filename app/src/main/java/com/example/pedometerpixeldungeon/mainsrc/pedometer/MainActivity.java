package com.bigdata.pedemoter_sqlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import static com.bigdata.pedemoter_sqlite.DBOpenHelper.TABLE_NAME;
import static com.bigdata.pedemoter_sqlite.DBOpenHelper.TITLE;
import static com.bigdata.pedemoter_sqlite.DBOpenHelper.TIME;
import static com.bigdata.pedemoter_sqlite.DBOpenHelper._ID;

/**
 * 메인 액티비티 클래스입니다.
 * @author 조용두
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    Button resetButton;

    private static final String DB_NAME = "MyDB";
    private static final int DB_VERSION = 1;
    private DBOpenHelper openHelper;

    // 현재 걸음 수
    int currentSteps = 0;

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
        resetButton = findViewById(R.id.resetButton);


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

        // 리셋 버튼 추가 - 리셋 기능
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));

            }
        });

        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);
        writeDB("Hello World!");
        Cursor cursor = readDB();
        displayDB(cursor);
        openHelper.close();

    }


    /**
     * 센서 속도 설정
     */
    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }


    /**
     * 걸음 센서 이벤트 발생시 걸음수 증가
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;
                stepCountView.setText(String.valueOf(currentSteps));
            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 화면에 보이게 하는것
     */
    private void displayDB(Cursor cursor) {
        StringBuilder builder = new StringBuilder("Saved DB:\n");
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            long time = cursor.getLong(1);
            String title = cursor.getString(2);
            builder.append(id).append(": ");
            builder.append(time).append(": ");
            builder.append(title).append("\n");
        }
        TextView textView = (TextView) findViewById(R.id.stepCountView);
        textView.setText(builder);
    }

    /**
     * 데이터 베이스 읽기
     */
    private Cursor readDB() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String[] from = {_ID, TIME, TITLE};
        Cursor cursor = db.query(TABLE_NAME, from, null, null, null, null, TIME + " " + "DESC");
        startManagingCursor(cursor);
        return cursor;
    }

    /**
     * 데이터 베이스에 쓰기
     */
    private void writeDB(String title) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, title);
        db.insertOrThrow(TABLE_NAME, null, values);
    }
}