package pl.pp.skoczki.SkoczkiLogic.game;


import org.springframework.stereotype.Component;
import pl.pp.skoczki.SkoczkiLogic.game.board.Board;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class GameController {


    @Resource(name = "pawnList")
    private List<Pawn> pawns;

    private Color colorWhoseMoveIsThis = Color.WHITE;
    private Board board;

    GameController(Board board) {
        this.board = board;
    }

    public List<Pawn> getPawnsThatCanMove() {
        return pawns.stream().filter(p -> p.getColor().equals(colorWhoseMoveIsThis)).collect(Collectors.toList());
    }

    public List<Position> getPositionsForPawn(Pawn pawn) {
        return board.returnPossibleMovesForPawn(pawn);
    }

    public Color move(Pawn pawn, Position position) {
        pawn.setPosition(position);
        return board.getWinningColor();
    }

}
