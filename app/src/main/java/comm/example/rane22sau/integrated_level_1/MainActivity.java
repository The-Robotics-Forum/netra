package comm.example.rane22sau.integrated_level_1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BT = 1001;
    private static final String TAG = "ConnectThread";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothSocket mBluetoothSocket;
    public static boolean isBluetoothConnected = false;
    global_variables gv;
    private Button button;
    private TextView text_message;
    private ListView lv_paired_devices;
    private ProgressBar progressBar;
    private ArrayList<String> list_paired_devices;
    private ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_paired_devices = findViewById(R.id.lv_paired_devices);
        progressBar = findViewById(R.id.progress_bluetooth);
        list_paired_devices = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<>(getApplicationContext()
                , android.R.layout.simple_list_item_1
                , list_paired_devices);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        lv_paired_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = ((TextView) view).getText().toString().trim();
                String macAddress = data.substring(data.length() - 17);
                new ConnectBluetooth().execute(macAddress);
            }

        });
        isBluetoothConnected = true;

    }

    public void openActivity2() {

        Intent intent = new Intent(this, Option.class);
        startActivity(intent);

    }


    public void onClickListPairedDevices(View view) {


        list_paired_devices.clear();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                String stringList = "Name: " + deviceName + "\n" + "MAC Address: " + deviceHardwareAddress;
                list_paired_devices.add(stringList);
            }
            lv_paired_devices.setAdapter(stringArrayAdapter);
        }
    }



   /* public void onClickSendMessage(View view) {

        String message = text_message.getText().toString().trim();

        if (!message.equals("")) {

            sendMessage(message);

        }

    }*/


    public void sendMessage(String message) {

        Log.d("BT", "in send msg");
        if (isBluetoothConnected) {
            Log.d("BT", "in send if");
            boolean isMessageSent = true;
            try {

                OutputStream os = mBluetoothSocket.getOutputStream();
                os.write(message.getBytes());
            } catch (IOException e) {
                isMessageSent = false;

                e.printStackTrace();
            }
            if (isMessageSent) {
                //Toast.makeText(this, "Sent: " + message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Sent: " + message);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error sending data");
            }
        } else {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickEnableBluetooth(View view) {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else
            Toast.makeText(this, "Bluetooth is already enabled.", Toast.LENGTH_SHORT).show();
    }


    public class ConnectBluetooth extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(strings[0]);
                mBluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(String.valueOf(MY_UUID)));
                mBluetoothAdapter.cancelDiscovery();
                mBluetoothSocket.connect();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage());
                isBluetoothConnected = false;

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            global_variables.setBT(1);
            if (isBluetoothConnected) {
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                list_paired_devices.clear();
                finish();

            } else
                Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);

        }

    }
}