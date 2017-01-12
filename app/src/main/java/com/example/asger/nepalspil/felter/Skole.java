package com.example.asger.nepalspil.felter;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.MainActivity;
import com.example.asger.nepalspil.models.SpillePlade;
import com.github.jinatonic.confetti.CommonConfetti;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.asger.nepalspil.R.layout.lektiehjaelp;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;


public class Skole extends AppCompatActivity {

    TextView schoolText;
    TextView playerInfo;


    @Override
    public void onResume() {
        super.onResume();
        updateInfo();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        final TextView schoolText = (TextView) findViewById(R.id.schoolText);
        final TextView playerInfo = (TextView) findViewById(R.id.schoolPlayerInfo);
        Button bSpis = (Button) findViewById(R.id.spis);
        Button bStuder = (Button) findViewById(R.id.Studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);
        Button back = (Button) findViewById(R.id.skoleBack);

        schoolText.setText("Velkommen til Skolen, her kan du spise, studere og tage din eksamen når tiden er.");
        playerInfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());

        onResume();
        {

        }


        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid() > 0) {
                    spis();
                    schoolText.setText("Mmm! Du har spist skolemad.");
                    playerInfo.setText(updateInfo());
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
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at spise");
                    dialog.show();
                }
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid() > 0) {
                    if (spiller.getTid() > 0 && studer()) {
                        schoolText.setText("Du har modtaget 1 viden!");
                        System.out.println(spiller.getViden());
                    } else {
                        Toast.makeText(Skole.this, "Du forstod ikke alt undervisningen, tag i lektiehjælpen for at forstå det", Toast.LENGTH_SHORT).show();
                        /*AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                       dialog.setTitle("Lektiehjælp!");
                        dialog.setMessage("Du kunne ikke forstå undervisningen, så din viden kan opnås hos lektiehjælpen.");
                        dialog.show();*/
                    }
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at studere.");
                    dialog.show();
                }
                playerInfo.setText(updateInfo());
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kanStartEksamen()) {
                    schoolText.setText("Held og Lykke!");
                    finish();
                    Intent myIntent = new Intent(Skole.this, Eksamen.class);
                    startActivity(myIntent);

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke nok viden til at starte eksamenen! Du skal have mindst " + vidensKrav() + " for at starte eksamenen.");
                    dialog.show();

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();

            }
        });


    }

    public static int vidensKrav = 10 * spiller.getKlassetrin();

    public int getvidensKrav() {
        return vidensKrav;
    }


    public boolean studer() {
        if (hasLearned()) {
            spiller.setViden(spiller.getViden() + 1);
            spiller.setTid(spiller.getTid() - 1);
            SpillePlade.updateInfobox();
            return true;
        } else {
            spiller.setTid(spiller.getTid() - 1);
            spiller.setGlemtViden(spiller.getGlemtViden() + 1);
            return false;
        }
    }

    public boolean hasLearned() {
        if (Math.random() > 0.1)
            return true;
        else return false;
    }

    public void spis() {
        if (spiller.getTid() > 0) {
            spiller.setTid(spiller.getTid() - 1);
            spiller.setHp(spiller.getHp() + 1);
            SpillePlade.updateInfobox();
        } else {
            schoolText.setText("");
        }

    }

    public boolean kanStartEksamen() {
        if ((spiller.getViden() >= vidensKrav())) {
            return true;
        } else
            return false;
    }

    public static String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();

    }


    public static int vidensKrav() {
        return 10 * spiller.getKlassetrin();
    }

}
