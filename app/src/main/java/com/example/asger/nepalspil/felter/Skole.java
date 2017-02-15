package com.example.asger.nepalspil.felter;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.diverse.Topbar;
import com.example.asger.nepalspil.model.Spiller;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class Skole extends AppCompatActivity {

    private Animation animation;
    private Animation animationfood;

    private Topbar topbar;


    //Studying
    final int VIDEN_PER_CLICK = 1;
    final int TIME_PER_CLICK = 1;

    //Eating
    final int FOOD_PER_CLICK = 10;
    final int COST_PER_FOOD_CLICK = 0;
    final int TIME_COST_EATING = 1;

    TextView taleboble_tekst;

    AlertDialog.Builder dialog;
    TextView flyvoptekst_studer;
    TextView flyvoptekst_spis;
    MediaPlayer spisLyd;
    MediaPlayer studérLyd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);

        topbar = new Topbar();
        topbar.init(this);
        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_hel_id);

        spisLyd = MediaPlayer.create(this, R.raw.bitesound);
        studérLyd = MediaPlayer.create(this, R.raw.study);
        taleboble_tekst = (TextView) findViewById(R.id.taleboble_tekst);
        final TextView klassetrin = (TextView) findViewById(R.id.klassetrin);
        Button bSpis = (Button) findViewById(R.id.knap_spis);
        Button bStuder = (Button) findViewById(R.id.knap_studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        animationfood = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        flyvoptekst_studer = (TextView) findViewById(R.id.flyvoptekst_studer);
        flyvoptekst_spis = (TextView) findViewById(R.id.flyvoptekst_spis);
        flyvoptekst_spis.setText("");
        flyvoptekst_studer.setText("");
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        topbar.opdaterGui(Spiller.instans);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/EraserDust.ttf");
        klassetrin.setTypeface(face);


        dialog = new AlertDialog.Builder(Skole.this);
        klassetrin.setText("Du går i " + Spiller.instans.klassetrin + ". klasse.");
        if (Spiller.instans.klassetrin >= 10) {
            bEksamen.setVisibility(View.INVISIBLE);
        }

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                dialog.setTitle("Skolen");
                dialog.setMessage(R.string.skole_hjælp);
                dialog.show();
            }
        });
        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                spis();
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                studér();
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                if (Spiller.instans.skoleKanStarteEksamen()) {
                    taleboble_tekst.setText("Held og Lykke!");
                    finish();
                    Intent myIntent = new Intent(Skole.this, Eksamen.class);
                    startActivity(myIntent);

                } else {
                    new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ikke nok viden!")
                            .setContentText("Du har ikke nok viden til at starte eksamen! Du skal have mindst " + Spiller.instans.skoleVidensKravForNæsteKlassetrin() + " viden for at starte eksamen.")
                            .show();

                }

            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                finish();
            }
        });


    }

    private void spis() {
        if (Spiller.instans.tid >= TIME_PER_CLICK) {
            flyvoptekst_spis.setText("+" + FOOD_PER_CLICK + " mad");
            flyvoptekst_spis.startAnimation(animationfood);
            if (Spiller.instans.tid > 0) {
                Spiller.instans.spis(TIME_COST_EATING, COST_PER_FOOD_CLICK, FOOD_PER_CLICK);
                topbar.opdaterGui(Spiller.instans);
            } else {
                taleboble_tekst.setText("");
            }

            taleboble_tekst.setText("Mmm! Du har spist skolemad.");
            if (spisLyd.isPlaying()) spisLyd.seekTo(0);
            else spisLyd.start();
        } else {
            new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Du har ikke tid til at spise.")
                    .show();
        }
        topbar.opdaterGui(Spiller.instans);
    }

    private void studér() {
        if (Spiller.instans.tid >= TIME_PER_CLICK) {
            Spiller.instans.læringBlyant--;
            if (Spiller.instans.læringBlyant==0) {
                new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Dine blyanter er brugt op")
                        .setContentText("Besøg butikken for at få nogle nye")
                        .show();
                vibrér();
                return;
            }
            Spiller.instans.læringKladdehæfte--;
            if (Spiller.instans.læringKladdehæfte==0) {
                new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Dit klassehæfte er brugt op")
                        .setContentText("Du har brug for et nyt for at kunne følge med")
                        .show();
                vibrér();
                return;
            }
            Spiller.instans.læringLommeregner--;
            if (Spiller.instans.læringLommeregner==0) {
                new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Din lommeregner er slidt op")
                        .setContentText("")
                        .show();
                vibrér();
                return;
            }

            int res = Spiller.instans.skoleStudér();
            if (res == Spiller.STUDER_VIDEN) {
                taleboble_tekst.setText("Du blev lidt klogere");
                flyvoptekst_studer.setText("+" + VIDEN_PER_CLICK + " viden");
                flyvoptekst_studer.startAnimation(animation);
                Spiller.instans.studér(TIME_PER_CLICK, VIDEN_PER_CLICK);
                if (studérLyd.isPlaying()) studérLyd.seekTo(0);
                else studérLyd.start();
            } else if (res == Spiller.STUDER_LEKTIEHJÆLP) {
                taleboble_tekst.setText("Du forstod det ikke, lektiehjælp kunne måske hjælpe");
                flyvoptekst_studer.setText("+1 lektiehjælp");
                flyvoptekst_studer.startAnimation(animation);
                Spiller.instans.studér(TIME_PER_CLICK, 0);
                Spiller.instans.glemtViden = Spiller.instans.glemtViden + 1;
            } else if (res == Spiller.STUDER_FORSTOD_IKKE) {
                taleboble_tekst.setText("Du forstod ikke denne lektion");
                Spiller.instans.studér(TIME_PER_CLICK, 0);
            }
        } else {
            new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Du har ikke tid til at studere.")
                    .show();
        }
        topbar.opdaterGui(Spiller.instans);
    }

    private void vibrér() {
        try {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Skole.this, "Kunne ikke vibrere med telefonen:\n" + e, Toast.LENGTH_LONG).show();
            Toast.makeText(Skole.this, "Har du husket:\n<uses-permission android:name=\"android.permission.VIBRATE\"/>\n i manifestet?", Toast.LENGTH_LONG).show();
        }
    }


}
