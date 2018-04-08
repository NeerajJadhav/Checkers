/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knightminmaxab;

import java.util.ArrayList;
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
        //Board.getStartBoard().printBoard();
        ValueStructure result = tester.start(Board.getStartBoard(),0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);    
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.min, Integer.MAX_VALUE, Integer.MIN_VALUE);
        result = tester.start(result.getPath().get(0), 0, minMaxAB.Player.max, Integer.MAX_VALUE, Integer.MIN_VALUE);
        
        System.out.println(result.toString());
        
//        Board current = Board.getStartBoard();        
//        while(!current.getTerminal()){
//            
//        }
        
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
