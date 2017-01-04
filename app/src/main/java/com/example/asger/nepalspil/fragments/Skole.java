package com.example.asger.nepalspil.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.asger.nepalspil.models.Spiller;
import com.example.asger.nepalspil.R;
import android.util.Log;

/**
 * Created by Asger on 21-11-2016.
 */

public class Skole extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skole);

    }

    Spiller s;
    private int vidensKrav = 10*s.getKlassetrin();

    public void studer(Spiller s){
        if (this.harlaert() == true) {
            s.setViden(s.getViden() + 1);
            s.setTid(s.getTid() - 1);
        }
        else return ;

    }

    public boolean harlaert(){
        if (Math.random()>0.1)
            return true;
        else return false;
    }

    public void spis(Spiller s){
        s.setTid(s.getTid()-1);
        s.setHp(s.getHp()+1);
    }

    public void startEksamen(Spiller s){
        if ((s.getViden() !=  vidensKrav ) || (s.getViden() > vidensKrav))

    }
}
