package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.board.GameBoard;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;

import javax.annotation.Resource;

import java.util.List;

@Configuration
public class GameControllerConfiguration {


    @Resource(name = "normalPawnList")
    List<Pawn> pawns;

    private GameBoard gameBoard;
    private ImageIoCContainer ioCContainer;

    GameControllerConfiguration(ImageIoCContainer ioCContainer,
                                GameBoard gameBoard) {
        this.ioCContainer = ioCContainer;
        this.gameBoard = gameBoard;
    }

    @Bean
    GameController gameController() {

        GameController gameController = new GameController(gameBoard, pawns);
        registerOnClickMethodForPawns(gameController);

        return gameController;
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
