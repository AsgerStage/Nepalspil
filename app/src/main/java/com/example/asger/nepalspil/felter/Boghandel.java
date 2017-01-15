package com.example.asger.nepalspil.felter;

import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
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
import com.example.asger.nepalspil.models.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Boghandel extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        SpillePlade.updateTextpenge();
        SpillePlade.updateTextmad();
        SpillePlade.updateTextviden();
        finish();
    }

    AlertDialog.Builder dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boghandel);
        dialog = new AlertDialog.Builder(Boghandel.this);
        final TextView playerInfo = (TextView) findViewById(R.id.bookstorePlayerInfo);
        final TextView bookstoreInfo = (TextView) findViewById(R.id.fieldinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        ImageView helpField = (ImageView) findViewById(R.id.boghandelHelp);
        Button work = (Button) findViewById(R.id.workButton);
        final Button buyBook = (Button) findViewById(R.id.buyBookButton);
        ImageView back = (ImageView) findViewById(R.id.backButton);

        bookstoreInfo.setText("Velkommen til boghandlen. Her kan du få et arbejde hvis du er nået langt nok i din uddannelse. \n Du kan også købe skole bøger her. Skolebøger giver hurtig viden");
        playerInfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2 && spiller.getKlassetrin() >= 6) {
                    work();

                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("cash.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    playerInfo.setText(updateInfo());
                } else if (spiller.getTid() < 2) {

                    dialog.setTitle("Intet tid!");
                    dialog.setMessage("Du har ikke nok tid til at arbejde");
                    dialog.show();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke uddannelse nok til at arbejde her");
                    dialog.show();
                }
            }
        });

        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage("I boghandlen kan man købe skolebøger som hjælper en med at studere, og så kan man arbejde her når man går i 6. klasse eller derover.");
                dialog.show();
            }
            });

        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getPenge() >= 100 && (spiller.getRunde() % 5 == 0)) {
                    spiller.setPenge(spiller.getPenge() - 100);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Bog købt");
                    dialog.setMessage("Du har købt en ny bog for 100 penge. +20 viden");
                    dialog.show();
                    spiller.setViden(spiller.getViden() + 20);
                    playerInfo.setText(updateInfo());

                } else if (spiller.getRunde() % 5 != 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Ikke på lager");
                    dialog.setMessage("Øv! Bogen er ikke på lager i dag. Kom tilbage en anden dag.");
                    dialog.show();

                } else if (spiller.getPenge() < 100) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Ikke nok penge!");
                    dialog.setMessage("Du har ikke penge nok til at købe en ny bog. Tjen penge ved at arbejde.");
                    dialog.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateTextpenge();
                SpillePlade.updateTextmad();
                SpillePlade.updateTextviden();
                v.startAnimation(AnimationUtils.loadAnimation(Boghandel.this, R.anim.image_click));
                finish();
            }
        });

    }

    private static void work() {
        spiller.setTid(spiller.getTid() - 2);
        spiller.setPenge(spiller.getPenge() + 20);
    }

    private void buyBook() {
        spiller.setBooks(spiller.getBooks() + 1);
        spiller.setPenge(spiller.getPenge() - 10);
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();

    }

}
