package com.example.asger.nepalspil.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
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
       infobox = (TextView) findViewById(R.id.infobox);
       //infobox.setText("Navn: "+spiller.getNavn()+"\n Mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());
        updateInfobox();

        felt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(0,Farm.class);
            }
        });



       felt1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(1,Farm.class);
           }
       });

       felt2.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(2,Farm.class);
           }
       });

       felt3.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(3,Farm.class);
           }
       });

       felt4.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(4,Skole.class);
           }
       });

       felt5.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(5,Farm.class);
           }
       });

       felt6.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               moveTo(6,Marked.class);
           }
       });

       felt7.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
              moveTo(7,Farm.class);


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

    public void moveTo(int pos,java.lang.Class<?> cls) {
        if (spiller.move(pos)) {
            Toast.makeText(SpillePlade.this, "Dagen er gået", Toast.LENGTH_SHORT).show();
            updateInfobox();
        }
        else {
            Intent intent = new Intent(SpillePlade.this, cls);
            updateInfobox();
            startActivity(intent);
        }
    }

}

