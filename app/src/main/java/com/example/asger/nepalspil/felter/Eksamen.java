package com.example.asger.nepalspil.felter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.asger.nepalspil.Highscore;
import com.example.asger.nepalspil.Hovedmenu_akt;
import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.model.Spiller;
import com.github.jinatonic.confetti.CommonConfetti;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.asger.nepalspil.model.Spiller.instans;

public class Eksamen extends AppCompatActivity {
    TextView spørgsmålTv;
    Button svarKnap1;
    Button svarKnap2;
    Button svarKnap3;
    FrameLayout container;
    AlertDialog.Builder dialog;
    SharedPreferences prefs;

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Er du sikker?")
                .setContentText("Hvis du forlader eksamen vil det koste dig " + Spiller.instans.skoleVidensKravForNæsteKlassetrin() + " viden.")
                .setConfirmText("Ja")
                .showCancelButton(true)
                .setCancelText("Nej, jeg kan godt!")
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
                        finish();
                    }

                }).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Spiller.instans==null) { finish(); return; } // genstart i frisk JVM - vis hovedmenu
        setContentView(R.layout.eksamen);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        spørgsmålTv = (TextView) findViewById(R.id.spørgsmål);
        svarKnap1 = (Button) findViewById(R.id.svar1);
        svarKnap2 = (Button) findViewById(R.id.svar2);
        svarKnap3 = (Button) findViewById(R.id.svar3);
        container = (FrameLayout) findViewById(R.id.container);
        dialog = new AlertDialog.Builder(Eksamen.this);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/EraserDust.ttf");
        spørgsmålTv.setTypeface(face);
        svarKnap1.setOnClickListener(forkert);
        svarKnap2.setOnClickListener(forkert);
        svarKnap3.setOnClickListener(forkert);

        switch (instans.klassetrin -1) {
            case 0:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvad spiser nepalesiske børn til morgenmad?", "Havregryn med mælk", "Grød", "Ris og grøntsager");
                    svarKnap3.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Går nepalesiske skolebørn med skoleuniform?", "Nej, det har de ikke råd til", "Ja næsten alle har en skoleuniform", "Nogle skoler bruger uniform andre gør ikke");
                    svarKnap2.setOnClickListener(korrekt);
                }
                break;
            case 1:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvorfor er det godt, at børnene får skolemad?", "Så kan de bedre koncentrere sig i timerne", "Så behøver de ikke selv tage mad med.", "Så føles dagen længere.");
                    svarKnap1.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Er det gratis at gå i skole i Nepal?", "De fleste skoler opkræver skolepenge", "Det er altid gratis", "Det koster altid penge");
                    svarKnap1.setOnClickListener(korrekt);
                }
                break;
            case 2:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvilke ingredienser bruges blandt andet i nepalesisk skolemad?", "Brød og kød", "Ris, løg, hvidløg og chili", "Nudler og chips");
                    svarKnap2.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Hvilke kornsorter dyrker man i Nepal?", "byg og rug", "ris og hvede", "havre og quinoa");
                    svarKnap2.setOnClickListener(korrekt);
                }
                break;
            case 3:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvad tid starter den offentlige skole i Nepal?", "Kl. 10", "Kl. 9", "Kl. 8");
                    svarKnap1.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Hvad tid slutter skolen i Nepal?", "Det er forskelligt", "Kl. 14", "kl. 16");
                    svarKnap3.setOnClickListener(korrekt);
                }
                break;
            case 4:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Er der privatskoler i Nepal?", "Ja, mange.", "Nej, det har de ikke råd til.", "Nogle få");
                    svarKnap1.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Hvor tit går børnene til eksamen i Nepal?", "Efter 3. klasse", "Efter hvert skoleår", "Efter 10. klasse");
                    svarKnap2.setOnClickListener(korrekt);
                }
                break;
            case 5:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvem går i folkeskolen i Nepal?", "cirka halvdelen", "Næsten alle", "De fattige");
                    svarKnap3.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Er undervisningen i den nepalesiske folkeskole god nok", "Nej desværre er den tit dårlig", "Ja da alle skolerne er vildt gode", "De fleste er gode");
                    svarKnap1.setOnClickListener(korrekt);
                }
                break;
            case 6:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvorfor er det vigtigt, at børn i Nepal lærer noget?", "Fordi de skal have en uddannelse og et bedre liv.", "Fordi de kommer til at kede sig derhjemme.", "Fordi de ikke kan lide at være alene hjemme");
                    svarKnap1.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Kan man tage en uddannelse i Nepal, hvis man ikke har bestået 10. klasse?", "Ja, det er let nok", "Kun hvis man spørger pænt", "Nej, man skal have 10. klasse for at få en uddannelse");
                    svarKnap3.setOnClickListener(korrekt);
                }
                break;
            case 7:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvorfor er der mange fattige børn i Nepal, som ikke laver lektier?", "De behøver ikke lave lektier", "Deres forældre sætter dem ikke i gang", "Hindureligionen forbyder lektier");
                    svarKnap2.setOnClickListener(korrekt);
                } else {
                    sætSpørgsmål("Hvilken usund snack er nepalesiske børn vilde med?", "vingummi", "lakrids", "nudler");
                    svarKnap3.setOnClickListener(korrekt);
                }
                break;
            case 8:
                if (Math.random() < 0.5) {
                    sætSpørgsmål("Hvad skal børn i Nepal kunne til eksamen?", "De skal kunne tænke selvstændigt", "De skal kunne deres bøger udenad", "De skal kunne forstå, hvad der står i bøgerne.");
                    svarKnap2.setOnClickListener(spilletErVundet);
                } else {
                    sætSpørgsmål("Bliver der snydt til eksamen i Nepal?", "Nej da! Hvordan kunne du tro det!", "Ja det er helt almindeligt", "En sjælden gang i mellem, men lærerne holder godt øje");
                    svarKnap2.setOnClickListener(spilletErVundet);
                }
                break;

        }
    }

    public void sætSpørgsmål(String titel, String sv1, String sv2, String sv3) {
        spørgsmålTv.setText(titel.toUpperCase());
        svarKnap1.setText(sv1);
        svarKnap2.setText(sv2);
        svarKnap3.setText(sv3);
    }

    private void konfetti() {
        CommonConfetti.rainingConfetti(container, new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW})
                .infinite();
    }

    View.OnClickListener korrekt = new View.OnClickListener() {
        public void onClick(View v) {

            final MediaPlayer mp = MediaPlayer.create(Eksamen.this, R.raw.tada);
            mp.start();
            konfetti();
            instans.klassetrin = instans.klassetrin + 1;

            new SweetAlertDialog(Eksamen.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("TILLYKKE!!!")
                    .setContentText("Du bestod og går nu i " + instans.klassetrin + ". klasse.")
                    .setConfirmText("Fedt!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();

        }
    };

    View.OnClickListener forkert = new View.OnClickListener() {
        public void onClick(View v) {

            instans.viden = instans.viden - 10 * instans.klassetrin;
            if (instans.viden < 0) {
                instans.viden = 0;
            }

            new SweetAlertDialog(Eksamen.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Øv...")
                    .setContentText("Du har desværre svaret forkert på eksamen og er derfor dumpet. -" + 10 * instans.klassetrin + " viden")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }
    };

    View.OnClickListener spilletErVundet = new View.OnClickListener() {
        public void onClick(View v) {
            final MediaPlayer mp = MediaPlayer.create(Eksamen.this, R.raw.tada);
            mp.start();
            konfetti();
            instans.klassetrin = instans.klassetrin + 1;
            if (prefs.getInt("" + instans.navn, -1) == -1) {
                prefs.edit().putInt("" + instans.navn, instans.runde).apply();
            } else if (prefs.getInt("" + instans.navn, -1) > instans.runde) {
                prefs.edit().putInt("" + instans.navn, instans.runde).apply();
            }

            String kaldenavn = prefs.getString("kaldenavn", "");
            Highscore.indsætHighscore(instans.navn, kaldenavn, instans.runde);
            //dialog.setMessage(Html.fromHtml("Godt klaret, du har vundet spillet på " + instans.runde + " uger! Nu kan jeg få en uddannelse.\n\nPrøv igen med en ny figur, eller <a href='http://skolemadtilnepal.wordpress.com/boernene-paa-bhawanipurskole'>klik her for at møde børnene i virkeligheden</a>"))
            dialog.setMessage(Html.fromHtml("Godt klaret "+kaldenavn+", du har gennemført spillet på " + instans.runde + " uger!</br/>\n" +
                    "Nu kan "+ instans.navn +" få en uddannelse.<br/>\n<br/>\n" +
                    "Prøv igen med en ny figur, eller <a href='http://skolemadtilnepal.wordpress.com/boernene-paa-bhawanipurskole'>klik her for at møde børnene i virkeligheden</a>"))
                    .setTitle("Du har gennemført spillet med "+ instans.navn)
                    .setCancelable(false)
                    .setIcon(instans.figurdata.drawable_figur_halv_id)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Intent intent = new Intent(Eksamen.this,Hovedmenu_akt.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    });
            AlertDialog alert = dialog.create();
            alert.show();
            // For at linket virker
            ((TextView) (alert.findViewById(android.R.id.message))).setMovementMethod(LinkMovementMethod.getInstance());

        }
    };


}
