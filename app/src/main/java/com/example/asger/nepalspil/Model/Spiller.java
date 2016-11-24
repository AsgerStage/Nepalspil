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
    private int viden;
    private int klassetrin;
    private boolean sex;
    private int tid;

    public Spiller(String navn) {
        this.navn = navn;
        this.position = 0;			//starter på felt 1
        this.penge=50;
        this.tid=16;
        this.viden=0;
        this.klassetrin=1;
        Log.d("Spiller","Spiller oprettet");
    }

    public Spiller(String navn,int penge, int tid, int viden, int hp,int klassetrin, boolean sex) {
        position = 0;			//starter på felt 1
        this.navn = navn;
        this.penge = penge;
        this.hp = hp;
        this.tid= tid;
        this.viden= viden;
        this.klassetrin= klassetrin;
        this.sex = sex;
        Log.d("Spiller","Spiller oprettet med balance");
    }

    public void move(int position){
        this.position=position;
        Log.d("Spiller","Spiller position ændret");
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getViden() {
        return viden;
    }

    public void setViden(int penge) {
        this.viden = viden;
    }

    public int getKlassetrin() {
        return klassetrin;
    }

    public void setKlassetrin(int Klassetrin) {
        this.klassetrin = Klassetrin;
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
