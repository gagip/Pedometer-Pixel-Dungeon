package com.example.pedometerpixeldungeon.mainsrc.pedometer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Scene;




/**
 * 메인 액티비티 클래스입니다.
 * @author 조용두
 */
public class PedometerGame extends Game implements SensorEventListener {
    // Sensor
    private SensorManager sensorManager;
    private Sensor stepCountSensor;

    // DB
    private static final String DB_NAME = "PedometerDB";
    private static final int DB_VERSION = 2;
    private PedometerDAO dao;
    private DBOpenHelper openHelper;

    // custom
    private static int sensorValue;                      // 센서 걸음 수 = 현재 걸음 수
    private static int preValue;                         // 이전 걸음 수
    boolean isSaved;                                    // 저장 여부

    public PedometerGame(Class<? extends Scene> c) {
        super(c);
    }


    /**
     * 권한 요청
     * 센서 연결
     * 센서 존재여부 체크
     * 리셋버튼
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DB객체 생성
        openHelper = new DBOpenHelper(this, DB_NAME, null, DB_VERSION);
        dao = PedometerDAO.getInstance();
        dao.setOpenHelper(openHelper);

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
    public void onResume() {
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
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("test", "onPause");
    }

    /**
     * 걸음 센서 이벤트 발생시 걸음수 증가
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

            if (!isSaved) {
                // DB Load
                Pedometer loadPedometer = dao.selectLeastPedometer();
                sensorValue = (int) event.values[0];
                preValue  = loadPedometer==null ? 0 : loadPedometer.getPreCount();

                // DB에 저장
                Pedometer pedometer = new Pedometer();
                pedometer.setCurCount(sensorValue);
                pedometer.setPreCount(preValue);

                dao.insertPedometer(pedometer);
                isSaved = !isSaved;
                Log.i("test", "DB에 저장 되었습니다.");
            }
            return;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}