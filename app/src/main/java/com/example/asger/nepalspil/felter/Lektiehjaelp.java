package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;
import com.example.asger.nepalspil.models.Spiller;

import org.w3c.dom.Text;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Lektiehjaelp extends AppCompatActivity {
    Button homeworkHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lektiehjaelp);

        final TextView lektiehjaelpInfo = (TextView) findViewById(R.id.lektiehjaelpTextField);
        final TextView playerInfo = (TextView) findViewById(R.id.lektiePlayerInfo);
        ImageView back = (ImageView) findViewById(R.id.lektieBack);
        homeworkHelp = (Button) findViewById(R.id.learn);

        lektiehjaelpInfo.setText("Her kan du få lektiehjælp, til at indhente det du ikke forstod fra undervisningen.");
        playerInfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid() + "\n Viden at hente: " + spiller.getGlemtViden());

        homeworkHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid() > 0 && spiller.getGlemtViden() > 0) {
                    learn();
                    playerInfo.setText(updateInfo());
                    Toast.makeText(Lektiehjaelp.this, "Du har opnået 1 viden.", Toast.LENGTH_SHORT).show();
                } else if (spiller.getGlemtViden() <= 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Lektiehjaelp.this);
                    dialog.setTitle("Ingen glemt viden.");
                    dialog.setMessage("Du har ikke behov for lektiehjælp, da du forstået al undervisning.");
                    dialog.show();
                } else if (spiller.getTid() <= 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Lektiehjaelp.this);
                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at få lektiehjælp. Kom igen i morgen.");
                    dialog.show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateTextpenge();
                SpillePlade.updateTextmad();
                SpillePlade.updateTextviden();
                v.startAnimation(AnimationUtils.loadAnimation(Lektiehjaelp.this, R.anim.image_click));
                finish();
            }
        });

    }

    private void learn(){
            spiller.setViden(spiller.getViden()+1);
            spiller.setTid(spiller.getTid()-1);
            spiller.setGlemtViden(spiller.getGlemtViden()-1);
    }



    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid() + "\n Viden at hente: " + spiller.getGlemtViden();

    }
}