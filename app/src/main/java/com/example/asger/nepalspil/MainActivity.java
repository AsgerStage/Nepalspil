package com.example.asger.nepalspil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asger.nepalspil.model.spillePlade;

/**
 * Created by Peter on 22-11-2016.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageButton asha = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton kaka = (ImageButton) findViewById(R.id.imageButton5);
        asha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Du har valgt Asha!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, spillePlade.class);
                startActivity(intent);

            }
        });
        kaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Du har valgt Kaka!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, spillePlade.class);
                startActivity(intent);

            }
        });

    }

}
