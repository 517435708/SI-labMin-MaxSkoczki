
package main.java.pl.pp.SkoczkiLogic;

import java.util.List;


public interface PawnInterface {
    // Change position of pawn
    void changePosition(List<Integer> position);
    
    // Return position of pawn
    List<Integer> getPosition();
    
    // Return color of pawn
    Enums.Color getColor();
}
