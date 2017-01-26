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
import com.example.asger.nepalspil.models.Spiller;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.asger.nepalspil.models.Spiller.instans;


public class Marken extends AppCompatActivity {
    final int MONEY_PER_CLICK = 3;
    final int TIME_PER_CLICK = 1;

    AlertDialog.Builder dialog;
    private Animation animation;
    private Topbar topbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marken);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Marken.this);
        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);

        Button work = (Button) findViewById(R.id.knap_arbejd);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView helpfield = (ImageView) findViewById(R.id.vaerkstedHelp);
        final TextView money = (TextView) findViewById(R.id.scrollmoney);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);
        updateText();

       /* Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/Mathlete-Bulky.otf");
        fieldinfo.setTypeface(face);*/


        helpfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage(R.string.marken_hjælp);
                dialog.show();
            }
        });
        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Marken.this, R.anim.image_click));
                if (instans.getTid() >= TIME_PER_CLICK) {
                    instans.work(TIME_PER_CLICK, MONEY_PER_CLICK);
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

                } else if (instans.getTid() < 2) {

                    new SweetAlertDialog(Marken.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Du har ikke tid til at arbejde.")
                            .show();

                }
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Marken.this, R.anim.image_click));
                finish();

            }
        });
    }


    public void updateText() {
        topbar.opdaterGui(instans);
    }
}