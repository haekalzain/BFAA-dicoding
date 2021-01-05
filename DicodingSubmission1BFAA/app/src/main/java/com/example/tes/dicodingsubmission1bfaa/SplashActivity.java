package com.example.tes.dicodingsubmission1bfaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.tes.dicodingsubmission1bfaa.activity.UserListActivity;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logo = findViewById(R.id.logo);
        AVLoadingIndicatorView avi = findViewById(R.id.avi);
        avi.setIndicator("BallClipRotateMultipleIndicator");
        new Handler().postDelayed(() -> {
            Intent intent= new Intent(SplashActivity.this, UserListActivity.class);
            startActivity(intent);
            finish();
        },3000);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        logo.startAnimation(myanim);
    }
}
