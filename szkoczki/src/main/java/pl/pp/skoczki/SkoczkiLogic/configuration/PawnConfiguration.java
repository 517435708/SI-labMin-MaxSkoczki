package pl.pp.skoczki.SkoczkiLogic.configuration;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Pawn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pl.pp.skoczki.SkoczkiLogic.configuration.ConfigConstants.*;
import static pl.pp.skoczki.SkoczkiLogic.game.pawn.Color.*;
import static pl.pp.skoczki.SkoczkiLogic.game.pawn.Position.*;

@Configuration
public class PawnConfiguration {


    @Bean(name = "reversePawnListWithOneOff")
    public List<Pawn> pawnsRevers() {

        List<Pawn> pawns = new ArrayList<>();

        pawns.add(new Pawn(WHITE, A1));
        pawns.add(new Pawn(WHITE, B1));
        pawns.add(new Pawn(WHITE, C1));
        pawns.add(new Pawn(WHITE, D1));
        pawns.add(new Pawn(WHITE, E1));
        pawns.add(new Pawn(WHITE, F1));
        pawns.add(new Pawn(WHITE, G1));
        pawns.add(new Pawn(WHITE, H5));
        pawns.add(new Pawn(WHITE, A2));
        pawns.add(new Pawn(WHITE, B2));
        pawns.add(new Pawn(WHITE, C2));
        pawns.add(new Pawn(WHITE, D2));
        pawns.add(new Pawn(WHITE, E2));
        pawns.add(new Pawn(WHITE, F2));
        pawns.add(new Pawn(WHITE, G2));
        pawns.add(new Pawn(WHITE, H2));
        pawns.add(new Pawn(BLACK, A8));
        pawns.add(new Pawn(BLACK, B8));
        pawns.add(new Pawn(BLACK, C8));
        pawns.add(new Pawn(BLACK, D8));
        pawns.add(new Pawn(BLACK, E8));
        pawns.add(new Pawn(BLACK, F8));
        pawns.add(new Pawn(BLACK, G8));
        pawns.add(new Pawn(BLACK, H8));
        pawns.add(new Pawn(BLACK, A5));
        pawns.add(new Pawn(BLACK, B7));
        pawns.add(new Pawn(BLACK, C7));
        pawns.add(new Pawn(BLACK, D7));
        pawns.add(new Pawn(BLACK, E7));
        pawns.add(new Pawn(BLACK, F7));
        pawns.add(new Pawn(BLACK, G7));
        pawns.add(new Pawn(BLACK, H7));

        pawns.forEach(this::generateImage);

        return pawns;
    }

    @Bean(name = "normalPawnList")
    public List<Pawn> pawnsNormal() {

        List<Pawn> pawns = new ArrayList<>();

        pawns.add(new Pawn(BLACK, A1));
        pawns.add(new Pawn(BLACK, B1));
        pawns.add(new Pawn(BLACK, C1));
        pawns.add(new Pawn(BLACK, D1));
        pawns.add(new Pawn(BLACK, E1));
        pawns.add(new Pawn(BLACK, F1));
        pawns.add(new Pawn(BLACK, G1));
        pawns.add(new Pawn(BLACK, H1));
        pawns.add(new Pawn(BLACK, A2));
        pawns.add(new Pawn(BLACK, B2));
        pawns.add(new Pawn(BLACK, C2));
        pawns.add(new Pawn(BLACK, D2));
        pawns.add(new Pawn(BLACK, E2));
        pawns.add(new Pawn(BLACK, F2));
        pawns.add(new Pawn(BLACK, G2));
        pawns.add(new Pawn(BLACK, H2));
        pawns.add(new Pawn(WHITE, A8));
        pawns.add(new Pawn(WHITE, B8));
        pawns.add(new Pawn(WHITE, C8));
        pawns.add(new Pawn(WHITE, D8));
        pawns.add(new Pawn(WHITE, E8));
        pawns.add(new Pawn(WHITE, F8));
        pawns.add(new Pawn(WHITE, G8));
        pawns.add(new Pawn(WHITE, H8));
        pawns.add(new Pawn(WHITE, A7));
        pawns.add(new Pawn(WHITE, B7));
        pawns.add(new Pawn(WHITE, C7));
        pawns.add(new Pawn(WHITE, D7));
        pawns.add(new Pawn(WHITE, E7));
        pawns.add(new Pawn(WHITE, F7));
        pawns.add(new Pawn(WHITE, G7));
        pawns.add(new Pawn(WHITE, H7));

        pawns.forEach(this::generateImage);

        return pawns;
    }

    private void generateImage(Pawn pawn) {
        Image image;
        try {
            if (pawn.getColor() == Color.WHITE) {
                image = new Image(new FileInputStream(RESOURCE_PATH + "white.png"));
            } else {
                image = new Image(new FileInputStream(RESOURCE_PATH + "black.png"));
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        ImageView imageView = new ImageView(image);
        imageView.setX(pawn.getPosition().getX() * IMAGE_WIDTH);
        imageView.setY(pawn.getPosition().getY() * IMAGE_WIDTH);

        pawn.setPawnImage(imageView);
    }

}
