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
import com.example.asger.nepalspil.models.Spiller;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.asger.nepalspil.models.Spiller.instans;

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
        topbar.opdaterGui(instans);


        switch (instans.getLearningAmp()) {
            case 0:
                buy.setText("Køb Kladehæfte");
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
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                dialog.setMessage(R.string.butik_hjælp);
                dialog.show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (instans.getLearningAmp() == 2) {
                    if (instans.getPenge() >= 700) {
                        buy();
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Lommeregner købt!")
                                .setContentText("Du har købt en ny lommeregner for 700kr.")
                                .show();
                        buy.setVisibility(View.INVISIBLE);
                    } else {
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ikke nok penge!")
                                .setContentText("Du har ikke penge nok. Tjen penge ved at arbejde.")
                                .show();
                    }
                }
                if (instans.getLearningAmp() == 1) {
                    if (instans.getPenge() >= 300) {
                        buy();
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Blyanter købt!")
                                .setContentText("Du har købt nye blyanter for 300kr.")
                                .show();
                        buy.setText("Køb Lommeregner");
                    } else {
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ikke nok penge!")
                                .setContentText("Du har ikke penge nok. Tjen penge ved at arbejde.")
                                .show();

                    }
                }
                if (instans.getLearningAmp() == 0) {
                    if (instans.getPenge() >= 150) {
                        buy();
                        new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Kladehæfte købt!")
                                .setContentText("Du har købt et kladehæfte for 150kr.")
                                .show();
                        buy.setText("Køb blyanter");
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
        switch (instans.getLearningAmp()) {
            case 0:
                instans.setLearningAmp(1);
                instans.setPenge(instans.getPenge() - 150);
                break;
            case 1:
                instans.setLearningAmp(2);
                instans.setPenge(instans.getPenge() - 300);
                break;
            case 2:
                instans.setLearningAmp(3);
                instans.setPenge(instans.getPenge() - 700);
                break;
        }
        topbar.opdaterGui(instans);
    }
}
