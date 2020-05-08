package pl.pp.skoczki.SkoczkiLogic.minmax;

import javafx.util.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pp.skoczki.SkoczkiLogic.game.board.GameBoard;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SimulatedGameBoard extends GameBoard {

    int boardValue = 0;
    private List<Pawn> simulatedPawns;
    private Pair<Pawn, Position> move;

    SimulatedGameBoard(GameBoard gameBoard) {
        simulatedPawns = gameBoard.getPawns();
    }

    List<Pawn> getSimulatedPawnsOfColor(Color color) {
        return simulatedPawns.stream()
                             .filter(p -> p.getColor()
                                           .equals(color))
                             .collect(Collectors.toList());
    }

    protected boolean isAreaEmpty(Position position) {
        return simulatedPawns.stream()
                             .noneMatch(p -> p.getPosition() == position);
    }

    void setMove(Pawn pawn, Position position) {
        move = new Pair<>(pawn, position);
    }

    void calculateValue() {
        for (var pawn : simulatedPawns) {
            boardValue += pawn.getPosition().getY();
        }
    }
}
