package com.vitpunerobotics.netra.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.vitpunerobotics.netra.R
import com.vitpunerobotics.netra.global_variables

class Manual : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)
        var right = findViewById<Button>(R.id.right)
        var left = findViewById<Button>(R.id.left)
        var stop = findViewById<Button>(R.id.stop)
        var forward = findViewById<Button>(R.id.forward)
        var backward = findViewById<Button>(R.id.backward)
        var t = findViewById<TextView>(R.id.STT)
        right.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("R")
                t.text = "R"
            }
        })
        left.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("L")
            }
            t.text = "L"
        })
        forward.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("F")
            }
            t.text = "F"
        })
        backward.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("B")
            }
            t.text = "B"
        })
        stop.setOnClickListener(View.OnClickListener {
            if (global_variables.bT == 1) {
                sendMessage("S")
            }
            t.text = "S"
        })
    }
}