package com.locationtracker.app.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.locationtracker.app.Common.LoginSignUp.LoginActivity;
import com.locationtracker.app.HelperClasses.Onboarding.SliderAdapter;
import com.locationtracker.app.R;
import com.locationtracker.app.User.MainActivity;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager slider;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;

    TextView[] dots;

    Button letsStart, next;

    Animation animation;

    SharedPreferences loginPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        //UI elements
        slider = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.linear_layout);
        letsStart = findViewById(R.id.lets_start_btn);
        next = findViewById(R.id.next_btn);

        sliderAdapter = new SliderAdapter(this);
        slider.setAdapter(sliderAdapter);

        setDots(0);

        changeListener.onPageSelected(0);

        slider.addOnPageChangeListener(changeListener);
    }

    private void setDots(int position) {

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.blue_gray_800));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            next.setOnClickListener(view ->
                    slider.setCurrentItem(position + 1));

            setDots(position);

            if (position == 0) {
                letsStart.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                letsStart.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                letsStart.setVisibility(View.INVISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this, R.anim.onboarding_anim);
                letsStart.setAnimation(animation);
                letsStart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void onBoardingSkipped(View view) {
        firstTimeCheck();
    }

    public void startClicked(View view) {
        firstTimeCheck();
    }

    private void firstTimeCheck(){
        loginPref = getSharedPreferences("loginPref", MODE_PRIVATE);
        boolean firstTime = loginPref.getBoolean("firstTime", true);

        if (firstTime) {
            loginPref.edit().putBoolean("firstTime", false).apply();
            Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}