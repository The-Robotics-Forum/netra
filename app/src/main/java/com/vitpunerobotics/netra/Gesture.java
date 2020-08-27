package com.vitpunerobotics.netra;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class Gesture extends MainActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    public String Command = "Hello";
    int a, b, c, d, e, f, initial_x;
    SeekBar h_min, h_max, v_min, v_max, s_min, s_max;
    ImageView setting, red1, blue1, green1, scroll;
    Button done;
    double CurrentArea = 0;
    int set_vis = 0, hidden = 0;
    Mat A, B, C;
    private JavaCameraView javaCameraView;
    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), demo.class);
        startActivity(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color__detector);
        red1 = findViewById(R.id.red);
        blue1 = findViewById(R.id.blue);
        green1 = findViewById(R.id.green);
        scroll = findViewById(R.id.scroll);
        setting = findViewById(R.id.settings);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set_vis == 0) {
                    set_vis = 1;
                    red1.setVisibility(View.VISIBLE);
                    green1.setVisibility(View.VISIBLE);
                    blue1.setVisibility(View.VISIBLE);
                    scroll.setVisibility(View.VISIBLE);
                } else {
                    if (set_vis == 1) {
                        set_vis = 0;
                        red1.setVisibility(View.INVISIBLE);
                        green1.setVisibility(View.INVISIBLE);
                        blue1.setVisibility(View.INVISIBLE);
                        scroll.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        h_min = findViewById(R.id.h_min);
        h_max = findViewById(R.id.h_max);
        v_min = findViewById(R.id.v_min);
        v_max = findViewById(R.id.v_max);
        s_min = findViewById(R.id.s_min);
        s_max = findViewById(R.id.s_max);
        h_max.setMax(255);
        h_min.setMax(255);
        v_max.setMax(255);
        v_min.setMax(255);
        s_max.setMax(255);
        s_min.setMax(255);
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_max.setVisibility(View.INVISIBLE);
                h_min.setVisibility(View.INVISIBLE);
                s_max.setVisibility(View.INVISIBLE);
                s_min.setVisibility(View.INVISIBLE);
                v_min.setVisibility(View.INVISIBLE);
                v_max.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
            }
        });
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_max.setVisibility(View.VISIBLE);
                h_min.setVisibility(View.VISIBLE);
                s_max.setVisibility(View.VISIBLE);
                s_min.setVisibility(View.VISIBLE);
                v_min.setVisibility(View.VISIBLE);
                v_max.setVisibility(View.VISIBLE);
                red1.setVisibility(View.INVISIBLE);
                green1.setVisibility(View.INVISIBLE);
                blue1.setVisibility(View.INVISIBLE);
                scroll.setVisibility(View.INVISIBLE);
                done.setVisibility(View.VISIBLE);
            }
        });
        red1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_min.setProgress(0);
                h_max.setProgress(0);
                s_min.setProgress(100);
                s_max.setProgress(255);
                v_min.setProgress(100);
                v_max.setProgress(255);
                red1.setVisibility(View.INVISIBLE);
                green1.setVisibility(View.INVISIBLE);
                blue1.setVisibility(View.INVISIBLE);
                scroll.setVisibility(View.INVISIBLE);
            }
        });
        blue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_min.setProgress(240);
                h_max.setProgress(255);
                s_min.setProgress(100);
                s_max.setProgress(255);
                v_min.setProgress(100);
                v_max.setProgress(255);
                red1.setVisibility(View.INVISIBLE);
                green1.setVisibility(View.INVISIBLE);
                blue1.setVisibility(View.INVISIBLE);
                scroll.setVisibility(View.INVISIBLE);
            }
        });
        green1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_min.setProgress(120);
                h_max.setProgress(150);
                s_min.setProgress(100);
                s_max.setProgress(255);
                v_min.setProgress(50);
                v_max.setProgress(155);
                red1.setVisibility(View.INVISIBLE);
                green1.setVisibility(View.INVISIBLE);
                blue1.setVisibility(View.INVISIBLE);
                scroll.setVisibility(View.INVISIBLE);
            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }

        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        javaCameraView = findViewById(R.id.cameraView);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, baseLoaderCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        A = new Mat(width, height, CvType.CV_8UC4);
        B = new Mat(width, height, CvType.CV_8UC1);
        C = new Mat(width, height, CvType.CV_8UC4);
        Log.d("Hello", String.valueOf(A));
    }

    @Override
    public void onCameraViewStopped() {
        A.release();
        B.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        a = h_min.getProgress();
        b = v_min.getProgress();
        c = s_min.getProgress();
        d = h_max.getProgress();
        e = v_max.getProgress();
        f = s_max.getProgress();

        Log.d("TAG", "" + a + "," + b + "," + c + " " + d + "," + e + "," + f);

        Imgproc.cvtColor(inputFrame.rgba(), A, Imgproc.COLOR_RGB2HSV);
        Core.inRange(A, new Scalar(a, c, b), new Scalar(d, e, f), B); // Blue Color

        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        //Log.d("TAG","Before Toast");
        Imgproc.findContours(B, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat current_contour, largest_contour = null;
        double largest_area = 0;
        int largest_contour_index = 0;

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            current_contour = contours.get(contourIdx);
            double contourArea = Imgproc.contourArea(current_contour);
            if (contourArea > largest_area) {
                largest_area = contourArea;
                largest_contour_index = contourIdx;
                largest_contour = current_contour;
            }
        }

        if (largest_contour != null) {
            Moments p = Imgproc.moments(largest_contour);
            int x = (int) (p.get_m10() / p.get_m00());
            int y = (int) (p.get_m01() / p.get_m00());

            Direction(x, y, largest_area);
            A = inputFrame.rgba();
            Imgproc.drawContours(A, contours, largest_contour_index, new Scalar(0, 255, 0, 255), 3);
        }

        return A;
    }

    public void Direction(int x, int y, double largest_area) {
        int div, sub;
        div = 2700;
        sub = 100;
        if (CurrentArea == 0)
            CurrentArea = largest_area;
        if (initial_x == 0)
            initial_x = x;
        if ((int) (CurrentArea / div) < (int) (largest_area / div) && !Command.equals("Backward")) {
            //    Toast.makeText(getApplicationContext(),"Forward",Toast.LENGTH_SHORT).show();
            Command = "Backward";
            Log.d("COMMAND", "Backward");
        } else if ((int) (CurrentArea / div) > (int) (largest_area / div) && !Command.equals("Forward")) {
            //  Toast.makeText(getApplicationContext(),"Backward",Toast.LENGTH_SHORT).show();
            Command = "Forward";
            Log.d("COMMAND", "Forward");
        } else if (0 <= x && x <= 1800 / 4 && !Command.equals("Rotate Left")) {
            Log.d("COMMAND", "Rotate Left");

            Command = "Rotate Left";
            send(Command);
        } else if (1800 * 3 / 4 <= x && x <= 1800 && !Command.equals("Rotate Right")) {
            Log.d("COMMAND", "Rotate Right");
            Command = "Rotate Right";
            send(Command);
        } else if (initial_x / sub < x / sub && !Command.equals("Move Left")) {
            Log.d("COMMAND", "Move Left");
            Command = "Move Left";
            send(Command);
        } else if (initial_x / sub > x / sub && !Command.equals("Move Right")) {
            Log.d("COMMAND", "Move Right");
            Command = "Move Right";
            send(Command);
        } else if (!Command.equals("Stop")) {
            Log.d("COMMAND", "Stop");
            Command = "Stop";
            send(Command);
        }
    }

    public void send(String messageStr) {
    }

}