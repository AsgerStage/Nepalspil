package com.example.asger.nepalspil.felter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Asger on 21-11-2016.
 */

public class Hjem extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        SpillePlade.updateTextpenge();
        SpillePlade.updateTextmad();
        SpillePlade.updateTextviden();
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hjem);          //Har ikke lavet hjem layout endnu

        ImageView im = (ImageView) findViewById(R.id.hjemprofile);
        TextView tv = (TextView) findViewById(R.id.hjemtext);
        Button bSov = (Button) findViewById(R.id.sleep);
        Button bBack = (Button) findViewById(R.id.hjemback);

        if (spiller.getNavn() == "Asha") {
            im.setImageResource(R.drawable.pige2);
        } else {
            im.setImageResource(R.drawable.dreng1);
        }

        tv.setText(("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid()));

        bBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
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
