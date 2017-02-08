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
    private TextView taleboble_tekst;
    private Button spilKnap;
    private Figurdata valgtFigur;
    private View valgtFigurview;

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

        taleboble_tekst = (TextView) findViewById(R.id.taleboble_tekst);
        spilKnap = (Button) findViewById(R.id.spil);
        spilKnap.setVisibility(View.GONE);
        spilKnap.setAlpha(0);
        taleboble_tekst.setAlpha(0);


        if (prefs.getInt("Kamal", -1) != -1) {
            KamalHighscore.setText("Kamal highscore: " + prefs.getInt("Kamal", -1)+" uger");

        }
        if (prefs.getInt("Laxmi", -1) != -1) {
            LaxmiHighscore.setText("Laxmi highscore: " + prefs.getInt("Laxmi", -1)+" uger");
        }
        if (prefs.getInt("Krishna", -1) != -1) {
            KrishnaHighscore.setText("Krishna highscore: " + prefs.getInt("Krishna", -1)+" uger");

        }
        if (prefs.getInt("Asha", -1) != -1) {
            AshaHighscore.setText("Asha highscore: " + prefs.getInt("Asha", -1)+" uger");
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

        spilKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", valgtFigur.navn);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
/*
        AshaFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this);
                pDialog
                        .setTitleText("Vil du spille som Asha?")
                        .setContentText(Grunddata.Asha.beskrivelse)
                        .setConfirmText("Ja, vi klarer det!")
                        .showCancelButton(true)
                        .setCancelText("Nej")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "Asha");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });
        KrishnaFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this);
                pDialog
                        .setTitleText("Vil du spille som Krishna?")
                        .setContentText(Grunddata.Krishna.beskrivelse)
                        .setConfirmText("Ja, vi klarer det!")
                        .showCancelButton(true)
                        .setCancelText("Nej")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "Krishna");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });
        LaxmiFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this);
                pDialog
                        .setTitleText("Vil du spille som Laxmi?")
                        .setContentText(Grunddata.Laxmi.beskrivelse)
                        .setConfirmText("Ja, vi klarer det!")
                        .showCancelButton(true)
                        .setCancelText("Nej")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "Laxmi");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });

        KamalFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this);
                pDialog
                        .setTitleText("Vil du spille som Kamal?")
                        .setContentText(Grunddata.Kamal.beskrivelse)
                        .setConfirmText("Ja, vi klarer det!")
                        .showCancelButton(true)
                        .setCancelText("Nej")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "Kamal");
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });
*/

    }

    private void visTaleboble(Figurdata kamal, View figurview) {
        spilKnap.setVisibility(View.VISIBLE);
        spilKnap.setText("Spil som\n"+kamal.navn);
        taleboble_tekst.setText(kamal.beskrivelse);
        taleboble_tekst.animate().alpha(1);
        spilKnap.animate().alpha(1);
        if (valgtFigurview != null) {
            valgtFigurview.animate().scaleX(1).scaleY(1);
        }
        valgtFigur = kamal;
        valgtFigurview = figurview;
        figurview.setRotationY(0);
//        figurview.animate().rotationY(720).setInterpolator(new DecelerateInterpolator()).scaleX(1.1f).scaleY(1.1f).setDuration(1000);
        // .translationY(-figurview.getHeight()*0.3f)
        figurview.animate().setInterpolator(new DecelerateInterpolator()).scaleX(1.2f).scaleY(1.2f).setDuration(2000);
    }
}

