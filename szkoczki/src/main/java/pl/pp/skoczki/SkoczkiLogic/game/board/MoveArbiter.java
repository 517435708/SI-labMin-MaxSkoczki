package pl.pp.skoczki.SkoczkiLogic.game.board;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import pl.pp.skoczki.SkoczkiLogic.game.GameController;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.minmax.ArtificialIntelligenceController;


@Setter
@Getter
public class MoveArbiter {
    private ImageView imageView;
    private Image whiteImage;
    private Image blackImage;

    private Color whoseTurnIsThis = Color.WHITE;

    private GameController gameController;
    private ArtificialIntelligenceController aiController;

    public MoveArbiter(GameController gameController,
                       ArtificialIntelligenceController aiController) {
        this.gameController = gameController;
        this.aiController = aiController;
    }

    public void swap() {
        if (whoseTurnIsThis.equals(Color.WHITE)) {
            imageView.setImage(blackImage);
            whoseTurnIsThis = Color.BLACK;
        } else {
            imageView.setImage(whiteImage);
            whoseTurnIsThis = Color.WHITE;
        }
        gameController.swapColors();
        new Thread(() -> {
            if (aiController.commit()) {
                swap();
            }
        }).start();
    }

}
