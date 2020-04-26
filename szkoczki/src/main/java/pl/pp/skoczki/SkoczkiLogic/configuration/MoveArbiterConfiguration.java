package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.board.MoveArbiter;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;
import pl.pp.skoczki.SkoczkiLogic.minmax.ArtificialIntelligenceController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.RESOURCE_PATH;

@Configuration
public class MoveArbiterConfiguration {

    @Resource(name = "turnImage")
    ImageView imageView;

    private ImageIoCContainer ioCContainer;

    MoveArbiterConfiguration(ImageIoCContainer ioCContainer) {
        this.ioCContainer = ioCContainer;
    }

    @Bean
    MoveArbiter moveArbiter(GameController gameController,
                            ArtificialIntelligenceController artificialIntelligenceController) throws
                                                                                               FileNotFoundException {
        MoveArbiter moveArbiter = new MoveArbiter(gameController, artificialIntelligenceController);
        Image whiteImage = new Image(new FileInputStream(RESOURCE_PATH + "white.png"));
        Image blackImage = new Image(new FileInputStream(RESOURCE_PATH + "black.png"));
        imageView.setImage(whiteImage);
        moveArbiter.setImageView(imageView);
        moveArbiter.setBlackImage(blackImage);
        moveArbiter.setWhiteImage(whiteImage);

        registerOnClickMethodForPositions(moveArbiter, gameController);
        return moveArbiter;
    }


    private void registerOnClickMethodForPositions(MoveArbiter moveArbiter,
                                                   GameController gameController) {
        for (var position : Position.values()) {
            ImageView imageViewPosition = position.getImageViewPosition();

            imageViewPosition.setOnMouseClicked((MouseEvent event) -> {
                if (imageViewPosition.isVisible()) {
                    Optional<Pawn> optionalPaw = gameController.getSelectedPawn();
                    optionalPaw.ifPresent(pawn -> {
                        moveArbiter.swap();
                        Color winning = gameController.move(pawn, position);
                        if (winning != Color.NONE) {
                            generatePopUp(winning);
                        }
                    });
                }

                ioCContainer.resetSelections();
            });
        }
    }

    private void generatePopUp(Color winning) {

        Stage stage = new Stage();
        stage.setWidth(150);
        stage.setHeight(100);

        Label label = new Label();
        if (winning.equals(Color.WHITE)) {
            label.setText("Wygrywa gracz biały!");
        } else {
            label.setText("Wygrywa gracz czarny!");
        }

        Button button = new Button();
        button.setText("OK");

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().addAll(label, button);
        button.setOnMouseClicked((MouseEvent event) -> {
            stage.close();
        });

        HBox box = new HBox();
        box.setPrefWidth(150);
        box.setPrefWidth(100);
        box.getChildren().addAll(toolBar);

        stage.setScene(new Scene(box));
        stage.show();
    }

}
