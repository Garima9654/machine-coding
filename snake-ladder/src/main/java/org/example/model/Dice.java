package org.example.model;

import java.util.Random;
import java.util.UUID;

public class Dice {
    public Integer rollDice(){
        return new Random().nextInt(6) +1;
    }
}
