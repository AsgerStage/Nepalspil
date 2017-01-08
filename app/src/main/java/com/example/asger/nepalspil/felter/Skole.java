package com.example.asger.nepalspil.felter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.MainActivity;
import com.example.asger.nepalspil.models.SpillePlade;
import com.github.jinatonic.confetti.CommonConfetti;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;



public class Skole extends AppCompatActivity {

    //AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    TextView schoolText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);

        final TextView schoolText = (TextView) findViewById(R.id.schoolText);
        Button bSpis = (Button) findViewById(R.id.spis);
        Button bStuder = (Button) findViewById(R.id.Studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);

        schoolText.setText("Velkommen til Skolen, her kan du spise, studere og tage din eksamen når tiden er.");

        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid() > 0) {
                    spis();
                    schoolText.setText("Mmm! Du har spist skolemad.");
                } else {

                }
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid()>0) {
                    if (spiller.getTid() > 0 && studer()) {
                        schoolText.setText("Du har modtaget 1 viden!");
                        System.out.println(spiller.getViden());
                    } else {
                        schoolText.setText("Du kunne ikke forstå undervisningen, så din viden kan opnås hos lektiehjælpen.");
                        System.out.println(spiller.getViden());
                    }
                }
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!kanStartEksamen()) {
                    schoolText.setText("Du har desværre ikke nok viden til at kunne gå til eksamen." +
                            "\n Få viden af at gå i skole, og tag eksamen næste år!");
                } else {
                    schoolText.setText("Held og Lykke!");
                    setContentView(R.layout.eksamen);

                }

            }
        });


    }


    private int vidensKrav = 10 * spiller.getKlassetrin();

    public boolean studer() {
        if (hasLearned()) {
            spiller.setViden(spiller.getViden() + 1);
            spiller.setTid(spiller.getTid() - 1);
            SpillePlade.updateInfobox();
            return true;
        } else {
            spiller.setTid(spiller.getTid() - 1);
           //Lektiehjaelp.
            return false;
            }
       }

    public boolean hasLearned(){
        if (Math.random()>0.5)
            return true;
        else return false;
    }

    public void spis(){
        if (spiller.getTid()>0) {
            spiller.setTid(spiller.getTid() - 1);
            spiller.setHp(spiller.getHp() + 1);
            SpillePlade.updateInfobox();
        }else{
            schoolText.setText("");
        }

    }

    public boolean kanStartEksamen(){
        if ((spiller.getViden() !=  vidensKrav ) || (spiller.getViden() > vidensKrav)) {
            return false;
        } else
           return true;
    }


}
