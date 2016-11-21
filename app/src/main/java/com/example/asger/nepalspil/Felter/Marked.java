package com.example.asger.nepalspil.Felter;

import android.util.Log;

/**
 * Created by Asger on 21-11-2016.
 */

public class Marked extends Felt{
    protected Marked(int feltnr, String title) {
        super(feltnr, title);
    }

    @Override
    public void landOnField() {
        Log.d("LandOnField","Landed on Marked");
    }
}
