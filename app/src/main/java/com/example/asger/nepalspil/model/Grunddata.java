package com.example.asger.nepalspil.model;

import java.util.HashMap;

/**
 * Created by j on 25-01-17.
 */

public class Grunddata {
    public static Grunddata instans;

    public HashMap<String, Figurdata> spillere = new HashMap<>();

    // De 4 indgange der er i hashmappen med 4 instanser af de forskellige figurer
    public static Figurdata Asha;
    public static Figurdata Krishna;
    public static Figurdata Laxmi;
    public static Figurdata Kamal;

}
