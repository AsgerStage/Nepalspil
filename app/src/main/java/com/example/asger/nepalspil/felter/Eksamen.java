package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;
import com.example.asger.nepalspil.R;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Senad on 1/12/2017.
 */

public class Eksamen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eksamen);

        TextView question = (TextView) findViewById(R.id.examQuestion);
        Button answer1 = (Button) findViewById(R.id.answer1Button);
        Button answer2 = (Button) findViewById(R.id.answer2Button);
        Button answer3 = (Button) findViewById(R.id.answer3Button);

        spiller.getViden();
        switch(spiller.getKlassetrin()-1){
            case 0:
                question.setText("Hvad spiser nepalesiske børn til morgenmad?");
                answer1.setText("Ris og grøntsager");
                answer2.setText("Havregryn med mælk");
                answer3.setText("Grød");
                answer1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        wrong();
                    }
                });
                answer2.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        spiller.setKlassetrin(spiller.getKlassetrin()+1);
                        finish();
                    }
                });
                answer3.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        wrong();
                    }
                });
        }
    }

    public void wrong(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Eksamen.this);
        dialog.setTitle("Forkert!");
        dialog.setMessage("Du svaret desværre forkert på eksamen, og derfor bestod du ikke");
        dialog.show();
    }
}