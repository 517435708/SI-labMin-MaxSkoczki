/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.pl.pp.SkoczkiLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Konrad
 */
public class GameHandler implements GameHandlerInterface{
    Enums.Color whoseTurn;
    Enums.Color lastJumpedColor;
    Board board;
    
    public GameHandler(){
        whoseTurn = Enums.Color.WHITE;
        lastJumpedColor = Enums.Color.NONE;
        board = new Board();
    }

    @Override
    public void handleTurn(List<Board> history) {
        while(true){
            printBoard();
            System.out.println("Now is "+ whoseTurn.toString()+ " turn");
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Type command: ");
            String command = keyboard.next();

            if(command.charAt(2) == '?'){
                String p1 = String.valueOf(command.charAt(0)) + String.valueOf(command.charAt(1));
                printMovesForPawn(Enums.Position.valueOf(p1));
            }
            if(command.charAt(2) == '>'){
                String p1 = String.valueOf(command.charAt(0)) + String.valueOf(command.charAt(1));
                String p2 = String.valueOf(command.charAt(3)) + String.valueOf(command.charAt(4));
                if(board.isEmptyArea(Enums.Position.valueOf(p2))){
                    move(Enums.Position.valueOf(p1),Enums.Position.valueOf(p2));
                    if(!board.isEmptyArea(Enums.Position.valueOf(p2))){
                        if(board.isEmptyArea(Enums.Position.valueOf(p1)))
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void printBoard() {
        for(int i = 7; i >= 0; i--){
            System.out.println();
            System.out.print((i + 1) + " | ");
            for(int j = 0; j < 8 ; j++){
                boolean drawEmpty = true;
                for(Pawn p : board.pawns){
                    if(p.getPosition().get(0) == j && p.getPosition().get(1) == i){
                        if(p.getColor() == Enums.Color.WHITE){
                            System.out.print("W ");
                        }
                        else{
                            System.out.print("B ");
                        }
                        drawEmpty = false;
                    }
                }
                if(drawEmpty) System.out.print("O ");
            }
        }
        System.out.println("\n   ----------------\n    A B C D E F G H");
    }

    @Override
    public void undoMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move(Enums.Position p1, Enums.Position p2) {
        try{
            if(board.returnColorOfPosition(p1) == whoseTurn){
                lastJumpedColor = board.movePawn(p1, p2, lastJumpedColor);
                
            }
            else
                throw new IllegalArgumentException("User try to move not his own pawn");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void surrender() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        List<Board> history = new ArrayList<Board>();
        while(true){
            history.add(board);
            handleTurn(history);
            if(board.didBlackWin()){
                System.out.println("***BLACK WON***");
                break;
            }
            if(board.didWhiteWin()){
                System.out.println("***WHITE WON***");
                break;
            }
            if(lastJumpedColor == whoseTurn || lastJumpedColor == Enums.Color.NONE){
                history = new ArrayList<Board>();
                lastJumpedColor = Enums.Color.NONE;
                if(whoseTurn == Enums.Color.BLACK)
                    whoseTurn = Enums.Color.WHITE;
                else
                    whoseTurn = Enums.Color.BLACK;
            }
        }
    }

    @Override
    public void printMovesForPawn(Enums.Position p) {
        int index = -1;
        for(int i = 0 ;i< board.pawns.size();i++){
            if(board.pawns.get(i).getPosition().equals(p.getValues()))
            {
                index = i;
            }
        }
        if(index == -1){
            System.out.println("No moves");
        }
        else{
            List<MovePair> mpl = board.returnPossibleMovesForPawn(board.pawns.get(index), lastJumpedColor);
            if(mpl.size() > 0){
                System.out.println("Possible positions:");
                for(MovePair mp : mpl){
                    System.out.println(Enums.Position.intToPosition(mp.targetPosition.get(0),mp.targetPosition.get(1)).toString());
                }
            }
            else{
                System.out.println("No moves");
            }
        }
    }
}
