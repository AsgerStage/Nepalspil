package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.activities.Hovedmenu_akt.spiller;


public class Farm extends AppCompatActivity {
    TextView textpenge;
    TextView textviden;
    TextView textmad;
    TextView playerinfo;
    final int MONEY_PER_CLICK = 3;
    final int TIME_PER_CLICK = 1;

    @Override
    public void onBackPressed() {

        SpillePlade.updateEntireBoard();
        finish();
    }

    AlertDialog.Builder dialog;
    private Animation animation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm);


        dialog = new AlertDialog.Builder(Farm.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusmoney);

        Button work = (Button) findViewById(R.id.workButton);
        ImageView back = (ImageView) findViewById(R.id.backButton);
        ImageView helpfield = (ImageView) findViewById(R.id.farmHelp);
        final TextView money = (TextView) findViewById(R.id.scrollmoney);
        textpenge = (TextView) findViewById(R.id.textpenge);
        textviden = (TextView) findViewById(R.id.textviden);
        textmad = (TextView) findViewById(R.id.textmad);
        updateText();

       /* Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/Mathlete-Bulky.otf");
        fieldinfo.setTypeface(face);*/

        fieldinfo.setText("I marken kan du tjene lidt penge til mad, skolesager eller en cykel ved at arbejde.");


        helpfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Dine forældre har ikke råd til at give dig en cykel eller til at købe frugt og grønt, så du må selv tjene penge i marken fra første klasse. Men husk, det tager tid fra dine studier. ");
                dialog.show();
            }
        });
        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Farm.this, R.anim.image_click));
                if (spiller.getTid() >= TIME_PER_CLICK) {
                    spiller.work(TIME_PER_CLICK, MONEY_PER_CLICK);
                    money.setText("+" + MONEY_PER_CLICK + " kr");
                    money.startAnimation(animation);
                    updateText();

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
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Farm.this, R.anim.image_click));
                finish();

            }
        });
    }


    public void updateText() {
        textpenge.setText(String.valueOf(spiller.getPenge()));
        textviden.setText(String.valueOf(spiller.getViden()));
        textmad.setText(String.valueOf(spiller.getHp()));
        playerinfo.setText(String.valueOf(spiller.getTid()));
        SpillePlade.updateEntireBoard();
    }
}