package main.java.pl.pp.SkoczkiLogic;

import java.util.ArrayList;
import java.util.List;

public interface BoardInterface {
    
    // Set pawns on the start positions
    void setStartPositions();
    
    // Check if white win
    boolean didWhiteWin();
    
    // Check if black win
    boolean didBlackWin();
    
    // Move execution
    Enums.Color movePawn(Enums.Position pawn, Enums.Position position, Enums.Color lastJumpedColor);
    
    // Returns possible positions for pawn move. Returns color of jumped pawns for future reference.
    List<MovePair> returnPossibleMovesForPawn(Pawn pawn, Enums.Color lastJumpedColor);
    
    Enums.Color returnColorOfPosition(Enums.Position p);
    
    boolean isEmptyArea(Enums.Position p);
    
}
