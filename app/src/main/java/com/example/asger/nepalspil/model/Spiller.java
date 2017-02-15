package com.example.asger.nepalspil.model;

import android.util.Log;


public class Spiller {
    public static Spiller instans;

    public static int LÆRINGSFART_INGEN = 0;
    public int læringsfart = 0;
    public int glemtViden;
    public int books;
    public int position;
    public String navn;
    public int penge;
    public int mad;
    public int viden;
    public int klassetrin;
    public int tid;
    public int runde;
    public int bevægelsesFart;
    public int bogKøbtIRundeNr;
    public boolean music;

    public Figurdata figurdata;



    // XXX
    public Spiller(String navn, int penge, int tid, int viden, int mad, int klassetrin, boolean sex, int runde, int bevægelsesFart, int glemtViden) {
        position = 0;            //starter på felt 1
        this.navn = navn;
        this.penge = penge;
        this.mad = mad;
        this.tid = tid;
        this.viden = viden;
        this.klassetrin = klassetrin;
        this.runde = runde;
        this.bevægelsesFart = bevægelsesFart;
        this.glemtViden = glemtViden;

        Log.d("Spiller", "Spiller oprettet med balance");
    }


    // XXX
    public Spiller(Boolean sex, int books, int position, String navn, int penge, int mad, int viden, int klassetrin, int tid, int runde, int bevægelsesFart, int bogKøbtIRundeNr) {
        this.books = books;
        this.position = position;
        this.navn = navn;
        this.penge = penge;
        this.mad = mad;
        this.viden = viden;
        this.klassetrin = klassetrin;
        this.tid = tid;
        this.runde = runde;
        this.bevægelsesFart = bevægelsesFart;
        this.bogKøbtIRundeNr = bogKøbtIRundeNr;
        this.music = music;

    }

    public final static int BRÆTSTØRRELSE = 8;//Kan ændres hvis spillepladen skulle udvides

    public Spiller(Figurdata figur) {
        this(figur.navn, figur.startpenge, 19, 0, 100, 1, figur.drengekøn, 1, 1, 0);
        figurdata = figur;
    }

    /**
     * Rykker spilleren og trækker den korrekte mængde tid fra spilleren.
     *
     * @param nyPosition ønsket ny pos
     * @return true hvis tiden er gået og ny runde er startet
     */
    public boolean rykTilFelt(int nyPosition) {
        //Løsningen virker lidt bøvlet, men da Javas modulo (%) kan blive negativ gav det nogle problemer.
        //Math.floorMod metoden kunne være benyttet, men det ville samtidigt gøre at applikationen kun vil virke til android API 24 og frem.
        int count1 = 0;
        int count2 = 0;
        int j = this.position;
        int i = this.position;

        if (nyPosition == this.position) {
            Log.d("Spiller", "Spiller trykkede på felt han/hun allerede stod på");
        } else {
            // Vi går begge veje rundt om spillebrættet for at finde ud af hvilken vej der er kortest
            while (true) {
                count1++;
                // Log.d("Spiller","1count1:"+count1); Til debugging
                i++;
                Log.d("Spiller", "i:" + i + " Newposition= " + nyPosition);
                if (((i + BRÆTSTØRRELSE) % BRÆTSTØRRELSE) == nyPosition) {
                    break;
                }              //Laver en ikke-negativ modulo, som Math.floorMod ville gøre.
            }
            while (true) {
                count2++;
                //  Log.d("Spiller","1count2:"+count2); //Til debugging
                j--;
                Log.d("Spiller", "j:" + j + " Newposition= " + nyPosition);
                if (((j + BRÆTSTØRRELSE) % BRÆTSTØRRELSE) == nyPosition) {
                    break;
                }
            }


            if (count2 > count1) { // fremad er kortest
                int timeToSubtract = (Math.round(count1 / bevægelsesFart));
                if (count1 == 4 && bevægelsesFart == 3) {
                    timeToSubtract = 2;
                } else if (timeToSubtract == 0) {
                    timeToSubtract = 1;
                }

                tid = tid - timeToSubtract;

                Log.d("Spiller", "Spiller rykket fra " + this.position + " til " + nyPosition + " og mistet tid: " + timeToSubtract + " og har nu " + this.tid + " tid");
                this.position = nyPosition;
            } else if (count1 >= count2) { // baglæns er kortest
                int timeToSubtract = (Math.round(count2 / bevægelsesFart));
                if (count2 == 4 && bevægelsesFart == 3) {
                    timeToSubtract = 2;
                }
                if (timeToSubtract == 0) {
                    timeToSubtract = 1;
                }

                tid = tid - timeToSubtract;
                Log.d("Spiller", count2 + " " + bevægelsesFart);
                Log.d("Spiller", "Spiller rykket fra " + this.position + " til " + nyPosition + " og mistet tid: " + timeToSubtract + " og har nu " + this.tid + " tid");
                this.position = nyPosition;
                //Den korteste vej trækkes fra spillerens tid
            }
        }


        if (this.tid <= 0) {
            this.runde++;
            this.tid = 19;
            this.position = 0;
            return true;
        }
        return false;
    }

    public void arbejd(int tidsrum, int indtægt) {
        this.penge = penge + indtægt;
        this.tid = tid - tidsrum;
    }

    public void studér(int tidsrum, int videnforskel) {
        this.viden = viden + videnforskel;
        this.tid = tid - tidsrum;
    }

    public void spis(int tidsrum, int udgift, int madforskel) {
        this.mad = mad + madforskel;
        this.tid = tid - tidsrum;
        this.penge = penge - udgift;
    }

}
