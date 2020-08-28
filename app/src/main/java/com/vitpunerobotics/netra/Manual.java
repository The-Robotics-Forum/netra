package com.vitpunerobotics.netra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Manual extends MainActivity {
    Button right, left, forward, backward, stop;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        stop = findViewById(R.id.stop);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        t = findViewById(R.id.STT);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global_variables.getBT() == 1) {
                    sendMessage("R");
                    t.setText("R");
                }
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global_variables.getBT() == 1) {
                    sendMessage("L");
                }
                t.setText("L");
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global_variables.getBT() == 1) {
                    sendMessage("F");
                }
                t.setText("F");
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global_variables.getBT() == 1) {
                    sendMessage("B");
                }
                t.setText("B");
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global_variables.getBT() == 1) {
                    sendMessage("S");
                }
                t.setText("S");
            }
        });

    }
}