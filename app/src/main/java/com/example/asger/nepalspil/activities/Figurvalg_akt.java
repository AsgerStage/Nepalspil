package com.example.asger.nepalspil.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.Figurdata;
import com.example.asger.nepalspil.models.Grunddata;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.grantland.widget.AutofitTextView;

/**
 * Created by Asger on 25-01-2017.
 */

public class Figurvalg_akt extends AppCompatActivity {
    ImageView AshaFigur;
    ImageView KrishnaFigur;
    ImageView LaxmiFigur;
    ImageView KamalFigur;

    AutofitTextView  KrishnaHighscore;
    AutofitTextView  LaxmiHighscore;
    AutofitTextView  AshaHighscore;
    AutofitTextView  KamalHighscore;


    SweetAlertDialog pDialog;
    private TextView tekst;
    private Button spilKnap;
    private Figurdata valgtFigur;
    private View valgtFigurview;
    private LinearLayout infoboks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.figurvalg);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        AshaFigur = (ImageView) findViewById(R.id.AshaFigur);
        KrishnaFigur = (ImageView) findViewById(R.id.KrishnaFigur);
        LaxmiFigur = (ImageView) findViewById(R.id.LaxmiFigur);
        KamalFigur = (ImageView) findViewById(R.id.KamalFigur);

        KrishnaHighscore = (AutofitTextView) findViewById(R.id.KrishnaHighscore);
        LaxmiHighscore = (AutofitTextView) findViewById(R.id.LaxmiHighscore);
        AshaHighscore = (AutofitTextView) findViewById(R.id.AshaHighscore);
        KamalHighscore = (AutofitTextView) findViewById(R.id.KamalHighscore);

        if (prefs.getInt("Kamal", -1) != -1) {
            KamalHighscore.setText("highscore: " + prefs.getInt("Kamal", -1)+" uger");
        }
        if (prefs.getInt("Laxmi", -1) != -1) {
            LaxmiHighscore.setText("highscore: " + prefs.getInt("Laxmi", -1)+" uger");
        }
        if (prefs.getInt("Krishna", -1) != -1) {
            KrishnaHighscore.setText("highscore: " + prefs.getInt("Krishna", -1)+" uger");
        }
        if (prefs.getInt("Asha", -1) != -1) {
            AshaHighscore.setText("highscore: " + prefs.getInt("Asha", -1)+" uger");
        }
        AshaFigur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visTaleboble(Grunddata.Asha, v);
            }
        });
        KrishnaFigur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visTaleboble(Grunddata.Krishna, v);
            }
        });
        LaxmiFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                visTaleboble(Grunddata.Laxmi, v);
            }
        });

        KamalFigur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visTaleboble(Grunddata.Kamal, v);
            }
        });

        infoboks = (LinearLayout) findViewById(R.id.infoboks);
        tekst = (TextView) findViewById(R.id.tekst);
        spilKnap = (Button) findViewById(R.id.spil);
        infoboks.setVisibility(View.INVISIBLE);


        spilKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", valgtFigur.navn);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void visTaleboble(Figurdata kamal, View figurview) {
        infoboks.setVisibility(View.VISIBLE);
        infoboks.setTranslationY(infoboks.getHeight());
        infoboks.animate().translationY(0);
        spilKnap.setText("Spil som\n"+kamal.navn);
        tekst.setText(kamal.beskrivelse);
        /*
        spilKnap.setVisibility(View.VISIBLE);
        tekst.animate().alpha(1);
        spilKnap.animate().alpha(1);
        */
        if (valgtFigurview != null) {
            valgtFigurview.animate().scaleX(1).scaleY(1).setDuration(300);
        }
        valgtFigur = kamal;
        valgtFigurview = figurview;
        figurview.setRotationY(0);
//        figurview.animate().rotationY(720).setInterpolator(new DecelerateInterpolator()).scaleX(1.1f).scaleY(1.1f).setDuration(1000);
        // .translationY(-figurview.getHeight()*0.3f)
        figurview.animate().setInterpolator(new DecelerateInterpolator()).scaleX(1.2f).scaleY(1.2f).setDuration(300);
    }
}

