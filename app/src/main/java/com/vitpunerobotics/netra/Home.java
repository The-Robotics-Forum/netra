package com.vitpunerobotics.netra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends MainActivity {
    Button voicebt, facebt, colorbt, ocrbt, movebt, gesturebt, pathbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        voicebt = findViewById(R.id.voice);
        facebt = findViewById(R.id.face);
        colorbt = findViewById(R.id.color);
        ocrbt = findViewById(R.id.ocr);
        gesturebt = findViewById(R.id.gesture);
        pathbt = findViewById(R.id.path);
        movebt = findViewById(R.id.move);
        voicebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.demo_ac);
                Intent Open_Voice = new Intent(getApplicationContext(), Voice_Control.class);
                //startActivity(Open_Voice);
            }
        });
        facebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_Face_Tracker = new Intent(getApplicationContext(), Face_Tracker.class);
                startActivity(Open_Face_Tracker);
            }
        });
        colorbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_Color = new Intent(getApplicationContext(), Color_Detector.class);
                startActivity(Open_Color);
            }
        });
        ocrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_Ocr = new Intent(getApplicationContext(), OCR_activity.class);
                startActivity(Open_Ocr);
            }
        });
        movebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_move = new Intent(getApplicationContext(), Move_dist.class);
                startActivity(Open_move);
            }
        });
        gesturebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_gesture = new Intent(getApplicationContext(), Gesture.class);
                startActivity(Open_gesture);
            }
        });
        pathbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_path = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(Open_path);
            }
        });
    }
}
