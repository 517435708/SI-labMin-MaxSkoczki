package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.RESOURCE_PATH;


@Configuration
public class PossibleMoveConfiguration {

    @Bean(name = "possibleMove")
    public ImageView possibleMove() throws
                                    FileNotFoundException {
        Image image = new Image(new FileInputStream(RESOURCE_PATH + "possiblemove.png"));
        ImageView imageView = new ImageView(image);
        imageView.setVisible(false);

        return imageView;
    }

}
