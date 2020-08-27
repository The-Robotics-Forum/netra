package comm.example.rane22sau.integrated_level_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Manual : MainActivity() {
    var right: Button? = null
    var left: Button? = null
    var forward: Button? = null
    var backward: Button? = null
    var stop: Button? = null
    var t: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)
        right = findViewById<View>(R.id.right) as Button
        left = findViewById<View>(R.id.left) as Button
        stop = findViewById<View>(R.id.stop) as Button
        forward = findViewById<View>(R.id.forward) as Button
        backward = findViewById<View>(R.id.backward) as Button
        t = findViewById<View>(R.id.STT) as TextView
        right!!.setOnClickListener {
            if (global_variables.getBT() == 1) {
                sendMessage("R")
                t!!.text = "R"
            }
        }
        left!!.setOnClickListener {
            if (global_variables.getBT() == 1) {
                sendMessage("L")
            }
            t!!.text = "L"
        }
        forward!!.setOnClickListener {
            if (global_variables.getBT() == 1) {
                sendMessage("F")
            }
            t!!.text = "F"
        }
        backward!!.setOnClickListener {
            if (global_variables.getBT() == 1) {
                sendMessage("B")
            }
            t!!.text = "B"
        }
        stop!!.setOnClickListener {
            if (global_variables.getBT() == 1) {
                sendMessage("S")
            }
            t!!.text = "S"
        }
    }
}