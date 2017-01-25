package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.example.asger.nepalspil.activities.SpillePlade;
import com.example.asger.nepalspil.models.Spiller;

import java.io.IOException;

import static com.example.asger.nepalspil.models.Spiller.instans;


public class Marked extends AppCompatActivity {
    Toast t;
    AlertDialog.Builder dialog;
    TextView textpenge;
    TextView textviden;
    TextView textmad;
    TextView playerinfo;
    private Animation animation;
    private Animation animationfood;
    private Topbar topbar;

    //Working
    final int MONEY_PER_CLICK = 5;
    final int TIME_PER_CLICK = 1;

    //Eating
    final int FOOD_PER_CLICK = 10;
    final int COST_PER_FOOD_CLICK = 5;
    final int TIME_COST_EATING = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marked);

        dialog = new AlertDialog.Builder(Marked.this);
        t = new Toast(Marked.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        topbar = new Topbar();
        topbar.init(this);

        Button work = (Button) findViewById(R.id.workButton);
        final Button eat = (Button) findViewById(R.id.eatButton);
        final ImageView hjemBack = (ImageView) findViewById(R.id.hjemBack);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        /*textpenge = (TextView) findViewById(R.id.textpenge);
        textviden = (TextView) findViewById(R.id.textviden);
        textmad = (TextView) findViewById(R.id.textmad);*/
        final TextView money = (TextView) findViewById(R.id.money);
        final TextView food = (TextView) findViewById(R.id.food);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        animationfood = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);


        fieldinfo.setText(" På markedet kan du\n købe mad eller arbejde \n for at tjene penge.");
        updateText();
        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("På markedet kan du købe masser af sund mad. Det giver overskud til at få viden, men det er dyrt. Du kan også arbejde på markedet, men det tager tid.");
                dialog.show();
            }
        });

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Marked.this, R.anim.image_click));
                if (Spiller.instans.getTid() >= TIME_PER_CLICK && Spiller.instans.getKlassetrin() >= 3) {
                    Spiller.instans.work(TIME_PER_CLICK, MONEY_PER_CLICK);
                    money.setText("+" + MONEY_PER_CLICK + " kr");
                    money.startAnimation(animation);


                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("cash.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updateText();
                } else if (Spiller.instans.getTid() < 2) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Intet tid!");
                    dialog.setMessage("Du har ikke nok tid til at arbejde");
                    dialog.show();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke uddannelse nok til at arbejde her");
                    dialog.show();
                }
            }
        });

        eat.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Marked.this, R.anim.image_click));
                if (Spiller.instans.getPenge() >= COST_PER_FOOD_CLICK) {
                    Spiller.instans.eat(TIME_COST_EATING, COST_PER_FOOD_CLICK, FOOD_PER_CLICK);
                    if (mp.isPlaying()) {
                        mp.stop();
                        food.setText("+" + FOOD_PER_CLICK + " mad");
                        food.startAnimation(animationfood);
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


                    updateText();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Ingen penge!");
                    dialog.setMessage("Du har ikke nok penge til at spise");
                    dialog.show();
                }
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t.cancel();
                v.startAnimation(AnimationUtils.loadAnimation(Marked.this, R.anim.image_click));
                finish();

            }
        });
    }


    public void eat() {
        ;
    }


    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        t.cancel();
        finish();
    }

    public void updateText() {
        topbar.opdaterGui(instans);
        SpillePlade.updateEntireBoard();
    }
}