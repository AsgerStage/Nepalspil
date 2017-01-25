package com.example.asger.nepalspil.models;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by j on 25-01-17.
 */

public class Figurdata {
    public JSONObject json;
    public String navn;
    public boolean drengekøn;
    public String beskrivelse;
    public int startpenge;
    public ArrayList<Figuruheld> uheld = new ArrayList<>();
}
