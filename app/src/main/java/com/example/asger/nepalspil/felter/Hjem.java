package com.example.asger.nepalspil.felter;

import android.content.DialogInterface;
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

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Asger on 21-11-2016.
 */


public class Hjem extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }
    AlertDialog.Builder dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hjem);          //Har ikke lavet hjem layout endnu

        ImageView im = (ImageView) findViewById(R.id.hjemprofile);
        dialog = new AlertDialog.Builder(Hjem.this);
        TextView tv = (TextView) findViewById(R.id.hjemtext);
        Button bSov = (Button) findViewById(R.id.sleep);
        ImageView hjemBack = (ImageView) findViewById(R.id.hjemBack);
        ImageView hjemhelp = (ImageView) findViewById(R.id.hjemhelp);

        if (spiller.getNavn() == "Asha") {
            im.setImageResource(R.drawable.pige2);
        } else {
            im.setImageResource(R.drawable.dreng1);
        }

        tv.setText(("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid()));

        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Hjem.this, R.anim.image_click));
                finish();
            }
        });

        hjemhelp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Hjem.this, R.anim.image_click));
                dialog.setMessage("Hej jeg hedder "+spiller.getNavn()+ ". Jeg bor med min familie i en landsby i Nepal. \n Min mor og far har aldrig gået i skole, så de tjener ikke så mange penge, så det er svært for dem at hjælpe mig med at få en uddannelse.");
                dialog.show();
            }
        });

        bSov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Hjem.this);
                dialog.setTitle("Afslut dagen?");
                dialog.setMessage("Er du sikker på at du vil afslutte dagen?");
                dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        spiller.setTid(0);
                        updateInfo();
                        SpillePlade.updateTextpenge();
                        SpillePlade.updateTextmad();
                        SpillePlade.updateTextviden();
                        Hjem.this.finish();
                    }
                });

                dialog.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                dialog.show();

            }
        });
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();
    }
}
