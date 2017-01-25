package com.example.asger.nepalspil.models;

import org.json.JSONObject;

/**
 * Created by j on 25-01-17.
 */

public class Figuruheld {
    public JSONObject json;
    /**
     * Navnet på uheldet. Kan være null for at signalere et 'tomt' uheld, dvs faktisk ikke et uheld,
     * men fyld i listen for at mindste risikoen for 'rigtige' uheld
     */
    public String titel;
    public String tekst;
    public int pengeForskel;
    public int madForskel;
    public int videnForskel;
    public double tidFaktor;
}
