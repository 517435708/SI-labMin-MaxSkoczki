package pl.pp.skoczki.SkoczkiLogic.minmax;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.board.Board;
import pl.pp.skoczki.SkoczkiLogic.game.board.GameBoard;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class ArtificialIntelligenceController {
    private static GameController gameController;
    private static GameBoard gameBoard;

    private static int calculationDepth = 15;
    private static boolean aiEnabled = true;
    private static Color aiColor = Color.BLACK;
    private static Boolean isHumanPlaying = Boolean.TRUE;
    private static Color humanColor = Color.WHITE;

    static Pair<Position, Position> bestMove;


    ArtificialIntelligenceController(GameController gameController,
                                     GameBoard gameBoard) {
        this.gameController = gameController;
        this.gameBoard = gameBoard;
    }

    public static void setIsHumanPlaying(Boolean aFalse) {
        isHumanPlaying = aFalse;
    }

    public static void setCalculationDepth(int cd){
        calculationDepth = cd;
    }

    public static void swapAiColor(){
        if(aiColor == Color.WHITE)
            aiColor = Color.BLACK;
        else
            aiColor = Color.WHITE;
    }

    public static boolean commit() {
        if ((aiEnabled && gameController.getColorWhoseMoveIsThis()
                .equals(aiColor)) || !isHumanPlaying) {

            List<Position> whitePositions = gameBoard.getPawns()
                    .stream()
                    .filter(p -> p.getColor()
                            .equals(Color.WHITE))
                    .map(Pawn::getPosition)
                    .collect(Collectors.toList());
            List<Position> blackPositions = gameBoard.getPawns()
                    .stream()
                    .filter(p -> p.getColor()
                            .equals(Color.BLACK))
                    .map(Pawn::getPosition)
                    .collect(
                            Collectors.toList());

            buildTree(calculationDepth,
                    gameController.getColorWhoseMoveIsThis(),
                    whitePositions,
                    blackPositions,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);


            gameController.move(bestMove.getKey(), bestMove.getValue());
            return true;
        }
        return false;
    }

    private static int buildTree(int depth,
                          Color color,
                          List<Position> whitePositions,
                          List<Position> blackPositions,
                          int alpha,
                          int betha) {


        if (depth == 0) {
            return valueOfBoard(whitePositions, blackPositions);
        }


        List<Pair<Position, Position>> moves;
        if (color.equals(Color.WHITE)) {
            moves = generateMovesPerPositions(whitePositions, blackPositions);
            moves.sort(Comparator.comparingInt(m -> (m.getValue()
                    .getY() - m.getKey()
                    .getY())));
        } else {
            moves = generateMovesPerPositions(blackPositions, whitePositions);
            moves.sort((m1, m2) -> (m2.getValue().getY() - m2.getKey().getY()) - (m1.getValue().getY() - m1.getKey().getY()));
        }


        if (color.equals(Color.BLACK)) {
            int buffer = alpha;
            for (var move : moves) {
                blackPositions.remove(move.getKey());
                blackPositions.add(move.getValue());
                alpha = Math.max(alpha, buildTree(depth - 1, Color.swap(color), whitePositions, blackPositions, alpha, betha));
                if (buffer != alpha) {
                    if (depth == calculationDepth)
                        bestMove = move;
                    buffer = alpha;
                }
                if (alpha >= betha) {
                    break;
                }
                blackPositions.remove(move.getValue());
                blackPositions.add(move.getKey());
            }
            return alpha;
        } else {
            int buffer = betha;
            for (var move : moves) {
                whitePositions.remove(move.getKey());
                whitePositions.add(move.getValue());
                betha = Math.min(betha, buildTree(depth - 1, Color.swap(color), whitePositions, blackPositions, alpha, betha));
                if (buffer != betha) {
                    if (depth == calculationDepth)
                        bestMove = move;
                    buffer = betha;
                }
                if (alpha >= betha) {
                    break;
                }
                whitePositions.remove(move.getValue());
                whitePositions.add(move.getKey());
            }
            return betha;
        }

    }

    private static int valueOfBoard(List<Position> whitePositions, List<Position> blackPositions) {
        int sum = 0;
        int tempValue = 0;
        for (var pos : whitePositions) {
            tempValue = pos.getY();
            if (tempValue < 3)
                sum += tempValue - 3;
            else if (tempValue > 6)
                sum += tempValue + 3;
            else
                sum += pos.getY();
        }
        for (var pos : blackPositions) {
            sum += pos.getY();
        }

        return sum;
    }


    private static List<Pair<Position, Position>> generateMovesPerPositions(List<Position> whoseMoving,
                                                                     List<Position> rest) {

        List<Pair<Position, Position>> moves = new ArrayList<>();
        for (var pos : whoseMoving) {
            List<Position> possibleMoves = generateMoves(pos, rest, whoseMoving);
            for (var move : possibleMoves) {
                moves.add(new Pair<>(pos, move));
            }
        }

        return moves;
    }

    private static List<Position> generateMoves(Position pos,
                                         List<Position> rest,
                                         List<Position> whoseMoving) {
        Queue<Position> positions = new LinkedList<>();
        List<Position> availablePositions = getPositionsNotOccupied(pos, rest, whoseMoving);

        positions.add(pos);
        List<Position> theAnswerOfJumps = new ArrayList<>();

        while (!positions.isEmpty()) {
            Position position = positions.poll();
            theAnswerOfJumps.add(position);
            List<Position> jumpPositions = getPossibleToJumpPositions(position, rest, whoseMoving).stream()
                    .filter(p -> isAreaEmpty(
                            p,
                            rest,
                            whoseMoving))
                    .collect(Collectors.toList());
            jumpPositions.stream()
                    .filter(p -> !theAnswerOfJumps.contains(p))
                    .forEach(positions::add);
        }
        theAnswerOfJumps.addAll(availablePositions);
        theAnswerOfJumps.remove(pos);
        return theAnswerOfJumps;
    }

    private static List<Position> getPossibleToJumpPositions(Position position, List<Position> pos1, List<Position> pos2) {

        List<Position> jumpPositions = new ArrayList<>();

        position.getNearPositions()
                .stream()
                .filter(p -> !isAreaEmpty(p, pos1, pos2))
                .forEach(neighbour -> calculateJumpPosition(position, neighbour).ifPresent(jumpPositions::add));
        //jumpPositions.removeIf(p -> p.y < position.y);
        return jumpPositions;
    }

    private static boolean isAreaEmpty(Position position, List<Position> positions, List<Position> positionsSecond) {
        return positions.stream()
                .noneMatch(p -> p == position) && positionsSecond.stream()
                .noneMatch(p -> p == position);
    }

    private static Optional<Position> calculateJumpPosition(Position root, Position neighbour) {
        return Position.intToPosition(2 * neighbour.getX() - root.getX(), 2 * neighbour.getY() - root.getY());
    }


    private static List<Position> getPositionsNotOccupied(Position pos, List<Position> rest, List<Position> whoseMoving) {
        List<Position> positions = new ArrayList<>();
        for (var nearPos : pos.getNearPositions()) {
            if (!rest.contains(nearPos) && !whoseMoving.contains(nearPos)) {
                positions.add(nearPos);
            }
        }
        return positions;
    }
}
