package com.example.asger.nepalspil.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;

public class Credit extends AppCompatActivity {
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit);


        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.credit);
        TextView scroll = (TextView) findViewById(R.id.textView);
        ImageView danida = (ImageView) findViewById(R.id.danida);
        ImageView dtu = (ImageView) findViewById(R.id.dtu);
        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/SF Movie Poster.ttf");
        scroll.setTypeface(face);

        scroll.setText("CREDITS \n \n" + "Udvikler - Asger Stage \n \n" + "Udvikler - Rasmus Olsen \n \n" + "Udvikler - Peter Jensen \n \n" + "Udvikler - Nicki Pedersen \n \n" + "Udvikler - Senad Begovic \n \n \n" + "PROJEKTLEDER & IDÉ \n \n" + "Anne Mette Nordfalk & Skoleliv i Nepal \n \n \n" + "GRAFIK AF \n \n" + "Brian Johannsen \n \n \n" + "I SAMARBEJDE MED \n \n");

        scroll.startAnimation(animation);
        danida.startAnimation(animation);
        dtu.startAnimation(animation);


    }
}
