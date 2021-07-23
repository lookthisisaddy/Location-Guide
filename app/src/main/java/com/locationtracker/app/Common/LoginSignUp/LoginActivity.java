package com.locationtracker.app.Common.LoginSignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.locationtracker.app.HelperClasses.LoginAdapter.LoginAdapter;
import com.locationtracker.app.R;
import com.locationtracker.app.User.MainActivity;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    RelativeLayout layout1, layout2, layout3;
    ImageView fb, google, twitter;
    Button skip;

    Animation fab_anim;

    SharedPreferences skipOneTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        layout1 = findViewById(R.id.fb_layout);
        layout2 = findViewById(R.id.google_layout);
        layout3 = findViewById(R.id.twi_layout);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fb = findViewById(R.id.fab_fb);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twi);
        skip = findViewById(R.id.skip_btn);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //animation
        fab_anim = AnimationUtils.loadAnimation(this, R.anim.fab_anim);
        layout1.setAnimation(fab_anim);
        layout2.setAnimation(fab_anim);
        layout3.setAnimation(fab_anim);
        tabLayout.setAnimation(fab_anim);

        //set adapter
        LoginAdapter adapter = new LoginAdapter(this);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        skipOneTime = getSharedPreferences("skipOneTime", MODE_PRIVATE);
        boolean firstTime = skipOneTime.getBoolean("firstTime", true);

        if (firstTime) {
            skip.setVisibility(View.VISIBLE);
            skipOneTime.edit().putBoolean("firstTime", false).apply();
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}