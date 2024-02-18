package com.example.mmos_ahmicic_gravity;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView gravityTextView;
    private SensorManager sensorManager;
    private Sensor mGravity;
    private boolean isGravitySensorPresent;
    private SensorEvent latestGravityEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gravityTextView = findViewById(R.id.gravityTextView);

        Button xButton = findViewById(R.id.xButton);
        Button yButton = findViewById(R.id.yButton);
        Button zButton = findViewById(R.id.zButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            mGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            isGravitySensorPresent = true;
        } else {
            gravityTextView.setText("Senzor nije dostupan na uređaju");
            isGravitySensorPresent = false;
        }

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGravityText(0);
            }
        });

        yButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGravityText(1);
            }
        });

        zButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGravityText(2);
            }
        });
    }

    private void updateGravityText(int axis) {
        if (isGravitySensorPresent) {
            if (latestGravityEvent != null) {
                switch (axis) {
                    case 0:
                        gravityTextView.setText("X os: " + latestGravityEvent.values[0] + " m/s2");
                        break;
                    case 1:
                        gravityTextView.setText("Y os: " + latestGravityEvent.values[1] + " m/s2");
                        break;
                    case 2:
                        gravityTextView.setText("Z os: " + latestGravityEvent.values[2] + " m/s2");
                        break;
                }
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        latestGravityEvent = event;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
            sensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
            sensorManager.unregisterListener(this, mGravity);
    }
}
