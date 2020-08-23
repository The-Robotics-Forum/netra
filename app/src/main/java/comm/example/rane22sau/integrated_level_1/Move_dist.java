package comm.example.rane22sau.integrated_level_1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class Move_dist extends MainActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    public static int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;
    int send = 0;
    Switch connectbt;
    double finalDistance;
    double t = 0, area = 3.14 * 4;
    SeekBar h_min, h_max, v_min, v_max, s_min, s_max;
    global_variables gv;
    Button red, blue, green;
    TextView text;
    private JavaCameraView javaCameraView;
    private Mat B, F;
    //te=findViewById(R.id.dist);
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent BTconnect = new Intent(getApplicationContext(), MainActivity.class);
        if (global_variables.BT == 0) {
            startActivity(BTconnect);

        }


        setContentView(R.layout.activity_move_dist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        red = (Button) findViewById(R.id.Red);
        blue = (Button) findViewById(R.id.Blue);
        green = (Button) findViewById(R.id.Green);
        text = findViewById(R.id.dist);
        text.setText(String.valueOf(t));

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*h_min.setProgress(0); REAL_RED Color
                h_max.setProgress(10);
                s_min.setProgress(150);
                s_max.setProgress(255);
                v_min.setProgress(150);
                v_max.setProgress(255);*/
                h_min.setProgress(161);
                h_max.setProgress(188);
                s_min.setProgress(121);
                s_max.setProgress(255);
                v_min.setProgress(194);
                v_max.setProgress(255);


            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_min.setProgress(240);
                h_max.setProgress(255);
                s_min.setProgress(100);
                s_max.setProgress(255);
                v_min.setProgress(100);
                v_max.setProgress(255);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h_min.setProgress(120);
                h_max.setProgress(150);
                s_min.setProgress(100);
                s_max.setProgress(255);
                v_min.setProgress(50);
                v_max.setProgress(155);


            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }

        javaCameraView = (JavaCameraView) findViewById(R.id.javaCameraView);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

        h_min = (SeekBar) findViewById(R.id.h_min);
        h_max = (SeekBar) findViewById(R.id.h_max);
        v_min = (SeekBar) findViewById(R.id.v_min);
        v_max = (SeekBar) findViewById(R.id.v_max);
        s_min = (SeekBar) findViewById(R.id.s_min);
        s_max = (SeekBar) findViewById(R.id.s_max);

        h_max.setMax(255);
        h_min.setMax(255);
        v_max.setMax(255);
        v_min.setMax(255);
        s_max.setMax(255);
        s_min.setMax(255);
        //Core.inRange(B,new Scalar(gv.a,gv.c,gv.b), new Scalar(gv.d,gv.e,gv.f),B);
        h_min.setProgress(global_variables.getA());
        h_max.setProgress(global_variables.getD());
        s_min.setProgress(global_variables.getC());
        s_max.setProgress(global_variables.getE());
        v_min.setProgress(global_variables.getB());
        v_max.setProgress(global_variables.getF());

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        B = new Mat(width, height, CvType.CV_8UC4);
        F = new Mat(width, height, CvType.CV_8UC1);
        Log.d("Before", String.valueOf(B));
    }

    @Override
    public void onCameraViewStopped() {
        B.release();
        F.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        double distance = 0;
        global_variables.a = h_min.getProgress();
        global_variables.b = v_min.getProgress();
        global_variables.c = s_min.getProgress();
        global_variables.d = h_max.getProgress();
        global_variables.e = v_max.getProgress();
        global_variables.f = s_max.getProgress();
        Log.d("TAG", "" + global_variables.a + " " + global_variables.b + " " + global_variables.c + " " + global_variables.d + " " + global_variables.e + " " + global_variables.f);
        F = inputFrame.rgba();
        Imgproc.cvtColor(F, B, Imgproc.COLOR_RGB2HSV);
        Scalar lowerThreshold = new Scalar(0, 0, 0); // Blue color – lower hsv values
        Scalar upperThreshold = new Scalar(0, 0, 255); // Blue color – higher hsv values
        //Core.inRange (B, lowerThreshold , upperThreshold, B );
        Core.inRange(B, new Scalar(global_variables.a, global_variables.b, global_variables.c), new Scalar(global_variables.d, global_variables.e, global_variables.f), B);
        Imgproc.dilate(B, B, new Mat());
        Imgproc.dilate(F, F, new Mat());
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(B, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);


        Mat current_contour, largest_contour = null;
        double largest_area = 0;
        int largest_contour_index = 0;

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            current_contour = contours.get(contourIdx);
            double contourArea = Imgproc.contourArea(current_contour);
            //Imgproc.drawContours ( F, contours, contourIdx,new Scalar(255, 255, 0, 255),3);
            if (contourArea > largest_area) {
                largest_area = contourArea;
                largest_contour_index = contourIdx;
                largest_contour = current_contour;

            }
        }
        Imgproc.drawContours(F, contours, largest_contour_index, new Scalar(255, 255, 0, 255), 3);

        t = largest_area;
        //distance=422.3657139*Math.pow((3.14*4)/largest_area,0.5);
        distance = 1301.73836155 * Math.pow((area) / t, 0.5);
        final double temp = largest_area;
        //distance=26*Math.pow(330/largest_area,0.5);
        //Log.d("TAG","pixel area "+temp);
        //Log.d("TAG", "Area  "+largest_contour);

        Log.d("TAG", "DISTANCE   " + distance);
        t = distance;
        //Toast.makeText(getApplicationContext(), (int) distance,Toast.LENGTH_SHORT).show();
        finalDistance = (int) distance;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(String.valueOf(finalDistance));
            }
        });
        if (finalDistance <= 30 && finalDistance >= 25) {
            Intent i = new Intent(getApplicationContext(), Main3Activity.class);
            Bundle v = new Bundle();
            /*v.putInt("a",a);
            v.putInt("b",b);
            v.putInt("c",c);
            v.putInt("d",d);
            v.putInt("e",e);
            v.putInt("f",f);
            i.putExtra("masking_values",v);
*/
            startActivity(i);
            finish();
        }


        return F;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null)
            javaCameraView.disableView();
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
}

