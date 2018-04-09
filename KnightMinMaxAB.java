/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knightminmaxab;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Zachary
 */
public class KnightMinMaxAB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        minMaxAB tester = new minMaxAB();
        ValueStructure turn_result = tester.start(Board.getStartBoard(),0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        Board current_board = turn_result.getPath().get(0).cloneBoard();
        current_board.printBoard();
        String[] userMove;
        //**************Main Game Loop*********************//
        while(!current_board.getTerminal()){    
            do{
                userMove = getUserMove();
            }
            while(!current_board.isValidMove(userMove[0], userMove[1]));
                
//            current_board.moveWhite(current_board.mapValue(userMove[0]), current_board.mapValue(userMove[1]));
            current_board.printBoard();              
            turn_result = tester.start(current_board, 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
        }
        //*****************End Main Game Loop*******************//
        
    }
    public static String[] getUserMove(){
        Scanner s = new Scanner(System.in);
        System.out.print("Which piece would you like to move?: ");
        Pattern letterNumber = Pattern.compile("[A-Z]\\d");
        String piece = s.next(letterNumber);
        System.out.println();
        System.out.print("Where would you like to move it?: ");
        String newLoc = s.next(letterNumber);
        System.out.println();
        return new String[]{piece,newLoc};
    }
    
}
