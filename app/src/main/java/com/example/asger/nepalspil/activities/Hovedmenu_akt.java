package com.example.asger.nepalspil.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.asger.nepalspil.BuildConfig;
import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.Spiller;

import io.fabric.sdk.android.Fabric;

public class Hovedmenu_akt extends AppCompatActivity {
    boolean continueBGMusic;
    ImageView checkmarkasha;
    ImageView checkmarkkaka;
    SharedPreferences prefs;

    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        continueBGMusic = true;
        dialog = new AlertDialog.Builder(this);
        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        if (!EMULATOR) {
            Fabric.with(this, new Crashlytics());
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String checkIfPlayedBefore = prefs.getString("Navn", null);


        setContentView(R.layout.hovedmenu);

        TextView versionTv = (TextView) findViewById(R.id.version);
        versionTv.setText("v. " + BuildConfig.VERSION_NAME);

        ImageButton asha = (ImageButton) findViewById(R.id.imageButton4);
        final ImageButton kaka = (ImageButton) findViewById(R.id.imageButton5);
        checkmarkasha = (ImageView) findViewById(R.id.checkmarkasha);
        checkmarkkaka = (ImageView) findViewById(R.id.checkmarkkaka);
        ImageView startknap = (ImageView) findViewById(R.id.startknap);
        ImageView indstillingerknap = (ImageView) findViewById(R.id.indstillingerknap);
        ImageView genoptagKnap = (ImageView) findViewById(R.id.genoptagKnap);


        asha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Hovedmenu_akt.this, "Du har valgt Asha!", Toast.LENGTH_SHORT).show();
                checkmarkkaka.setVisibility(View.INVISIBLE);
                checkmarkasha.setVisibility(View.VISIBLE);
                Spiller.instans = new Spiller("Asha", 10, 16, 0, 100, 1, false, 1, 1, 0);
            }


        });
        kaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Hovedmenu_akt.this, "Du har valgt Kaka!", Toast.LENGTH_SHORT).show();
                checkmarkasha.setVisibility(View.INVISIBLE);
                checkmarkkaka.setVisibility(View.VISIBLE);
                Spiller.instans = new Spiller("Kaka", 10, 16, 0, 100, 1, true, 1, 1, 0);

            }
        });

        startknap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hovedmenu_akt.this, R.anim.image_click));

                if (Spiller.instans == null) {
                    Toast.makeText(Hovedmenu_akt.this, "Du mangler at vælge en figur.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Spiller.instans.getNavn() == "Kaka" || Spiller.instans.getNavn() == "Asha") {
                    Intent intent = new Intent(Hovedmenu_akt.this, SpillePlade.class);
                    startActivity(intent);
                }
            }
        });


        genoptagKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hovedmenu_akt.this, R.anim.image_click));

                Spiller.instans = new Spiller(prefs.getBoolean("Sex", true), prefs.getInt("Books", 0), prefs.getInt("Position", 0), prefs.getString("Navn", null), prefs.getInt("Penge", 0), prefs.getInt("Hp", 0), prefs.getInt("Viden", 0), prefs.getInt("Klassetrin", 0), prefs.getInt("Tid", 0), prefs.getInt("Runde", 0), prefs.getInt("Movespeed", 1), prefs.getInt("LastBookBought", 0));
                if (Spiller.instans.getSex() == true) {
                    checkmarkasha.setVisibility(View.INVISIBLE);
                    checkmarkkaka.setVisibility(View.VISIBLE);
                } else if (Spiller.instans.getSex() == false) {
                    checkmarkkaka.setVisibility(View.INVISIBLE);
                    checkmarkasha.setVisibility(View.VISIBLE);
                }
                Intent intent = new Intent(Hovedmenu_akt.this, SpillePlade.class);
                intent.putExtra("genoptag", true);
                startActivity(intent);
            }
        });

        indstillingerknap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hovedmenu_akt.this, R.anim.image_click));

                CharSequence options[] = new CharSequence[]{"Skoleliv i Nepal", "Credits", "Sluk musik"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Hovedmenu_akt.this);
                builder.setTitle("Indstillinger");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.skoleliv-i-nepal.dk/"));
                                startActivity(browserIntent);
                                break;
                            case 1:
                                Intent intent = new Intent(Hovedmenu_akt.this, Credit.class);
                                startActivity(intent);
                                break;
                            case 2:
                                MusicManager.stop();
                                break;


                        }

                    }
                });
                builder.show();


            }
        });


    }


    public void onPause() {
        super.onPause();
        if (!continueBGMusic)
            MusicManager.pause();
    }

    public void onResume() {
        super.onResume();

        continueBGMusic = false;
        MusicManager.start(this, R.raw.backgroundloop);
    }


}
