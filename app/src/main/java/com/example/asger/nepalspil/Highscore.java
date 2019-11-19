package com.example.asger.nepalspil;

import android.util.Log;

import com.example.asger.nepalspil.model.HighscoreElement;
/*
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by j on 30-08-17.
 */

public class Highscore {
    public static boolean AKTIV = false;
    private static HashMap<String, ArrayList<HighscoreElement>> figurtop5 = new HashMap<>();
    /*
    private static FirebaseDatabase database;
    private static DatabaseReference dbRod;
    private static DatabaseReference dbHighscoreDenneMåned;
    private static FirebaseUser fbBruger;
    private static DatabaseReference dbDenneMåned;
*/
    public static ArrayList<HighscoreElement> getTop5(String figurnavn) {
        return figurtop5.get(figurnavn);
    }

    public static void init(final Set<String> figurnavne) {
        /*
        if (database!=null) return;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                fbBruger = authResult.getUser();
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("Hello, World! v3");
                dbRod = database.getReference("v3");
                dbDenneMåned = dbRod.child("måned")
                        .child(new SimpleDateFormat("yyyy MM").format(new Date()));
                dbDenneMåned.child("programstart").child(new SimpleDateFormat("dd HH:mm:ss").format(new Date())).setValue(fbBruger.getUid());

                dbHighscoreDenneMåned = dbDenneMåned.child("highscore");
                figurtop5.clear();
                for (String figur : figurnavne) figurtop5.put(figur, new ArrayList<HighscoreElement>());
                opdaterFraDb();
            }
        });
         */
    }
/*
    private static void opdaterFraDb() {
        for (final String figurnavn : figurtop5.keySet()) {
            dbHighscoreDenneMåned.child(figurnavn).orderByChild("antalUger").limitToFirst(5)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Highscore", "highscore: " + dataSnapshot);
                            ArrayList<HighscoreElement> top5 = figurtop5.get(figurnavn);
                            top5.clear();
                            for (DataSnapshot c : dataSnapshot.getChildren()) {
                                Log.d("Highscore", "highscore: " + c);
                                HighscoreElement elem = c.getValue(HighscoreElement.class);
                                Log.d("Highscore", "highscore: " + elem);
                                top5.add(elem);
                            }
                            while (top5.size() < 5)
                                top5.add(new HighscoreElement(figurnavn, "Noah", 60, 0));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }
*/
    public static void indsætHighscore(final String figurnavn, final String kaldenavn, final int antalUger) {
        /*
        if (database==null) return;
        final DatabaseReference ref = dbHighscoreDenneMåned.child(figurnavn).child(kaldenavn);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HighscoreElement gl = dataSnapshot.getValue(HighscoreElement.class);
                Log.d("Highscore", "ny highscore? "+gl+" >=" + antalUger);
                if (gl==null || gl.antalUger>=antalUger) {
                    ref.setValue(new HighscoreElement(figurnavn, kaldenavn, antalUger, System.currentTimeMillis()));
                }
                opdaterFraDb();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         */
    }

    public static void registrerSpilstart(String figurnavn, String kaldenavn) {
        /*
        dbDenneMåned.child("spilstart")
                .child(new SimpleDateFormat("dd HH:mm:ss").format(new Date()))
                .child(figurnavn).setValue(kaldenavn, fbBruger.getUid());
         */
    }
}
