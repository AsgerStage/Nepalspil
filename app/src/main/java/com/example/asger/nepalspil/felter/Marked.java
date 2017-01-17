package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import java.io.IOException;

public class Marked extends AppCompatActivity {
    Toast t;
    AlertDialog.Builder dialog;
    TextView textpenge;
    TextView textviden;
    TextView textmad;
    TextView playerinfo;

    //Working
    final int MONEY_PER_CLICK =10;
    final int TIME_PER_CLICK =2;

    //Eating
    final int FOOD_PER_CLICK=10;
    final int COST_PER_FOOD_CLICK=5;
    final int TIME_COST_EATING=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marked);

        dialog = new AlertDialog.Builder(Marked.this);
        t = new Toast(Marked.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        Button work = (Button) findViewById(R.id.workButton);
        final Button eat = (Button) findViewById(R.id.eatButton);
        final ImageView back = (ImageView) findViewById(R.id.backButton);
        ImageView helpField = (ImageView) findViewById(R.id.markedHelp);

        textpenge = (TextView) findViewById(R.id.textpenge);
        textviden = (TextView) findViewById(R.id.textviden);
        textmad = (TextView) findViewById(R.id.textmad);


        fieldinfo.setText("Dette er market. Her kan man arbejde og tjene penge, eller man kan købe mad.");
        updateInfo();
        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Velkommen til markedet. Her kan du arbejde for at tjene penge eller du kan spise for at ikke at sulte.");
                dialog.show();
            }
        });

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= TIME_PER_CLICK && spiller.getKlassetrin() >= 4) {
                    spiller.work(TIME_PER_CLICK,MONEY_PER_CLICK);


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

                    updateInfo();
                } else if (spiller.getTid() < 2) {
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
                if (spiller.getPenge() >= COST_PER_FOOD_CLICK) {
                    spiller.eat(TIME_COST_EATING,COST_PER_FOOD_CLICK,FOOD_PER_CLICK);
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
                    ImageView im = new ImageView(Marked.this);
                    im.setImageResource(R.drawable.mad);
                    t.setView(im);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    if (back.isPressed()) {
                        SpillePlade.updateEntireBoard();
                        t.cancel();
                    }


                    //Toast.makeText(Marked.this, "Du har spist! +10 HP -5 Penge", Toast.LENGTH_LONG).show();
                    updateInfo();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Ingen penge!");
                    dialog.setMessage("Du har ikke nok penge til at spise");
                    dialog.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t.cancel();
                SpillePlade.updateTextpenge();
                SpillePlade.updateTextmad();
                SpillePlade.updateTextviden();
                v.startAnimation(AnimationUtils.loadAnimation(Marked.this, R.anim.image_click));
                finish();

            }
        });
    }



    public void eat() {
        ;
    }

    public void updateInfo() {
        SpillePlade.updateInfobox();
        updateText();
        playerinfo.setText("Tid: " + spiller.getTid());

    }

    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        t.cancel();
        finish();
    }

    public void updateText() {
        textpenge.setText(String.valueOf(spiller.getPenge()));
        textviden.setText(String.valueOf(spiller.getViden()));
        textmad.setText(String.valueOf(spiller.getHp()));
    }
}