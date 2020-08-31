package com.vitpunerobotics.netra.activities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.vitpunerobotics.netra.R
import com.vitpunerobotics.netra.global_variables
import java.io.IOException
import java.util.*

open class MainActivity : AppCompatActivity() {

    private val button: Button? = null
    private val text_message: TextView? = null
    private lateinit var lv_paired_devices: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var list_paired_devices: ArrayList<String>
    private lateinit var stringArrayAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lv_paired_devices = findViewById(R.id.lv_paired_devices)
        progressBar = findViewById(R.id.progress_bluetooth)
        list_paired_devices = ArrayList()
        stringArrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, list_paired_devices)
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        lv_paired_devices.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val data = (view as TextView).text.toString().trim { it <= ' ' }
            val macAddress = data.substring(data.length - 17)
            ConnectBluetooth().execute(macAddress)
        }
        isBluetoothConnected = true
    }

    fun onClickListPairedDevices(view: View?) {
        list_paired_devices.clear()
        val pairedDevices = mBluetoothAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                val stringList = "Name: $deviceName\nMAC Address: $deviceHardwareAddress"
                list_paired_devices.add(stringList)
            }
            lv_paired_devices.adapter = stringArrayAdapter
        }
    }


    fun sendMessage(message: String) {
        Log.d("BT", "in send msg")
        if (isBluetoothConnected) {
            Log.d("BT", "in send if")
            var isMessageSent = true
            try {
                val os = mBluetoothSocket!!.outputStream
                os.write(message.toByteArray())
            } catch (e: IOException) {
                isMessageSent = false
                e.printStackTrace()
            }
            if (isMessageSent) {
                Log.d(TAG, "Sent: $message")
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Error sending data")
            }
        } else {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickEnableBluetooth(view: View?) {
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else Toast.makeText(this, "Bluetooth is already enabled.", Toast.LENGTH_SHORT).show()
    }

    inner class ConnectBluetooth : AsyncTask<String?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): Void? {
            try {
                val device = mBluetoothAdapter!!.getRemoteDevice(strings[0])
                mBluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID.toString()))
                mBluetoothAdapter!!.cancelDiscovery()
                mBluetoothSocket!!.connect()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, e.message)
                isBluetoothConnected = false
            }
            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            global_variables.bT = 1
            if (isBluetoothConnected) {
                Toast.makeText(this@MainActivity, "Connected", Toast.LENGTH_SHORT).show()
                list_paired_devices.clear()
                finish()
            } else Toast.makeText(this@MainActivity, "Connection Failed", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1001
        private const val TAG = "ConnectThread"
        private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var mBluetoothAdapter: BluetoothAdapter? = null
        var mBluetoothSocket: BluetoothSocket? = null
        var isBluetoothConnected = false
    }
}