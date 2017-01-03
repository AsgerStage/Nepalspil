package com.example.asger.nepalspil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.data.Spiller;
import com.example.asger.nepalspil.data.spillePlade;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Peter on 22-11-2016.
 */

public class MainActivity extends AppCompatActivity {
    public static Spiller spiller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash);

        ImageButton asha = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton kaka = (ImageButton) findViewById(R.id.imageButton5);
        asha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,"Du har valgt Asha!", Toast.LENGTH_SHORT).show();
               spiller = new Spiller("Asha", 10, 16, 0, 100,1,false,1);
                Intent intent = new Intent(MainActivity.this, spillePlade.class);
                startActivity(intent);

            }


        });
        kaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Du har valgt Kaka!", Toast.LENGTH_SHORT).show();
               spiller = new Spiller("Kaka", 10, 16, 0,100,1,true,1);

                Intent intent = new Intent(MainActivity.this, spillePlade.class);
                startActivity(intent);

            }
        });

    }

}
