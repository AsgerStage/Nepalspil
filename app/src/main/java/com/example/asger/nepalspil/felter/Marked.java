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
import com.example.asger.nepalspil.diverse.Topbar;
import com.example.asger.nepalspil.model.Spiller;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.asger.nepalspil.model.Spiller.instans;


public class Marked extends AppCompatActivity {
    Toast t;
    AlertDialog.Builder dialog;
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
    private MediaPlayer spisLyd;
    private MediaPlayer arbejdLyd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Spiller.instans==null) { finish(); return; } // genstart i frisk JVM - vis hovedmenu
        setContentView(R.layout.marked);
        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);

        dialog = new AlertDialog.Builder(Marked.this);
        t = new Toast(Marked.this);
        playerinfo = (TextView) findViewById(R.id.texttid);
        arbejdLyd = MediaPlayer.create(this, R.raw.cash);
        spisLyd = MediaPlayer.create(this, R.raw.bitesound);


        topbar = new Topbar();
        topbar.init(this);

        Button work = (Button) findViewById(R.id.knap_arbejd);
        final Button eat = (Button) findViewById(R.id.eatButton);
        final ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        final TextView money = (TextView) findViewById(R.id.flyvoptekst_arbejd);
        final TextView food = (TextView) findViewById(R.id.flyvoptekst_spis);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        animationfood = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);


        updateText();
        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage(R.string.marked_hjælp);
                dialog.show();
            }
        });

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Marked.this, R.anim.image_click));
                if (Spiller.instans.tid >= TIME_PER_CLICK && Spiller.instans.klassetrin >= 3) {
                    Spiller.instans.arbejd(TIME_PER_CLICK, MONEY_PER_CLICK);
                    money.setText("+" + MONEY_PER_CLICK + " kr");
                    money.startAnimation(animation);


                    if (arbejdLyd.isPlaying()) arbejdLyd.seekTo(0);
                    else arbejdLyd.start();

                    updateText();
                } else if (Spiller.instans.tid < 2) {
                    new SweetAlertDialog(Marked.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Du har ikke tid til at arbejde.")
                            .show();

                } else {
                    new SweetAlertDialog(Marked.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ikke nok viden")
                            .setContentText("Du har ikke uddannelse nok til at arbejde her.")
                            .show();
                }
            }
        });

        eat.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Marked.this, R.anim.image_click));
                if (Spiller.instans.penge >= COST_PER_FOOD_CLICK) {
                    Spiller.instans.spis(TIME_COST_EATING, COST_PER_FOOD_CLICK, FOOD_PER_CLICK);
                    food.setText("+" + FOOD_PER_CLICK + " mad");
                    food.startAnimation(animationfood);
                    if (spisLyd.isPlaying()) spisLyd.seekTo(0);
                    else spisLyd.start();

                    updateText();

                } else {
                    new SweetAlertDialog(Marked.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ingen penge")
                            .setContentText("Du har ikke råd til at spise her.")
                            .show();
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
        t.cancel();
        finish();
    }

    public void updateText() {
        topbar.opdaterGui(instans);
    }
}