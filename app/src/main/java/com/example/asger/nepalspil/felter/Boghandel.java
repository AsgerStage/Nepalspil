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
import com.example.asger.nepalspil.diverse.Topbar;
import com.example.asger.nepalspil.model.Spiller;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Boghandel extends AppCompatActivity {
    final int MONEY_PER_CLICK = 12;
    final int TIME_PER_CLICK = 1;

    private Animation animation;
    private Topbar topbar;

    AlertDialog.Builder dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boghandel);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Boghandel.this);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        Button work = (Button) findViewById(R.id.knap_arbejd);
        final Button buyBook = (Button) findViewById(R.id.knap_koeb_bog);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        final TextView money = (TextView) findViewById(R.id.scrollmoney);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_hel_id);
        topbar.opdaterGui(Spiller.instans);
        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                if (Spiller.instans.tid >= TIME_PER_CLICK && Spiller.instans.klassetrin >= 6) {
                    money.setText("+" + MONEY_PER_CLICK + " kr");
                    money.startAnimation(animation);
                    Spiller.instans.arbejd(TIME_PER_CLICK, MONEY_PER_CLICK);

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

                    topbar.opdaterGui(Spiller.instans);
                } else if (Spiller.instans.tid < 2) {

                    new SweetAlertDialog(Boghandel.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Du har ikke tid til at arbejde.")
                            .show();

                } else {
                    new SweetAlertDialog(Boghandel.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ikke nok viden")
                            .setContentText("Du har ikke uddannelse nok til at arbejde her.")
                            .show();
                }
            }
        });

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                dialog.setMessage(R.string.boghandel_hjælp);
                dialog.show();
            }
        });

        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                if (Spiller.instans.penge >= 30 && Spiller.instans.bogKøbtIRundeNr + 5 <= Spiller.instans.runde) {

                    Spiller.instans.viden = Spiller.instans.viden + 10;
                    Spiller.instans.penge = Spiller.instans.penge - 30;
                    new SweetAlertDialog(Boghandel.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Bog købt")
                            .setContentText("Du har købt en ny bog for 30kr. +10 viden")
                            .show();

                    Spiller.instans.bogKøbtIRundeNr = Spiller.instans.runde;
                    topbar.opdaterGui(Spiller.instans);

                } else if (Spiller.instans.penge < 30) {
                    new SweetAlertDialog(Boghandel.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ikke nok penge!.")
                            .setContentText("Bogen koster 30 kroner, men du har kun " + Spiller.instans.penge)
                            .show();

                } else if (!(Spiller.instans.bogKøbtIRundeNr + 5 < Spiller.instans.runde)) {
                    new SweetAlertDialog(Boghandel.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Bogen er ikke på lager.")
                            .setContentText("Kig tilbage på et andet tidspunkt")
                            .show();
                }
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                finish();
            }
        });

    }


}
