package pl.pp.skoczki.SkoczkiLogic.game;


import javafx.scene.image.ImageView;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.pp.skoczki.SkoczkiLogic.game.board.Board;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.IMAGE_WIDTH;

public class GameController {

    private List<Pawn> pawns;

    @Getter
    private Color colorWhoseMoveIsThis = Color.WHITE;
    private Board board;

    public GameController(Board board,
                          List<Pawn> pawns) {
        this.board = board;
        this.pawns = pawns;
    }

    public List<Pawn> getPawnsThatCanMove() {
        return pawns.stream()
                    .filter(p -> p.getColor()
                                  .equals(colorWhoseMoveIsThis))
                    .collect(Collectors.toList());
    }

    public List<Position> getPositionsForPawn(Pawn pawn) {
        return board.returnPossibleMovesForPawn(pawn);
    }

    public Color move(Pawn pawn, Position position) {
        pawn.setPosition(position);
        ImageView imageView = pawn.getPawnImage();

        imageView.setX(position.getX() * IMAGE_WIDTH);
        imageView.setY(position.getY() * IMAGE_WIDTH);

        if (board.getWinningColor() != Color.NONE) {
            colorWhoseMoveIsThis = Color.NONE;
        }

        return board.getWinningColor();
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
