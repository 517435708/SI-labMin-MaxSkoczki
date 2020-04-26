package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.*;

@Configuration
@DependsOn("pawnList")
public class BoardConfiguration {

    @Resource(name = "pawnList")
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

    private ToolBar generateToolBar() {
        ToolBar toolBar = new ToolBar();

        toolBar.setMinWidth(TOOLBAR_WIDTH);
        toolBar.setMaxWidth(TOOLBAR_WIDTH);
        toolBar.setPrefWidth(TOOLBAR_WIDTH);

        Label label = new Label("New Game: ");
        toolBar.getItems()
               .add(label);

        Button siVsSi = new Button("SI vs SI");
        toolBar.getItems()
               .add(siVsSi);

        Button siVsHuman = new Button("SI vs Human");
        toolBar.getItems()
               .add(siVsHuman);

        Label turn = new Label("Whose turn\n is this: ");
        toolBar.getItems()
               .add(turn);

        toolBar.setOrientation(Orientation.VERTICAL);
        return toolBar;
    }

}
