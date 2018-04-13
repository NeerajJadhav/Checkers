/*
 * Created by $user on 12/4/2018 at $hour24:$minute
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.com.ai;

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
        while (true) {
            int action = mainMenu();
            switch (action) {
                case 1:
                    playerVsComputer();
                    break;
                case 2:
                    computerVsComputer();
                    break;
                case 3:
                    System.out.println("Thank you for playing!");
                    System.exit(0);
            }
        }

    }

    public static String[] getUserMove() {
        Scanner s = new Scanner(System.in);
        System.out.print("Which piece would you like to move?: ");
        Pattern letterNumber = Pattern.compile("[A-Z]\\d");
        String piece = s.next(letterNumber);
        System.out.println();
        System.out.print("Where would you like to move it?: ");
        String newLoc = s.next(letterNumber);
        System.out.println();
        return new String[]{piece, newLoc};
    }

    public static void playerVsComputer() {
        //"H1", "H3", "H5", "E6", "E8" B1K
        String[] wp = new String[]{"G2", "H1"};
        String[] wk = new String[]{};
        String[] bp = new String[]{"A6", "A8", "C6", "C8", "F1", "E2", "F3", "F5"};
        String[] bk = new String[]{"H3"};
        minMaxAB mmABGame = new minMaxAB();
//        ValueStructure turn_result = mmABGame.start(Board.getStartBoard(), 0, minMaxAB.Player.max, 12000, (-12000));
        ValueStructure turn_result = mmABGame.start(Board.getDebugBoard(wp, bp, wk, bk), 0, minMaxAB.Player.max, 12000, (-12000));
        Board current_board = turn_result.getPath().get(0).cloneBoard();
        current_board.printBoard();
        String[] userMove;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            do {
                userMove = getUserMove();
            } while (!current_board.isValidMove(userMove[0], userMove[1]));
//          current_board.moveWhite(current_board.mapValue(userMove[0]), current_board.mapValue(userMove[1]));
            current_board.printBoard();
            turn_result = mmABGame.start(current_board, 0, minMaxAB.Player.max, 20000, (-20000));
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
        }
        if (current_board.getTerminal()) {
            System.out.println(current_board.getWinner());
        }
        //*****************End Main Game Loop*******************//
    }

    public static int mainMenu() {
        Scanner s = new Scanner(System.in);
        int option = 0;
        System.out.println("Let's Play Checkers!\n\n");
        do {
            System.out.println("\t\t  Main Menu");
            System.out.println("\t\t--------------");
            System.out.println("\t1.Play against the computer!");
            System.out.println("\t2.Watch the computers play!");
            System.out.println("\t3.Exit");
            System.out.print("Option: ");
            option = s.nextInt();
        } while (option != 1 && option != 2 && option != 3);
        return option;
    }

    public static void computerVsComputer() {
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            turn_result = mmABGame.start(current_board, 0, minMaxAB.Player.max, 12000, (-12000));
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            turn_result = mmABGame.start(current_board, 0, minMaxAB.Player.min, 20000, (-20000));
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            if (getConfirm() != 1) {
                return;
            }
        }
        //*****************End Main Game Loop*******************//
    }

    public static int getConfirm() {
        Scanner s = new Scanner(System.in);
        int option = 0;
        do {
            System.out.print("\nPress 1 to continue watching \n Or press 2 to exit: ");
            option = s.nextInt();
        } while (option != 1 && option != 2);

        return option;
    }
}
