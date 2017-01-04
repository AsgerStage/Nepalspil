package com.example.asger.nepalspil.fragments;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.example.asger.nepalspil.models.Spiller;
import com.example.asger.nepalspil.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import static com.example.asger.nepalspil.R.id.textView;

/**
 * Created by Asger on 21-11-2016.
 */

public class Skole extends AppCompatActivity {

    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);
        setText("Velkommen til Skolen, her kan du spise, studere og tage din eksamen når tiden er.");

        Button bSpis = (Button) findViewById(R.id.spis);
        Button bStuder = (Button) findViewById(R.id.Studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);

        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spis(s);
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studer(s);
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEksamen(s);
            }
        });


    }
    TextView textView = (TextView) findViewById(R.id.textView3);

    Spiller s;
    private int vidensKrav = 10*s.getKlassetrin();

    public void studer(Spiller s){
        if (this.harlaert()) {
            s.setViden(s.getViden() + 1);
            s.setTid(s.getTid() - 1);
        }
        else

            setText("Du kunne ikke forstå undervisningen, så din viden kan opnås hos lektiehjælpen.");

    }

    public boolean harlaert(){
        if (Math.random()>0.1)
            return true;
        else return false;
    }

    public void spis(Spiller s){
        s.setTid(s.getTid()-1);
        s.setHp(s.getHp()+1);
    }

    public void startEksamen(Spiller s){
        if ((s.getViden() !=  vidensKrav ) || (s.getViden() > vidensKrav)) {
            dialog.setTitle("Desværre");
            dialog.setMessage("Du har desværre ikke nok viden til at kunne gå til eksamen.\n Få viden af at gå i skole, og tag eksamen næste år!");
            dialog.show();
        } else
            setText("Held og lykke!");
            setContentView(R.layout.eksamen);
    }

    public void setText(String yourText){
        textView.setText(yourText);
    }
}
