package pl.pp.skoczki.SkoczkiLogic;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static pl.pp.skoczki.SkoczkiLogic.Color.*;
import static pl.pp.skoczki.SkoczkiLogic.Position.*;

@Getter
public class Board {

    private List<Pawn> pawns = new ArrayList<>();

    public Board() {
        setStartPositions();
    }

    private void setStartPositions() {
        pawns.add(new Pawn(WHITE, A1));
        pawns.add(new Pawn(WHITE, B1));
        pawns.add(new Pawn(WHITE, C1));
        pawns.add(new Pawn(WHITE, D1));
        pawns.add(new Pawn(WHITE, E1));
        pawns.add(new Pawn(WHITE, F1));
        pawns.add(new Pawn(WHITE, G1));
        pawns.add(new Pawn(WHITE, H1));
        pawns.add(new Pawn(WHITE, A2));
        pawns.add(new Pawn(WHITE, B2));
        pawns.add(new Pawn(WHITE, C2));
        pawns.add(new Pawn(WHITE, D2));
        pawns.add(new Pawn(WHITE, E2));
        pawns.add(new Pawn(WHITE, F2));
        pawns.add(new Pawn(WHITE, G2));
        pawns.add(new Pawn(WHITE, H2));
        pawns.add(new Pawn(BLACK, A8));
        pawns.add(new Pawn(BLACK, B8));
        pawns.add(new Pawn(BLACK, C8));
        pawns.add(new Pawn(BLACK, D8));
        pawns.add(new Pawn(BLACK, E8));
        pawns.add(new Pawn(BLACK, F8));
        pawns.add(new Pawn(BLACK, G8));
        pawns.add(new Pawn(BLACK, H8));
        pawns.add(new Pawn(BLACK, A7));
        pawns.add(new Pawn(BLACK, B7));
        pawns.add(new Pawn(BLACK, C7));
        pawns.add(new Pawn(BLACK, D7));
        pawns.add(new Pawn(BLACK, E7));
        pawns.add(new Pawn(BLACK, F7));
        pawns.add(new Pawn(BLACK, G7));
        pawns.add(new Pawn(BLACK, H7));
    }


    Color getWinningColor() {
        if (didWhiteWin()) {
            return WHITE;
        }

        if (didBlackWin()) {
            return BLACK;
        }

        return NONE;
    }

    void updatePawnPosition(Pawn pawn, Position whereMovePawn) {
        pawns.stream()
             .filter(p -> p.equals(pawn))
             .findFirst()
             .ifPresent(p -> p.setPosition(whereMovePawn));
    }

    List<Position> returnPossibleMovesForPawn(Pawn pawn) {
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
            List<Position> jumpPositions = getPossibleToJumpPositions(position).stream().filter(this::isAreaEmpty).collect(Collectors.toList());
            jumpPositions.stream().filter(p -> !theAnswerOfJumps.contains(p)).forEach(positions::add);
        }
        theAnswerOfJumps.addAll(availablePositions);
        theAnswerOfJumps.remove(pawn.getPosition());
        return theAnswerOfJumps;
    }

    private boolean didWhiteWin() {
        return pawns.stream()
                    .noneMatch(p -> p.getColor() == WHITE && p.getPosition()
                                                              .getY() < 6);
    }

    private boolean didBlackWin() {
        return pawns.stream()
                    .noneMatch(p -> p.getColor() == BLACK && p.getPosition()
                                                              .getY() > 1);

    }

    private List<Position> getPossibleToJumpPositions(Position position) {

        List<Position> jumpPositions = new ArrayList<>();

        position.getNearPositions()
                .stream()
                .filter(p -> !isAreaEmpty(p))
                .forEach(neighbour -> calculateJumpPosition(position, neighbour).ifPresent(jumpPositions::add));

        return jumpPositions;
    }

    private Optional<Position> calculateJumpPosition(Position root, Position neighbour) {
        return Position.intToPosition(2*neighbour.getX() - root.getX(), 2*neighbour.getY() - root.getY());
    }

    private boolean isAreaEmpty(Position position) {
        return pawns.stream()
                    .noneMatch(p -> p.getPosition() == position);
    }

}
