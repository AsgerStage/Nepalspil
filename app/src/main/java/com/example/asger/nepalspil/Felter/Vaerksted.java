package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asger.nepalspil.R;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Vaerksted extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaerksted);

        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        final TextView playerinfo = (TextView) findViewById(R.id.playerinfo);

        Button work = (Button) findViewById(R.id.workButton);
        Button buy = (Button) findViewById(R.id.buyBikeButton);
        Button back = (Button) findViewById(R.id.backButton);

        fieldinfo.setText("Velkommen til værkstedet! Her kan man arbejde eller købe en cykel.");
        playerinfo.setText("Navn: "+spiller.getNavn()+"\n Mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();
                    playerinfo.setText(updateInfo());
                }
                else{

                }
            }
        });

        buy.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                if(spiller.getPenge()>=50){
                    buy();
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

    public void buy(){
        spiller.setPenge(spiller.getPenge()-500);
        spiller.setBike(true);
    }

    public String updateInfo(){
        return "Navn: "+spiller.getNavn()+"\n Mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid();
    }

}

