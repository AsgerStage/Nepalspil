package com.example.asger.nepalspil.model;

import android.app.Activity;
import android.util.Log;

import com.example.asger.nepalspil.diverse.Serialisering;

import java.io.FileNotFoundException;
import java.io.Serializable;


public class Spiller implements Serializable {
    public static Spiller instans;

    // Sæt IKKE versionsnummer så objekt kan læses selvom klassen er ændret.
    // private static final long serialVersionUID = 12345;

    public static int LÆRINGSFART_INGEN = 0;
    public int læringBlyant = 0;
    public int læringKladdehæfte = 0;
    public int læringLommeregner = 0;
    public int glemtViden;
    public int bøger;
    public int position = 0; //starter på felt 1
    public String navn = "Kamal";
    public int penge;
    public int mad = 100;
    public int viden = 0;
    public int klassetrin = 1;
    public int tid = 19;
    public int runde = 1;
    public int bevægelsesFart = 1;
    public int bogKøbtIRundeNr;

    public transient Figurdata figurdata;


    public final static int BRÆTSTØRRELSE = 8;//Kan ændres hvis spillepladen skulle udvides

    public Spiller(Figurdata figur) {
        figurdata = figur;
        this.navn = figur.navn;
        this.penge = figur.startpenge;
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

    public static Spiller læs(Activity akt) {
        try {
            return (Spiller) Serialisering.hent(akt.getFilesDir() + "/spiller.ser");
        } catch (FileNotFoundException ex) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void gem(Activity akt, Spiller spiller) {
        try {
            Serialisering.gem(spiller, akt.getFilesDir() + "/spiller.ser");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    public boolean skoleKanStarteEksamen() {
        if ((viden >= skoleVidensKravForNæsteKlassetrin())) {
            return true;
        } else
            return false;
    }


    public int skoleVidensKravForNæsteKlassetrin() {
        switch (klassetrin) {
            case 1:
                return 10;
            case 2:
                return 40;
            case 3:
                return 90;
            case 4:
                return 110;
            case 5:
                return 160;
            case 6:
                return 220;
            case 7:
                return 300;
            case 8:
                return 400;
            case 9:
                return 500;
            case 10:
                return 700;
            case 11:
                return 800;


        }
        return 10 * klassetrin;
    }



    public static int STUDER_VIDEN = 1;
    public static int STUDER_LEKTIEHJÆLP = 2;
    public static int STUDER_FORSTOD_IKKE = 3;

    /**
     * Denne her metode er ret uforståelig - forklar hvad der sker.
     * @param studer_viden_sands
     * @param studer_lektiehjælp_sands
     * @return
     */
    private static int tryToStudy(double studer_viden_sands, double studer_lektiehjælp_sands) {
        double rand = Math.random();
        Log.d("Spil", "rand = " + rand + " viden_sands = " + studer_viden_sands + " lektiehjælp_sands = " + studer_lektiehjælp_sands);
        if (rand < studer_viden_sands) return STUDER_VIDEN;
        else if (rand < studer_lektiehjælp_sands) return STUDER_LEKTIEHJÆLP;
        return STUDER_FORSTOD_IKKE;
    }


    public int skoleStudér() {
        if (læringBlyant<1) {
            return tryToStudy(0.50, 0.75); // 50% chance for at lære noget, 25% for lektiehjælp, 25% for ikke af forstå noget
        }
        else if (læringKladdehæfte<1) {
            return tryToStudy(0.55, 0.80); // 55% chance for at lære noget, 25% for lektiehjælp, 20% for ikke af forstå noget
        }
        else if (læringLommeregner<1) {
            return tryToStudy(0.60, 0.85); // 60% chance for at lære noget, 25% for lektiehjælp, 15% for ikke af forstå noget
        } else {
            return tryToStudy(0.70, 1.00); // 70% chance for at lære noget, 30% for lektiehjælp
        }
    }
}
