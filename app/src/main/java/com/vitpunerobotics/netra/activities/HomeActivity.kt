package com.vitpunerobotics.netra.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.vitpunerobotics.netra.R


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var bluetooth = findViewById<Button>(R.id.bluetooth)
        var voicebt = findViewById<Button>(R.id.voice)
        var facebt = findViewById<Button>(R.id.face)
        var colorbt = findViewById<Button>(R.id.color)
        var ocrbt = findViewById<Button>(R.id.ocr)
        var movebt = findViewById<Button>(R.id.move)
        var manual = findViewById<Button>(R.id.manual)
        val dark = findViewById<Button>(R.id.dark)


        val sharedPreferences = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val isDarkModeOn = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false)

        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES)
            dark.text = "Dark Mode Off"
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            dark.text = "Dark Mode On";
        }


        dark.setOnClickListener(View.OnClickListener {
            if (isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean(
                        "isDarkModeOn", false)
                editor.apply()
                dark.text = "Night mode off"}
            else {

                // if dark mode is off
                // it will turn it on
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate
                                        .MODE_NIGHT_YES)

                // it will set isDarkModeOn
                // boolean to true
                editor.putBoolean(
                        "isDarkModeOn", true)
                editor.apply();

                // change text of Button
                dark.text = "Disable Dark Mode"
            }
        })

        manual.setOnClickListener(View.OnClickListener {
            val bluetooth_intend = Intent(applicationContext, ManualControlActivity::class.java)

            startActivity(bluetooth_intend)
        })
        voicebt.setOnClickListener(View.OnClickListener {
            setContentView(R.layout.activity_home)
            val Open_Voice = Intent(applicationContext, VoiceControlActivity::class.java)
            startActivity(Open_Voice)
        })
        bluetooth.setOnClickListener(View.OnClickListener {
            val bluetooth_intend = Intent(applicationContext, BluetoothActivity::class.java)
            startActivity(bluetooth_intend)
        })
        facebt.setOnClickListener(View.OnClickListener {
            val Open_Face_Tracker = Intent(applicationContext, FaceTrackerActivity::class.java)
            startActivity(Open_Face_Tracker)
        })
        colorbt.setOnClickListener(View.OnClickListener {
            val Open_Color = Intent(applicationContext, ColorDetectionActivity::class.java)
            startActivity(Open_Color)
        })
        ocrbt.setOnClickListener(View.OnClickListener {
            val Open_Ocr = Intent(applicationContext, OCRActivity::class.java)
            startActivity(Open_Ocr)
        })
        movebt.setOnClickListener(View.OnClickListener {
            val Open_move = Intent(applicationContext, Move_dist::class.java)
            startActivity(Open_move)
        })
    }



}