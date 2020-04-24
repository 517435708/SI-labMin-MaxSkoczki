
package main.java.pl.pp.SkoczkiLogic;

import java.util.List;

public class MovePair {
    List<Integer> targetPosition;
    Enums.Color jumpedColor;
    
    public MovePair(List<Integer> positions, Enums.Color col){
        if(positions.size() != 2)
                throw new IllegalArgumentException("Wrong size of MovePair position argument");
            if(positions.get(0) < 0 || positions.get(1) < 0 || positions.get(0) > 7 || positions.get(1) > 7){
                System.out.println(positions.get(0) + " " + positions.get(1));
                throw new IllegalArgumentException("Position argument for MovePair miss the Board range");}
            else{
                this.targetPosition = positions;
                this.jumpedColor = col;
            }
    }
}
