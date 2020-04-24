package main.java.pl.pp.SkoczkiLogic;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements PawnInterface{
    protected int xPosition;
    protected int yPosition;
    Enums.Color color;
    public Pawn(Enums.Color color, List<Integer> positions){
            if(positions.size() != 2)
                throw new IllegalArgumentException("Wrong size of Pawn position argument");
            if(positions.get(0) < 0 || positions.get(1) < 0 || positions.get(0) > 7 || positions.get(1) > 7)
                throw new IllegalArgumentException("Position argument for Pawn miss the Board range");
            else{
                this.xPosition = positions.get(0);
                this.yPosition = positions.get(1);
                this.color = color;
            }
    }
    

    @Override
    public void changePosition(List<Integer> position) {
        if(position.size() != 2)
                throw new IllegalArgumentException("Wrong size of Pawn position argument");
            if(position.get(0) < 0 || position.get(1) < 0 || position.get(0) > 7 || position.get(1) > 7)
                throw new IllegalArgumentException("Position argument for Pawn miss the Board range");
            if(position.get(0) != this.xPosition && position.get(1) != this.yPosition)
                throw new IllegalArgumentException("Pawn is trying to move in two axis");
            else{
                this.xPosition = position.get(0);
                this.yPosition = position.get(1);
            }
    }

    @Override
    public List<Integer> getPosition() {
        List<Integer> temp = new ArrayList<Integer>();
        temp.add(this.xPosition);
        temp.add(this.yPosition);
        return temp;
    }

    @Override
    public Enums.Color getColor() {
        return this.color;
    }
    
}
