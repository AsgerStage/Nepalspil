package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;
import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Senad on 1/12/2017.
 */

public class Eksamen extends AppCompatActivity {
    TextView question;
    Button answer1;
    Button answer2;
    Button answer3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eksamen);

        question = (TextView) findViewById(R.id.examQuestion);
        answer1 = (Button) findViewById(R.id.answer1Button);
        answer2 = (Button) findViewById(R.id.answer2Button);
        answer3 = (Button) findViewById(R.id.answer3Button);

        spiller.getViden();
        switch(spiller.getKlassetrin()-1){
            case 0:
                setQuestion("Hvad spiser nepalesiske børn til morgenmad?","Ris og grøntsager","Havregryn med mælk","Grød");
                setSecondCorrect();
       /*         question.setText("Hvad spiser nepalesiske børn til morgenmad?");
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
                });*/
        }
    }

    public void wrong(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Eksamen.this);
        dialog.setTitle("Forkert!");
        dialog.setMessage("Du svaret desværre forkert på eksamen, og derfor bestod du ikke");
        dialog.show();
    }

    public void setQuestion(String titel,String answerbox1, String answerbox2,String answerbox3)
    {
        question.setText(titel);
        answer1.setText(answerbox1);
        answer2.setText(answerbox2);
        answer3.setText(answerbox3);
    }
    public void setFirstCorrect(){
        answer1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                spiller.setKlassetrin(spiller.getKlassetrin()+1);
                SpillePlade.updateInfobox();
                finish();
            }
        });
        answer2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                wrong();
            }
        });
        answer3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                wrong();
            }
        });
    }


    public void setSecondCorrect(){
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


    public void setThirdCorrect(){
        answer1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                wrong();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                wrong();
            }
        });
        answer3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                spiller.setKlassetrin(spiller.getKlassetrin()+1);
                finish();
            }
        });
    }

}