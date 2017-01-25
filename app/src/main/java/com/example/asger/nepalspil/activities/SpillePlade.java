package com.example.asger.nepalspil.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.felter.Boghandel;
import com.example.asger.nepalspil.felter.Butikken;
import com.example.asger.nepalspil.felter.Farm;
import com.example.asger.nepalspil.felter.Hjem;
import com.example.asger.nepalspil.felter.Lektiehjaelp;
import com.example.asger.nepalspil.felter.Marked;
import com.example.asger.nepalspil.felter.Skole;
import com.example.asger.nepalspil.felter.Vaerksted;
import com.example.asger.nepalspil.models.Figuruheld;
import com.example.asger.nepalspil.models.Spiller;

import java.util.concurrent.ThreadLocalRandom;

import com.example.asger.nepalspil.felter.Topbar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.asger.nepalspil.activities.MusicManager.mp;
import static com.example.asger.nepalspil.models.Spiller.instans;

//import static com.example.asger.nepalspil.R.id.player;

public class SpillePlade extends AppCompatActivity {
    TextView infobox;
    private Topbar topbar;


    ImageView Player;
    static ImageView ur;
    //    static ClockImageView ur;
    static private TextView tidTextView;

    boolean continueBGMusic;
    AlertDialog.Builder dialog;
    SweetAlertDialog pDialog;
    SharedPreferences prefs;


    private Button[] felter = new Button[8];
    private static Class[] feltNummerTilAktivitet = {
            Hjem.class, Lektiehjaelp.class, Vaerksted.class, Boghandel.class, Skole.class, Farm.class, Marked.class, Butikken.class
    };
    ImageView ingameopt;
    ImageView spilpladeHelp;
    int lastEvent = 0;
    int randomNum = 0;

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Afslut spil?")
                .setContentText("Er du sikker på du vil afslutte spillet?")
                .showCancelButton(true)
                .setConfirmText("Ja")
                .setCancelText("Nej")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        SpillePlade.this.finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();


    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spilplade);

        continueBGMusic = true;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(SpillePlade.this);
        if (Spiller.instans.sex) {
            Player = (ImageView) findViewById(R.id.krishna);
            Player.setImageResource(R.drawable.drenghelfigur1);

        } else if (!Spiller.instans.sex) {
            Player = (ImageView) findViewById(R.id.krishna);
            Player.setImageResource(R.drawable.pigehelfigur2);

        }
        if (getIntent().getBooleanExtra("genoptag", false) == false) { // nyt spil - vis dialog
            new AlertDialog.Builder(this)
                    .setMessage("Hej! Hjælp os med at få en uddannelse. Vi skal have mad, viden og penge, så vi kan købe bøger, blyanter og en cykel og bestå de årlige eksamener. \n \n Hvis vi når 10. klasse, kan vi tage en uddannelse og få et godt job, og du har vundet spillet.")
                    .setCancelable(false)
                    .setPositiveButton("OK", null)
                    .show();
        }



        infobox = (TextView) findViewById(R.id.infobox);


        tidTextView = (TextView) findViewById(R.id.tid);
        //ur = (ClockImageView) findViewById(R.id.ur);
        ur = (ImageView) findViewById(R.id.ur);
        ur.setImageResource(R.drawable.ur16);
        updateEntireBoard();

        felter[0] = (Button) findViewById(R.id.felt0);
        felter[1] = (Button) findViewById(R.id.felt1);
        felter[2] = (Button) findViewById(R.id.felt2);
        felter[3] = (Button) findViewById(R.id.felt3);
        felter[4] = (Button) findViewById(R.id.felt4);
        felter[5] = (Button) findViewById(R.id.felt5);
        felter[6] = (Button) findViewById(R.id.felt6);
        felter[7] = (Button) findViewById(R.id.felt7);
        ImageView back = (ImageView) findViewById(R.id.hjemBack);
        back.setVisibility(View.INVISIBLE);
        ingameopt = (ImageView) findViewById(R.id.menuknap);
        spilpladeHelp = (ImageView) findViewById(R.id.vaerkstedHelp);

        // Placér spilleren på pladen - skal ske efter onCreate, så vi sender det til hovedtråden forsinket
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sætBrikposition(Spiller.instans.getPosition());
            }
        });


        felter[0].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(0);
                saveToPrefs();
            }
        });


        felter[1].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(1);
                saveToPrefs();


