package com.example.asger.nepalspil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Asger on 07-11-2016.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView im = (ImageView) findViewById(R.id.imageView);

        im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, imageViewPop.class));
                if (confirmation().moveToField() == true){
                    setPlayerPos;
                    updateTimer;
                }
                else
                    closeWindow;
            });

        }

    }
