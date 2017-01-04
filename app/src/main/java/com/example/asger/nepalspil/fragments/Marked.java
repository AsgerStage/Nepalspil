package com.example.asger.nepalspil.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;

import com.example.asger.nepalspil.R;

/**
 * Created by Asger on 21-11-2016.
 */

public class Marked extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marked);

        Button work = (Button) findViewById(R.id.workButton);
        Button eat = (Button) findViewById(R.id.eatButton);
        Button back = (Button) findViewById(R.id.backButton);
        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();
                }
                else{

                }
            }
        });

        eat.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                if(spiller.getPenge()>=5){
                    eat();
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

}
