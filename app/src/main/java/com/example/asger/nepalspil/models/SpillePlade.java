package com.example.asger.nepalspil.models;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.felter2.Boghandel;
import com.example.asger.nepalspil.felter2.Farm;
import com.example.asger.nepalspil.felter2.Hjem;
import com.example.asger.nepalspil.felter2.Marked;
import com.example.asger.nepalspil.felter2.Skole;
import com.example.asger.nepalspil.felter2.Toejbutik;
import com.example.asger.nepalspil.felter2.Vaerksted;

//import static com.example.asger.nepalspil.R.id.player;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Nicki on 22-11-2016.
 */

public class SpillePlade extends AppCompatActivity {
    TextView infobox;
    ImageView Player;
    ImageView ur;
    ImageView unusedPlayer;

    ImageButton felt0;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);
        if(spiller.sex==true){Player=(ImageView) findViewById(R.id.kaka);
        unusedPlayer= (ImageView) findViewById(R.id.asha);
        }
        else if(spiller.sex==false){Player=(ImageView) findViewById(R.id.asha);
            unusedPlayer= (ImageView) findViewById(R.id.kaka);
        }
        unusedPlayer.setVisibility(View.INVISIBLE);
        infobox = (TextView) findViewById(R.id.infobox);
        ur = (ImageView) findViewById(R.id.imgUr);
        ur.setImageResource(R.drawable.ur1);
       //infobox.setText("Navn: "+spiller.getNavn()+"\n Mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());
        updateInfobox();
        felt0 = (ImageButton) findViewById(R.id.felt0);
        final ImageButton felt1 = (ImageButton) findViewById(R.id.felt1);
        final ImageButton felt2 = (ImageButton) findViewById(R.id.felt2);
        final ImageButton felt3 = (ImageButton) findViewById(R.id.felt3);
        final ImageButton felt4 = (ImageButton) findViewById(R.id.felt4);
        final ImageButton felt5 = (ImageButton) findViewById(R.id.felt5);
        final ImageButton felt6 = (ImageButton) findViewById(R.id.felt6);
        final ImageButton felt7 = (ImageButton) findViewById(R.id.felt7);
        Player.setLayoutParams(felt0.getLayoutParams());




        felt0.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(0,Hjem.class,felt0.getLayoutParams());
            }
       });


       felt1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(1,Farm.class,felt1.getLayoutParams());



/*
               TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                       0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
               animation.setDuration(2000);  // animation duration
               animation.setFillAfter(true);

               star.startAnimation(animation);  // start animation*/
           }
       });

       felt2.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(2,Vaerksted.class, felt2.getLayoutParams());
           }
       });

       felt3.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(3,Boghandel.class, felt3.getLayoutParams());
           }
       });

       felt4.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(4,Skole.class, felt4.getLayoutParams());
           }
       });

       felt5.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(5,Farm.class,felt5.getLayoutParams());
           }
       });

       felt6.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(6,Marked.class,felt6.getLayoutParams());
           }
       });

       felt7.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
              moveTo(7,Toejbutik.class,felt7.getLayoutParams());


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

    public void moveTo(int pos,java.lang.Class<?> cls, ViewGroup.LayoutParams params) {
        if (spiller.move(pos)) {
            Toast.makeText(SpillePlade.this, "Dagen er gået", Toast.LENGTH_SHORT).show();
            updateTimer();
            updateInfobox();
            Player.setLayoutParams(felt0.getLayoutParams());
        }
        else {
            final Intent intent = new Intent(SpillePlade.this, cls);
            updateTimer();
            updateInfobox();
            Log.d("Spilleplade","Height:"+params.height+" Width: "+params.width);
            Player.setLayoutParams(params);


            startActivity(intent);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //Do something after 1500ms
                }
            }, 1500);
            //
        }
    }

    public void updateTimer()
    {
        if(spiller.getTid()>12) {//tid mellem 16 og 13
            ur.setImageResource(R.drawable.ur1);
        }
        else if (spiller.getTid()>8 && spiller.getTid()<13) { //tid mellem 12 og 9
            ur.setImageResource(R.drawable.ur2);
        }
        else if(spiller.getTid()>4 && spiller.getTid()<9){//tid mellem 8 og 5
            ur.setImageResource(R.drawable.ur3);
        }
        else if(spiller.getTid()>=0 && spiller.getTid()<5) {//tid mellem 4 og 0
            ur.setImageResource(R.drawable.ur4);
        }
        else
            ur.setImageResource(R.drawable.ur1);

    }

}

