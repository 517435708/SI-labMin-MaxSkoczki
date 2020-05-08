package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

import javax.annotation.Resource;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.IMAGE_WIDTH;

@Component
class ImageIoCContainer {


    @Resource(name = "select")
    ImageView imageView;

    void resetSelections() {
        for (var position : Position.values()) {
            position.getImageViewPosition().setVisible(false);
        }

        imageView.setVisible(false);
    }

    void moveSelection(Position position) {
        imageView.setVisible(true);
        imageView.setX(position.getX() * IMAGE_WIDTH);
        imageView.setY(position.getY() * IMAGE_WIDTH);
    }
}
