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

    public Pawn(Pawn pawn) {
        this.color = pawn.getColor();
        this.position = pawn.getPosition();
    }

    public Pawn(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        Pawn pawn = (Pawn) obj;
        return this.color.equals(pawn.color) && this.position.equals(pawn.position);
    }

    @Override
    public int hashCode() {
        return color.hashCode()*(position.hashCode() + 1) + position.hashCode();
    }

}
