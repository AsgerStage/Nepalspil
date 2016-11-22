package com.example.asger.nepalspil.model;

import android.util.Log;

/**
 * Created by Asger on 21-11-2016.
 */

public class Spiller {
    private int position;
    private String navn;
    private int penge;
    private int hp;
    private boolean sex;
    private int tid;

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

    public int getPenge() {
        return penge;
    }

    public void setPenge(int penge) {
        this.penge = penge;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

}
