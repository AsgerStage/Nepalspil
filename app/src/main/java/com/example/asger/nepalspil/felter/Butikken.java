package com.example.asger.nepalspil.felter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import static com.example.asger.nepalspil.models.Spiller.instans;

public class Butikken extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    AlertDialog.Builder dialog;
    private Topbar topbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butik);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Butikken.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.taleboble_tekst);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        final Button buy = (Button) findViewById(R.id.knap_koeb);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);
        updateText();

        fieldinfo.setText("I butikken kan du købe skoleting, som gør det lettere at få viden.");


        switch (instans.getLearningAmp()) {
            case 0:
                buy.setText("Køb Kladehæfte");
                break;
            case 1:
                buy.setText("Køb blyanter");
                break;
            case 2:
                buy.setText("Køb Lommeregner");
                break;
            case 3:
                buy.setVisibility(View.INVISIBLE);
                break;
        }

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                dialog.setMessage("I butikken kan du købe skoleting. Kladdehæfter koster 150 kr og øger din chance for at lære noget i skolen \n\n blyanter koster 300 kr \n\n lommeregner koster 700 kr og fjerner risiko for overhovedet ikke at lære noget i skolen.");
                dialog.show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (instans.getLearningAmp() == 2) {
                    if (instans.getPenge() >= 700) {
                        buy();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Lommeregner købt");
                        dialog.setMessage("Du har købt en ny lommeregner for 700kr.");
                        dialog.show();
                        buy.setVisibility(View.INVISIBLE);
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Ikke nok penge!");
                        dialog.setMessage("Du har ikke penge nok. Tjen penge ved at arbejde.");
                        dialog.show();
                    }
                }
                if (instans.getLearningAmp() == 1) {
                    if (instans.getPenge() >= 300) {
                        buy();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Blyanter købt");
                        dialog.setMessage("Du har købt nye blyanter for 300kr.");
                        dialog.show();
                        buy.setText("Køb Lommeregner");
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Ikke nok penge!");
                        dialog.setMessage("Du har ikke penge nok. Tjen penge ved at arbejde.");
                        dialog.show();
                    }
                }
                if (instans.getLearningAmp() == 0) {
                    if (instans.getPenge() >= 150) {
                        buy();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Kladehæfte købt");
                        dialog.setMessage("Du har købt et kladehæfte for 150kr.");
                        dialog.show();
                        buy.setText("Køb blyanter");
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Butikken.this);
                        dialog.setTitle("Ikke nok penge!");
                        dialog.setMessage("Du har ikke penge nok. Tjen penge ved at arbejde.");
                        dialog.show();
                    }
                }
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                finish();
            }
        });

    }

    private void buy() {
        switch (instans.getLearningAmp()) {
            case 0:
                instans.setLearningAmp(1);
                instans.setPenge(instans.getPenge() - 150);
                break;
            case 1:
                instans.setLearningAmp(2);
                instans.setPenge(instans.getPenge() - 300);
                break;
            case 2:
                instans.setLearningAmp(3);
                instans.setPenge(instans.getPenge() - 700);
                break;
        }
    }


    public void updateText() {
        topbar.opdaterGui(instans);
        SpillePlade.updateEntireBoard();
    }
}
