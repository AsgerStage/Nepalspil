package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.MainActivity;
import com.example.asger.nepalspil.models.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;


/**
 * Created by Asger on 21-11-2016.
 */

public class Farm extends AppCompatActivity {

    AlertDialog.Builder dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm);

        dialog= new AlertDialog.Builder(Farm.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        final TextView playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        Button work = (Button) findViewById(R.id.workButton);
        ImageView back = (ImageView) findViewById(R.id.backButton);
        ImageView helpfield = (ImageView) findViewById(R.id.farmHelp);


        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/Mathlete-Bulky.otf");
        fieldinfo.setTypeface(face);

        fieldinfo.setText("Velkommen ind i laden! Du kan arbejde her, hvis du er klog nok!");
        playerinfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());

        helpfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Velkommen til rismarken. Her kan du arbejde fra 1 klassetrin af og tjene en smule penge");
                dialog.show();
            }
        });
        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();
                    playerinfo.setText(updateInfo());

                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("cash.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (spiller.getTid() < 2) {

                    dialog.setTitle("Intet tid!");
                    dialog.setMessage("Du har ikke nok tid til at arbejde");
                    dialog.show();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateTextpenge();
                SpillePlade.updateTextmad();
                SpillePlade.updateTextviden();
                v.startAnimation(AnimationUtils.loadAnimation(Farm.this, R.anim.image_click));
                finish();

            }
        });
    }

    public void work() {
        spiller.setTid(spiller.getTid() - 2);
        spiller.setPenge(spiller.getPenge() + 5);
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();
    }

}