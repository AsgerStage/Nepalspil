package com.example.asger.nepalspil.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.asger.nepalspil.R;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;



public class Skole extends AppCompatActivity {

    //AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);

        TextView textView = (TextView) findViewById(R.id.textView3);
        Button bSpis = (Button) findViewById(R.id.spis);
        Button bStuder = (Button) findViewById(R.id.Studer);
        //Button bEksamen = (Button) findViewById(R.id.eksamen);
        textView.setText("Velkommen til Skolen, her kan du spise, studere og tage din eksamen når tiden er.");
        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spiller.getTid()>0) {
                    spis();
                }else{

                }
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spiller.getTid()>0){
                    studer();
                }else{

                }
            }
        });

       /* bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEksamen();
            }
        });*/


    }


    //private int vidensKrav = 10*spiller.getKlassetrin();

    public void studer(){
        spiller.setViden(spiller.getViden()+1);
        spiller.setTid(spiller.getTid()-1);
        //textView.setText("Du kunne ikke forstå undervisningen, så din viden kan opnås hos lektiehjælpen.");
    }

    /*public boolean harlaert(){
        if (Math.random()>0.1)
            return true;
        else return false;
    }*/

    public void spis(){
        spiller.setTid(spiller.getTid()-1);
        spiller.setHp(spiller.getHp()+1);
    }

    /*public void startEksamen(){
        if ((spiller.getViden() !=  vidensKrav ) || (spiller.getViden() > vidensKrav)) {
            dialog.setTitle("Desværre");
            dialog.setMessage("Du har desværre ikke nok viden til at kunne gå til eksamen.\n Få viden af at gå i skole, og tag eksamen næste år!");
            dialog.show();
        } else
            setText("Held og lykke!");
            setContentView(R.layout.eksamen);
    }*/


}
