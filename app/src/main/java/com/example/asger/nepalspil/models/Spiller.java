package com.example.asger.nepalspil.models;

import android.util.Log;


public class Spiller {
    public static Spiller instans;
    public boolean sex;
    private int learningAmp = 0;
    private int glemtViden;
    private int books;
    private int position;
    private String navn;
    private int penge;
    private int hp;
    private int viden;
    private int klassetrin;
    private int tid;
    public int runde;
    private int moveSpeed;
    private int lastBookBought;
    public boolean music;


    public Spiller(String navn) {
        this.navn = navn;
        this.glemtViden = 0;
        this.position = 0;            //starter på felt 1
        this.penge = 50;
        this.tid = 16;
        this.viden = 0;
        this.klassetrin = 1;
        this.runde = 1;
        this.moveSpeed = 1;


        Log.d("Spiller", "Spiller oprettet");
    }

    public Spiller(String navn, int penge, int tid, int viden, int hp, int klassetrin, boolean sex, int runde, int moveSpeed, int glemtViden) {
        position = 0;            //starter på felt 1
        this.navn = navn;
        this.penge = penge;
        this.hp = hp;
        this.tid = tid;
        this.viden = viden;
        this.klassetrin = klassetrin;
        this.sex = sex;
        this.runde = runde;
        this.moveSpeed = moveSpeed;
        this.glemtViden = glemtViden;

        Log.d("Spiller", "Spiller oprettet med balance");
    }


    public Spiller(Boolean sex, int books, int position, String navn, int penge, int hp, int viden, int klassetrin, int tid, int runde, int moveSpeed, int lastBookBought) {
        this.sex = sex;
        this.books = books;
        this.position = position;
        this.navn = navn;
        this.penge = penge;
        this.hp = hp;
        this.viden = viden;
        this.klassetrin = klassetrin;
        this.tid = tid;
        this.runde = runde;
        this.moveSpeed = moveSpeed;
        this.lastBookBought = lastBookBought;
        this.music = music;

    }

    /**
     * Rykker spilleren og trækker den korrekte mængde tid fra spilleren.
     *
     * @param newPosition ønsket ny pos
     * @return true hvis tiden er gået og ny runde er startet
     */
    public boolean move(int newPosition) {
        //Løsningen virker lidt bøvlet, men da Javas modulo (%) kan blive negativ gav det nogle problemer.
        //Math.floorMod metoden kunne være benyttet, men det ville samtidigt gøre at applikationen kun vil virke til android API 24 og frem.
        int boardsize = 8;//Kan ændres hvis spillepladen skulle udvides
        int count1 = 0;
        int count2 = 0;
        int j = this.position;
        int i = this.position;

        if (newPosition == this.position) {
            Log.d("Spiller", "Spiller trykkede på felt han/hun allerede stod på");
        } else {
            // Vi går begge veje rundt om spillebrættet for at finde ud af hvilken vej der er kortest
            while (true) {
                count1++;
                // Log.d("Spiller","1count1:"+count1); Til debugging
                i++;
                Log.d("Spiller", "i:" + i + " Newposition= " + newPosition);
                if (((i + boardsize) % boardsize) == newPosition) {
                    break;
                }              //Laver en ikke-negativ modulo, som Math.floorMod ville gøre.
            }
            while (true) {
                count2++;
                //  Log.d("Spiller","1count2:"+count2); //Til debugging
                j--;
                Log.d("Spiller", "j:" + j + " Newposition= " + newPosition);
                if (((j + boardsize) % boardsize) == newPosition) {
                    break;
                }
            }


            if (count2 > count1) { // fremad er kortest
                int timeToSubtract = (Math.round(count1 / moveSpeed));
                if (count1 == 4 && moveSpeed == 3) {
                    timeToSubtract = 2;
                } else if (timeToSubtract == 0) {
                    timeToSubtract = 1;
                }

                tid = tid - timeToSubtract;

                Log.d("Spiller", "Spiller rykket fra " + this.position + " til " + newPosition + " og mistet tid: " + timeToSubtract + " og har nu " + this.tid + " tid");
                this.position = newPosition;
            } else if (count1 >= count2) { // baglæns er kortest
                int timeToSubtract = (Math.round(count2 / moveSpeed));
                if (count2 == 4 && moveSpeed == 3) {
                    timeToSubtract = 2;
                }
                if (timeToSubtract == 0) {
                    timeToSubtract = 1;
                }

                tid = tid - timeToSubtract;
                Log.d("Spiller", count2 + " " + moveSpeed);
                Log.d("Spiller", "Spiller rykket fra " + this.position + " til " + newPosition + " og mistet tid: " + timeToSubtract + " og har nu " + this.tid + " tid");
                this.position = newPosition;
                //Den korteste vej trækkes fra spillerens tid
            }
        }


        if (this.tid <= 0) {
            this.runde++;
            this.tid = 16;
            this.position = 0;
            return true;
        }
        return false;
    }

    public void work(int timeCost, int money) {
        setPenge(getPenge() + money);
        setTid(getTid() - timeCost);
    }

    public void study(int timeCost, int viden) {
        setViden(getViden() + viden);
        setTid(getTid() - timeCost);
    }

    public void eat(int timeCost, int moneyCost, int hp) {
        setHp(getHp() + hp);
        setTid(getTid() - timeCost);
        setPenge(getPenge() - moneyCost);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getViden() {
        return viden;
    }

    public void setViden(int viden) {
        this.viden = viden;
    }

    public int getKlassetrin() {
        return klassetrin;
    }

    public void setKlassetrin(int Klassetrin) {
        this.klassetrin = Klassetrin;
    }

    public int getPenge() {
        return penge;
    }

    public void setPenge(int penge) {
        this.penge = penge;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getBooks() {
        return books;
    }

    public void setBooks(int books) {
        this.books = books;
    }

    public void setmoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getmoveSpeed() {
        return moveSpeed;
    }

    public boolean getSex() {
        return sex;
    }

    public int getGlemtViden() {
        return glemtViden;
    }

    public int getLearningAmp() {
        return learningAmp;
    }

    public void setLearningAmp(int learningAmp) {
        this.learningAmp = learningAmp;
    }

    public void setGlemtViden(int glemtViden) {
        this.glemtViden = glemtViden;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getRunde() {
        return runde;
    }

    public void setRunde(int runde) {
        this.runde = runde;
    }

    public int getLastBookBought() {
        return lastBookBought;
    }

    public void setLastBookBought(int lastBookBought) {
        this.lastBookBought = lastBookBought;
    }
}
