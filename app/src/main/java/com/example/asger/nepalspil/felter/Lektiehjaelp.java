package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
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

import java.io.IOException;

import static com.example.asger.nepalspil.models.Spiller.instans;

public class Lektiehjaelp extends AppCompatActivity {
    //Studying
    final int VIDEN_PER_CLICK = 1;
    final int TIME_PER_CLICK = 1;

    Button homeworkHelp;
    AlertDialog.Builder dialog;
    private Animation animation;
    private Topbar topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lektiehjaelp);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Lektiehjaelp.this);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        final TextView lektiehjaelpInfo = (TextView) findViewById(R.id.lektiehjaelpTextField);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        homeworkHelp = (Button) findViewById(R.id.knap_lektiehjaelp);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        final TextView scrollknowledge = (TextView) findViewById(R.id.scrollknowledge);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/EraserDust.ttf");
        lektiehjaelpInfo.setTypeface(face);
        lektiehjaelpInfo.setText("Her kan du få lektiehjælp for at indhente det, du ikke forstod i timerne.");
        topbar.opdaterGui(instans);
        homeworkHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Lektiehjaelp.this, R.anim.image_click));
                if (instans.getTid() >= TIME_PER_CLICK && instans.getGlemtViden() > 0) {
                    scrollknowledge.setText("+" + VIDEN_PER_CLICK + " viden");
                    scrollknowledge.startAnimation(animation);
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("study.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    learn();
                } else if (instans.getGlemtViden() <= 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Lektiehjaelp.this);
                    dialog.setTitle("Ingen glemt viden.");
                    dialog.setMessage("Du har ikke behov for lektiehjælp, da du forstået al undervisning.");
                    dialog.show();
                } else if (instans.getTid() <= TIME_PER_CLICK) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Lektiehjaelp.this);
                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at få lektiehjælp. Kom igen i morgen.");
                    dialog.show();
                }

            }
        });

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Lektiehjaelp.this, R.anim.image_click));
                dialog.setMessage("Tit er det rigtig svært at forstå, hvad lærerne forklarer og dine forældre kan ikke hjælpe med lektierne. Så derfor har du brug for lektiehjælp.");
                dialog.show();
            }
        });
        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Lektiehjaelp.this, R.anim.image_click));
                finish();
            }
        });

    }

    private void learn() {
        instans.study(TIME_PER_CLICK, VIDEN_PER_CLICK);
        instans.setGlemtViden(instans.getGlemtViden() - 1);
        topbar.opdaterGui(instans);
    }
}