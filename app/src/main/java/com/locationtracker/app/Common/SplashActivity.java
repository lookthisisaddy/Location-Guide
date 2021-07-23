package com.locationtracker.app.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.locationtracker.app.R;
import com.locationtracker.app.User.MainActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView logoImageView;
    TextView copyrightsTextView;

    Animation logo_anim, bottom_anim;

    SharedPreferences onBoardingPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        logoImageView = findViewById(R.id.logo_image);
        copyrightsTextView = findViewById(R.id.copyrights);

        logo_anim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        logoImageView.setAnimation(logo_anim);
        copyrightsTextView.setAnimation(bottom_anim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingPref = getSharedPreferences("onBoardingPref", MODE_PRIVATE);

                boolean firstTime = onBoardingPref.getBoolean("firstTime", true);

                if (firstTime) {
                    onBoardingPref.edit().putBoolean("firstTime", false).apply();
                    Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                finish();

            }
        }, 3000);
    }
}