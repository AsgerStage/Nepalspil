package com.example.asger.nepalspil.models;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.MainActivity;
import com.example.asger.nepalspil.activities.MusicManager;
import com.example.asger.nepalspil.felter.Boghandel;
import com.example.asger.nepalspil.felter.Farm;
import com.example.asger.nepalspil.felter.Hjem;
import com.example.asger.nepalspil.felter.Lektiehjaelp;
import com.example.asger.nepalspil.felter.Marked;
import com.example.asger.nepalspil.felter.Skole;
import com.example.asger.nepalspil.felter.Toejbutik;
import com.example.asger.nepalspil.felter.Vaerksted;

//import static com.example.asger.nepalspil.R.id.player;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Nicki on 22-11-2016.
 */

public class SpillePlade extends AppCompatActivity {
    static TextView infobox;
    ImageView Player;
    ImageView ur;
    ImageView unusedPlayer;
    boolean continueBGMusic;
    AlertDialog.Builder dialog;
    SharedPreferences prefs;

    ImageButton felt0;
    ImageButton felt1;
    ImageButton felt2;
    ImageButton felt3;
    ImageButton felt4;
    ImageButton felt5;
    ImageButton felt6;
    ImageButton felt7;
    ImageButton ingameopt;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);
        continueBGMusic = true;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        dialog = new AlertDialog.Builder(SpillePlade.this);
        if (spiller.sex) {
            Player = (ImageView) findViewById(R.id.kaka);
            unusedPlayer = (ImageView) findViewById(R.id.asha);
        } else if (!spiller.sex) {
            Player = (ImageView) findViewById(R.id.asha);
            unusedPlayer = (ImageView) findViewById(R.id.kaka);
        }
        unusedPlayer.setVisibility(View.INVISIBLE);
        infobox = (TextView) findViewById(R.id.infobox);
        ur = (ImageView) findViewById(R.id.imgUr);
        ur.setImageResource(R.drawable.ur1);
        //infobox.setText("Navn: "+spiller.getNavn()+"\n mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());
        updateInfobox();
        felt0 = (ImageButton) findViewById(R.id.felt0);
        felt1 = (ImageButton) findViewById(R.id.felt1);
        felt2 = (ImageButton) findViewById(R.id.felt2);
        felt3 = (ImageButton) findViewById(R.id.felt3);
        felt4 = (ImageButton) findViewById(R.id.felt4);
        felt5 = (ImageButton) findViewById(R.id.felt5);
        felt6 = (ImageButton) findViewById(R.id.felt6);
        felt7 = (ImageButton) findViewById(R.id.felt7);
        ingameopt = (ImageButton) findViewById(R.id.ingameoptions);
        MoveIcon();


        felt0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(0, Hjem.class, felt0.getLayoutParams());
                saveToPrefs();
            }
        });


        felt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(1, Lektiehjaelp.class, felt1.getLayoutParams());
                saveToPrefs();


