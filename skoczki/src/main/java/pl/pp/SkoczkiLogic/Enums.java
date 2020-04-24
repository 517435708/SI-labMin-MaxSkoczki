
package main.java.pl.pp.SkoczkiLogic;

import java.util.Arrays;
import java.util.List;


public class Enums {
    public enum Color {
 		WHITE, BLACK, NONE;
 	}
    public enum Position{

        A1(0, 0),
        A2(0, 1),
        A3(0, 2),
        A4(0, 3),
        A5(0, 4),
        A6(0, 5),
        A7(0, 6),
        A8(0, 7),
        B1(1, 0),
        B2(1, 1),
        B3(1, 2),
        B4(1, 3),
        B5(1, 4),
        B6(1, 5),
        B7(1, 6),
        B8(1, 7),
        C1(2, 0),
        C2(2, 1),
        C3(2, 2),
        C4(2, 3),
        C5(2, 4),
        C6(2, 5),
        C7(2, 6),
        C8(2, 7),
        D1(3, 0),
        D2(3, 1),
        D3(3, 2),
        D4(3, 3),
        D5(3, 4),
        D6(3, 5),
        D7(3, 6),
        D8(3, 7),
        E1(4, 0),
        E2(4, 1),
        E3(4, 2),
        E4(4, 3),
        E5(4, 4),
        E6(4, 5),
        E7(4, 6),
        E8(4, 7),
        F1(5, 0),
        F2(5, 1),
        F3(5, 2),
        F4(5, 3),
        F5(5, 4),
        F6(5, 5),
        F7(5, 6),
        F8(5, 7),
        G1(6, 0),
        G2(6, 1),
        G3(6, 2),
        G4(6, 3),
        G5(6, 4),
        G6(6, 5),
        G7(6, 6),
        G8(6, 7),
        H1(7, 0),
        H2(7, 1),
        H3(7, 2),
        H4(7, 3),
        H5(7, 4),
        H6(7, 5),
        H7(7, 6),
        H8(7, 7);
        private final List<Integer> values;

        Position(Integer ...values) {
            this.values = Arrays.asList(values);
        }

        public List<Integer> getValues() {
            return values;
        }
        public static Position intToPosition(int x, int y){
            if(y<0 || y>7) throw new IllegalArgumentException("wrong position");
            switch(x){
                case 0:
                {
                    switch(y){
                        case 0:{
                            return Position.A1;
                        }
                        case 1:{
                            return Position.A2;
                        }
                        case 2:{
                            return Position.A3;
                        }
                        case 3:{
                            return Position.A4;
                        }
                        case 4:{
                            return Position.A5;
                        }
                        case 5:{
                            return Position.A6;
                        }
                        case 6:{
                            return Position.A7;
                        }
                        case 7:{
                            return Position.A8;
                        }
                    }
                    break;
                }
                case 1:
                {
                    switch(y){
                        case 0:{
                            return Position.B1;
                        }
                        case 1:{
                            return Position.B2;
                        }
                        case 2:{
                            return Position.B3;
                        }
                        case 3:{
                            return Position.B4;
                        }
                        case 4:{
                            return Position.B5;
                        }
                        case 5:{
                            return Position.B6;
                        }
                        case 6:{
                            return Position.B7;
                        }
                        case 7:{
                            return Position.B8;
                        }
                    }
                    break;
                }
                case 2:
                {
                    switch(y){
                        case 0:{
                            return Position.C1;
                        }
                        case 1:{
                            return Position.C2;
                        }
                        case 2:{
                            return Position.C3;
                        }
                        case 3:{
                            return Position.C4;
                        }
                        case 4:{
                            return Position.C5;
                        }
                        case 5:{
                            return Position.C6;
                        }
                        case 6:{
                            return Position.C7;
                        }
                        case 7:{
                            return Position.C8;
                        }
                    }
                    break;
                }
                case 3:
                {
                    switch(y){
                        case 0:{
                            return Position.D1;
                        }
                        case 1:{
                            return Position.D2;
                        }
                        case 2:{
                            return Position.D3;
                        }
                        case 3:{
                            return Position.D4;
                        }
                        case 4:{
                            return Position.D5;
                        }
                        case 5:{
                            return Position.D6;
                        }
                        case 6:{
                            return Position.D7;
                        }
                        case 7:{
                            return Position.D8;
                        }
                    }
                    break;
                }
                case 4:
                {
                    switch(y){
                        case 0:{
                            return Position.E1;
                        }
                        case 1:{
                            return Position.E2;
                        }
                        case 2:{
                            return Position.E3;
                        }
                        case 3:{
                            return Position.E4;
                        }
                        case 4:{
                            return Position.E5;
                        }
                        case 5:{
                            return Position.E6;
                        }
                        case 6:{
                            return Position.E7;
                        }
                        case 7:{
                            return Position.E8;
                        }
                    }
                    break;
                }
                case 5:
                {
                    switch(y){
                        case 0:{
                            return Position.F1;
                        }
                        case 1:{
                            return Position.F2;
                        }
                        case 2:{
                            return Position.F3;
                        }
                        case 3:{
                            return Position.F4;
                        }
                        case 4:{
                            return Position.F5;
                        }
                        case 5:{
                            return Position.F6;
                        }
                        case 6:{
                            return Position.F7;
                        }
                        case 7:{
                            return Position.F8;
                        }
                    }
                    break;
                }
                case 6:
                {
                    switch(y){
                        case 0:{
                            return Position.G1;
                        }
                        case 1:{
                            return Position.G2;
                        }
                        case 2:{
                            return Position.G3;
                        }
                        case 3:{
                            return Position.G4;
                        }
                        case 4:{
                            return Position.G5;
                        }
                        case 5:{
                            return Position.G6;
                        }
                        case 6:{
                            return Position.G7;
                        }
                        case 7:{
                            return Position.G8;
                        }
                    }
                    break;
                }
                case 7:
                {
                    switch(y){
                        case 0:{
                            return Position.H1;
                        }
                        case 1:{
                            return Position.H2;
                        }
                        case 2:{
                            return Position.H3;
                        }
                        case 3:{
                            return Position.H4;
                        }
                        case 4:{
                            return Position.H5;
                        }
                        case 5:{
                            return Position.H6;
                        }
                        case 6:{
                            return Position.H7;
                        }
                        case 7:{
                            return Position.H8;
                        }
                    }
                    break;
                }
                default:
                {
                    throw new IllegalArgumentException("wrong position");
                }
        }
           return null; 
        }
    }
}
