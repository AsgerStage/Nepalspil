package com.example.asger.nepalspil;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.asger.nepalspil.diverse.AppOpdatering;
import com.example.asger.nepalspil.diverse.MusicManager;
import com.example.asger.nepalspil.model.Figuruheld;
import com.example.asger.nepalspil.model.Grunddata;
import com.example.asger.nepalspil.model.Spiller;
import com.example.asger.nepalspil.model.Figurdata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

import io.fabric.sdk.android.Fabric;

public class Hovedmenu_akt extends AppCompatActivity {
    boolean continueBGMusic;
    SharedPreferences prefs;

    AlertDialog.Builder dialog;
    static final int FIGURNUMMER_REQUEST = 1;
    private ImageView genoptagKnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        continueBGMusic = true;
        dialog = new AlertDialog.Builder(this);
        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        if (!EMULATOR) {
            Fabric.with(this, new Crashlytics());
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (savedInstanceState==null) AppOpdatering.tjekForNyAPK(this);

        setContentView(R.layout.hovedmenu);

        TextView versionTv = (TextView) findViewById(R.id.version);
        versionTv.setText("v. " + BuildConfig.VERSION_NAME);

        ImageView startknap = (ImageView) findViewById(R.id.startknap);
        ImageView indstillingerknap = (ImageView) findViewById(R.id.indstillingerknap);
        genoptagKnap = (ImageView) findViewById(R.id.genoptagKnap);

        indlæsGrunddata();

        if (Spiller.instans==null) Spiller.instans = Spiller.læs(this);
        if (Spiller.instans!=null) {
            Spiller.instans.figurdata = Grunddata.instans.spillere.get(Spiller.instans.navn);
            if (Spiller.instans.figurdata == null) {
                IllegalStateException fejl = new IllegalStateException(Spiller.instans.navn + " mangler i grunddata");
                if (EMULATOR) throw fejl;
                Crashlytics.logException(fejl);
                Spiller.instans = null;
            }
        }

        startknap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hovedmenu_akt.this, R.anim.image_click));

                Intent intent = new Intent(Hovedmenu_akt.this, Figurvalg_akt.class);
                startActivityForResult(intent, FIGURNUMMER_REQUEST);
            }
        });


        genoptagKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hovedmenu_akt.this, R.anim.image_click));
                Intent intent = new Intent(Hovedmenu_akt.this, Spilleplade_akt.class);
                intent.putExtra("genoptag", true);
                startActivity(intent);
            }
        });

        indstillingerknap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Hovedmenu_akt.this, R.anim.image_click));

                CharSequence options[] = new CharSequence[]{"Besøg skoleliv-i-nepal.dk", "Akkreditering - tak til", "Sluk musik"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Hovedmenu_akt.this);
                builder.setTitle("Indstillinger");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.skoleliv-i-nepal.dk/"));
                                startActivity(browserIntent);
                                break;
                            case 1:
                                Intent intent = new Intent(Hovedmenu_akt.this, Akkredtering_akt.class);
                                startActivity(intent);
                                break;
                            case 2:
                                MusicManager.stop();
                                break;


                        }

                    }
                });
                builder.show();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FIGURNUMMER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String figurnavn = data.getStringExtra("result");
                Figurdata figurdata = Grunddata.instans.spillere.get(figurnavn);
                Log.d("Hovedmenu_akt", "figurnavn = "+ figurnavn + " vælges ud af "+Grunddata.instans.spillere.keySet());
                if (figurdata==null) {
                    Exception fejl = new IllegalStateException("figurnavn "+ figurnavn + " fandtes ikke i "+Grunddata.instans.spillere.keySet());
                    fejl.printStackTrace();
                    figurdata = Grunddata.instans.spillere.entrySet().iterator().next().getValue(); // Tag en tilfældig!
                }
                Spiller.instans = new Spiller(figurdata);
                Intent intent = new Intent(Hovedmenu_akt.this, Spilleplade_akt.class);
                startActivity(intent);
            }
        }
    }

    private void indlæsGrunddata() {
        Grunddata gd = new Grunddata();
        try {
            InputStream is = getResources().openRawResource(R.raw.grunddata);
            //InputStream is = new URL("http://javabog.dk/eksempel.json").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            System.err.println("indlæsGrunddata str = " + str);

            JSONObject json = new JSONObject(str);

            JSONArray figurer = json.getJSONArray("figurer");
            for (int i = 0; i < figurer.length(); i++) {
                JSONObject figurjson = figurer.getJSONObject(i);
                System.err.println("obj = " + figurjson);
                Figurdata figur = new Figurdata();
                figur.json = figurjson;
                figur.navn = figurjson.getString("navn");
                figur.drengekøn = figurjson.optBoolean("drengekøn");
                figur.beskrivelse = figurjson.getString("beskrivelse");
                figur.startpenge = figurjson.getInt("startpenge");

                JSONArray uheld = figurjson.getJSONArray("uheld");
                for (int j = 0; j < uheld.length(); j++) {
                    JSONObject uheldjson = uheld.getJSONObject(j);
                    Figuruheld u = new Figuruheld();
                    u.json = uheldjson;
                    figur.uheld.add(u);
                    u.titel = uheldjson.optString("titel", null);
                    if (u.titel == null)
                        continue; // tomt uheld, dvs faktisk ikke et uheld, men fyld for at mindste risikoen for 'rigtige' uheld
                    u.tekst = uheldjson.getString("tekst");
                    u.pengeForskel = uheldjson.optInt("pengeForskel");
                    u.madForskel = uheldjson.optInt("madForskel");
                    u.videnForskel = uheldjson.optInt("videnForskel");
                    u.tidFaktor = uheldjson.optDouble("tidFaktor", 1);
                    // ... etc
                }

                gd.spillere.put(figur.navn, figur);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Grunddata.instans = gd;
        // Initialisering af de fire figurer der altid er
        Grunddata.Asha = Grunddata.instans.spillere.get("Asha");
        Grunddata.Krishna = Grunddata.instans.spillere.get("Krishna");
        Grunddata.Laxmi = Grunddata.instans.spillere.get("Laxmi");
        Grunddata.Kamal = Grunddata.instans.spillere.get("Kamal");

        // Initialisering af drawables til figurerne
        Grunddata.Asha.drawable_figur_halv_id = R.drawable.figur_asha_halv;
        Grunddata.Asha.drawable_figur_hel_id = R.drawable.figur_asha_hel;
        Grunddata.Krishna.drawable_figur_halv_id = R.drawable.figur_krishna_halv;
        Grunddata.Krishna.drawable_figur_hel_id = R.drawable.figur_krishna_hel;
        Grunddata.Laxmi.drawable_figur_halv_id = R.drawable.figur_laxmi_halv;
        Grunddata.Laxmi.drawable_figur_hel_id = R.drawable.figur_laxmi_hel;
        Grunddata.Kamal.drawable_figur_halv_id = R.drawable.figur_kamal_halv;
        Grunddata.Kamal.drawable_figur_hel_id = R.drawable.figur_kamal_hel;
    }


    public void onPause() {
        super.onPause();
        if (!continueBGMusic)
            MusicManager.pause();
    }

    public void onResume() {
        super.onResume();
        continueBGMusic = false;
        MusicManager.start(this, R.raw.backgroundloop);
        genoptagKnap.setEnabled(Spiller.instans!=null);
    }


}
