package com.example.ham.mkbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash_screen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MK", MODE_PRIVATE);
        final String name = sharedPreferences.getString("mk", "");
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    if (name.isEmpty()) {
                        startActivity(new Intent(splash_screen.this, login_Register.class));
                        finish();

                    } else {
                        startActivity(new Intent(splash_screen.this, MainActivity.class));
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();


    }
}
