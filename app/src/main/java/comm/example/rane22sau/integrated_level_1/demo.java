package comm.example.rane22sau.integrated_level_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class demo extends AppCompatActivity {
    Button voicebt, facebt, colorbt, ocrbt, movebt, gesturebt, pathbt, bluetooth, manual;
    global_variables g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_ac);
        bluetooth = (Button) findViewById(R.id.bluetooth);
        voicebt = (Button) findViewById(R.id.voice);
        facebt = (Button) findViewById(R.id.face);
        colorbt = (Button) findViewById(R.id.color);
        ocrbt = (Button) findViewById(R.id.ocr);
        gesturebt = findViewById(R.id.gesture);
        pathbt = findViewById(R.id.path);
        movebt = findViewById(R.id.move);
        manual = findViewById(R.id.manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bluetooth_intend = new Intent(getApplicationContext(), Manual.class);
                //global_variables gv;
                startActivity(bluetooth_intend);
            }
        });

        voicebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setContentView(R.layout.demo_ac);
                Intent Open_Voice = new Intent(getApplicationContext(), Voice_Control.class);
                startActivity(Open_Voice);
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bluetooth_intend = new Intent(getApplicationContext(), MainActivity.class);


                startActivity(bluetooth_intend);
            }
        });

        facebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_Face_Tracker = new Intent(getApplicationContext(), Face_Tracker.class);
                startActivity(Open_Face_Tracker);
            }
        });

        colorbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_Color = new Intent(getApplicationContext(), Color_Detector.class);
                startActivity(Open_Color);
            }
        });
        ocrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_Ocr = new Intent(getApplicationContext(), OCR_activity.class);
                startActivity(Open_Ocr);
            }
        });
        movebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Open_move = new Intent(getApplicationContext(), Move_dist.class);
                startActivity(Open_move);
            }
        });

    }
}
