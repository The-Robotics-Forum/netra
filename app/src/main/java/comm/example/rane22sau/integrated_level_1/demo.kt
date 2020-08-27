package comm.example.rane22sau.integrated_level_1

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class demo : AppCompatActivity() {
    var voicebt: Button? = null
    var facebt: Button? = null
    var colorbt: Button? = null
    var ocrbt: Button? = null
    var movebt: Button? = null
    var gesturebt: Button? = null
    var pathbt: Button? = null
    var bluetooth: Button? = null
    var manual: Button? = null
    var g: global_variables? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_ac)
        bluetooth = findViewById<View>(R.id.bluetooth) as Button
        voicebt = findViewById<View>(R.id.voice) as Button
        facebt = findViewById<View>(R.id.face) as Button
        colorbt = findViewById<View>(R.id.color) as Button
        ocrbt = findViewById<View>(R.id.ocr) as Button
        gesturebt = findViewById(R.id.gesture)
        pathbt = findViewById(R.id.path)
        movebt = findViewById(R.id.move)
        manual = findViewById(R.id.manual)
        manual.setOnClickListener(View.OnClickListener {
            val bluetooth_intend = Intent(applicationContext, Manual::class.java)
            //global_variables gv;
            startActivity(bluetooth_intend)
        })
        voicebt!!.setOnClickListener {
            setContentView(R.layout.demo_ac)
            val Open_Voice = Intent(applicationContext, Voice_Control::class.java)
            startActivity(Open_Voice)
        }
        bluetooth!!.setOnClickListener {
            val bluetooth_intend = Intent(applicationContext, MainActivity::class.java)
            startActivity(bluetooth_intend)
        }
        facebt!!.setOnClickListener {
            val Open_Face_Tracker = Intent(applicationContext, Face_Tracker::class.java)
            startActivity(Open_Face_Tracker)
        }
        colorbt!!.setOnClickListener {
            val Open_Color = Intent(applicationContext, Color_Detector::class.java)
            startActivity(Open_Color)
        }
        ocrbt!!.setOnClickListener {
            val Open_Ocr = Intent(applicationContext, OCR_activity::class.java)
            startActivity(Open_Ocr)
        }
        movebt.setOnClickListener(View.OnClickListener {
            val Open_move = Intent(applicationContext, Move_dist::class.java)
            startActivity(Open_move)
        })
    }
}