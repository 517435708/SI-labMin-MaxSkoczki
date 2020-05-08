package pl.pp.skoczki.SkoczkiLogic.game.pawn;


import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Pawn {
    private Color color;
    private Position position;
    private ImageView pawnImage;

    private boolean selected = false;

    public Pawn(Color color, Position position) {
        this.color = color;
        this.position = position;
    }
}
