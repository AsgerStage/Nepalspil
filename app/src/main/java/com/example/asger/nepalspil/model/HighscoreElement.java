package com.example.asger.nepalspil.model;

/**
 * Created by j on 31-08-17.
 */
public class HighscoreElement {
    public String figurnavn;
    public String kaldenavn;
    public int antalUger;
    public long tidsstempel;

    public HighscoreElement() {
    }

    public HighscoreElement(String figurnavn, String kaldenavn, int antalUger, long tidsstempel) {
        this.figurnavn = figurnavn;
        this.kaldenavn = kaldenavn;
        this.antalUger = antalUger;
        this.tidsstempel = tidsstempel;
    }

    @Override
    public String toString() {
        return "HighscoreElement{" +
//                    "figur='" + figur + '\'' +
                ", kaldenavn='" + kaldenavn + '\'' +
                ", antalUger=" + antalUger +
//                    ", tidsstempel=" + tidsstempel +
                '}';
    }
}
