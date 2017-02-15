package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.diverse.Topbar;
import com.example.asger.nepalspil.model.Spiller;

import static com.example.asger.nepalspil.model.Spiller.instans;


public class Hjem extends AppCompatActivity {


    AlertDialog.Builder dialog;
    private Topbar topbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hjem);

        topbar = new Topbar();
        topbar.init(this);

        ImageView figur = (ImageView) findViewById(R.id.figur);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);
        dialog = new AlertDialog.Builder(Hjem.this);
        topbar.opdaterGui(Spiller.instans);

        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView hjemhelp = (ImageView) findViewById(R.id.vaerkstedHelp);
        hjemhelp.setVisibility(View.INVISIBLE);
        TextView taleboble_tekst = (TextView) findViewById(R.id.taleboble_tekst);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);

        String beskrivelse_hjemme = Spiller.instans.figurdata.json.optString("beskrivelse_hjemme" , "(FEJL: 'beskrivelse_hjemme' mangler for denne figur)\nHej jeg hedder " + instans.navn + ". Jeg bor med min familie i en landsby i Nepal. \nMin mor og far har aldrig gået i skole, så de tjener ikke så mange penge, så det er svært for dem at hjælpe mig med at få en uddannelse.");
        taleboble_tekst.setText(beskrivelse_hjemme);
        figur.setImageResource(Spiller.instans.figurdata.drawable_figur_halv_id);


        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hjem.this, R.anim.image_click));
                finish();
            }
        });


    }


}
