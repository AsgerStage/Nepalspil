package com.example.asger.nepalspil.Model;

import android.util.Log;

/**
 * Created by Asger on 21-11-2016.
 */

public class Spiller {
    private int position;
    private String navn;
    private int penge;
    private int hp;

    public Spiller(String navn) {
        this.navn = navn;
        position = 0;			//starter på felt 1
        penge=0;
        Log.d("Spiller","Spiller oprettet");
    }

    public Spiller(String navn,int penge) {
        position = 0;			//starter på felt 1
        this.navn = navn;
        this.penge = penge;
        Log.d("Spiller","Spiller oprettet med balance");
    }

    public void move(int position){
        this.position=position;
        Log.d("Spiller","Spiller position ændret");
    }
}
