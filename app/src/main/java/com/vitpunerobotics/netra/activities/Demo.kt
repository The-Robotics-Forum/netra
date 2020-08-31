package com.vitpunerobotics.netra.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.vitpunerobotics.netra.R
import com.vitpunerobotics.netra.global_variables

class demo : AppCompatActivity() {

    var g: global_variables? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_ac)
        var bluetooth = findViewById<Button>(R.id.bluetooth)
        var voicebt = findViewById<Button>(R.id.voice)
        var facebt = findViewById<Button>(R.id.face)
        var colorbt = findViewById<Button>(R.id.color)
        var ocrbt = findViewById<Button>(R.id.ocr)
        var movebt = findViewById<Button>(R.id.move)
        var manual = findViewById<Button>(R.id.manual)
        manual.setOnClickListener(View.OnClickListener {
            val bluetooth_intend = Intent(applicationContext, Manual::class.java)
            //global_variables gv;
            startActivity(bluetooth_intend)
        })
        voicebt.setOnClickListener(View.OnClickListener {
            setContentView(R.layout.demo_ac)
            val Open_Voice = Intent(applicationContext, Voice_Control::class.java)
            startActivity(Open_Voice)
        })
        bluetooth.setOnClickListener(View.OnClickListener {
            val bluetooth_intend = Intent(applicationContext, MainActivity::class.java)
            startActivity(bluetooth_intend)
        })
        facebt.setOnClickListener(View.OnClickListener {
            val Open_Face_Tracker = Intent(applicationContext, Face_Tracker::class.java)
            startActivity(Open_Face_Tracker)
        })
        colorbt.setOnClickListener(View.OnClickListener {
            val Open_Color = Intent(applicationContext, Color_Detector::class.java)
            startActivity(Open_Color)
        })
        ocrbt.setOnClickListener(View.OnClickListener {
            val Open_Ocr = Intent(applicationContext, OCR_activity::class.java)
            startActivity(Open_Ocr)
        })
        movebt.setOnClickListener(View.OnClickListener {
            val Open_move = Intent(applicationContext, Move_dist::class.java)
            startActivity(Open_move)
        })
    }
}