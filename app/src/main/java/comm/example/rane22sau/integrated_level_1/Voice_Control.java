package comm.example.rane22sau.integrated_level_1;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class Voice_Control extends MainActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), demo.class);
        startActivity(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice__control);

        ImageView b;
        TextView t;
        Button s;

        t = (TextView) findViewById(R.id.textView);
        b = (ImageView) findViewById(R.id.btn);
        s = (Button) findViewById(R.id.STOP);
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intent, 1);

            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global_variables.getBT() == 1) {
                    sendMessage("S");
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            TextView t;
            t = (TextView) findViewById(R.id.textView);

            ArrayList<String> str = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String print = "";

            for (String s : str) {
                if (s.contains("forward")) {

                    if (global_variables.getBT() == 1) {
                        sendMessage("F");
                    }
                    Log.d("TAG", s);
                    break;
                } else if (s.contains("backward")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("B");
                    }
                    Log.d("TAG", s);
                    break;
                } else if (s.contains("left")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("L");
                    }
                    Log.d("TAG", s);
                    break;
                } else if (s.contains("right")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("R");
                    }
                    Log.d("TAG", s);
                    break;
                } else if (s.contains("stop")) {
                    if (global_variables.getBT() == 1) {
                        sendMessage("S");
                    }
                    Log.d("TAG", s);
                    break;
                }
                t.setText(str.get(0));


            }
            Log.d("TAG", "onActivityResult: " + data + print);


        } else {
            Log.d("TAG", "onactivity else pop up");
            Toast.makeText(getApplicationContext(), "Failed to recognize speech!", Toast.LENGTH_LONG).show();

        }

    }

    public void putString(String str) throws IOException {
        if (MainActivity.mBluetoothAdapter.isEnabled()) {
            boolean isMessageSent = true;
            try {
                OutputStream os = MainActivity.mBluetoothSocket.getOutputStream();
                os.write(str.getBytes());
            } catch (IOException e) {
                isMessageSent = false;
                e.printStackTrace();
            }

        } else {

            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();

        }


    }
}

