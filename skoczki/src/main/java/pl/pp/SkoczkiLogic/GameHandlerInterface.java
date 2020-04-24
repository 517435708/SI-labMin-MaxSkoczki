package main.java.pl.pp.SkoczkiLogic;

import java.util.List;

public interface GameHandlerInterface {

    void run();
    void handleTurn(List<Board> history);
    void printBoard();
    void undoMove();
    void stopMove();
    void move(Enums.Position p1, Enums.Position p2);
    void surrender();
    void printMovesForPawn(Enums.Position p1);
}
