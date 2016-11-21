package com.example.asger.nepalspil.Felter;

import com.example.asger.nepalspil.Felter.Felt;
import com.example.asger.nepalspil.Felter.Skole;

import java.util.ArrayList;

/**
 * Created by Asger on 21-11-2016.
 */

public class Gameboard {
ArrayList<Felt> Board = new ArrayList<Felt>();

    public Gameboard()
    {

        Hjem Hjem = new Hjem(0,"Hjem");
        Farm Farm = new Farm(1,"Gård");
        Marked Marked = new Marked(2,"Marked");
        Skole Skole = new Skole(3,"Skole");

        Board.add(Hjem);
        Board.add(Farm);
        Board.add(Marked);
        Board.add(Skole);
    }
}
