package com.example.asger.nepalspil.felter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asger.nepalspil.R;
import com.example.asger.nepalspil.models.SpillePlade;

import static com.example.asger.nepalspil.activities.MainActivity.spiller;

/**
 * Created by Bruger on 03-01-2017.
 */

public class Boghandel extends AppCompatActivity {
    Button work, buyBook;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boghandel);

        final TextView playerInfo = (TextView) findViewById(R.id.playerinfo);
        final TextView bookstoreInfo = (TextView) findViewById(R.id.fieldinfo);

        Button work = (Button) findViewById(R.id.workButton);
        Button buyBook = (Button) findViewById(R.id.buyBookButton);
        Button back = (Button) findViewById(R.id.backButton);

        work.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (spiller.getTid() >= 2) {
                    work();
                    System.out.println("Penge:" + spiller.getPenge());
                    System.out.println("Tid: " + spiller.getTid());
                    playerinfo.setText(updateInfo());
                } else {

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }

    private static void work() {
        spiller.setTid(spiller.getTid() - 2);
        spiller.setPenge(spiller.getPenge() + 10);
    }

    private void buyBook() {
        spiller.setBooks(spiller.getBooks()+1)
        spiller.setPenge(spiller.getPenge()-10);
    }

    public String updateInfo() {
        SpillePlade.updateInfobox();
        return "Navn: " + spiller.getNavn() + "\n mad: " + spiller.getHp() + "\n Penge: " + spiller.getPenge() + "\n Viden: " + spiller.getViden() + "\n Klassetrin: " + spiller.getKlassetrin() + "\n Tid: " + spiller.getTid();

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
