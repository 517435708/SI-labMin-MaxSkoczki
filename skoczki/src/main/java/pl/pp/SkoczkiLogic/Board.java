
package main.java.pl.pp.SkoczkiLogic;

import java.util.ArrayList;
import java.util.List;

public final class Board implements BoardInterface{

    List<Pawn> pawns = new ArrayList<Pawn>();
    
    public Board(){
        setStartPositions();
    }
    @Override
    public void setStartPositions() {
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.A1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.B1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.C1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.D1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.E1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.F1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.G1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.H1.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.A2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.B2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.C2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.D2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.E2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.F2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.G2.getValues()));
        pawns.add(new Pawn(Enums.Color.WHITE, Enums.Position.H2.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.A8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.B8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.C8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.D8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.E8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.F8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.G8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.H8.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.A7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.B7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.C7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.D7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.E7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.F7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.G7.getValues()));
        pawns.add(new Pawn(Enums.Color.BLACK, Enums.Position.H7.getValues()));
    }

    @Override
    public boolean didWhiteWin() {
        for(Pawn pawn : pawns){
            if(pawn.getColor() == Enums.Color.WHITE)
                if(pawn.getPosition().get(1) < 6)
                    return false;
        }
        return true;
    }

    @Override
    public boolean didBlackWin() {
        for(Pawn pawn : pawns){
            if(pawn.getColor() == Enums.Color.BLACK)
                if(pawn.getPosition().get(1) > 1)
                    return false;
        }
        return true;
    }

    @Override
    public Enums.Color movePawn(Enums.Position pawn, Enums.Position position, Enums.Color lastJumpedColor) {
        int x = -1;
        for(int i = 0; i<this.pawns.size();i++){
            if(pawns.get(i).getPosition().equals(pawn.getValues()))
                x = i;
        }
        if(x == -1)
            throw new IllegalArgumentException("Start position don't match any pawn");
        for(MovePair p : this.returnPossibleMovesForPawn(this.pawns.get(x), lastJumpedColor)){
            System.out.println(position.toString());
                if(Enums.Position.intToPosition(p.targetPosition.get(0), p.targetPosition.get(1)) == position){
                    this.pawns.get(x).changePosition(position.getValues());
                    return p.jumpedColor;
                }
        }
        throw new IllegalArgumentException("Pawn can't reach selected position");
    }

    @Override
    public List<MovePair> returnPossibleMovesForPawn(Pawn pawn, Enums.Color lastJumpedColor) {
        List<MovePair> moves = new ArrayList<MovePair>();
        
        // X- axis
        int range = 1;
        boolean match = false;
        boolean endAxis = false;
        while(true){
            if(endAxis) break;
            if(pawn.getPosition().get(0) >= range){
                for(Pawn p : this.pawns){
                        if(p.getPosition().get(0) == pawn.getPosition().get(0) - range && p.getPosition().get(1) == pawn.getPosition().get(1)){
                            if(p.getColor() == lastJumpedColor || lastJumpedColor == Enums.Color.NONE){
                                match = true;
                                lastJumpedColor = p.getColor();
                        }
                            else{
                                endAxis = true;
                            }
                    }
                }
                if(match){
                    range++;
                }
                else{
                    List<Integer> tempPos = new ArrayList<Integer>();
                    tempPos.add(pawn.getPosition().get(0) - range);
                    tempPos.add(pawn.getPosition().get(1));
                    moves.add(new MovePair(tempPos, lastJumpedColor));
                    endAxis = true;
                }
            }
            else break;
            match = false;
        }
        
        // X+ axis
        range = 1;
        match = false;
        endAxis = false;
        while(true){
            if(endAxis) break;
            if(pawn.getPosition().get(0) + range > 8){
                for(Pawn p : this.pawns){
                        if(p.getPosition().get(0) == pawn.getPosition().get(0) + range && p.getPosition().get(1) == pawn.getPosition().get(1)){
                            if(p.getColor() == lastJumpedColor || lastJumpedColor == Enums.Color.NONE){
                                match = true;
                                lastJumpedColor = p.getColor();
                        }
                            else{
                                endAxis = true;
                            }
                    }
                }
                if(match){
                    range++;
                }
                else{
                    List<Integer> tempPos = new ArrayList<Integer>();
                    tempPos.add(pawn.getPosition().get(0) + range);
                    tempPos.add(pawn.getPosition().get(1));
                    moves.add(new MovePair(tempPos, lastJumpedColor));
                    endAxis = true;
                }
            }
            else break;
            match = false;
        }
        
        // Y- axis
        range = 1;
        match = false;
        endAxis = false;
        while(true){
            if(endAxis) break;
            if(pawn.getPosition().get(1) >= range){
                for(Pawn p : this.pawns){
                        if(p.getPosition().get(0) == pawn.getPosition().get(0) && p.getPosition().get(1) == pawn.getPosition().get(1) - range){
                            if(p.getColor() == lastJumpedColor || lastJumpedColor == Enums.Color.NONE){
                                match = true;
                                lastJumpedColor = p.getColor();
                        }
                            else{
                                endAxis = true;
                            }
                    }
                }
                if(match){
                    range++;
                }
                else{
                    List<Integer> tempPos = new ArrayList<Integer>();
                    tempPos.add(pawn.getPosition().get(0));
                    tempPos.add(pawn.getPosition().get(1) - range);
                    moves.add(new MovePair(tempPos, lastJumpedColor));
                    endAxis = true;
                }
            }
            else break;
            match = false;
        }

        // Y+ axis
        range = 1;
        match = false;
        endAxis = false;
        while(true){
            if(endAxis) break;
            if(pawn.getPosition().get(1) + range < 8){
                for(Pawn p : this.pawns){
                        if(p.getPosition().get(0) == pawn.getPosition().get(0) && p.getPosition().get(1) == pawn.getPosition().get(1) + range){
                            if(p.getColor() == lastJumpedColor || lastJumpedColor == Enums.Color.NONE){
                                match = true;
                                lastJumpedColor = p.getColor();
                        }
                            else{
                                endAxis = true;
                            }
                    }
                }
                if(match){
                    range++;
                }
                else{
                    List<Integer> tempPos = new ArrayList<Integer>();
                    tempPos.add(pawn.getPosition().get(0));
                    tempPos.add(pawn.getPosition().get(1) + range);
                    moves.add(new MovePair(tempPos, lastJumpedColor));
                    endAxis = true;
                }
            }
            else break;
            match = false;
        }
        
        return moves;
    }

    @Override
    public Enums.Color returnColorOfPosition(Enums.Position p) {
        for(Pawn pawn : pawns){
            if(pawn.getPosition().equals(p.getValues())){
                return pawn.getColor();
            }
        }
        return Enums.Color.NONE;
    }

    @Override
    public boolean isEmptyArea(Enums.Position p) {
        for(Pawn pawn : pawns){
            if(pawn.getPosition().equals(p.getValues()))
                return false;
        }
        return true;
    }
    
}
