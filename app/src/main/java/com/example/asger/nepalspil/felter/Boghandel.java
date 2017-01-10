package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import java.io.IOException;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Boghandel extends AppCompatActivity {
    Button work, buyBook;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boghandel);

        final TextView playerInfo = (TextView) findViewById(R.id.bookstorePlayerInfo);
        final TextView bookstoreInfo = (TextView) findViewById(R.id.fieldinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        Button work = (Button) findViewById(R.id.workButton);
        final Button buyBook = (Button) findViewById(R.id.buyBookButton);
        Button back = (Button) findViewById(R.id.backButton);

        bookstoreInfo.setText("Velkommen til boghandlen. Her kan du få et arbejde hvis du er nået langt nok i din uddannelse. \n Du kan også købe skole bøger her.");
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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
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

        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getPenge() >= 20) {
                    buyBook();
                    playerInfo.setText(updateInfo());
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Bog købt");
                    dialog.setMessage("Du har købt en ny bog for 20 penge.");
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Boghandel.this);
                    dialog.setTitle("Ikke nok penge!");
                    dialog.setMessage("Du har ikke penge nok til at købe en ny bog. Tjen penge ved at arbejde.");
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

    private static void work() {
        spiller.setTid(spiller.getTid() - 2);
        spiller.setPenge(spiller.getPenge() + 10);
    }

    private void buyBook() {
        spiller.setBooks(spiller.getBooks()+1);
        spiller.setPenge(spiller.getPenge()-10);
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();

    }

}
