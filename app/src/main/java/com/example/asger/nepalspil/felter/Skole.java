package com.example.asger.nepalspil.felter;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);

        topbar = new Topbar();
        topbar.init(this);
        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_hel_id);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        taleboble_tekst = (TextView) findViewById(R.id.taleboble_tekst);
        final TextView klassetrin = (TextView) findViewById(R.id.klassetrin);
        Button bSpis = (Button) findViewById(R.id.knap_spis);
        Button bStuder = (Button) findViewById(R.id.knap_studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        animationfood = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        final TextView flyvoptekst_studer = (TextView) findViewById(R.id.flyvoptekst_studer);
        final TextView flyvoptekst_spis = (TextView) findViewById(R.id.flyvoptekst_spis);
        flyvoptekst_spis.setText("");
        flyvoptekst_studer.setText("");
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        topbar.opdaterGui(instans);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/EraserDust.ttf");
        klassetrin.setTypeface(face);


        dialog = new AlertDialog.Builder(Skole.this);
        klassetrin.setText("Du går i " + instans.getKlassetrin() + ". klasse.");
        if (instans.getKlassetrin() >= 10) {
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
                if (instans.getTid() >= TIME_PER_CLICK) {
                    flyvoptekst_spis.setText("+" + FOOD_PER_CLICK + " mad");
                    flyvoptekst_spis.startAnimation(animationfood);
                    spis();
                    taleboble_tekst.setText("Mmm! Du har spist skolemad.");
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("bitesound.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Du har ikke tid til at spise.")
                            .show();
                }
                topbar.opdaterGui(instans);
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                int thisStudy = studer();
                if (instans.getTid() >= TIME_PER_CLICK) {
                    if (instans.getTid() >= TIME_PER_CLICK && thisStudy == 1) {
                        taleboble_tekst.setText("Du blev lidt klogere");
                        flyvoptekst_studer.setText("+" + VIDEN_PER_CLICK + " viden");
                        flyvoptekst_studer.startAnimation(animation);
                        instans.study(TIME_PER_CLICK, VIDEN_PER_CLICK);
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        try {
                            mp.reset();
                            AssetFileDescriptor afd;
                            afd = getAssets().openFd("study.mp3");
                            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                            mp.prepare();
                            mp.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(instans.getViden());
                    } else if (thisStudy == 2) {
                        taleboble_tekst.setText("Med lidt lektiehjælp kunne du nok forstå det");
                        flyvoptekst_studer.setText("+1 lektiehjælp");
                        flyvoptekst_studer.startAnimation(animation);
                        instans.study(TIME_PER_CLICK, 0);
                        instans.setGlemtViden(instans.getGlemtViden() + 1);
                    } else if (thisStudy == 3) {
                        taleboble_tekst.setText("Du forstod det ikke rigtig");
                        flyvoptekst_studer.setText("+0 viden");
                        flyvoptekst_studer.startAnimation(animation);
                        instans.study(TIME_PER_CLICK, 0);
                    }
                } else {

                    new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Du har ikke tid til at studere.")
                            .show();
                }
                topbar.opdaterGui(instans);
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                if (kanStartEksamen()) {
                    taleboble_tekst.setText("Held og Lykke!");
                    finish();
                    Intent myIntent = new Intent(Skole.this, Eksamen.class);
                    startActivity(myIntent);

                } else {
                    new SweetAlertDialog(Skole.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ikke nok viden!")
                            .setContentText("Du har ikke nok viden til at starte eksamen! Du skal have mindst " + vidensKrav() + " viden for at starte eksamen.")
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


    public int studer() {
        int result = 0;
        switch (instans.getLearningAmp()) {

            case 0:
                result = tryToStudy(0.5, 0.25, 0);
                Log.d("Spil", "Spiller studied with 0 learning Amp");
                break;
            case 1:
                result = tryToStudy(0.45, 0.2, 0);
                Log.d("Spil", "Spiller studied with 1 learning Amp");
                break;
            case 2:
                result = tryToStudy(0.40, 0.15, 0);
                Log.d("Spil", "Spiller studied with 2 learning Amp");
                break;
            case 3:
                result = tryToStudy(0.30, 0, 0);
                Log.d("Spil", "Spiller studied with 3 learning Amp");
                break;


        }
        return result;
    }


    public void spis() {
        if (instans.getTid() > 0) {
            instans.eat(TIME_COST_EATING, COST_PER_FOOD_CLICK, FOOD_PER_CLICK);
            topbar.opdaterGui(instans);
        } else {
            taleboble_tekst.setText("");
        }

    }

    public boolean kanStartEksamen() {
        if ((instans.getViden() >= vidensKrav())) {
            return true;
        } else
            return false;
    }


    public static int vidensKrav() {
        switch (instans.getKlassetrin()) {
            case 1:
                return 10;
            case 2:
                return 40;
            case 3:
                return 90;
            case 4:
                return 110;
            case 5:
                return 160;
            case 6:
                return 220;
            case 7:
                return 300;
            case 8:
                return 400;
            case 9:
                return 500;
            case 10:
                return 700;
            case 11:
                return 800;


        }
        return 10 * instans.getKlassetrin();
    }

    public int tryToStudy(double success, double homework, double fail) {
        double rand = Math.random();
        Log.d("Spil", "rand = " + rand + " Success = " + success + " homework = " + homework + "fail = " + fail);
        if (success <= rand && rand < 1) return 1;
        else if (rand < success && rand >= homework) return 2;
        else if (rand < homework && rand >= fail) return 3;
        else return 0;
    }

}
