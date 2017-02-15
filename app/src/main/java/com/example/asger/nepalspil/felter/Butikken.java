package com.example.asger.nepalspil.felter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.diverse.Topbar;
import com.example.asger.nepalspil.model.Spiller;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Butikken extends AppCompatActivity {

    AlertDialog.Builder dialog;
    private Topbar topbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butik);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Butikken.this);
        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        final Button buy = (Button) findViewById(R.id.knap_koeb);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);
        topbar.opdaterGui(Spiller.instans);


        switch (Spiller.instans.getLearningAmp()) {
            case 0:
                buy.setText("Køb kladdehæfte");
                break;
            case 1:
                buy.setText("Køb blyanter");
                break;
            case 2:
                buy.setText("Køb lommeregner");
                break;
            case 3:
                buy.setVisibility(View.INVISIBLE);
                break;
        }

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                dialog.setMessage(R.string.butik_hjælp);
                dialog.show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Spiller.instans.getLearningAmp() == 0) {
                    if (Spiller.instans.getPenge() >= 10) {
                        buy();
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Kladdehæfte købt!")
                                .setContentText("Du har købt et kladdehæfte for 10kr.")
                                .show();
                        buy.setText("Køb blyanter");
                    } else {
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ikke nok penge!")
                                .setContentText("Du har ikke penge nok. Tjen penge ved at arbejde.")
                                .show();
                    }
                }
                if (Spiller.instans.getLearningAmp() == 1) {
                    if (Spiller.instans.getPenge() >= 20) {
                        buy();
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Blyanter købt!")
                                .setContentText("Du har købt nye blyanter for 20kr.")
                                .show();
                        buy.setText("Køb Lommeregner");
                    } else {
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ikke nok penge!")
                                .setContentText("Du har ikke penge nok. Tjen penge ved at arbejde.")
                                .show();

                    }
                }
                if (Spiller.instans.getLearningAmp() == 2) {
                    if (Spiller.instans.getPenge() >= 200) {
                        buy();
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Lommeregner købt!")
                                .setContentText("Du har købt en ny lommeregner for 200kr.")
                                .show();
                        buy.setVisibility(View.INVISIBLE);
                    } else {
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ikke nok penge!")
                                .setContentText("Du har ikke penge nok. Tjen penge ved at arbejde.")
                                .show();
                    }
                }
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                finish();
            }
        });

    }

    private void buy() {
        switch (Spiller.instans.getLearningAmp()) {
            case 0:
                Spiller.instans.setLearningAmp(1);
                Spiller.instans.setPenge(Spiller.instans.getPenge() - 10);
                break;
            case 1:
                Spiller.instans.setLearningAmp(2);
                Spiller.instans.setPenge(Spiller.instans.getPenge() - 20);
                break;
            case 2:
                Spiller.instans.setLearningAmp(3);
                Spiller.instans.setPenge(Spiller.instans.getPenge() - 200);
                break;
        }
        topbar.opdaterGui(Spiller.instans);
    }
}
