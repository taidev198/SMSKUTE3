package com.example.traig.smskute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
         ImageView imageView = findViewById(R.id.img);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
         Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        imageView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
              //  imageView.startAnimation(animation1);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade);
                Intent intent = new Intent(welcome.this,MainActivity.class);

                startActivity(intent);

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
