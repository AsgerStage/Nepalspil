package com.example.asger.nepalspil.models;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.fragments.Farm;
import com.example.asger.nepalspil.fragments.Marked;
import com.example.asger.nepalspil.fragments.Skole;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Nicki on 22-11-2016.
 */

public class SpillePlade extends AppCompatActivity {
    TextView infobox;
    ImageView star;


   protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);
       final ImageButton felt0 = (ImageButton) findViewById(R.id.felt0);
       final ImageButton felt1 = (ImageButton) findViewById(R.id.felt1);
       final ImageButton felt2 = (ImageButton) findViewById(R.id.felt2);
       final ImageButton felt3 = (ImageButton) findViewById(R.id.felt3);
       final ImageButton felt4 = (ImageButton) findViewById(R.id.felt4);
       final ImageButton felt5 = (ImageButton) findViewById(R.id.felt5);
       final ImageButton felt6 = (ImageButton) findViewById(R.id.felt6);
       final ImageButton felt7 = (ImageButton) findViewById(R.id.felt7);
       star= (ImageView) findViewById(R.id.imageView);
       infobox = (TextView) findViewById(R.id.infobox);
       //infobox.setText("Navn: "+spiller.getNavn()+"\n Mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());
        updateInfobox();

        felt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(0,Farm.class,felt0.getLayoutParams(),felt0.getLayoutParams());
            }
        });



       felt1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(1,Farm.class,felt1.getLayoutParams(),felt0.getLayoutParams());
           }
       });

       felt2.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(2,Farm.class, felt2.getLayoutParams(),felt0.getLayoutParams());
           }
       });

       felt3.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(3,Farm.class, felt3.getLayoutParams(),felt0.getLayoutParams());
           }
       });

       felt4.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(4,Farm.class, felt4.getLayoutParams(),felt0.getLayoutParams());
           }
       });

       felt5.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(5,Farm.class,felt5.getLayoutParams(),felt0.getLayoutParams());
           }
       });

       felt6.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(6,Marked.class,felt6.getLayoutParams(),felt0.getLayoutParams());
           }
       });

       felt7.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
              moveTo(7,Farm.class,felt7.getLayoutParams(),felt0.getLayoutParams());


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

    public void updateInfobox()
    {
        infobox.setText("Navn: "+spiller.getNavn()+"\n Mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());
    }

    public void moveTo(int pos,java.lang.Class<?> cls, ViewGroup.LayoutParams params,ViewGroup.LayoutParams param0) {
        if (spiller.move(pos)) {
            Toast.makeText(SpillePlade.this, "Dagen er gået", Toast.LENGTH_SHORT).show();
            updateInfobox();
            star.setLayoutParams(param0);
        }
        else {
            Intent intent = new Intent(SpillePlade.this, cls);
            updateInfobox();
            Log.d("Spilleplade","Height:"+params.height+" Width: "+params.width);
            star.setLayoutParams(params);
            startActivity(intent);
        }
    }

    public void updateTimer()
    {
        if(spiller.getTid()>12) {//tid mellem 16 og 13
            imgUr.setImageResource(R.drawable.ur1);
        }
        else if (spiller.getTid()>8 && spiller.getTid()<13) { //tid mellem 12 og 9
            imgUr.setImageResource(R.drawable.ur2);
        }
        else if(spiller.getTid()>4 && spiller.getTid()<9){//tid mellem 8 og 5
            imgUr.setImageResource(R.drawable.ur3);
        }
        else if(spiller.getTid()>=0 && spiller.getTid()<5) {//tid mellem 4 og 0
            imgUr.setImageResource(R.drawable.ur4);
        }
        else
            imgUr.setImageResource(R.drawable.ur1);

    }

}

