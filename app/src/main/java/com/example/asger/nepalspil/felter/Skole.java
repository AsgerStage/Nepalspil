package com.example.asger.nepalspil.felter;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.activities.MainActivity;
import com.example.asger.nepalspil.models.SpillePlade;
import com.github.jinatonic.confetti.CommonConfetti;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.asger.nepalspil.R.layout.lektiehjaelp;
import static com.example.asger.nepalspil.activities.MainActivity.spiller;


public class Skole extends AppCompatActivity {

    private Animation animation;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onBackPressed() {
      SpillePlade.updateEntireBoard();
        finish();
    }

    TextView schoolText;
    TextView playerInfo;
    AlertDialog.Builder dialog;

    @Override
    public void onResume() {
        super.onResume();
        updateInfo();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cash);
        final TextView schoolText = (TextView) findViewById(R.id.schoolText);
        final TextView playerInfo = (TextView) findViewById(R.id.schoolPlayerInfo);
        final TextView klasseText = (TextView) findViewById(R.id.klassetrin);
        Button bSpis = (Button) findViewById(R.id.spis);
        Button bStuder = (Button) findViewById(R.id.Studer);
        Button bEksamen = (Button) findViewById(R.id.eksamen);
        ImageView back = (ImageView) findViewById(R.id.skoleBack);
        ImageView helpField = (ImageView) findViewById(R.id.skoleHelp);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plusknowledge);
        final TextView scroll = (TextView) findViewById(R.id.plusknowledge);

        dialog = new AlertDialog.Builder(Skole.this);
        schoolText.setText("Velkommen til Skolen, her kan du spise, studere og tage din eksamen når tiden er.");
        playerInfo.setText("Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid());
        klasseText.setText("Du går i: " +spiller.getKlassetrin() + ". Klasse.");

        onResume();
        {

        }


        helpField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Skolen");
                dialog.setMessage("I skolen kan du studere og få en smule mad. Tryk på knapperne for at studere eller spise. Når du har gået nok i skole kan du tage din eksamen for at komme op i næste klasse. En gang imellem når man studerer forstår man ikke alt undervisningen, og derfor kan man tage i lektiehjælpen for at forstå det.");
                dialog.show();
            }
        });
        bSpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid() > 0) {
                    spis();
                    schoolText.setText("Mmm! Du har spist skolemad.");
                    playerInfo.setText(updateInfo());
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("bitesound.mp3");
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at spise");
                    dialog.show();
                }
            }
        });

        bStuder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spiller.getTid() > 0) {
                    if (spiller.getTid() > 0 && studer()) {;
                        scroll.setText("+1 viden");
                        scroll.startAnimation(animation);
                        System.out.println(spiller.getViden());
                    } else {
                        Toast.makeText(Skole.this, "Du forstod ikke alt undervisningen, tag i lektiehjælpen for at forstå det", Toast.LENGTH_SHORT).show();
                        /*AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                       dialog.setTitle("Lektiehjælp!");
                        dialog.setMessage("Du kunne ikke forstå undervisningen, så din viden kan opnås hos lektiehjælpen.");
                        dialog.show();*/
                    }
                } else {

                    dialog.setTitle("Ikke nok tid!");
                    dialog.setMessage("Du har ikke nok tid til at studere.");
                    dialog.show();
                }
                playerInfo.setText(updateInfo());
            }
        });

        bEksamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kanStartEksamen()) {
                    schoolText.setText("Held og Lykke!");
                    finish();
                    Intent myIntent = new Intent(Skole.this, Eksamen.class);
                    startActivity(myIntent);

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Skole.this);
                    dialog.setTitle("Ikke nok viden!");
                    dialog.setMessage("Du har ikke nok viden til at starte eksamenen! Du skal have mindst " + vidensKrav() + " for at starte eksamenen.");
                    dialog.show();

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SpillePlade.updateEntireBoard();
                v.startAnimation(AnimationUtils.loadAnimation(Skole.this, R.anim.image_click));
                finish();


            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static int vidensKrav = 10 * spiller.getKlassetrin();


    public boolean studer() {
        if (hasLearned()) {

            spiller.setViden(spiller.getViden() + 1);
            spiller.setTid(spiller.getTid() - 1);
            SpillePlade.updateInfobox();
            return true;
        } else {
            spiller.setTid(spiller.getTid() - 1);
            spiller.setGlemtViden(spiller.getGlemtViden() + 1);
            return false;
        }
    }

    public boolean hasLearned() {
        if (Math.random() > 0.1)
            return true;
        else return false;
    }

    public void spis() {
        if (spiller.getTid() > 0) {
            spiller.setTid(spiller.getTid() - 1);
            spiller.setHp(spiller.getHp() + 1);
            SpillePlade.updateInfobox();
        } else {
            schoolText.setText("");
        }

    }

    public boolean kanStartEksamen() {
        if ((spiller.getViden() >= vidensKrav())) {
            return true;
        } else
            return false;
    }

    public static String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();

    }


    public static int vidensKrav() {
        return 10 * spiller.getKlassetrin();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Skole Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
