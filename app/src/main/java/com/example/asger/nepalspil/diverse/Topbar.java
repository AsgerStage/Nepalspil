package com.example.asger.nepalspil.diverse;

import android.app.Activity;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.model.Spiller;

/**
 * Created by j on 24-01-17.
 */

public class Topbar {
    TextView textpenge;
    TextView textviden;
    TextView textmad;
    TextView texttid;

    public void init(Activity akt) {
        textpenge = (TextView) akt.findViewById(R.id.textpenge);
        textviden = (TextView) akt.findViewById(R.id.textviden);
        textmad = (TextView) akt.findViewById(R.id.textmad);
        texttid = (TextView) akt.findViewById(R.id.texttid);
    }

    public void opdaterGui(Spiller spiller) {
        textpenge.setText(String.valueOf(spiller.penge));
        textviden.setText(String.valueOf(spiller.viden));
        textmad.setText(String.valueOf(spiller.mad));
        texttid.setText(String.valueOf(spiller.tid));
    }
}
