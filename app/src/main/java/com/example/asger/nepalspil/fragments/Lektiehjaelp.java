package com.example.asger.nepalspil.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.Spiller;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Lektiehjaelp extends Fragment {

    Button learn;
    AlertDialog.Builder dialog;
    Spiller s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.lektiehjaelp, container,false);

        learn = (Button) rod.findViewById(R.id.learn);
        learn.setText("Her kan du modtage lektiehjælp og opnå den viden du missede i skolen.");
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spiller.getTid()>0) {
                    learn();
                }else{

                }
            }
        });


        return rod;
    }


    public int gemtViden = 0;

    public int getGemtViden(){
        this.gemtViden = gemtViden;
    }

    public int set gemtViden(){
        this.
    }

    private void learn(){
        if (gemtViden > 0){
            s.setViden(s.getViden()+1);
            s.setTid(s.getTid()-1);
        }
        else
            dialog.setTitle("");
            dialog.setMessage("Du har ikke brug for lektiehjælp.");
            dialog.show();
    }
}