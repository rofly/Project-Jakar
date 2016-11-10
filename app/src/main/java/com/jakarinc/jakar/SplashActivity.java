package com.jakarinc.jakar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jakarinc.jakar.Controller.Main.MainActivity;
import com.jakarinc.jakar.RemoteIO.ProgressDialogCall;

/**
 * Created by Henrique on 10/11/2016.
 */
public class SplashActivity extends Activity {

        // Splash screen timer
        private static int SPLASH_TIME_OUT = 6000;
        private AnimationDrawable animation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_theme);
            ImageView la = (ImageView) findViewById(R.id.animation);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            boolean b = new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }