package com.example.asger.nepalspil.felter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Butikken extends AppCompatActivity {
  TextView textpenge;
    TextView textviden;
    TextView textmad;

    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    AlertDialog.Builder dialog;
    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toejbutik);

        dialog = new AlertDialog.Builder(Butikken.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.tbInfo);
       // final TextView playerinfo = (TextView) findViewById(R.id.tbStats);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.tbHelp);
        final Button buy = (Button) findViewById(R.id.tbBuy);
        ImageView back = (ImageView) findViewById(R.id.tbBack);
        textpenge = (TextView) findViewById(R.id.textpenge);
        textviden = (TextView) findViewById(R.id.textviden);
        textmad = (TextView) findViewById(R.id.textmad);
        updateText();

        fieldinfo.setText("Velkommen til butikken! her kan du købe skoleudstyr som gør det nemmere at lære i skolen");
       // playerinfo.setText("");

        switch (spiller.getLearningAmp()) {
            case 0:
                buy.setText("Køb Papir");
                break;
            case 1:
                buy.setText("Køb blyanter");
                break;
            case 2:
                buy.setText("Køb Lommeregner");
                break;
            case 3:
                buy.setVisibility(View.INVISIBLE);
                break;
        }

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Velkommen til tøjbutikken. Her kan du købe tøj og diverse skoleting til at gøre dit live bedre."+
                        "Papirblokken koster 150kr. og øger chancen for at lærer noget i skolen.\n" +
                        "Egne blyanter koster 300kr. og reducerer chancen for at skulle bruge lektiehjælp.\n"  +
                        "Lommeregneren koster 700kr. og fjerner risikoen for ikke at lærer noget over hovedet i skolen.");
                dialog.show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getLearningAmp()==2) {
                    if (spiller.getPenge() >= 700) {
                        buy();
                      //  playerinfo.setText(updateInfo());
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Lommeregner købt");
                        dialog.setMessage("Du har købt en ny lommeregner for 700kr.");
                        dialog.show();
                        buy.setVisibility(View.INVISIBLE);
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Ikke nok penge!");
                        dialog.setMessage("Du har ikke penge nok. Tjen penge ved at arbejde.");
                        dialog.show();
                    }
                }
                if (spiller.getLearningAmp()==1) {
                    if (spiller.getPenge() >= 300) {
                        buy();
                       // playerinfo.setText(updateInfo());
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Blyanter købt");
                        dialog.setMessage("Du har købt nye blyanter for 300kr.");
                        dialog.show();
                        buy.setText("Køb Lommeregner");
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Ikke nok penge!");
                        dialog.setMessage("Du har ikke penge nok. Tjen penge ved at arbejde.");
                        dialog.show();
                    }
                }
                if (spiller.getLearningAmp() == 0) {
                    if (spiller.getPenge() >= 150) {
                        buy();
                       // playerinfo.setText(updateInfo());
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Papir købt");
                        dialog.setMessage("Du har købt en blok papir for 150kr.");
                        dialog.show();
                        buy.setText("Køb blyanter");
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Ikke nok penge!");
                        dialog.setMessage("Du har ikke penge nok. Tjen penge ved at arbejde.");
                        dialog.show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                finish();
            }
        });

    }

    private void buy() {
        switch (spiller.getLearningAmp()){
            case 0:
                spiller.setLearningAmp(1);
                spiller.setPenge(spiller.getPenge()-150);
                break;
            case 1:
                spiller.setLearningAmp(2);
                spiller.setPenge(spiller.getPenge()-300);
                break;
            case 2:
                spiller.setLearningAmp(3);
                spiller.setPenge(spiller.getPenge()-700);
                break;
        }
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        //Har ingen funktion i butikken
        return "";
    }

    public  void updateText() {
        textpenge.setText(String.valueOf(spiller.getPenge()));
        textviden.setText(String.valueOf(spiller.getViden()));
        textmad.setText(String.valueOf(spiller.getHp()));
    }
}
