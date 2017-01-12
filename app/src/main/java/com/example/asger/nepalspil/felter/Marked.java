package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import java.io.IOException;

public class Marked extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marked);

        final Toast t = new Toast(Marked.this);
        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        final TextView playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        Button work = (Button) findViewById(R.id.workButton);
        final Button eat = (Button) findViewById(R.id.eatButton);
        final Button back = (Button) findViewById(R.id.backButton);

        fieldinfo.setText("Dette er market. Her kan man arbejde og tjene penge, eller man kan købe mad.");
        playerinfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2 && spiller.getKlassetrin() >= 4) {
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

                    playerinfo.setText(updateInfo());
                } else if (spiller.getTid() < 2) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Intet tid!");
                    dialog.setMessage("Du har ikke nok tid til at arbejde");
                    dialog.show();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke uddannelse nok til at arbejde her");
                    dialog.show();
                }
            }
        });

        eat.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getPenge() >= 5) {
                    eat();
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
                    ImageView im = new ImageView(Marked.this);
                    im.setImageResource(R.drawable.mad);
                    t.setView(im);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    if(back.isPressed())
                        t.cancel();

                    //Toast.makeText(Marked.this, "Du har spist! +10 HP -5 Penge", Toast.LENGTH_LONG).show();
                    playerinfo.setText(updateInfo());

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Marked.this);
                    dialog.setTitle("Ingen penge!");
                    dialog.setMessage("Du har ikke nok penge til at spise");
                    dialog.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t.cancel();
                finish();

            }
        });
    }

    public void work() {
        spiller.setTid(spiller.getTid() - 2);
        spiller.setPenge(spiller.getPenge() + 10);
    }

    public void eat() {
        spiller.setPenge(spiller.getPenge() - 5);
        spiller.setHp(spiller.getHp() + 10);
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();

    }

}