/*
               TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                       0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
               animation.setDuration(2000);  // animation duration
               animation.setFillAfter(true);

               star.startAnimation(animation);  // start animation*/
            }
        });

        felter[2].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(2);
                saveToPrefs();
            }
        });

        felter[3].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(3);
                saveToPrefs();
            }
        });

        felter[4].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(4);
                saveToPrefs();
            }
        });

        felter[5].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(5);
                saveToPrefs();
            }
        });

        felter[6].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(6);
                saveToPrefs();
            }
        });

        felter[7].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(7);
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


    /**
     * Flytter spilleren
     *
     * @param feltPos feltnummer
     */
    private void flytBrikTilFelt(int feltPos) {

        final int gammelPos = Spiller.instans.getPosition();
        final int gammelTid = Spiller.instans.getTid();

        final boolean turenErGået = Spiller.instans.move(feltPos);

        final int nyPos = Spiller.instans.getPosition();
        final int nyTid = Spiller.instans.getTid();
        int tidÆndring = gammelTid - nyTid;

        if (turenErGået || tidÆndring == 0) {
            flytBrikTilFeltAfslutning(turenErGået, nyPos);
            return;
        }

        // Vi skal lave en animation fra startfelt til slutfelt
        if (tidÆndring <= 0) {
            new IllegalStateException("Intern fejl - tidÆndring=" + tidÆndring).printStackTrace();
            flytBrikTilFeltAfslutning(turenErGået, nyPos);
            return;
        }

        int posÆndring = nyPos - gammelPos;
        // ovenstående vil gå mellem felterne 0-1-2-3-4-5-6 til 7, men aldrig passere 0.
        // tjek for om det er smartere med et hop mellem felt 7 og felt 0:
        if (posÆndring > Spiller.BRÆTSTØRRELSE / 2) {
            posÆndring = posÆndring - Spiller.BRÆTSTØRRELSE; // smartere at gå baglæns via 0 og 7
            Log.d("SpillePlade", "flytBrikTilFelt: smartere at gå baglæns via 0 og 7  posÆndring=" + posÆndring);
        } else if (posÆndring < -Spiller.BRÆTSTØRRELSE / 2) {
            posÆndring = posÆndring + Spiller.BRÆTSTØRRELSE; // smartere at gå forlæns via 7 og 0
            Log.d("SpillePlade", "flytBrikTilFelt: smartere at gå forlæns via 7 og 0  posÆndring=" + posÆndring);
        }

        final double posÆndringPerTid = (double) (posÆndring) / tidÆndring;

        final AnimatorListenerAdapter brikAnimationLytter = new AnimatorListenerAdapter() {
            int tid = gammelTid;
            double pos = gammelPos;

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("SpillePlade", "onAnimationEnd " + tid + " =? " + nyTid + "  pos=" + pos);
                if (tid == nyTid)
                    return; // vi er allerede færdige (forstår ikke hvorfor onAnimationEnd blir kaldt en ekstra gang, men det gør den)
                tid = tid - 1;
                pos = pos + posÆndringPerTid;
                updateTimer(tid);
                if (tid == nyTid) {
                    // vi er færdige med at rykke animeret
                    flytBrikTilFeltAfslutning(turenErGået, nyPos);
                } else {
                    // ryk til næste felt animeret. onAnimationEnd kaldes igen når brikken er fremme ved næste felt
                    sætBrikpositionOgTidAnimeret((int) (pos + 0.5), this);
                }
            }
        };

        brikAnimationLytter.onAnimationEnd(null); // start animationen ved at kalde onAnimationEnd (lidt et hack :-)


    }


    private void flytBrikTilFeltAfslutning(boolean turenErGået, int feltPos) {

        if (turenErGået) {
            if (Spiller.instans.getHp() - 30 > 0) {

                Toast.makeText(SpillePlade.this, "Ugen er gået", Toast.LENGTH_SHORT).show();

            } else if (Spiller.instans.getHp() - 30 <= 0) {
                dialog.setTitle("Husk at spise!");
                dialog.setMessage("Ugen er gået og du har glemt at spise, du har derfor mindre tid i denne uge");
                dialog.show();

                Spiller.instans.setTid(8);
            }
            if (Spiller.instans.getHp() >= 30) {
                Spiller.instans.setHp(Spiller.instans.getHp() - 30);
            } else Spiller.instans.setHp(0);
            if (Spiller.instans.runde % 5 == 0) randomEvent();
            updateText();

            sætBrikposition(Spiller.instans.getPosition());
        } else {
            Class aktivitet = feltNummerTilAktivitet[feltPos];
            final Intent intent = new Intent(SpillePlade.this, aktivitet);
            updateText();

            sætBrikposition(Spiller.instans.getPosition());


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

    private void updateTimer(int tid) {
        tidTextView.setText("" + tid);
        //int minutter = (20 - Spiller.instans.getTid())*60*12 / 24; // Vi regner i dage á 12 timer, da uret er 12timers
        //ur.animateToTime(minutter / 60, minutter % 60);
        switch (tid) {
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


    public void updateText() {
        topbar.opdaterGui(instans);
        SpillePlade.updateEntireBoard();
        // updateTimer(Spiller.instans.getTid());
        infobox.setText("Uge: " + Spiller.instans.getRunde());
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueBGMusic = false;
        MusicManager.start(this, R.raw.backgroundloop);
        updateText();
    }

    public void saveToPrefs() {
        prefs.edit().putBoolean("Sex", Spiller.instans.getSex()).apply();
        prefs.edit().putInt("GlemtViden", Spiller.instans.getGlemtViden()).apply();
        prefs.edit().putInt("Books", Spiller.instans.getBooks()).apply();
        prefs.edit().putInt("Position", Spiller.instans.getPosition()).apply();
        prefs.edit().putString("Navn", Spiller.instans.getNavn()).apply();
        prefs.edit().putInt("Penge", Spiller.instans.getPenge()).apply();
        prefs.edit().putInt("Hp", Spiller.instans.getHp()).apply();
        prefs.edit().putInt("Viden", Spiller.instans.getViden()).apply();
        prefs.edit().putInt("Klassetrin", Spiller.instans.getKlassetrin()).apply();
        prefs.edit().putInt("Tid", Spiller.instans.getTid()).apply();
        prefs.edit().putInt("moveSpeed", Spiller.instans.getmoveSpeed()).apply();
        prefs.edit().putInt("Runde", Spiller.instans.getRunde()).apply();
        prefs.edit().putInt("LastBookBought", Spiller.instans.getLastBookBought()).apply();
    }

    private void sætBrikposition(int feltnummer) {
        Log.d("Spilleplade", "sætBrikposition called to " + feltnummer);
        Button felt = felter[(feltnummer + Spiller.BRÆTSTØRRELSE) % Spiller.BRÆTSTØRRELSE];
        Player.animate().translationXBy(felt.getX() - Player.getX()).translationYBy(felt.getY() - Player.getY());
        //Player.setX(felt.getX());
        //Player.setY(felt.getY());
    }


    private void sætBrikpositionOgTidAnimeret(int feltnummer, Animator.AnimatorListener animatorListener) {
        Log.d("Spilleplade", "sætBrikpositionOgTidAnimeret " + feltnummer);
        Button felt = felter[(feltnummer + Spiller.BRÆTSTØRRELSE) % Spiller.BRÆTSTØRRELSE];
        Player.animate()
                .translationXBy(felt.getX() - Player.getX())
                .translationYBy(felt.getY() - Player.getY())
                .setListener(animatorListener);
    }


    public void randomEvent() {
        while (lastEvent == randomNum) {
            randomNum = ThreadLocalRandom.current().nextInt(0, Spiller.instans.figurdata.uheld.size());
        }

        lastEvent = randomNum;
        Figuruheld u = Spiller.instans.figurdata.uheld.get(randomNum);
        Log.d("SpillePlade", "Uheld " + randomNum + " skete: " + u.json);
        if (u.titel == null) return;// ikke et rigtigt uheld, bare fyld

        int nyPenge = Spiller.instans.getPenge() + u.pengeForskel;
        if (nyPenge < 0) return; // uheld kunne ikke ske - ikke penge nok
        int nyViden = Spiller.instans.getViden() + u.videnForskel;
        if (nyViden < 0) return; // uheld kunne ikke ske - ikke viden nok
        int nyMad = Spiller.instans.getHp() + u.madForskel;
        if (nyMad < 0) return; // uheld kunne ikke ske - ikke mad nok

        // Opdater spiller med uheld
        Spiller.instans.setPenge(nyPenge);
        Spiller.instans.setViden(nyViden);
        Spiller.instans.setHp(nyMad);
        Spiller.instans.setTid((int) (Spiller.instans.getTid() * u.tidFaktor));

        dialog.setTitle(u.titel);
        dialog.setMessage(u.tekst);
        dialog.show();

        /*
        switch (randomNum) {
            case 1:

                dialog.setTitle("Du er blevet syg!");
                dialog.setMessage("Du er blevet syg og har derfor halvt så meget tid i denne uge.");
                dialog.show();
                Spiller.instans.setTid(Spiller.instans.getTid() / 2);
                Log.d("SpillePlade", "Random event 1 triggered");
                break;

            case 2:
                dialog.setTitle("Dine forældre har brug for penge");
                dialog.setMessage("Dine forældre har brugt nogle af dine penge. -20kr");
                dialog.show();
                if (Spiller.instans.getPenge() >= 20) {
                    Spiller.instans.setPenge(Spiller.instans.getPenge() - 20);
                } else Spiller.instans.setPenge(0);
                Log.d("SpillePlade", "Random event 2 triggered");
                break;

            case 3:
                dialog.setTitle("På vejen hjem faldt du og slog hovedet");
                dialog.setMessage("Du har mistet viden. -10 viden");
                dialog.show();
                if (Spiller.instans.getViden() >= 10) {
                    Spiller.instans.setViden(Spiller.instans.getViden() - 10);
                } else Spiller.instans.setViden(0);
                Log.d("SpillePlade", "Random event 3 triggered");
                break;

            case 4:
                dialog.setTitle("Maden du har spiste var dårlig");
                dialog.setMessage("Du er nu mere sulten. -30 mad");
                dialog.show();
                if (Spiller.instans.getHp() >= 30) {
                    Spiller.instans.setHp(Spiller.instans.getHp() - 30);
                } else Spiller.instans.setHp(0);
                Log.d("SpillePlade", "Random event 4 triggered");
                break;

            case 5:
                dialog.setTitle("Vejret er dårligt");
                dialog.setMessage("Det tordner og lyner og du bliver hjemme i denne uge.");
                dialog.show();
                Spiller.instans.setTid(0);
                Log.d("SpillePlade", "Random event 5 triggered");
                break;

            case 6:
                dialog.setTitle("Du er blevet røvet");
                dialog.setMessage("En tyv har taget alle dine penge -" + Spiller.instans.getPenge());
                dialog.show();
                Spiller.instans.setPenge(0);
                Log.d("SpillePlade", "Random event 6 triggered");
                break;

            case 7:
                dialog.setTitle("Du vågner op super frisk!");
                dialog.setMessage("Du er frisk og springfyldt med energi, og har ekstra tid i denne uge. +3 tid");
                dialog.show();
                Spiller.instans.setTid(Spiller.instans.getTid() + 3);
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
        */

    }


    static public void updateEntireBoard() {
    }
}


