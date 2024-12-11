package com.example.userauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class FlashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 6000; // Duration in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        // Use a Handler to delay the transition to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(FlashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        }, SPLASH_SCREEN_DURATION);
    }
}