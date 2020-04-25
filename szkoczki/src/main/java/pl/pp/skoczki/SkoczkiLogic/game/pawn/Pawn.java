package pl.pp.skoczki.SkoczkiLogic.game.pawn;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Color;
import pl.pp.skoczki.SkoczkiLogic.game.pawn.Position;

@Getter
@Setter
@AllArgsConstructor
public
class Pawn {
    private Color color;
    private Position position;
}
