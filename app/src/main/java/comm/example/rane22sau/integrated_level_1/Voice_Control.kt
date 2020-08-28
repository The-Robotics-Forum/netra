package comm.example.rane22sau.integrated_level_1

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.util.*

class Voice_Control : MainActivity() {
    override fun onBackPressed() {
        super.onBackPressed()
        val b = Intent(applicationContext, demo::class.java)
        startActivity(b)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice__control)

        val t: TextView = findViewById<View>(R.id.textView) as TextView
        val b: ImageView = findViewById<View>(R.id.btn) as ImageView
        val s: Button = findViewById<View>(R.id.STOP) as Button
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
        b.setOnClickListener { startActivityForResult(intent, 1) }
        s.setOnClickListener {
            if (global_variables.getBT() == 1) {
                sendMessage("S")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val t: TextView
            t = findViewById<View>(R.id.textView) as TextView
            val str = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val print = ""
            for (s in str) {
                if (s.contains("forward")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("F")
                    }
                    Log.d("TAG", s)
                    break
                } else if (s.contains("backward")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("B")
                    }
                    Log.d("TAG", s)
                    break
                } else if (s.contains("left")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("L")
                    }
                    Log.d("TAG", s)
                    break
                } else if (s.contains("right")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("R")
                    }
                    Log.d("TAG", s)
                    break
                } else if (s.contains("stop")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("S")
                    }
                    Log.d("TAG", s)
                    break
                }
                t.text = str[0]
            }
            Log.d("TAG", "onActivityResult: $data$print")
        } else {
            Log.d("TAG", "onactivity else pop up")
            Toast.makeText(applicationContext, "Failed to recognize speech!", Toast.LENGTH_LONG).show()
        }
    }

    @Throws(IOException::class)
    fun putString(str: String) {
        if (mBluetoothAdapter.isEnabled) {
            var isMessageSent = true
            try {
                val os = mBluetoothSocket.outputStream
                os.write(str.toByteArray())
            } catch (e: IOException) {
                isMessageSent = false
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show()
        }
    }
}