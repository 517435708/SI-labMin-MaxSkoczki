package pl.pp.skoczki.SkoczkiLogic.minmax;

import javafx.util.Pair;
import org.springframework.stereotype.Component;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.board.GameBoard;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import java.util.*;

@Component
public class ArtificialIntelligenceController {

    private GameController gameController;
    private GameBoard gameBoard;

    private int calculationDepth = 3;
    private boolean aiEnabled = true;
    private Color aiColor = Color.BLACK;

    ArtificialIntelligenceController(GameController gameController,
                                     GameBoard gameBoard) {
        this.gameController = gameController;
        this.gameBoard = gameBoard;
    }

    public boolean commit() {
        if (aiEnabled && gameController.getColorWhoseMoveIsThis()
                                       .equals(aiColor)) {

            SimulatedGameBoard bestBoard = buildTree(calculationDepth,
                                                     aiColor,
                                                     new SimulatedGameBoard(gameBoard),
                                                     Integer.MIN_VALUE,
                                                     Integer.MAX_VALUE);
            Pair<Pawn, Position> move = bestBoard.getMove();
            gameController.move(move.getKey(), move.getValue());

            return true;
        }
        return false;
    }

    private SimulatedGameBoard buildTree(int depth,
                                         Color color,
                                         SimulatedGameBoard simulatedBoard,
                                         int alpha,
                                         int betha) {


        List<SimulatedGameBoard> simulatedGameBoards = generateBoardForEachPawn(simulatedBoard,
                                                                                color);
        if (depth == 0 || simulatedGameBoards.isEmpty()) {
            if (color.equals(Color.WHITE)) {
                return simulatedGameBoards.stream()
                                          .max(Comparator.comparingInt(SimulatedGameBoard::getBoardValue))
                                          .orElseThrow();

            } else {
                return simulatedGameBoards.stream()
                                          .min(Comparator.comparingInt(SimulatedGameBoard::getBoardValue))
                                          .orElseThrow();
            }
        }

        SimulatedGameBoard bestBoard = null;

        if (color.equals(Color.BLACK)) {
            int best = Integer.MIN_VALUE;
            for (var board : simulatedGameBoards) {
                SimulatedGameBoard root = buildTree(depth - 1, Color.swap(color), board, alpha, betha);
                if (root.getBoardValue() > best) {
                    best = root.getBoardValue();
                    bestBoard = board;
                }
                if (alpha < root.getBoardValue()) {
                    alpha = root.getBoardValue();
                }
                if (betha <= alpha) {
                    break;
                }
            }
            return bestBoard;
        } else {
            int best = Integer.MAX_VALUE;
            for (var board : simulatedGameBoards) {
                SimulatedGameBoard root = buildTree(depth - 1, Color.swap(color), board, alpha, betha);
                if (root.getBoardValue() < best) {
                    best = root.getBoardValue();
                    bestBoard = board;
                }
                if (betha > root.getBoardValue()) {
                    betha = root.getBoardValue();
                }
                if (betha <= alpha) {
                    break;
                }
            }
            return bestBoard;
        }

    }

    private List<SimulatedGameBoard> generateBoardForEachPawn(SimulatedGameBoard simulatedGameBoard,
                                                              Color simulatedPawnsOfColor) {

        List<Pawn> pawns = simulatedGameBoard.getSimulatedPawnsOfColor(simulatedPawnsOfColor);
        Map<Pawn, List<Position>> possibleMovesByPawn = new HashMap<>();

        for (var pawn : pawns) {
            possibleMovesByPawn.put(pawn, simulatedGameBoard.returnPossibleMovesForPawn(pawn));
        }

        List<SimulatedGameBoard> boardsForEachPawn = new ArrayList<>();
        for (var entry : possibleMovesByPawn.entrySet()) {
            List<SimulatedGameBoard> allBoards = getBoardsForEntry(entry, simulatedGameBoard);
            boardsForEachPawn.addAll(allBoards);
        }

        return boardsForEachPawn;
    }

    private List<SimulatedGameBoard> getBoardsForEntry(Map.Entry<Pawn, List<Position>> entry,
                                                       SimulatedGameBoard rootBoard) {

        List<SimulatedGameBoard> simulatedGameBoards = new ArrayList<>();
        for (var position : entry.getValue()) {
            Pawn pawn = entry.getKey();
            List<Pawn> pawns = copyPawns(pawn, position, rootBoard.getSimulatedPawns());

            SimulatedGameBoard simulatedGameBoard = new SimulatedGameBoard();
            simulatedGameBoard.setSimulatedPawns(pawns);
            simulatedGameBoard.setMove(pawn, position);
            simulatedGameBoard.calculateValue();

            simulatedGameBoards.add(simulatedGameBoard);
        }


        return simulatedGameBoards;
    }

    private List<Pawn> copyPawns(Pawn pawn,
                                 Position position,
                                 List<Pawn> simulatedPawns) {
        Pawn pawnCopy = new Pawn(pawn);
        pawnCopy.setPosition(position);

        List<Pawn> pawns = new ArrayList<>(simulatedPawns);

        pawns.remove(pawn);
        pawns.add(pawnCopy);

        return pawns;
    }

    private SimulatedGameBoard getBestBoard(Map.Entry<Pawn, List<Position>> entry,
                                            SimulatedGameBoard rootBoard) {
        List<SimulatedGameBoard> simulatedGameBoards = new ArrayList<>();
        for (var position : entry.getValue()) {
            Pawn pawn = entry.getKey();
            Position positionBuffer = pawn.getPosition();
            pawn.setPosition(position);
            List<Pawn> pawns = new ArrayList<>(rootBoard.getSimulatedPawns());
            pawn.setPosition(positionBuffer);
            SimulatedGameBoard simulatedGameBoard = new SimulatedGameBoard();
            simulatedGameBoard.setSimulatedPawns(pawns);
            simulatedGameBoard.setMove(pawn, position);
            simulatedGameBoard.calculateValue();

            simulatedGameBoards.add(simulatedGameBoard);
        }

        int bestValue = deduceWorstValue();
        SimulatedGameBoard bestBoard = rootBoard;

        for (var board : simulatedGameBoards) {
            if (deduce(board.getBoardValue(), bestValue) > 0) {
                bestBoard = board;
            }
        }

        return bestBoard;
    }

    private int deduce(int boardValue, int bestValue) {
        if (aiColor.equals(Color.WHITE)) {
            return boardValue - bestValue;
        }
        return bestValue - boardValue;
    }

    private int deduceWorstValue() {
        if (aiColor.equals(Color.WHITE)) {
            return -104;
        }
        return 104;
    }


}
