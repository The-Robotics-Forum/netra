package comm.example.rane22sau.integrated_level_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashScreen extends MainActivity {
    ImageView Im1, Im2;
    Animation fade_in, scale_big;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Im1 = findViewById(R.id.imageView2);
        Im2 = findViewById(R.id.im2);
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    fade_in = AnimationUtils.loadAnimation(splashScreen.this, R.anim.fade_in);
                    scale_big = AnimationUtils.loadAnimation(splashScreen.this, R.anim.scale_big);
                    // After 5 seconds redirect to another intent
                    //Remove activity
                    Im1.startAnimation(fade_in);
                    Im2.startAnimation(scale_big);
                    sleep(3 * 1000);
                } catch (Exception e) {

                } finally {
                    Im2.setVisibility(View.INVISIBLE);
                    Im1.setVisibility(View.INVISIBLE);
                    //Intent i=new Intent(getBaseContext(),Home.class);
                    Intent i = new Intent(getBaseContext(), demo.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_down, R.anim.fade_out);
                    finish();
                }
            }
        };
        // start thread
        background.start();

    }
}