/*
               TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                       0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
               animation.setDuration(2000);  // animation duration
               animation.setFillAfter(true);

               star.startAnimation(animation);  // start animation*/
            }
        });

        felt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(2, Vaerksted.class, felt2.getLayoutParams());
                saveToPrefs();
            }
        });

        felt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(3, Boghandel.class, felt3.getLayoutParams());
                saveToPrefs();
            }
        });

        felt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(4, Skole.class, felt4.getLayoutParams());
                saveToPrefs();
            }
        });

        felt5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(5, Farm.class, felt5.getLayoutParams());
                saveToPrefs();
            }
        });

        felt6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(6, Marked.class, felt6.getLayoutParams());
                saveToPrefs();
            }
        });

        felt7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveTo(7, Toejbutik.class, felt7.getLayoutParams());
                saveToPrefs();


            }
        });

        ingameopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(SpillePlade.this, R.anim.image_click));

                CharSequence options[] = new CharSequence[]{"Hjælp", "Sluk musik", "Sluk lyde", "Nyt spil"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SpillePlade.this);
                builder.setTitle("Indstillinger");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            MusicManager.stop();

                            switch (which) {
                                case 0:

                                    break;
                                case 1:
                                    MusicManager.stop();
                                    break;
                                case 2:
                                    int d;
                                    break;
                                case 3:
                                    Intent myIntent = new Intent(SpillePlade.this, MainActivity.class);
                                    startActivity(myIntent);
                                    break;


                            }
                        }


                        // the user clicked on options[which]
                    }

                });
                builder.show();


            }
        });


    }
    /*protected void moveTo(ImageButton v){
        felt0.setColorFilter(null);
        felt1.setColorFilter(null);
        felt2.setColorFilter(null);
        felt3.setColorFilter(null);
        felt4.setColorFilter(null);
        felt5.setColorFilter(null);
        felt6.setColorFilter(null);
        felt7.setColorFilter(null);
        v.setColorFilter(android.R.color.holo_green_dark);
    }*/

    public static void updateInfobox() {
        infobox.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());
    }

    public void moveTo(int pos, java.lang.Class<?> cls, ViewGroup.LayoutParams params) {
        if (spiller.move(pos)) {
            if (spiller.getHp() - 30 > 0) {
                Toast.makeText(SpillePlade.this, "Dagen er gået", Toast.LENGTH_SHORT).show();

            } else if (spiller.getHp() - 30 <= 0) {
                dialog.setTitle("Husk at spise!");
                dialog.setMessage("Dagen er gået,og du har glemt at spise, og derfor har du mindre tid idag");
                dialog.show();
                spiller.setTid(8);
            }
            spiller.setHp(spiller.getHp() - 30);
            updateTimer();
            updateInfobox();
            Player.setLayoutParams(felt0.getLayoutParams());
        } else {
            final Intent intent = new Intent(SpillePlade.this, cls);
            updateTimer();
            updateInfobox();
            Log.d("Spilleplade", "Height:" + params.height + " Width: " + params.width);
            Player.setLayoutParams(params);


            startActivity(intent);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //Do something after 1500ms
                }
            }, 1500);
            //
        }
    }

    public void updateTimer() {
        if (spiller.getTid() > 12) {//tid mellem 16 og 13
            ur.setImageResource(R.drawable.ur1);
        } else if (spiller.getTid() > 8 && spiller.getTid() < 13) { //tid mellem 12 og 9
            ur.setImageResource(R.drawable.ur2);
        } else if (spiller.getTid() > 4 && spiller.getTid() < 9) {//tid mellem 8 og 5
            ur.setImageResource(R.drawable.ur3);
        } else if (spiller.getTid() >= 0 && spiller.getTid() < 5) {//tid mellem 4 og 0
            ur.setImageResource(R.drawable.ur4);
        } else
            ur.setImageResource(R.drawable.ur1);

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

    public void onStop() {
        super.onStop();

        continueBGMusic = false;
        MusicManager.stop();
    }

    public void saveToPrefs() {
        prefs.edit().putBoolean("Sex", spiller.getSex()).apply();
        prefs.edit().putInt("GlemtViden", spiller.getGlemtViden()).apply();
        prefs.edit().putInt("Books", spiller.getBooks()).apply();
        prefs.edit().putInt("Position", spiller.getPosition()).apply();
        prefs.edit().putString("Navn", spiller.getNavn()).apply();
        prefs.edit().putInt("Penge", spiller.getPenge()).apply();
        prefs.edit().putInt("Hp", spiller.getHp()).apply();
        prefs.edit().putInt("Viden", spiller.getViden()).apply();
        prefs.edit().putInt("Klassetrin", spiller.getKlassetrin()).apply();
        prefs.edit().putInt("Tid", spiller.getTid()).apply();
        prefs.edit().putBoolean("Bike", spiller.isBike()).apply();
        prefs.edit().putInt("Runde", spiller.getRunde()).apply();
    }

    public void MoveIcon() {
        if (spiller.getPosition() == 1) {
            Player.setLayoutParams(felt1.getLayoutParams());
        } else if (spiller.getPosition() == 2) {
            Player.setLayoutParams(felt2.getLayoutParams());
        } else if (spiller.getPosition() == 3) {
            Player.setLayoutParams(felt3.getLayoutParams());
        } else if (spiller.getPosition() == 4) {
            Player.setLayoutParams(felt4.getLayoutParams());
        } else if (spiller.getPosition() == 5) {
            Player.setLayoutParams(felt5.getLayoutParams());
        } else if (spiller.getPosition() == 6) {
            Player.setLayoutParams(felt6.getLayoutParams());
        } else if (spiller.getPosition() == 7) {
            Player.setLayoutParams(felt7.getLayoutParams());
        } else {
            Player.setLayoutParams(felt0.getLayoutParams());
        }
    }

}


