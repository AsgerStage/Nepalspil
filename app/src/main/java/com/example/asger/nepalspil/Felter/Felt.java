package com.example.asger.nepalspil.felter;

/**
 * Created by Asger on 21-11-2016.
 * asd
 */

public abstract class Felt {
    protected int feltnr;
    protected String title;
    protected Felt(int feltnr, String title) {
        this.feltnr = feltnr;
        this.title = title;

    }
    public abstract void landOnField();
}
