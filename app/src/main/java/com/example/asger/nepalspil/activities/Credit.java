package com.example.asger.nepalspil.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;

public class Credit extends AppCompatActivity {
    private Animation animation;
    private ScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit);


        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.credit);
        scroll = (ScrollView) findViewById(R.id.scroll);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        ImageView danida = (ImageView) findViewById(R.id.danida);
        ImageView dtu = (ImageView) findViewById(R.id.dtu);
        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/SF Movie Poster.ttf");
        textView1.setTypeface(face);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTypeface(face);
        //scroll.setText("CREDITS \n \nUdvikler - Asger Stage \n \nUdvikler - Rasmus Olsen \n \nUdvikler - Peter Jensen \n \nUdvikler - Nicki Pedersen \n \nUdvikler - Senad Begovic \n \n \nPROJEKTLEDER & IDÉ \n \nAnne Mette Nordfalk & Skoleliv i Nepal \n \n \nGRAFIK AF \n \nBrian Johannsen \n \n \nI SAMARBEJDE MED \n \n");
/*
        scroll.startAnimation(animation);
        danida.startAnimation(animation);
        dtu.startAnimation(animation);
*/
        h.postDelayed(runnable, 100);
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                h.removeCallbacks(runnable);
                return false;
            }
        });

    }

    Handler h = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scroll.smoothScrollBy(0, 3);
            h.postDelayed(runnable, 100);
        }
    };
}
