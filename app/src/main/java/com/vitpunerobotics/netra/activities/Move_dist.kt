package com.vitpunerobotics.netra.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.vitpunerobotics.netra.R
import com.vitpunerobotics.netra.global_variables
import com.vitpunerobotics.netra.global_variables.bT
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.android.JavaCameraView
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.util.*

class Move_dist : MainActivity(), CvCameraViewListener2 {


    var finalDistance = 0.0
    var t = 0.0
    var area = 3.14 * 4
    var h_min: SeekBar? = null
    var h_max: SeekBar? = null
    var v_min: SeekBar? = null
    var v_max: SeekBar? = null
    var s_min: SeekBar? = null
    var s_max: SeekBar? = null

    var text: TextView? = null
    private lateinit var javaCameraView: JavaCameraView
    private var B: Mat? = null
    private var F: Mat? = null


    private val baseLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> javaCameraView.enableView()
                else -> super.onManagerConnected(status)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val b = Intent(applicationContext, demo::class.java)
        startActivity(b)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val BTconnect = Intent(applicationContext, MainActivity::class.java)
        if (bT == 0) {
            startActivity(BTconnect)
        }
        setContentView(R.layout.activity_move_dist)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        var red = findViewById<Button>(R.id.Red)
        var blue = findViewById<Button>(R.id.Blue)
        var green = findViewById<Button>(R.id.Green)
        text = findViewById(R.id.dist)
        text!!.text = t.toString()
        red.setOnClickListener(View.OnClickListener {
            h_min!!.progress = 161
            h_max!!.progress = 188
            s_min!!.progress = 121
            s_max!!.progress = 255
            v_min!!.progress = 194
            v_max!!.progress = 255
        })
        blue.setOnClickListener(View.OnClickListener {
            h_min!!.progress = 240
            h_max!!.progress = 255
            s_min!!.progress = 100
            s_max!!.progress = 255
            v_min!!.progress = 100
            v_max!!.progress = 255
        })
        green.setOnClickListener(View.OnClickListener {
            h_min!!.progress = 120
            h_max!!.progress = 150
            s_min!!.progress = 100
            s_max!!.progress = 255
            v_min!!.progress = 50
            v_max!!.progress = 155
        })
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        }
        javaCameraView = findViewById(R.id.javaCameraView)
        javaCameraView.visibility = SurfaceView.VISIBLE
        javaCameraView.setCvCameraViewListener(this)
        h_min = findViewById(R.id.h_min)
        h_max = findViewById(R.id.h_max)
        v_min = findViewById(R.id.v_min)
        v_max = findViewById(R.id.v_max)
        s_min = findViewById(R.id.s_min)
        s_max = findViewById(R.id.s_max)
        h_max!!.max = 255
        h_min!!.max = 255
        v_max!!.max = 255
        v_min!!.max = 255
        s_max!!.max = 255
        s_min!!.max = 255
        //Core.inRange(B,new Scalar(gv.a,gv.c,gv.b), new Scalar(gv.d,gv.e,gv.f),B);
        h_min!!.progress = global_variables.a
        h_max!!.progress = global_variables.d
        s_min!!.progress = global_variables.c
        s_max!!.progress = global_variables.e
        v_min!!.progress = global_variables.b
        v_max!!.progress = global_variables.f
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        B = Mat(width, height, CvType.CV_8UC4)
        F = Mat(width, height, CvType.CV_8UC1)
        Log.d("Before", B.toString())
    }

    override fun onCameraViewStopped() {
        B!!.release()
        F!!.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        var distance = 0.0
        global_variables.a = h_min!!.progress
        global_variables.b = v_min!!.progress
        global_variables.c = s_min!!.progress
        global_variables.d = h_max!!.progress
        global_variables.e = v_max!!.progress
        global_variables.f = s_max!!.progress
        Log.d("TAG", "" + global_variables.a + " " + global_variables.b + " " + global_variables.c + " " + global_variables.d + " " + global_variables.e + " " + global_variables.f)
        F = inputFrame.rgba()
        Imgproc.cvtColor(F, B, Imgproc.COLOR_RGB2HSV)
        val lowerThreshold = Scalar(0.0, 0.0, 0.0) // Blue color – lower hsv values
        val upperThreshold = Scalar(0.0, 0.0, 255.0) // Blue color – higher hsv values
        Core.inRange(B, Scalar(global_variables.a.toDouble(), global_variables.b.toDouble(), global_variables.c.toDouble()), Scalar(global_variables.d.toDouble(), global_variables.e.toDouble(), global_variables.f.toDouble()), B)
        Imgproc.dilate(B, B, Mat())
        Imgproc.dilate(F, F, Mat())
        val hierarchy = Mat()
        val contours: List<MatOfPoint> = ArrayList()
        Imgproc.findContours(B, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE)
        var current_contour: Mat
        var largest_contour: Mat? = null
        var largest_area = 0.0
        var largest_contour_index = 0
        for (contourIdx in contours.indices) {
            current_contour = contours[contourIdx]
            val contourArea = Imgproc.contourArea(current_contour)

            if (contourArea > largest_area) {
                largest_area = contourArea
                largest_contour_index = contourIdx
                largest_contour = current_contour
            }
        }
        Imgproc.drawContours(F, contours, largest_contour_index, Scalar(255.0, 255.0, 0.0, 255.0), 3)
        t = largest_area

        distance = 1301.73836155 * Math.pow(area / t, 0.5)
        val temp = largest_area

        Log.d("TAG", "DISTANCE   $distance")
        t = distance
        //Toast.makeText(getApplicationContext(), (int) distance,Toast.LENGTH_SHORT).show();
        finalDistance = distance
        runOnUiThread { text!!.text = finalDistance.toString() }
        if (finalDistance <= 30 && finalDistance >= 25) {
            val i = Intent(applicationContext, Main3Activity::class.java)
            val v = Bundle()

            startActivity(i)
            finish()
        }
        return F!!
    }

    override fun onPause() {
        super.onPause()
        javaCameraView.disableView()
    }

    override fun onPostResume() {
        super.onPostResume()
        if (OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, baseLoaderCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        javaCameraView.disableView()
    }



}


