package pl.pp.skoczki.SkoczkiLogic.game.board;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static pl.pp.skoczki.SkoczkiLogic.game.pawn.Color.*;

@Getter
@Component
public class GameBoard implements Board {

    @Resource(name = "normalPawnList")
    private List<Pawn> pawns;


    public Color getWinningColor() {
        if (didWhiteWin()) {
            return WHITE;
        }

        if (didBlackWin()) {
            return BLACK;
        }

        return NONE;
    }

    @Override
    public List<Position> returnPossibleMovesForPawn(Pawn pawn) {
        Queue<Position> positions = new LinkedList<>();
        List<Position> availablePositions = pawn.getPosition()
                                                .getNearPositions()
                                                .stream()
                                                .filter(this::isAreaEmpty)
                                                .collect(Collectors.toList());

        positions.add(pawn.getPosition());
        List<Position> theAnswerOfJumps = new ArrayList<>();

        while (!positions.isEmpty()) {
            Position position = positions.poll();
            theAnswerOfJumps.add(position);
            List<Position> jumpPositions = getPossibleToJumpPositions(position).stream()
                                                                               .filter(this::isAreaEmpty)
                                                                               .collect(Collectors.toList());
            jumpPositions.stream()
                         .filter(p -> !theAnswerOfJumps.contains(p))
                         .forEach(positions::add);
        }
        theAnswerOfJumps.addAll(availablePositions);
        theAnswerOfJumps.remove(pawn.getPosition());
        return theAnswerOfJumps;
    }

    protected boolean isAreaEmpty(Position position) {
        return pawns.stream()
                    .noneMatch(p -> p.getPosition() == position);
    }

    private List<Position> getPossibleToJumpPositions(Position position) {

        List<Position> jumpPositions = new ArrayList<>();

        position.getNearPositions()
                .stream()
                .filter(p -> !isAreaEmpty(p))
                .forEach(neighbour -> calculateJumpPosition(position, neighbour).ifPresent(jumpPositions::add));

        return jumpPositions;
    }


    private boolean didWhiteWin() {
        return pawns.stream()
                    .noneMatch(p -> p.getColor() == WHITE && p.getPosition()
                                                              .getY() > 1);
    }

    private boolean didBlackWin() {
        return pawns.stream()
                    .noneMatch(p -> p.getColor() == BLACK && p.getPosition()
                                                              .getY() < 6);

    }

    private Optional<Position> calculateJumpPosition(Position root, Position neighbour) {
        return Position.intToPosition(2 * neighbour.getX() - root.getX(), 2 * neighbour.getY() - root.getY());
    }

}
