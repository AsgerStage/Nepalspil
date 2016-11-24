package com.example.asger.nepalspil.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asger.nepalspil.R;

import static com.example.asger.nepalspil.MainActivity.spiller;

/**
 * Created by Nicki on 22-11-2016.
 */

public class spillePlade extends AppCompatActivity {

   protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);
        ImageButton felt0 = (ImageButton) findViewById(R.id.felt0);
        ImageButton felt1 = (ImageButton) findViewById(R.id.felt1);
        ImageButton felt2 = (ImageButton) findViewById(R.id.felt2);
        ImageButton felt3 = (ImageButton) findViewById(R.id.felt3);
        ImageButton felt4 = (ImageButton) findViewById(R.id.felt4);
        ImageButton felt5 = (ImageButton) findViewById(R.id.felt5);
        ImageButton felt6 = (ImageButton) findViewById(R.id.felt6);
        ImageButton felt7 = (ImageButton) findViewById(R.id.felt7);
       TextView infobox = (TextView) findViewById(R.id.infobox);
       infobox.setText("        Navn:"+spiller.getNavn()+"\n        Mad:"+spiller.getHp()+"\n        Penge: "+spiller.getPenge()+"\n        Viden: "+spiller.getViden()+"\n        Klassetrin: "+spiller.getKlassetrin()+"\n        Tid: "+spiller.getTid());

        felt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              //  moveTo(felt1);
                Intent intent = new Intent(spillePlade.this, Farm.class);
                startActivity(intent);
            }
        });



       felt1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {


               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

       felt2.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {


               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

       felt3.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {


               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

       felt4.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {


               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

       felt5.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {


               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

       felt6.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {


               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

       felt7.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               Intent intent = new Intent(spillePlade.this, Farm.class);
               startActivity(intent);
           }
       });

   }
    /*protected void moveTo(ImageButton v){
        felt0.setColorFilter(null);
        felt1.setColorFilter(null);
        felt2.setColorFilter(null);
        felt3.setColorFilter(null);
        felt4.setColorFilter(null);
        felt5.setColorFilter(null);
        felt6.setColorFilter(null);
        felt7.setColorFilter(null);
        v.setColorFilter(android.R.color.holo_green_dark);
    }*/


}

