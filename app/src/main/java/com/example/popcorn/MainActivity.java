package com.example.popcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.popcorn.controllers.activities.AuthActivity;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_SCREEN_TIME_OUT);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                } finally {
                    Intent openMainActivity = new Intent(getApplicationContext(), AuthActivity.class);
                    startActivity(openMainActivity );

                }
            }
        };

        timer.start();
    }
}