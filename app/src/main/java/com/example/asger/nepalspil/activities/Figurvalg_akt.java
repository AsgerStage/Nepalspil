package com.example.asger.nepalspil.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.Figurdata;
import com.example.asger.nepalspil.models.Grunddata;
import com.example.asger.nepalspil.models.Spiller;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Asger on 25-01-2017.
 */

public class Figurvalg_akt extends AppCompatActivity {
    ImageView AshaFigur;
    ImageView KrishnaFigur;
    ImageView LaxmiFigur;
    ImageView KamalFigur;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.figurvalg);

        AshaFigur = (ImageView) findViewById(R.id.AshaFigur);
        KrishnaFigur = (ImageView) findViewById(R.id.KrishnaFigur);
        LaxmiFigur = (ImageView) findViewById(R.id.LaxmiFigur);
        KamalFigur = (ImageView) findViewById(R.id.KamalFigur);


        AshaFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this, SweetAlertDialog.WARNING_TYPE);
                pDialog
                        .setTitleText("Vil du spille som Asha?")
                        .setContentText("Asha er meget fattig og vil have det ekstra svært")
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
                                returnIntent.putExtra("result","Asha");
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });
        KrishnaFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this, SweetAlertDialog.WARNING_TYPE);
                pDialog
                        .setTitleText("Vil du spille som Krishna?")
                        .setContentText("Krishna kommer fra en fattig familie og har det svært i skolen")
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
                                returnIntent.putExtra("result","Krishna");
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                        }

                        }).show();
                ;

            }
        });
        LaxmiFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this, SweetAlertDialog.WARNING_TYPE);
                pDialog
                        .setTitleText("Vil du spille som Laxmi?")
                        .setContentText("Laxmi er en klog pige fra en lidt fattig familie")
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
                                returnIntent.putExtra("result","Laxmi");
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });

        KamalFigur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pDialog = new SweetAlertDialog(Figurvalg_akt.this, SweetAlertDialog.WARNING_TYPE);
                pDialog
                        .setTitleText("Vil du spille som Kamal?")
                        .setContentText("Kamal er en klog dreng fra en ikke så fattig familie")
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
                                returnIntent.putExtra("result","Kamal");
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }

                        }).show();
                ;

            }
        });


    }
}

