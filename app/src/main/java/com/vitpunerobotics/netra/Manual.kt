package com.vitpunerobotics.netra

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Manual : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)
        var right = findViewById(R.id.right) as Button
        var left = findViewById(R.id.left) as Button
        var stop = findViewById(R.id.stop) as Button
        var forward = findViewById(R.id.forward) as Button
        var backward = findViewById(R.id.backward) as Button
        var t = findViewById(R.id.STT) as TextView
        right.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("R")
                t.setText("R")
            }
        })
        left.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("L")
            }
            t.setText("L")
        })
        forward.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("F")
            }
            t.setText("F")
        })
        backward.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("B")
            }
            t.setText("B")
        })
        stop.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("S")
            }
            t.setText("S")
        })
    }
}