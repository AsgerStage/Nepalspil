package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;


/**
 * Created by Asger on 21-11-2016.
 */

public class Farm extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm);

        final TextView fieldinfo = (TextView) findViewById(R.id.fieldinfo);
        final TextView playerinfo = (TextView) findViewById(R.id.playerinfo);

        Button work = (Button) findViewById(R.id.workButton);
        Button back = (Button) findViewById(R.id.backButton);

        fieldinfo.setText("Dette er farmen. Her kan man kun arbejde.");
        playerinfo.setText("Navn: "+spiller.getNavn()+"\n mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid());

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

        back.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                finish();
            }
        });
    }

    public void work(){
        spiller.setTid(spiller.getTid()-2);
        spiller.setPenge(spiller.getPenge()+5);
    }

    public String updateInfo(){
        SpillePlade.updateInfobox();
        return "Navn: "+spiller.getNavn()+"\n mad: "+spiller.getHp()+"\n Penge: "+spiller.getPenge()+"\n Viden: "+spiller.getViden()+"\n Klassetrin: "+spiller.getKlassetrin()+"\n Tid: "+spiller.getTid();
    }

}