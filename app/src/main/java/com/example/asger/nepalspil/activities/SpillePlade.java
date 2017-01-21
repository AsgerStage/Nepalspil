package com.example.asger.nepalspil.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.felter.Boghandel;
import com.example.asger.nepalspil.felter.Farm;
import com.example.asger.nepalspil.felter.Hjem;
import com.example.asger.nepalspil.felter.Lektiehjaelp;
import com.example.asger.nepalspil.felter.Marked;
import com.example.asger.nepalspil.felter.Skole;
import com.example.asger.nepalspil.felter.Vaerksted;
import com.example.asger.nepalspil.felter.Butikken;

//import static com.example.asger.nepalspil.R.id.player;

import java.util.concurrent.ThreadLocalRandom;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;
import static com.example.asger.nepalspil.activities.MusicManager.mp;

public class SpillePlade extends AppCompatActivity {
    static TextView infobox;
    static TextView textpenge;
    static TextView textviden;
    static TextView textmad;

    ImageView Player;
    static ImageView ur;
//    static ClockImageView ur;
    static private TextView tidTextView;
    ImageView unusedPlayer;
    boolean continueBGMusic;
    AlertDialog.Builder dialog;
    SharedPreferences prefs;

    Button felt0;
    Button felt1;
    Button felt2;
    Button felt3;
    Button felt4;
    Button felt5;
    Button felt6;
    Button felt7;
    ImageView ingameopt;
    ImageView spilpladeHelp;
    int lastEvent = 0;
    int randomNum = 0;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Er du sikker på du vil afslutte spillet?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SpillePlade.this.finish();
                    }
                })
                .setNegativeButton("Nej", null)
                .show();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);

        continueBGMusic = true;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        dialog = new AlertDialog.Builder(SpillePlade.this);
        if (spiller.sex) {
            new AlertDialog.Builder(this)
                    .setMessage("Hej! Hjælp os med at få en uddannelse. Vi skal have mad, viden og penge, så vi kan købe bøger, blyanter og en cykel og bestå de årlige eksamener. \n \n Hvis vi når 10. klasse, kan vi tag en uddannelse og få et godt job, og du har vundet spillet.")
                    .setCancelable(false)
                    .setPositiveButton("OK", null)
                    .show();
            Player = (ImageView) findViewById(R.id.kaka);
            unusedPlayer = (ImageView) findViewById(R.id.asha);

        } else if (!spiller.sex) {
            new AlertDialog.Builder(this)
                    .setMessage("Hej! Hjælp os med at få en uddannelse. Vi skal have mad, viden og penge, så vi kan købe bøger, blyanter og en cykel og bestå de årlige eksamener. \n \n Hvis vi når 10. klasse, kan vi tag en uddannelse og få et godt job, og du har vundet spillet.")
                    .setCancelable(false)
                    .setPositiveButton("OK", null)
                    .show();
            Player = (ImageView) findViewById(R.id.asha);
            unusedPlayer = (ImageView) findViewById(R.id.kaka);

        }
        unusedPlayer.setVisibility(View.INVISIBLE);

        infobox = (TextView) findViewById(R.id.infobox);
        textpenge = (TextView) findViewById(R.id.textpenge);
        textviden = (TextView) findViewById(R.id.textviden);
        textmad = (TextView) findViewById(R.id.textmad);

        tidTextView = (TextView) findViewById(R.id.tid);
        //ur = (ClockImageView) findViewById(R.id.ur);
        ur = (ImageView) findViewById(R.id.ur);
        ur.setImageResource(R.drawable.ur16);
        updateInfobox();
        updateEntireBoard();

        felt0 = (Button) findViewById(R.id.felt0);
        felt1 = (Button) findViewById(R.id.felt1);
        felt2 = (Button) findViewById(R.id.felt2);
        felt3 = (Button) findViewById(R.id.felt3);
        felt4 = (Button) findViewById(R.id.felt4);
        felt5 = (Button) findViewById(R.id.felt5);
        felt6 = (Button) findViewById(R.id.felt6);
        felt7 = (Button) findViewById(R.id.felt7);
        ingameopt = (ImageView) findViewById(R.id.ingameopt);
        spilpladeHelp = (ImageView) findViewById(R.id.spilpladeHelp);
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
                moveTo(7, Butikken.class, felt7.getLayoutParams());
                saveToPrefs();


            }
        });

        spilpladeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SpillePlade.this);
                dialog.setMessage("Målet med spillet er at færdiggøre 10. klasse. Du starter i 1. klasse og skal til eksamen hvert år. \n \nFor at bestå den årlige eksamen skal du optjene viden, og for at få viden skal du studere og have noget at spise. \n\nPå pladens otte felter kan du optjene viden, mad og penge og købe vigtige hjælpemidler.\n \nUndervejs vil du møde forhindringer, som gør det sværere at nå målet.\nSpillet er på tid, så du skal skynde dig. Du kan se, hvor meget du har optjent øverst på skærmen. ");
                dialog.show();

            }
        });

        ingameopt.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             v.startAnimation(AnimationUtils.loadAnimation(SpillePlade.this, R.anim.image_click));

                                             CharSequence options[] = new CharSequence[]{"Stop musik", "Afslut spil"};
                                             AlertDialog.Builder builder = new AlertDialog.Builder(SpillePlade.this);
                                             builder.setTitle("Indstillinger");
                                             builder.setItems(options, new DialogInterface.OnClickListener() {
                                                         @Override
                                                         public void onClick(DialogInterface dialog, int which) {


                                                             switch (which) {
                                                                 case 0:
                                                                     mp.stop();
                                                                     break;
                                                                 case 1:
                                                                     finish();
                                                                     break;


                                                             }

                                                         }

                                                     }

                                             );
                                             builder.show();


                                         }
                                     }

        );


    }


    /* public static void updateInfobox() {
         infobox.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid() + "\n Dag: " + spiller.getRunde());
     }*/
    public static void updateInfobox() {
        infobox.setText("Uge: " + spiller.getRunde());
    }

    public static void updateTextpenge() {
        textpenge.setText(String.valueOf(spiller.getPenge()));
    }

    public static void updateTextviden() {
        textviden.setText(String.valueOf(spiller.getViden()));
    }

    public static void updateTextmad() {
        textmad.setText(String.valueOf(spiller.getHp()));
    }

    /**
     * Flytter spilleren
     * @param feltPos feltnummer
     * @param aktivitet skærmbillede der skal startes hvis rykket lykkedes
     * @param params
     */
    public void moveTo(int feltPos, java.lang.Class<?> aktivitet, ViewGroup.LayoutParams params) {
        if (spiller.move(feltPos)) {
            if (spiller.getHp() - 30 > 0) {

                Toast.makeText(SpillePlade.this, "Ugen er gået", Toast.LENGTH_SHORT).show();

            } else if (spiller.getHp() - 30 <= 0) {
                dialog.setTitle("Husk at spise!");
                dialog.setMessage("Ugen er gået og du har glemt at spise, du har derfor mindre tid i denne uge");
                dialog.show();

                spiller.setTid(8);
            }
            if (spiller.getHp() >= 30) {
                spiller.setHp(spiller.getHp() - 30);
            } else spiller.setHp(0);
            if (spiller.runde % 5 == 0) randomEvent();

            updateTimer();
            updateInfobox();
            updateTextpenge();
            updateTextviden();
            updateTextmad();

            MoveIcon();
        } else {
            final Intent intent = new Intent(SpillePlade.this, aktivitet);
            updateTimer();
            updateInfobox();
            updateTextpenge();
            updateTextviden();
            updateTextmad();

            Log.d("Spilleplade", "Height:" + params.height + " Width: " + params.width);
            MoveIcon();


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(intent);
                    //Do something after 1500ms
                }
            }, 500);
            //
        }
    }

    static public void updateTimer() {
        tidTextView.setText(""+spiller.getTid());
        //int minutter = (20 - spiller.getTid())*60*12 / 24; // Vi regner i dage á 12 timer, da uret er 12timers
        //ur.animateToTime(minutter / 60, minutter % 60);
        switch (spiller.getTid()) {
            case 0:
                ur.setImageResource(R.drawable.ur0);
                break;
            case 1:
                ur.setImageResource(R.drawable.ur1);
                break;
            case 2:
                ur.setImageResource(R.drawable.ur2);
                break;
            case 3:
                ur.setImageResource(R.drawable.ur3);
                break;
            case 4:
                ur.setImageResource(R.drawable.ur4);
                break;
            case 5:
                ur.setImageResource(R.drawable.ur5);
                break;
            case 6:
                ur.setImageResource(R.drawable.ur6);
                break;
            case 7:
                ur.setImageResource(R.drawable.ur7);
                break;
            case 8:
                ur.setImageResource(R.drawable.ur8);
                break;
            case 9:
                ur.setImageResource(R.drawable.ur9);
                break;
            case 10:
                ur.setImageResource(R.drawable.ur10);
                break;
            case 11:
                ur.setImageResource(R.drawable.ur11);
                break;
            case 12:
                ur.setImageResource(R.drawable.ur12);
                break;
            case 13:
                ur.setImageResource(R.drawable.ur13);
                break;
            case 14:
                ur.setImageResource(R.drawable.ur14);
                break;
            case 15:
                ur.setImageResource(R.drawable.ur15);
                break;
            case 16:
                ur.setImageResource(R.drawable.ur16);
                break;
            case 17:
                ur.setImageResource(R.drawable.ur17);
                break;
            case 18:
                ur.setImageResource(R.drawable.ur18);
                break;
            case 19:
                ur.setImageResource(R.drawable.ur19);
                break;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!continueBGMusic) {
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueBGMusic = false;
        MusicManager.start(this, R.raw.backgroundloop);
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
        prefs.edit().putInt("moveSpeed", spiller.getmoveSpeed()).apply();
        prefs.edit().putInt("Runde", spiller.getRunde()).apply();
        prefs.edit().putInt("LastBookBought", spiller.getLastBookBought()).apply();
    }

    public void MoveIcon() {
        if (spiller.getPosition() == 1) {
            setPlayerIconParams(felt1);
        } else if (spiller.getPosition() == 2) {
            setPlayerIconParams(felt2);
        } else if (spiller.getPosition() == 3) {
            setPlayerIconParams(felt3);
        } else if (spiller.getPosition() == 4) {
            setPlayerIconParams(felt4);
        } else if (spiller.getPosition() == 5) {
            setPlayerIconParams(felt5);
        } else if (spiller.getPosition() == 6) {
            setPlayerIconParams(felt6);
        } else if (spiller.getPosition() == 7) {
            setPlayerIconParams(felt7);
        } else {
            setPlayerIconParams(felt0);
        }
        Log.d("Spilleplade", "MoveIcon called to " + spiller.getPosition());
    }

    public void setPlayerIconParams(Button felt) {
        Player.animate().translationXBy(felt.getX()-Player.getX()).translationYBy(felt.getY()-Player.getY());
        //Player.setX(felt.getX());
        //Player.setY(felt.getY());


    }

    public void randomEvent() {
        while (lastEvent == randomNum) {
            randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        }

        lastEvent = randomNum;
        switch (randomNum) {
            case 1:

                dialog.setTitle("Du er blevet syg!");
                dialog.setMessage("Du er blevet syg og har derfor halvt så meget tid i denne uge.");
                dialog.show();
                spiller.setTid(spiller.getTid() / 2);
                Log.d("SpillePlade", "Random event 1 triggered");
                break;

            case 2:
                dialog.setTitle("Dine forældre har brug for penge");
                dialog.setMessage("Dine forældre har brugt nogle af dine penge. -20kr");
                dialog.show();
                if (spiller.getPenge() >= 20) {
                    spiller.setPenge(spiller.getPenge() - 20);
                } else spiller.setPenge(0);
                Log.d("SpillePlade", "Random event 2 triggered");
                break;

            case 3:
                dialog.setTitle("På vejen hjem faldt du og slog hovedet");
                dialog.setMessage("Du har mistet viden. -10 viden");
                dialog.show();
                if (spiller.getViden() >= 10) {
                    spiller.setViden(spiller.getViden() - 10);
                } else spiller.setViden(0);
                Log.d("SpillePlade", "Random event 3 triggered");
                break;

            case 4:
                dialog.setTitle("Maden du har spiste var dårlig");
                dialog.setMessage("Du er nu mere sulten. -30 mad");
                dialog.show();
                if (spiller.getHp() >= 30) {
                    spiller.setHp(spiller.getHp() - 30);
                } else spiller.setHp(0);
                Log.d("SpillePlade", "Random event 4 triggered");
                break;

            case 5:
                dialog.setTitle("Vejret er dårligt");
                dialog.setMessage("Det tordner og lyner og du bliver hjemme i denne uge.");
                dialog.show();
                spiller.setTid(0);
                Log.d("SpillePlade", "Random event 5 triggered");
                break;

            case 6:
                dialog.setTitle("Du er blevet røvet");
                dialog.setMessage("En tyv har taget alle dine penge -" + spiller.getPenge());
                dialog.show();
                spiller.setPenge(0);
                Log.d("SpillePlade", "Random event 6 triggered");
                break;

            case 7:
                dialog.setTitle("Du vågner op super frisk!");
                dialog.setMessage("Du er frisk og springfyldt med energi, og har ekstra tid i denne uge. +3 tid");
                dialog.show();
                spiller.setTid(spiller.getTid() + 3);
                Log.d("SpillePlade", "Random event 7 triggered");
                break;

            case 8:
                Log.d("SpillePlade", "Random event 8 triggered (nothing)");
                break;

            case 9:
                Log.d("SpillePlade", "Random event 9 triggered (nothing)");
                break;

            case 10:
                Log.d("SpillePlade", "Random event 10 triggered (nothing)");
                break;

            case 11:
                Log.d("SpillePlade", "Random event 11 triggered (nothing)");
                break;
        }

    }


    static public void updateEntireBoard() {
        SpillePlade.updateTextpenge();
        SpillePlade.updateTextmad();
        SpillePlade.updateTextviden();
        SpillePlade.updateTimer();
    }
}


