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

public class Boghandel extends AppCompatActivity {
    TextView textpenge;
    TextView textviden;
    TextView textmad;
    TextView playerInfo;
    final int MONEY_PER_CLICK = 20;
    final int TIME_PER_CLICK = 1;

    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    private Animation animation;

    AlertDialog.Builder dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boghandel);

        dialog = new AlertDialog.Builder(Boghandel.this);
        playerInfo = (TextView) findViewById(R.id.bookstorePlayerInfo);
        final TextView bookstoreInfo = (TextView) findViewById(R.id.fieldinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.boghandelHelp);
        Button work = (Button) findViewById(R.id.workButton);
        final Button buyBook = (Button) findViewById(R.id.buyBookButton);
        ImageView back = (ImageView) findViewById(R.id.backButton);
        final TextView money = (TextView) findViewById(R.id.scrollmoney);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusmoneybook);
        textpenge = (TextView) findViewById(R.id.textpenge);
        textviden = (TextView) findViewById(R.id.textviden);
        textmad = (TextView) findViewById(R.id.textmad);


        bookstoreInfo.setText("I boghandlen kan du købe skolebøger. Skolebøger giver mere viden. \n Du kan også få et job i boghandlen, hvis du har gået i skole længe nok.");
        updateText();
        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                if (spiller.getTid() >= TIME_PER_CLICK && spiller.getKlassetrin() >= 6) {
                    money.setText("+" + MONEY_PER_CLICK + " kr");
                    money.startAnimation(animation);
                    spiller.work(TIME_PER_CLICK, MONEY_PER_CLICK);

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

                    updateText();
                } else if (spiller.getTid() < 2) {

                    dialog.setTitle("Intet tid!");
                    dialog.setMessage("Du har ikke nok tid til at arbejde");
                    dialog.show();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke uddannelse nok til at arbejde her");
                    dialog.show();
                }
            }
        });

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                dialog.setMessage("I boghandlen kan man købe skolebøger, som hjælper en med at få viden, hvis man har penge nok. Du kan også arbejde i boghandlen fra 6. klasse.");
                dialog.show();
            }
        });

        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                if (spiller.getPenge() >= 30 && spiller.getLastBookBought() + 5 <= spiller.getRunde()) {

                    spiller.setViden(spiller.getViden() + 10);
                    spiller.setPenge(spiller.getPenge() - 30);
                    dialog.setTitle("Bog købt");
                    dialog.setMessage("Du har købt en ny bog for 30 penge. +10 viden");
                    dialog.show();
                    spiller.setLastBookBought(spiller.getRunde());
                    updateText();
                } else if (spiller.getPenge() < 30) {
                    dialog.setTitle("Du mangler penge!");
                    dialog.setMessage("Bogen koster 30 kroner, men du har kun " + spiller.getPenge());
                    dialog.show();
                } else if (!(spiller.getLastBookBought() + 5 < spiller.getRunde())) {
                    dialog.setTitle("Boghandlen har ikke den bog du vil have");
                    dialog.setMessage("Kig tilbage på et andet tidspunkt");
                    dialog.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                finish();
            }
        });

    }


    public void updateText() {
        textpenge.setText(String.valueOf(spiller.getPenge()));
        textviden.setText(String.valueOf(spiller.getViden()));
        textmad.setText(String.valueOf(spiller.getHp()));
        playerInfo.setText(String.valueOf(spiller.getTid()));
        SpillePlade.updateInfobox();
    }

}
