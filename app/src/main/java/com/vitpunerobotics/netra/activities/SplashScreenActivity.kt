package com.vitpunerobotics.netra.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.vitpunerobotics.netra.R

class SplashScreenActivity : BluetoothActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_activity)

        val Im1 = findViewById<ImageView>(R.id.imageView2)
        val Im2 = findViewById<ImageView>(R.id.im2)

        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 5 seconds
                    val fade_in = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.fade_in) as Animation
                    val scale_big = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.scale_big) as Animation
                    // After 5 seconds redirect to another intent
                    //Remove activity
                    Im1.startAnimation(fade_in)
                    Im2.startAnimation(scale_big)
                    sleep(3 * 1000.toLong())
                } catch (e: Exception) {
                } finally {
                    Im2.visibility = View.INVISIBLE
                    Im1.visibility = View.INVISIBLE
                    //Intent i=new Intent(getBaseContext(),Home.class);
                    val i = Intent(baseContext, HomeActivity::class.java)
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

