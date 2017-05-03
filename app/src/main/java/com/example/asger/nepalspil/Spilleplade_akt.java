package com.example.asger.nepalspil;

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
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.diverse.MusicManager;
import com.example.asger.nepalspil.felter.Boghandel;
import com.example.asger.nepalspil.felter.Butikken;
import com.example.asger.nepalspil.felter.Marken;
import com.example.asger.nepalspil.felter.Hjem;
import com.example.asger.nepalspil.felter.Lektiehjaelp;
import com.example.asger.nepalspil.felter.Marked;
import com.example.asger.nepalspil.felter.Skole;
import com.example.asger.nepalspil.felter.Vaerksted;
import com.example.asger.nepalspil.model.Figuruheld;
import com.example.asger.nepalspil.model.Spiller;

import java.util.concurrent.ThreadLocalRandom;

import com.example.asger.nepalspil.diverse.Topbar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.asger.nepalspil.model.Spiller.instans;

/*
rsync /home/j/android/studenterprojekter/BrInt/g10_Nepalspil/Nepalspil/app/build/outputs/apk/app-debug.apk         j:javabog.dk/privat/nepalspil.apk
 */
public class Spilleplade_akt extends AppCompatActivity {
    TextView infobox;
    private Topbar topbar;


    ImageView figurbrik;
    ImageView ur;
    //    static ClockImageView ur;
    TextView tidTextView;

    boolean continueBGMusic;
    AlertDialog.Builder dialog;
    SweetAlertDialog pDialog;
    SharedPreferences prefs;


    private Button[] felter = new Button[8];
    private static Class[] feltNummerTilAktivitet = {
            Hjem.class, Lektiehjaelp.class, Vaerksted.class, Boghandel.class, Skole.class, Marken.class, Marked.class, Butikken.class
    };
    ImageView ingameopt;
    ImageView spilpladeHelp;
    int lastEvent = 0;
    int randomNum = 0;
    private boolean brikErUnderFlytning;

