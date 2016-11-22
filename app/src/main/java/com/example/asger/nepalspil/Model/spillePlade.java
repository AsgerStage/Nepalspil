package com.example.asger.nepalspil.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.asger.nepalspil.R;

/**
 * Created by Nicki on 22-11-2016.
 */

public class spillePlade extends AppCompatActivity {

    Spiller s = new Spiller("Adam", 50);        //Dummy spiller
    ImageView im = (ImageView) findViewById(R.id.felt1);


        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);


        im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                s.setTid(s.getTid()-1);
                s.move(1);
                im.setColorFilter(android.R.color.holo_green_dark);
                
            }
        });
    }
}

