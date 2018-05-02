package CheckersAI;

import java.util.Scanner;

/**
 * This is the main driver for the Checkers Program. It provides IO to user for gameplay and controls the game loops.
 */
public class Main2Checkers {

    static final int MAX_VALUE = 7000;
    static final int MIN_VALUE = -8000;
    static int totalBoards = 0;
    static int totalPrune = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        while (true) {
            int action = mainMenu();
            switch (action) {
                case 1:
                    computerVsComputer1();
                    break;
                case 2:
                    computerVsComputer2();
                    break;
                case 3:
                    computerVsComputer3();
                    break;
                case 4:
                    computerVsComputer4();
                    break;
                case 5:
                    System.out.println("Thank you for playing!");
                    System.exit(0);
            }
        }

    }

    private static int mainMenu() {
        Scanner s = new Scanner(System.in);
        int option = 0;
        System.out.println("Algorithm Comparison!\n\n");
        do {
            System.out.println("\t\t  Main Menu");
            System.out.println("\t\t--------------");
            System.out.println("\t1.minMaxAB getScore vs minMaxAB getScore2");
            System.out.println("\t2.ABSearch getScore vs ABSearch getScore2");
            System.out.println("\t3.minMaxAB getScore vs ABSearch getScore2");
            System.out.println("\t4.minMaxAB getScore2 vs ABSearch getScore");
            System.out.println("\t5.Exit");
            System.out.print("Option: ");
            option = s.nextInt();
        } while (option < 0 || option > 51
                );
        return option;
    }

    //THIS FUNCTION PLAYS 2 MIN-MAX-AB ALGORITHMS AGAINST EACH OTHER
    // Both utilize the primary evaluation function

    /**
     *
     */
    public static void computerVsComputer1() {
        minMaxAB mmABGame = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            minMaxAB.setEval(0);
            turn_result = mmABGame.start(current_board, 0, Board.Player.black, MAX_VALUE, MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            minMaxAB.setEval(1);
            turn_result = mmABGame.start(current_board, 0, Board.Player.white, MAX_VALUE, MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

    //THIS FUNCTION PLAYS TWO ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
    //  Each uses a different evaluation function, black with primary

    /**
     *
     */
    public static void computerVsComputer2() {
        AlphaBetaSearch absearch = new AlphaBetaSearch();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        int userConfirm;
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            absearch.setScoreOption(0);
            turn_result = absearch.start(current_board, Board.Player.black);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal()) {
                absearch.setScoreOption(1);
                turn_result = absearch.start(current_board, Board.Player.white);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalPrune += turn_result.pruneCount;
                totalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

    //THIS FUNCTION PLAYS MIN-MAX AB VS ALPHA-BETA SEARCH ALGORITHMS AGAINST EACH OTHER
    // Both use the primary evaluation function

    /**
     *
     */
    public static void computerVsComputer3() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            minMaxAB.setEval(0);
            turn_result = mmAB.start(current_board, 0, Board.Player.black, MAX_VALUE, MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal()) {
                ABS.setScoreOption(1);
                turn_result = ABS.start(current_board, Board.Player.white);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalPrune += turn_result.pruneCount;
                totalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }

    public static void computerVsComputer4() {
        AlphaBetaSearch ABS = new AlphaBetaSearch();
        minMaxAB mmAB = new minMaxAB();
        ValueStructure turn_result;
        Board current_board = Board.getStartBoard();
        //**************Main Game Loop*********************//
        while (!current_board.getTerminal()) {
            minMaxAB.setEval(1);
            turn_result = mmAB.start(current_board, 0, Board.Player.black, MAX_VALUE, MIN_VALUE);
            current_board = turn_result.getPath().get(0).cloneBoard();
            current_board.printBoard();
            totalPrune += turn_result.pruneCount;
            totalBoards += turn_result.boardsEvaluatedCount;
            System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
            System.out.println("Prunes: " + turn_result.pruneCount);
            if (!current_board.getTerminal()) {
                ABS.setScoreOption(0);
                turn_result = ABS.start(current_board, Board.Player.white);
                current_board = turn_result.getPath().get(0).cloneBoard();
                current_board.printBoard();
                totalPrune += turn_result.pruneCount;
                totalBoards += turn_result.boardsEvaluatedCount;
                System.out.println("Boards Evaluated: " + turn_result.boardsEvaluatedCount);
                System.out.println("Prunes: " + turn_result.pruneCount);
            }
            // if (getConfirm() != 1) {
            //     return;
            // }
            if (current_board.getTerminal()) {
                System.out.println(current_board.getWinner());
                System.out.println("Total Boards Evaluated: " + totalBoards);
                System.out.println("Total Prunes: " + totalPrune);
            }
        }
        //*****************End Main Game Loop*******************//
    }
}
