package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;
import pl.pp.skoczki.SkoczkiLogic.minmax.ArtificialIntelligenceController;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.*;



@Configuration
@DependsOn("normalPawnList")
public class BoardConfiguration {


    @Resource(name = "normalPawnList")
    private List<Pawn> pawns;

    @Bean(name = "chessBoard")
    public Scene gamePanel() throws
                             FileNotFoundException {

        ToolBar toolBar = generateToolBar();
        Group box = board();

        HBox hBox = new HBox(toolBar);
        hBox.getChildren()
            .add(box);
        return new Scene(hBox, CHESSBOARD_WIDTH + TOOLBAR_WIDTH, CHESSBOARD_WIDTH);
    }


    @Bean(name = "select")
    public ImageView select() throws
                              FileNotFoundException {
        Image image = new Image(new FileInputStream(RESOURCE_PATH + "select.png"));
        ImageView imageView = new ImageView(image);
        imageView.setVisible(false);

        return imageView;
    }


    private Group board() throws
                          FileNotFoundException {

        ImageView[] positionImages = new ImageView[64];
        ImageView[] boardImages = new ImageView[65];
        ImageView[] pawnsImages = generatePawnsImages();

        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                Image image;
                if ((i + j) % 2 == 0) {
                    image = new Image(new FileInputStream(RESOURCE_PATH + "darkSquare.png"));
                } else {
                    image = new Image(new FileInputStream(RESOURCE_PATH + "lightSquare.png"));
                }
                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(IMAGE_WIDTH);
                imageView.setFitHeight(IMAGE_WIDTH);
                imageView.setX(IMAGE_WIDTH * i);
                imageView.setY(IMAGE_WIDTH * j);
                boardImages[i * 8 + j] = imageView;
                positionImages[i*8 + j] = Position.intToPosition(i, j).orElseThrow().getImageViewPosition();
            }
        }
        boardImages[64] = select();

        ImageView[] images = merge(boardImages, pawnsImages);
        images = merge(images, positionImages);

        return new Group(images);
    }

    private ImageView[] generatePawnsImages() {
        return pawns.stream()
                    .map(Pawn::getPawnImage)
                    .toArray(ImageView[]::new);
    }


    private ImageView[] merge(ImageView[] boardImages, ImageView[] pawnsImages) {
        ImageView[] images = new ImageView[boardImages.length + pawnsImages.length];

        int i = 0;
        for (var item : boardImages) {
            images[i] = item;
            i++;
        }

        for (var item : pawnsImages) {
            images[i] = item;
            i++;
        }

        return images;
    }

    @Bean("turnImage")
    public ImageView turnImage() throws
                                   FileNotFoundException {
        return new ImageView();
    }

    private ToolBar generateToolBar() throws
                                      FileNotFoundException {
        ToolBar toolBar = new ToolBar();

        toolBar.setMinWidth(TOOLBAR_WIDTH);
        toolBar.setMaxWidth(TOOLBAR_WIDTH);
        toolBar.setPrefWidth(TOOLBAR_WIDTH);

        Label label = new Label("New Game: ");
        toolBar.getItems()
               .add(label);


        Button siVsHuman = new Button("SI vs Human");


        siVsHuman.setOnAction(e ->{
            ArtificialIntelligenceController.setIsHumanPlaying(Boolean.TRUE);
        });

        toolBar.getItems()
               .add(siVsHuman);
        Button siVsSi = new Button("SI vs SI");
        siVsSi.setOnAction(e ->{
            ArtificialIntelligenceController.setIsHumanPlaying(Boolean.FALSE);
        });
        toolBar.getItems()
                .add(siVsSi);

        Label turn = new Label("Whose turn is this: ");
        toolBar.getItems()
               .add(turn);

        ImageView imageBuffer = turnImage();


        StackPane imageContainer = new StackPane();

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(IMAGE_WIDTH + 10);
        rectangle.setHeight(IMAGE_WIDTH + 10);
        rectangle.setFill(Color.BROWN);
        rectangle.setStroke(Color.BROWN);

        imageContainer.getChildren().addAll(rectangle, imageBuffer);

        toolBar.getItems().add(imageContainer);

        Label algorithmDepthLabel = new Label("Algorithm Depth :");
        toolBar.getItems().add(algorithmDepthLabel);

        TextArea textArea = new TextArea("15");
        textArea.setPrefHeight(16);
        toolBar.getItems().add(textArea);

        Button setDepth = new Button("Set depth");
        setDepth.setOnAction(e ->{
            ArtificialIntelligenceController.setCalculationDepth(Integer.parseInt(textArea.getText()));
        });
        toolBar.getItems()
                .add(setDepth);
        Button changeColor = new Button("Change your color");
        changeColor.setOnAction(e ->{
            ArtificialIntelligenceController.swapAiColor();
            ArtificialIntelligenceController.commit();
            GameController.swapColors();
        });
        toolBar.getItems()
                .add(changeColor);

        toolBar.setOrientation(Orientation.VERTICAL);
        return toolBar;
    }

}
