package com.vitpunerobotics.netra.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import com.vitpunerobotics.netra.R
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.android.JavaCameraView
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.util.*

class ColorDetectionActivity : BluetoothActivity(), CvCameraViewListener2 {

    var h_min: SeekBar? = null
    var h_max: SeekBar? = null
    var v_min: SeekBar? = null
    var v_max: SeekBar? = null
    var s_min: SeekBar? = null
    var s_max: SeekBar? = null

    var set_vis = 0

    var A: Mat? = null
    var B: Mat? = null
    var C: Mat? = null
    private lateinit var javaCameraView: JavaCameraView
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
        val b = Intent(applicationContext, HomeActivity::class.java)
        startActivity(b)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_detection)
        var red1 = findViewById<ImageView>(R.id.red)
        var blue1 = findViewById<ImageView>(R.id.blue)
        var green1 = findViewById<ImageView>(R.id.green)
        var scroll = findViewById<ImageView>(R.id.scroll)
        var setting = findViewById<ImageView>(R.id.settings)
        setting.setOnClickListener(View.OnClickListener {
            if (set_vis == 0) {
                set_vis = 1
                red1.visibility = View.VISIBLE
                green1.visibility = View.VISIBLE
                blue1.visibility = View.VISIBLE
                scroll.visibility = View.VISIBLE
            } else {
                if (set_vis == 1) {
                    set_vis = 0
                    red1.visibility = View.INVISIBLE
                    green1.visibility = View.INVISIBLE
                    blue1.visibility = View.INVISIBLE
                    scroll.visibility = View.INVISIBLE
                }
            }
        })
        h_min = findViewById(R.id.h_min)
        h_max = findViewById(R.id.h_max)
        v_min = findViewById(R.id.v_min)
        v_max = findViewById(R.id.v_max)
        s_min = findViewById(R.id.s_min)
        s_max = findViewById(R.id.s_max)
        h_max?.max = 255
        h_min?.max = 255
        v_max?.max = 255
        v_min?.max = 255
        s_max?.max = 255
        s_min?.max = 255
        var done = findViewById<Button>(R.id.done)
        done.setOnClickListener(View.OnClickListener {
            h_max?.visibility = View.INVISIBLE
            h_min?.visibility = View.INVISIBLE
            s_max?.visibility = View.INVISIBLE
            s_min?.visibility = View.INVISIBLE
            v_min?.visibility = View.INVISIBLE
            v_max?.visibility = View.INVISIBLE
            done.visibility = View.INVISIBLE
        })
        scroll.setOnClickListener(View.OnClickListener {
            h_max!!.visibility = View.VISIBLE
            h_min!!.visibility = View.VISIBLE
            s_max!!.visibility = View.VISIBLE
            s_min!!.visibility = View.VISIBLE
            v_min!!.visibility = View.VISIBLE
            v_max!!.visibility = View.VISIBLE
            red1.visibility = View.INVISIBLE
            green1.visibility = View.INVISIBLE
            blue1.visibility = View.INVISIBLE
            scroll.visibility = View.INVISIBLE
            done.visibility = View.VISIBLE
        })
        red1.setOnClickListener(View.OnClickListener {
            h_min!!.progress = 0
            h_max!!.progress = 0
            s_min!!.progress = 100
            s_max!!.progress = 255
            v_min!!.progress = 100
            v_max!!.progress = 255
            red1.visibility = View.INVISIBLE
            green1.visibility = View.INVISIBLE
            blue1.visibility = View.INVISIBLE
            scroll.visibility = View.INVISIBLE
        })
        blue1.setOnClickListener(View.OnClickListener {
            h_min!!.progress = 240
            h_max!!.progress = 255
            s_min!!.progress = 100
            s_max!!.progress = 255
            v_min!!.progress = 100
            v_max!!.progress = 255
            red1.visibility = View.INVISIBLE
            green1.visibility = View.INVISIBLE
            blue1.visibility = View.INVISIBLE
            scroll.visibility = View.INVISIBLE
        })
        green1.setOnClickListener(View.OnClickListener {
            h_min!!.progress = 120
            h_max!!.progress = 150
            s_min!!.progress = 100
            s_max!!.progress = 255
            v_min!!.progress = 50
            v_max!!.progress = 155
            red1.visibility = View.INVISIBLE
            green1.visibility = View.INVISIBLE
            blue1.visibility = View.INVISIBLE
            scroll.visibility = View.INVISIBLE
        })
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        javaCameraView = findViewById(R.id.cameraView)
        javaCameraView.visibility = SurfaceView.VISIBLE
        javaCameraView.setCvCameraViewListener(this)
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

    override fun onPause() {
        super.onPause()
        javaCameraView.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        A = Mat(width, height, CvType.CV_8UC4)
        B = Mat(width, height, CvType.CV_8UC1)
        C = Mat(width, height, CvType.CV_8UC4)
        Log.d("Hello", A.toString())
    }

    override fun onCameraViewStopped() {
        A!!.release()
        B!!.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        var a = h_min!!.progress
        var b = v_min!!.progress
        var c = s_min!!.progress
        var d = h_max!!.progress
        var e = v_max!!.progress
        var f = s_max!!.progress
        Log.d("TAG", "$a,$b,$c $d,$e,$f")
        Imgproc.cvtColor(inputFrame.rgba(), A, Imgproc.COLOR_RGB2HSV)
        Core.inRange(A, Scalar(a.toDouble(), c.toDouble(), b.toDouble()), Scalar(d.toDouble(), e.toDouble(), f.toDouble()), B) // Blue Color
        val hierarchy = Mat()
        val contours: List<MatOfPoint> = ArrayList()

        //Log.d("TAG","Before Toast");
        Imgproc.findContours(B, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
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
        if (largest_contour != null) {
            val p = Imgproc.moments(largest_contour)
            val x = (p._m10 / p._m00).toInt()
            val y = (p._m01 / p._m00).toInt()
            A = inputFrame.rgba()
            Imgproc.drawContours(A, contours, largest_contour_index, Scalar(0.0, 255.0, 0.0, 255.0), 3)
        }
        return A!!
    }


}
