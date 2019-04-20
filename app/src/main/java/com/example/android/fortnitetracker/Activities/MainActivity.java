package com.example.android.fortnitetracker.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.android.fortnitetracker.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 3000;

    TextView splashText;
    Typeface fortniteFont;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashText = (TextView) findViewById(R.id.tv_fortnite);
        fortniteFont = Typeface.createFromAsset(getAssets(), getString(R.string.fortnite_font_resource));
        splashText.setTypeface(fortniteFont);

        animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        splashText.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
