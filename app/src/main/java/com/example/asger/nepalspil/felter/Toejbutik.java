package com.example.asger.nepalspil.felter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Toejbutik extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    AlertDialog.Builder dialog;
    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toejbutik);

        dialog = new AlertDialog.Builder(Toejbutik.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.tbInfo);
        final TextView playerinfo = (TextView) findViewById(R.id.tbStats);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.tbHelp);
        Button buy = (Button) findViewById(R.id.tbBuy);
        ImageView back = (ImageView) findViewById(R.id.tbBack);

        fieldinfo.setText("Velkommen til Tøjbutikken! Her kan man købe nyt skoletøj");
        playerinfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("Velkommen til tøjbutikken. Her kan du købe tøj og diverse skoleting til at gøre dit live bedre.");
                dialog.show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getPenge() >= 20) {
                    buyCloth();
                    playerinfo.setText(updateInfo());
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Toejbutik.this);
                    dialog.setTitle("Bog købt");
                    dialog.setMessage("Du har købt en ny bog for 20 penge.");
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Toejbutik.this);
                    dialog.setTitle("Ikke nok penge!");
                    dialog.setMessage("Du har ikke penge nok til at købe en ny bog. Tjen penge ved at arbejde.");
                    dialog.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Toejbutik.this, R.anim.image_click));
                finish();
            }
        });
    }

    private void buyCloth() {
        spiller.setBooks(spiller.getBooks() + 1);
        spiller.setPenge(spiller.getPenge() - 10);
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();
    }
}