    @Override
    public void onBackPressed() {
        /*
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        FrameLayout v = new FrameLayout(this);
        v.addView(new Button(this));
        wm.addView(v, new WindowManager.LayoutParams());

//        CommonConfetti.rainingConfetti((ViewGroup) getWindow().getDecorView(), new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW}).infinite();
        CommonConfetti.rainingConfetti(v, new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW}).infinite();
        */
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
                        Spilleplade_akt.this.finish();
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
        if (Spiller.instans==null) { finish(); return; } // genstart i frisk JVM - vis hovedmenu
        setContentView(R.layout.spilleplade);

        continueBGMusic = true;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Spilleplade_akt.this);
        figurbrik = (ImageView) findViewById(R.id.krishna);
        figurbrik.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);

        if (savedInstanceState==null && getIntent().getBooleanExtra("genoptag", false) == false) { // nyt spil - vis dialog
            new AlertDialog.Builder(this)
                    .setTitle("Hej!")
                    .setIcon(Spiller.instans.figurdata.drawable_figur_halv_id)
                    .setMessage("Hjælp mig med at nå 10. klasse.\n\nHvis vi klarer den, så jeg kan få en uddannelse, og du har vundet spillet.")
                    .setCancelable(false)
                    .setPositiveButton("OK", null)
                    .show();
        }



        infobox = (TextView) findViewById(R.id.infobox);


        tidTextView = (TextView) findViewById(R.id.tid);
        //ur = (ClockImageView) findViewById(R.id.ur);
        ur = (ImageView) findViewById(R.id.ur);
        ur.setImageResource(R.drawable.ur16);

        felter[0] = (Button) findViewById(R.id.felt0);
        felter[1] = (Button) findViewById(R.id.felt1);
        felter[2] = (Button) findViewById(R.id.felt2);
        felter[3] = (Button) findViewById(R.id.felt3);
        felter[4] = (Button) findViewById(R.id.felt4);
        felter[5] = (Button) findViewById(R.id.felt5);
        felter[6] = (Button) findViewById(R.id.felt6);
        felter[7] = (Button) findViewById(R.id.felt7);
        ImageView back = (ImageView) findViewById(R.id.ikon_tilbage);
        back.setVisibility(View.INVISIBLE);
        ingameopt = (ImageView) findViewById(R.id.menuknap);
        spilpladeHelp = (ImageView) findViewById(R.id.vaerkstedHelp);

        // Placér spilleren på pladen - skal ske efter onCreate, så vi sender det til hovedtråden forsinket
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sætBrikposition(Spiller.instans.position);
            }
        });


        felter[0].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(0);
            }
        });


        felter[1].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(1);


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
            }
        });

        felter[3].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(3);
            }
        });

        felter[4].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(4);
            }
        });

        felter[5].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(5);
            }
        });

        felter[6].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(6);
            }
        });

        felter[7].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flytBrikTilFelt(7);


            }
        });

        spilpladeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Spilleplade_akt.this, R.anim.image_click));
                AlertDialog.Builder dialog = new AlertDialog.Builder(Spilleplade_akt.this);
                dialog.setMessage("Målet med spillet er at færdiggøre 10. klasse. Du starter i 1. klasse og skal til eksamen hvert år. \n \nFor at bestå den årlige eksamen skal du optjene viden, og for at få viden skal du studere og have noget at spise. \n\nPå pladens otte felter kan du optjene viden, mad og penge og købe vigtige hjælpemidler.\n \nUndervejs vil du møde forhindringer, som gør det sværere at nå målet.\nSpillet er på tid, så du skal skynde dig. Du kan se, hvor meget du har optjent øverst på skærmen. ");
                dialog.show();

            }
        });

        ingameopt.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             v.startAnimation(AnimationUtils.loadAnimation(Spilleplade_akt.this, R.anim.image_click));

                                             CharSequence options[] = new CharSequence[]{"Stop musik", "Afslut spil"};
                                             AlertDialog.Builder builder = new AlertDialog.Builder(Spilleplade_akt.this);
                                             builder.setTitle("Indstillinger");
                                             builder.setItems(options, new DialogInterface.OnClickListener() {
                                                         @Override
                                                         public void onClick(DialogInterface dialog, int which) {


                                                             switch (which) {
                                                                 case 0:
                                                                     MusicManager.mp.stop();
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
        if (brikErUnderFlytning) return;
        brikErUnderFlytning = true;

        final int gammelPos = Spiller.instans.position;
        final int gammelTid = Spiller.instans.tid;

        final boolean turenErGået = Spiller.instans.rykTilFelt(feltPos);
        Spiller.gem(this, Spiller.instans);

        final int nyPos = Spiller.instans.position;
        final int nyTid = Spiller.instans.tid;
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

        final double posÆndringPerSkridt, tidÆndringPerSkridt;
        if (Math.abs(posÆndring) <= Math.abs(tidÆndring)) {
            posÆndringPerSkridt = (double) posÆndring / tidÆndring;
            tidÆndringPerSkridt = 1;
        } else {
            posÆndringPerSkridt = Math.signum(posÆndring);
            tidÆndringPerSkridt = (double) Math.abs(tidÆndring) / Math.abs(posÆndring);
        }


        final AnimatorListenerAdapter brikAnimationLytter = new AnimatorListenerAdapter() {
            double tid = gammelTid - 0.001; // undgå afrundingsproblemer
            double pos = gammelPos;

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("SpillePlade", "onAnimationEnd " + tid + " <=? " + nyTid + "  pos=" + pos);
                if (tid <= nyTid)
                    return; // vi er allerede færdige (forstår ikke hvorfor onAnimationEnd blir kaldt en ekstra gang, men det gør den)
                tid = tid - tidÆndringPerSkridt;
                pos = pos + posÆndringPerSkridt;
                updateTimer((int) (tid+0.5));
                if (tid <= nyTid) {
                    // vi er færdige med at rykke animeret
                    flytBrikTilFeltAfslutning(turenErGået, nyPos);
                } else {
                    // ryk til næste felt animeret. onAnimationEnd kaldes igen når brikken er fremme ved næste felt
                    sætBrikpositionOgTidAnimeret((int) (pos + 0.5 + Spiller.BRÆTSTØRRELSE), this, tidÆndringPerSkridt);
                }
            }
        };

        brikAnimationLytter.onAnimationEnd(null); // start animationen ved at kalde onAnimationEnd (lidt et hack :-)


    }

    private void flytBrikTilFeltAfslutning(boolean turenErGået, int feltPos) {

        if (turenErGået) {
            if (Spiller.instans.mad - 30 > 0) {

                Toast.makeText(Spilleplade_akt.this, "Ugen er gået", Toast.LENGTH_SHORT).show();

            } else if (Spiller.instans.mad - 30 <= 0) {
                dialog.setTitle("Husk at spise!");
                dialog.setMessage("Ugen er gået og du har spist for lidt, derfor er du langsommere i denne uge");
                dialog.show();

                Spiller.instans.tid = 10;
            }
            if (Spiller.instans.mad >= 30) {
                Spiller.instans.mad = Spiller.instans.mad - 30;
            } else Spiller.instans.mad = 0;
            if (Spiller.instans.runde % 5 == 0) randomEvent();
            updateText();

            sætBrikposition(Spiller.instans.position);
            brikErUnderFlytning = false;
        } else {
            Class aktivitet = feltNummerTilAktivitet[feltPos];
            final Intent intent = new Intent(Spilleplade_akt.this, aktivitet);
            updateText();

            sætBrikposition(Spiller.instans.position);


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    brikErUnderFlytning = false;
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
        updateTimer(Spiller.instans.tid);
        infobox.setText("Uge: " + Spiller.instans.runde);
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueBGMusic = false;
        MusicManager.start(this, R.raw.backgroundloop);
        updateText();


    }

    private void sætBrikposition(int feltnummer) {
        Log.d("Spilleplade", "sætBrikposition called to " + feltnummer);
        Button felt = felter[(feltnummer + Spiller.BRÆTSTØRRELSE) % Spiller.BRÆTSTØRRELSE];
        figurbrik.animate()
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .translationXBy(felt.getX() - figurbrik.getX()).translationYBy(felt.getY() - figurbrik.getY());
        //figurbrik.setX(felt.getX());
        //figurbrik.setY(felt.getY());
    }


    private void sætBrikpositionOgTidAnimeret(int feltnummer, Animator.AnimatorListener animatorListener, double tidÆndringPerSkridt) {
        Log.d("Spilleplade", "sætBrikpositionOgTidAnimeret " + feltnummer);
        Button felt = felter[(feltnummer + Spiller.BRÆTSTØRRELSE) % Spiller.BRÆTSTØRRELSE];
        figurbrik.animate()
                .translationXBy(felt.getX() - figurbrik.getX())
                .translationYBy(felt.getY() - figurbrik.getY())
                .setDuration((int) (250*tidÆndringPerSkridt))
                .setInterpolator(new LinearInterpolator())
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

        int nyPenge = Spiller.instans.penge + u.pengeForskel;
        if (nyPenge < 0) return; // uheld kunne ikke ske - ikke penge nok
        int nyViden = Spiller.instans.viden + u.videnForskel;
        if (nyViden < 0) return; // uheld kunne ikke ske - ikke viden nok
        int nyMad = Spiller.instans.mad + u.madForskel;
        if (nyMad < 0) return; // uheld kunne ikke ske - ikke mad nok

        // Opdater spiller med uheld
        Spiller.instans.penge = nyPenge;
        Spiller.instans.viden = nyViden;
        Spiller.instans.mad = nyMad;
        Spiller.instans.tid = (int) (Spiller.instans.tid * u.tidFaktor);

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


}


