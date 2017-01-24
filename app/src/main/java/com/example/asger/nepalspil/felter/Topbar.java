package com.example.asger.nepalspil.felter;

import android.app.Activity;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.Spiller;

import static com.example.asger.nepalspil.activities.Hovedmenu_akt.spiller;

/**
 * Created by j on 24-01-17.
 */

class Topbar {
    TextView textpenge;
    TextView textviden;
    TextView textmad;
    TextView playerinfo;

    public void init(Activity akt) {
        textpenge = (TextView) akt.findViewById(R.id.textpenge);
        textviden = (TextView) akt.findViewById(R.id.textviden);
        textmad = (TextView) akt.findViewById(R.id.textmad);
        playerinfo = (TextView) akt.findViewById(R.id.playerinfo);
    }

    public void opdaterGui(Spiller spiller) {
        textpenge.setText(String.valueOf(spiller.getPenge()));
        textviden.setText(String.valueOf(spiller.getViden()));
        textmad.setText(String.valueOf(spiller.getHp()));
        playerinfo.setText(String.valueOf(spiller.getTid()));
    }
}
