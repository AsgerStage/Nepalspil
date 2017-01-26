package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.SpillePlade;

import static com.example.asger.nepalspil.models.Spiller.instans;


public class Hjem extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        SpillePlade.updateEntireBoard();
        finish();
    }

    AlertDialog.Builder dialog;
    private Topbar topbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hjem);

        topbar = new Topbar();
        topbar.init(this);

        ImageView im = (ImageView) findViewById(R.id.figur);
        dialog = new AlertDialog.Builder(Hjem.this);
        updateText();
        ImageView hjemBack = (ImageView) findViewById(R.id.ikon_tilbage);
        ImageView hjemhelp = (ImageView) findViewById(R.id.vaerkstedHelp);
        hjemhelp.setVisibility(View.INVISIBLE);
        TextView spillerintro = (TextView) findViewById(R.id.taleboble_tekst);
        ImageView menu = (ImageView) findViewById(R.id.menuknap);
        menu.setVisibility(View.INVISIBLE);
        spillerintro.setText("Hej jeg hedder " + instans.getNavn() + ". Jeg bor med min familie i en landsby i Nepal. \nMin mor og far har aldrig gået i skole, så de tjener ikke så mange penge, så det er svært for dem at hjælpe mig med at få en uddannelse.");

        if (instans.getSex() == false) {
            im.setImageResource(R.drawable.figur_asha_halv);
        } else {
            im.setImageResource(R.drawable.figur_krishna_halv);
        }


        hjemBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Hjem.this, R.anim.image_click));
                finish();
            }
        });


    }


    public void updateText() {
        topbar.opdaterGui(instans);
        SpillePlade.updateEntireBoard();

    }
}
