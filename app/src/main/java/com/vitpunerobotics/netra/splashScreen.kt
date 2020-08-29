package com.vitpunerobotics.netra

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class splashScreen : MainActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var Im1 = findViewById(R.id.imageView2) as ImageView
        var Im2 = findViewById(R.id.im2) as ImageView
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 5 seconds
                    var fade_in = AnimationUtils.loadAnimation(this@splashScreen, R.anim.fade_in) as Animation
                    var scale_big = AnimationUtils.loadAnimation(this@splashScreen, R.anim.scale_big) as Animation
                    // After 5 seconds redirect to another intent
                    //Remove activity
                    Im1.startAnimation(fade_in)
                    Im2.startAnimation(scale_big)
                    sleep(3 * 1000.toLong())
                } catch (e: Exception) {
                } finally {
                    Im2.setVisibility(View.INVISIBLE)
                    Im1.setVisibility(View.INVISIBLE)
                    //Intent i=new Intent(getBaseContext(),Home.class);
                    val i = Intent(baseContext, demo::class.java)
                    startActivity(i)
                    overridePendingTransition(R.anim.slide_down, R.anim.fade_out)
                    finish()
                }
            }
        }
        // start thread
        background.start()
    }
}

