package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.diverse.Topbar;
import com.example.asger.nepalspil.model.Spiller;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Butikken extends AppCompatActivity {

    AlertDialog.Builder dialog;
    private Topbar topbar;
    private Button køb;
    private int pris;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butik);

        topbar = new Topbar();
        topbar.init(this);

        dialog = new AlertDialog.Builder(Butikken.this);
        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);
        figur.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Spiller.instans.penge += 10000;
                Spiller.instans.tid += 10000;
                Spiller.instans.viden += 10000;
                return true;
            }
        });
        ImageView helpField = (ImageView) findViewById(R.id.vaerkstedHelp);
        køb = (Button) findViewById(R.id.knap_koeb);
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);
        topbar.opdaterGui(Spiller.instans);


        opdaterTekstOgPris();

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                dialog.setMessage(R.string.butik_hjælp);
                dialog.show();
            }
        });

        køb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                køb();
            }
        });

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Butikken.this, R.anim.image_click));
                finish();
            }
        });

    }

    private void køb() {
        if (Spiller.instans.penge >= pris) {
            Spiller.instans.penge = Spiller.instans.penge - pris;
            topbar.opdaterGui(Spiller.instans);

            if (Spiller.instans.læringBlyant<1) {
                Spiller.instans.læringBlyant = 30;
                new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Blyanter købt!")
                        .setContentText("Du har købt nye blyanter for 10kr.")
                        .show();
            }
            else if (Spiller.instans.læringKladdehæfte<1) {
                Spiller.instans.læringKladdehæfte = 50;
                new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Kladdehæfte købt!")
                        .setContentText("Du har købt et kladdehæfte for 20kr.")
                        .show();
            }
            else if (Spiller.instans.læringLommeregner<1) {
                Spiller.instans.læringLommeregner = 150;
                new SweetAlertDialog(Butikken.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Lommeregner købt!")
                        .setContentText("Du har købt en ny lommeregner for 200kr.")
                        .show();
            }
            opdaterTekstOgPris();
        } else {
            new SweetAlertDialog(Butikken.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Ikke nok penge!")
                    .setContentText("Du har ikke "+pris+" kr. Tjen penge ved at arbejde.")
                    .show();
        }
    }

    private void opdaterTekstOgPris() {
        if (Spiller.instans.læringBlyant<1) {
            køb.setText("Køb blyanter");
            pris = 10;
        }
        else if (Spiller.instans.læringKladdehæfte<1) {
            køb.setText("Køb kladdehæfte");
            pris = 20;
        }
        else if (Spiller.instans.læringLommeregner<1) {
            køb.setText("Køb lommeregner");
            pris = 200;
        }
        else køb.setVisibility(View.INVISIBLE);
    }

}
