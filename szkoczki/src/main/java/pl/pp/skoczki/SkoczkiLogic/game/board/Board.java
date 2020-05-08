package pl.pp.skoczki.SkoczkiLogic.game.board;

import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import java.util.List;

public interface Board {
    List<Position> returnPossibleMovesForPawn(Pawn pawn);
}
