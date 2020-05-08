package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.board.Board;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Configuration
public class GameControllerConfiguration {

    @Resource(name = "pawnList")
    List<Pawn> pawns;

    private Board board;
    private ImageIoCContainer ioCContainer;

    GameControllerConfiguration(ImageIoCContainer ioCContainer,
                                Board board) {
        this.ioCContainer = ioCContainer;
        this.board = board;
    }

    @Bean
    GameController gameController() {

        GameController gameController = new GameController(board, pawns);
        registerOnClickMethodForPawns(gameController);
        registerOnClickMethodForPositions(gameController);

        return gameController;
    }

    private void registerOnClickMethodForPositions(GameController gameController) {
        for (var position : Position.values()) {
            ImageView imageViewPosition = position.getImageViewPosition();

            imageViewPosition.setOnMouseClicked((MouseEvent event) -> {
                if (imageViewPosition.isVisible()) {
                    Optional<Pawn> optionalPaw = gameController.getSelectedPawn();
                    optionalPaw.ifPresent(pawn -> {
                        gameController.swapColors();
                        Color winning = gameController.move(pawn, position);
                        if (winning != Color.NONE) {
                            System.out.println("Wygrywa: " + winning);
                        }
                    });
                }

                ioCContainer.resetSelections();
            });
        }
    }

    private void registerOnClickMethodForPawns(GameController gameController) {
        for (var pawn : pawns) {
            ImageView imageView = pawn.getPawnImage();

            imageView.setOnMouseClicked((MouseEvent e) -> {

                if (pawn.getColor().equals(gameController.getColorWhoseMoveIsThis())) {
                    ioCContainer.resetSelections();
                    ioCContainer.moveSelection(pawn.getPosition());

                    gameController.getSelectedPawn().ifPresent(p -> p.setSelected(false));
                    pawn.setSelected(true);
                    gameController.getPositionsForPawn(pawn)
                                  .forEach(position -> position.getImageViewPosition()
                                                               .setVisible(true));
                }

            });
        }
    }


}
