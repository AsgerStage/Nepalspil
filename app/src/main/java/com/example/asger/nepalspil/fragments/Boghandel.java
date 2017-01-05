package com.example.asger.nepalspil.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asger.nepalspil.R;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Boghandel extends AppCompatActivity {
    Button work, buyBook;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boghandel);

        Button work = (Button) findViewById(R.id.workButton);
        Button buyBook = (Button) findViewById(R.id.buyBookButton);
        Button back = (Button) findViewById(R.id.backButton);

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();
                    System.out.println("Penge:" + spiller.getPenge());
                    System.out.println("Tid: " + spiller.getTid());
                    //playerinfo.setText(updateInfo());
                }
                else{

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                finish();
            }
        });
    }

    private static void work(){
        spiller.setTid(spiller.getTid()-2);
        spiller.setPenge(spiller.getPenge()+10);
    }

    private void buyBook() {
        //spiller.setBook(spiller.getBooks()+1)
        //spiller.setPenge(spiller.getPenge()-10);
    }

}
//(For help)
/*
public class fragmentOne extends Fragment implements OnClickListener {
    Button myButton;

    @Override
    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        View myView = inflater.inflate(R.layout.fragment_1, container, false);
        myButton = (Button) myView.findViewById(R.id.myButton);
        myButton.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onClick(View v) {
        // implements your things
    }
}
 */
