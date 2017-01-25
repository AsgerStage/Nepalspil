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
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.models.Spiller.instans;


public class Skole extends AppCompatActivity {

    private Animation animation;
    private Animation animationfood;
    static TextView textpenge;
    static TextView textviden;
    static TextView textmad;
    static TextView playerInfo;
    private Topbar topbar;


    //Studying
    final int VIDEN_PER_CLICK = 1;
    final int TIME_PER_CLICK = 1;

    //Eating
    final int FOOD_PER_CLICK = 10;
    final int COST_PER_FOOD_CLICK = 0;
    final int TIME_COST_EATING = 1;

    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    TextView schoolText;

    AlertDialog.Builder dialog;

    @Override
    public void onResume() {
        super.onResume();
        // updateInfo();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);

        topbar = new Topbar();
        topbar.init(this);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        final TextView schoolText = (TextView) findViewById(R.id.schoolText);
       // playerInfo = (TextView) findViewById(R.id.schoolPlayerInfo);
        final TextView klassetrin = (TextView) findViewById(R.id.klassetrin);
        Button bSpis = (Button) findViewById(R.id.spis);
        Button bStuder = (Button) findViewById(R.id.Studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);
        ImageView hjemBack = (ImageView) findViewById(R.id.hjemBack);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        animationfood = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        final TextView scroll = (TextView) findViewById(R.id.plusknowledge);
        final TextView mad = (TextView) findViewById(R.id.scrollfood);
        mad.setText("");
        scroll.setText("");
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        updateText();

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/EraserDust.ttf");
        klassetrin.setTypeface(face);



        dialog = new AlertDialog.Builder(Skole.this);
        schoolText.setText("I skolen kan du spise, studere og gå til eksamen.");
        klassetrin.setText("Du går i " + instans.getKlassetrin() + ". klasse.");
        if (instans.getKlassetrin() >= 10) {
            bEksamen.setVisibility(View.INVISIBLE);
        }
        onResume();
        {

        }


        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                dialog.setTitle("Skolen");
                dialog.setMessage("I skolen kan du studere og få en smule mad. \n Tryk på knapperne for at studere eller spise. Når du har fået viden nok, kan du gå til eksamen og hvis du består, kan du rykke op i næste klasse.\n Al undervisning er svær, så husk at bruge lektiehjælpen, hvis du ikke forstår det hele.");
                dialog.show();
            }
        });
        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                if (instans.getTid() >= TIME_PER_CLICK) {
                    mad.setText("+" + FOOD_PER_CLICK + " mad");
                    mad.startAnimation(animationfood);
                    spis();
                    schoolText.setText("Mmm! Du har spist skolemad.");
                   updateText();
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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at spise");
                    dialog.show();
                }
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                int thisStudy = studer();
                if (instans.getTid() >= TIME_PER_CLICK) {
                    if (instans.getTid() >= TIME_PER_CLICK && thisStudy == 1) {
                        scroll.setText("+" + VIDEN_PER_CLICK + " viden");
                        scroll.startAnimation(animation);
                        instans.study(TIME_PER_CLICK, VIDEN_PER_CLICK);
                       updateText();
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
                        scroll.setText("+1 lektiehjælp");
                        scroll.startAnimation(animation);
                        instans.study(TIME_PER_CLICK, 0);
                        instans.setGlemtViden(instans.getGlemtViden() + 1);
                        updateText();
                    } else if (thisStudy == 3) {
                        scroll.setText("+0 viden");
                        scroll.startAnimation(animation);
                        instans.study(TIME_PER_CLICK, 0);
                        updateText();
                    }
                } else {

                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at studere.");
                    dialog.show();
                }
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                if (kanStartEksamen()) {
                    schoolText.setText("Held og Lykke!");
                    finish();
                    Intent myIntent = new Intent(Skole.this, Eksamen.class);
                    startActivity(myIntent);

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke nok viden til at starte eksamenen! Du skal have mindst " + vidensKrav() + " viden for at starte eksamen.");
                    dialog.show();

                }

            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
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
            updateText();
        } else {
            schoolText.setText("");
        }

    }

    public boolean kanStartEksamen() {
        if ((instans.getViden() >= vidensKrav())) {
            return true;
        } else
            return false;
    }

    public static String updateInfo() {
        return "Tid: " + instans.getTid();

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
   public void updateText() {
       topbar.opdaterGui(instans);
       SpillePlade.updateEntireBoard();
   }


}
