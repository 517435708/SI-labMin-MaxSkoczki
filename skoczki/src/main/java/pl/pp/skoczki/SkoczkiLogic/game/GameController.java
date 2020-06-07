package pl.pp.skoczki.SkoczkiLogic.game;


import javafx.scene.image.ImageView;
import lombok.Getter;
import pl.pp.skoczki.SkoczkiLogic.game.board.GameBoard;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.IMAGE_WIDTH;

public class GameController {

    private List<Pawn> pawns;

    @Getter
    private Color colorWhoseMoveIsThis = Color.WHITE;
    private GameBoard gameBoard;

    public GameController(GameBoard gameBoard,
                          List<Pawn> pawns) {
        this.gameBoard = gameBoard;
        this.pawns = pawns;
    }

    public List<Pawn> getPawnsThatCanMove() {
        return pawns.stream()
                    .filter(p -> p.getColor()
                                  .equals(colorWhoseMoveIsThis))
                    .collect(Collectors.toList());
    }

    public List<Position> getPositionsForPawn(Pawn pawn) {
        return gameBoard.returnPossibleMovesForPawn(pawn);
    }

    public Color move(Position reference, Position position) {
        Pawn pawn = pawns.stream().filter(p -> p.getPosition() == reference).findFirst().orElseThrow();
        return move(pawn,position);
    }


    public Color move(Pawn reference, Position position) {

        Pawn pawn = pawns.stream().filter(p -> p.equals(reference)).findFirst().orElseThrow();

        pawn.setPosition(position);
        ImageView imageView = pawn.getPawnImage();

        imageView.setX(position.getX() * IMAGE_WIDTH);
        imageView.setY(position.getY() * IMAGE_WIDTH);

        if (gameBoard.getWinningColor() != Color.NONE) {
            colorWhoseMoveIsThis = Color.NONE;
        }

        return gameBoard.getWinningColor();
    }

    public Optional<Pawn> getSelectedPawn() {
        return pawns.stream()
                    .filter(Pawn::isSelected)
                    .findFirst();
    }

    public void swapColors() {
        if (colorWhoseMoveIsThis.equals(Color.WHITE)) {
            colorWhoseMoveIsThis = Color.BLACK;
        } else {
            colorWhoseMoveIsThis = Color.WHITE;
        }
    }
}
