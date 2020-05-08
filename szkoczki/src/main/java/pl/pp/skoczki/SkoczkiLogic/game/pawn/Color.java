package pl.pp.skoczki.SkoczkiLogic.game.pawn;


public enum Color {
    WHITE,
    BLACK,
    NONE;

    public static Color swap(Color color) {
        if (color.equals(WHITE))
            return BLACK;
        return WHITE;
    }
}