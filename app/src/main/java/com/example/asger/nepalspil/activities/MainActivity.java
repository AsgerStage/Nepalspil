package com.example.asger.nepalspil.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.felter.Skole;
import com.example.asger.nepalspil.models.Spiller;
import com.example.asger.nepalspil.models.SpillePlade;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Peter on 22-11-2016.
 */

public class MainActivity extends AppCompatActivity {
    public static Spiller spiller;
    boolean continueBGMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        continueBGMusic=true;
        Fabric.with(this, new Crashlytics());


        setContentView(R.layout.splash);

        ImageButton asha = (ImageButton) findViewById(R.id.imageButton4);
        final ImageButton kaka = (ImageButton) findViewById(R.id.imageButton5);
        final ImageView checkmarkasha = (ImageView) findViewById(R.id.imageView1);
        final ImageView checkmarkkaka = (ImageView) findViewById(R.id.imageView2);
        ImageView start = (ImageView) findViewById(R.id.startknap);
        ImageView options = (ImageView) findViewById(R.id.optionsknap);


        asha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Du har valgt Asha!", Toast.LENGTH_SHORT).show();
                checkmarkkaka.setVisibility(View.INVISIBLE);
                checkmarkasha.setVisibility(View.VISIBLE);
                spiller = new Spiller("Asha", 10, 16, 0, 100, 1, false, 1, false, 0);
            }


        });
        kaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Du har valgt Kaka!", Toast.LENGTH_SHORT).show();
                checkmarkasha.setVisibility(View.INVISIBLE);
                checkmarkkaka.setVisibility(View.VISIBLE);
                spiller = new Spiller("Kaka", 10, 16, 0, 100, 1, true, 1, false, 0);

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_click));

                if (spiller == null) {
                    Toast.makeText(MainActivity.this, "Du mangler at vælge en figur.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (spiller.getNavn() == "Kaka" || spiller.getNavn() == "Asha") {
                    Intent intent = new Intent(MainActivity.this, SpillePlade.class);
                    startActivity(intent);
                }
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_click));
                CharSequence options[] = new CharSequence[]{"Skoleliv i Nepal", "Om spillet", "option 3"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setTitle("Indstillinger");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.skoleliv-i-nepal.dk/"));
                                startActivity(browserIntent);
                                ;
                            case 1:



                        }


                        // the user clicked on options[which]
                    }
                });
                builder.show();


            }
        });


    }
    public void onPause()
    {
        super.onPause();
        if(!continueBGMusic)
            MusicManager.pause();
    }
    public void onResume()
    {
        super.onResume();

        continueBGMusic=false;
        MusicManager.start(this,R.raw.backgroundloop);
    }



}
