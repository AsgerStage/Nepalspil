package com.example.asger.nepalspil;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by j on 30-08-17.
 */

class Highscore {
    private static FirebaseDatabase database;
    private static DatabaseReference dbRod;
    private static DatabaseReference dbHighscoreDenneMåned;
    private static FirebaseUser fbBruger;

    public static class HighscoreElement {
        public String figur;
        public String øgenavn;
        public int antalUger;
        public long tidsstempel;

        public HighscoreElement() {}

        public HighscoreElement(String figur, String øgenavn, int antalUger, long tidsstempel) {
            this.figur = figur;
            this.øgenavn = øgenavn;
            this.antalUger = antalUger;
            this.tidsstempel = tidsstempel;
        }

        @Override
        public String toString() {
            return "HighscoreElement{" +
//                    "figur='" + figur + '\'' +
                    ", øgenavn='" + øgenavn + '\'' +
                    ", antalUger=" + antalUger +
//                    ", tidsstempel=" + tidsstempel +
                    '}';
        }
    }

    public static void init() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                fbBruger = authResult.getUser();
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("Hello, World!");
                dbRod = database.getReference("v1");
                dbHighscoreDenneMåned = dbRod.child("highscoreMåned")
                        .child(new SimpleDateFormat("yyyy_MM").format(new Date()));
                opdaterFraDb();
            }
        });
    }

    private static void opdaterFraDb() {
        dbHighscoreDenneMåned.orderByChild("antalUger").limitToFirst(5)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Highscore", "highscore: "+dataSnapshot);
                        for (DataSnapshot c : dataSnapshot.getChildren()) {
                            Log.d("Highscore", "highscore: "+c);
                            HighscoreElement elem = c.getValue(HighscoreElement.class);
                            Log.d("Highscore", "highscore: "+elem);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public static void indsætHighscore(final String figur, final String øgenavn, final int antalUger) {
        if (database==null) return;
        final DatabaseReference ref = dbHighscoreDenneMåned.child(fbBruger.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HighscoreElement gl = dataSnapshot.getValue(HighscoreElement.class);
                Log.d("Highscore", "ny highscore? "+gl+" >=" + antalUger);
                if (gl==null || gl.antalUger>=antalUger) {
                    /*
                    ref.child("tidsstempel").setValue(System.currentTimeMillis());
                    ref.child("antalUger").setValue(antalUger);
                    ref.child("øgenavn").setValue(øgenavn);
                    ref.child("figur").setValue(figur);
                    */
                    ref.setValue(new HighscoreElement(figur, øgenavn, antalUger, System.currentTimeMillis()));
                }
                opdaterFraDb();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
