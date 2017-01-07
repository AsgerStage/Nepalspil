package com.example.asger.nepalspil.felter;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
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


/**
 * Created by Asger on 21-11-2016.
 */

public class Marked extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marked);

        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        final TextView playerinfo = (TextView) findViewById(R.id.playerinfo);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);

        Button work = (Button) findViewById(R.id.workButton);
        final Button eat = (Button) findViewById(R.id.eatButton);
        Button back = (Button) findViewById(R.id.backButton);

        fieldinfo.setText("Dette er market. Her kan man arbejde og tjene penge, eller man kan købe hej.");
        playerinfo.setText("Navn: "+spiller.getNavn()+"\n hej: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();

                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("cash.mp3");
                        mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        mp.prepare();
                        mp.start();
                    }catch (IllegalStateException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    playerinfo.setText(updateInfo());
                }
                else{

                }
            }
        });

        eat.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                if(spiller.getPenge()>=5){
                    eat();
                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("eat.mp3");
                        mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        mp.prepare();
                        mp.start();
                    }catch (IllegalStateException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast t = new Toast(Marked.this);
                    ImageView im = new ImageView(Marked.this);
                    im.setImageResource(R.drawable.hej);
                    t.setView(im);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    //Toast.makeText(Marked.this, "Du har spist! +10 HP -5 Penge", Toast.LENGTH_LONG).show();
                    playerinfo.setText(updateInfo());

                }
                else{

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                finish();

            }
        });
    }

    public void work(){
        spiller.setTid(spiller.getTid()-2);
        spiller.setPenge(spiller.getPenge()+10);
    }

    public void eat(){
        spiller.setPenge(spiller.getPenge()-5);
        spiller.setHp(spiller.getHp()+10);
    }

    public String updateInfo(){
        SpillePlade.updateInfobox();
        return "Navn: "+spiller.getNavn()+"\n hej: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid();

    }

}
