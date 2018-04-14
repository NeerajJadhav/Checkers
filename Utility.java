
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Zachary
 */
public class Utility {

    private static final int PAWN_VALUE = 200;
    private static final int FORWARD_PAWN_VALUE = 75;
    private static final int KING_VALUE = 500;
    private static final int NEAR_KING_VALUE = 75;
    private static final int JUMP_PAWN = 25;
    private static final int JUMP_KING = 50;
    private static final int KING_DEFENSE = 35;

    public static int getScore(Board b, minMaxAB.Player p) {
        float bValue = 0;//new Random().nextInt(9999);
        float wValue = 0;//new Random().nextInt(9999);
        ArrayList<Integer> wPieces = b.getWhite();
        ArrayList<Integer> wK_Pieces = b.getkWhite();
        ArrayList<Integer> bPieces = b.getBlack();
        ArrayList<Integer> bK_Pieces = b.getkBlack();
        int n = b.getDIMENSION();

        bValue += b.getBlack().size() * PAWN_VALUE;
        bValue += b.getkBlack().size() * KING_VALUE;
        wValue += b.getWhite().size() * PAWN_VALUE;
        wValue += b.getkWhite().size() * KING_VALUE;

        //Evaluate jumps for Black pieces
        for (Integer i : bPieces) {
            if (i > 40) {
                if (i > 48) {
                    bValue += NEAR_KING_VALUE;
                } else {
                    bValue += FORWARD_PAWN_VALUE;
                }
            }
            if (b.getTopBorder().contains(i)) {
                bValue += KING_DEFENSE;
            }
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if (wK_Pieces.contains(i + (n - 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if (wK_Pieces.contains(i + (n + 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
        }
        for (Integer i : bK_Pieces) {
            if (b.getTopBorder().contains(i)) {
                bValue += KING_DEFENSE;
            }
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if (wK_Pieces.contains(i + (n - 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if (wK_Pieces.contains(i + (n + 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }

            if (b.canJumpRearLeft(i)) { // check left rear jump
                if (wK_Pieces.contains(i - (n + 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if (wK_Pieces.contains(i - (n - 1))) {
                    bValue += JUMP_KING;
                } else {
                    bValue += JUMP_PAWN;
                }
            }
        }

        //Evaluate jumps for White pieces
        for (Integer i : wPieces) {
            if (i < 25) {
                if (i < 17) {
                    wValue += NEAR_KING_VALUE;
                } else {
                    wValue += FORWARD_PAWN_VALUE;
                }
            }
            if (b.getBottomBorder().contains(i)) {
                wValue += KING_DEFENSE;
            }
            if (b.canJumpRearLeft(i)) { // check left rear jump
                if (bK_Pieces.contains(i - (n + 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if (bK_Pieces.contains(i - (n - 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
        }
        //Evaluate jumps for KING White pieces
        for (Integer i : wK_Pieces) {
            if (b.getBottomBorder().contains(i)) {
                wValue += KING_DEFENSE;
            }
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if (bK_Pieces.contains(i + (n - 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if (bK_Pieces.contains(i + (n + 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }

            if (b.canJumpRearLeft(i)) { // check left rear jump
                if (bK_Pieces.contains(i - (n + 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if (bK_Pieces.contains(i - (n - 1))) {
                    wValue += JUMP_KING;
                } else {
                    wValue += JUMP_PAWN;
                }
            }
        }

        if (p == minMaxAB.Player.max) {
            return (int) (bValue - wValue);
        } else if (b.getTerminal() && p == minMaxAB.Player.min) {
            return (-1) * (int) (bValue - wValue);
        }
        return (int) (wValue - bValue);
//          return (int) (bValue-wValue);
    }

    public static int basicGetScore(Board b, minMaxAB.Player p) {

        int bValue = 0;//new Random().nextInt(9999);
        int wValue = 0;//new Random().nextInt(9999);

        ArrayList<Integer> wPieces = b.getWhite();
        ArrayList<Integer> wK_Pieces = b.getkWhite();
        ArrayList<Integer> bPieces = b.getBlack();
        ArrayList<Integer> bK_Pieces = b.getkBlack();
        int n = b.getDIMENSION();

        bValue += b.getBlack().size() * 100;
        bValue += b.getkBlack().size() * 200;
        wValue += b.getWhite().size() * 100;
        wValue += b.getkWhite().size() * 200;

        //Evaluate jumps for Black pieces
        for (Integer i : bPieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpForwardRight(i)) // check left foward jump
            {
                bValue += 50;
            }
        }
        //Evaluate black king pieces
        for (Integer i : bK_Pieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpRearLeft(i) || b.canJumpForwardRight(i) || b.canJumpRearRight(i)) { // check left foward jump
                bValue += 50;
            }
            //Evaluate jumps for White pieces
            if (b.canJumpRearLeft(i) || b.canJumpRearRight(i)) { // check left rear jump
                wValue += 50;

            }
        }
        //Evaluate jumps for white pieces
        for (Integer i : wPieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpForwardRight(i)) // check left foward jump
            {
                bValue += 50;
            }
        }
        //Evaluate jumps for KING White pieces
        for (Integer i : wK_Pieces) {
            if (b.canJumpForwardLeft(i) || b.canJumpForwardRight(i) || b.canJumpRearLeft(i) || b.canJumpRearRight(i)) { // check left foward jump
                wValue += 50;
            }
        }

        if (p == minMaxAB.Player.max) {
            return (bValue - wValue);
        } else if (b.getTerminal() && p == minMaxAB.Player.min) {
            return (-1) * (bValue - wValue);
        }
        return (wValue - bValue);
    }

    /**
     * Generates legal moves for the left side for a given piece and adds it to
     * the array list of children
     *
     * @param current Type: Board - Current board to be analyzed
     * @param children Type: Board ArrayList - Generates move and adds the new
     * move to this list
     * @param isBlack Type: Boolean - Is the piece designated in @param piece a
     * black or a white piece
     * @param isKing Type: Boolean - Is the piece designated in @param piece a
     * king or a pawn
     * @param piece Type : int - Piece number whose moves are to be analyzed.
     */
    private static void makeMoveForLeft(Board current, ArrayList<Board> children, boolean isBlack, boolean isKing, int piece) {
        Board child;
        int n = current.getDIMENSION();
        if (isBlack) {
            if (current.canMoveForwardLeft(piece)) { //check forward left for empty
                child = current.cloneBoard();
                child.moveBlack(piece, piece + (n - 1)); //move black piece to empty square
                children.add(child);
            } else if (current.canJumpForwardLeft(piece)) { //Jump the white piece
                child = current.cloneBoard();
                child.moveBlack(piece, piece + (2 * (n - 1))); //Move Black Piece
                child.moveWhite(piece + (n - 1), 0); //Delete Jumped White piece
                children.add(child);
            }
            if (isKing) {
                if (current.canMoveRearLeft(piece)) {//CHECK LEFT REAR FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(piece, piece - (n + 1)); //move black token to empty spot
                    children.add(child);
                } else if (current.canJumpRearLeft(piece)) {
                    child = current.cloneBoard();
                    child.moveBlack(piece, piece - (2 * (n + 1)));
                    child.moveWhite(piece - (n + 1), 0);
                    children.add(child);
                }
            }
        } else if (!isBlack) {

            if (current.canMoveRearLeft(piece)) { //check left rear for empty
                child = current.cloneBoard();
                child.moveWhite(piece, piece - (n + 1));
                children.add(child);
            } else if ( //CHECK LEFT REAR FOR JUMP
                    current.canJumpRearLeft(piece)) {
                child = current.cloneBoard();
                child.moveWhite(piece, piece - (2 * (n + 1))); //move white token
                child.moveBlack(piece - (n + 1), 0);  //remove jumped black token
                children.add(child);
            }

            if (isKing) {
                if (current.canMoveForwardLeft(piece)) {//check forward left for empty
                    child = current.cloneBoard();
                    child.moveWhite(piece, piece + (n - 1)); //move left forward
                    children.add(child);
                } else if (//check left forward for jump
                        current.canJumpForwardLeft(piece)) {
                    child = current.cloneBoard();
                    child.moveWhite(piece, piece + (2 * (n - 1))); //move white king
                    child.moveBlack(piece + (n - 1), 0); //remove jumped black token
                    children.add(child);
                }
            }
        }
    }

    /**
     * Generates legal moves for the right side for a given piece and adds it to
     * the array list of children
     *
     * @param current Type: Board - Current board to be analyzed
     * @param children Type: Board ArrayList - Generates move and adds the new
     * move to this list
     * @param isBlack Type: Boolean - Is the piece designated in @param piece a
     * black or a white piece
     * @param isKing Type: Boolean - Is the piece designated in @param piece a
     * king or a pawn
     * @param piece Type : int - Piece number whose moves are to be analyzed.
     */
    private static void makeMoveForRight(Board current, ArrayList<Board> children, boolean isBlack, boolean isKing, int piece) {
        Board child;
        int n = current.getDIMENSION();
        if (isBlack) {
            if (current.canMoveForwardRight(piece)) { //check forward right for empty
                child = current.cloneBoard();
                child.moveBlack(piece, piece + (n + 1)); //move token to forward right
                children.add(child);
            } else if (current.canJumpForwardRight(piece)) { //Jump White Piece
                child = current.cloneBoard();
                child.moveBlack(piece, piece + (2 * (n + 1))); //move black piece
                child.moveWhite(piece + (n + 1), 0); //remove jumped white token
                children.add(child);
            }
            if (isKing) {
                if (current.canMoveRearRight(piece)) {//CHECK RIGHT REAR FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(piece, piece - (n - 1)); //move black king right rear to empty space
                    children.add(child);

                } else if (//CHECK RIGHT REAR FOR JUMP
                        current.canJumpRearRight(piece)) {
                    child = current.cloneBoard();
                    child.moveBlack(piece, piece - (2 * (n - 1)));
                    child.moveWhite(piece - (n - 1), 0);
                    children.add(child);
                }
            }
        } else if (!isBlack) {
            if (current.canMoveRearRight(piece)) { //check rear right for empty
                child = current.cloneBoard();
                child.moveWhite(piece, piece - (n - 1)); //move white token right rear
                children.add(child);

            } else if ( //check for right rear jump
                    current.canJumpRearRight(piece)) {
                child = current.cloneBoard();
                child.moveWhite(piece, piece - (2 * (n - 1))); //move white piece
                child.moveBlack(piece - (n - 1), 0); //delete black piece
                children.add(child);
            }

            if (isKing) {
                if (current.canMoveForwardRight(piece)) { //check front right for empty
                    child = current.cloneBoard();
                    child.moveWhite(piece, piece + (n + 1)); //move white token right front
                    children.add(child);
                } else if ( //check for right front jump
                        current.canJumpForwardRight(piece)) {
                    child = current.cloneBoard();
                    child.moveWhite(piece, piece + (2 * (n + 1))); //move white piece
                    child.moveBlack(piece + (n + 1), 0); //delete black piece
                    children.add(child);
                }
            }
        }
    }

    public static ArrayList<Board> moveGen(Board current, minMaxAB.Player player) {
        int n = current.getDIMENSION();
        ArrayList<Integer> bPieces = current.getBlack();
        ArrayList<Integer> wPieces = current.getWhite();
        ArrayList<Integer> bkPieces = current.getkBlack();
        ArrayList<Integer> wkPieces = current.getkWhite();
        ArrayList<Board> children = new ArrayList<>();
        Board child;
        int countWPieces = wPieces.size() + wkPieces.size();
        ///**************************BLACK*********************************///
        if (player == minMaxAB.Player.max && countWPieces >= 1) {
            //**********************BLACK PIECE MOVE GENERATION**********************//
            for (int p : bPieces) {
                makeMoveForLeft(current, children, true, false, p);
                makeMoveForRight(current, children, true, false, p);
            }
            //********************END BLACK PIECE MOVE GENERATION*****************//

            //**********************BLACK KING MOVE GENERATION**********************//
            for (int k : bkPieces) {

                makeMoveForLeft(current, children, true, true, k);

                makeMoveForRight(current, children, true, true, k);
            }
            //********************END BLACK KING MOVE GENERATION*****************//
        } ///************************END BLACK*******************************///
        ///**************************WHITE*********************************///		
        else {
            //**********************WHITE PIECE MOVE GENERATION**********************//
            for (int p : wPieces) {
                makeMoveForLeft(current, children, false, false, p);
                makeMoveForRight(current, children, false, false, p);
            }
            //********************END WHITE PIECE MOVE GENERATION*****************//
            //**********************WHITE KING MOVE GENERATION**********************//
            for (int k : wkPieces) {

                makeMoveForLeft(current, children, false, true, k);
                makeMoveForRight(current, children, false, true, k);

                //********************END WHITE KING MOVE GENERATION*****************//
            }
            ///************************END WHITE*******************************///
        }
        return children; //RETURN ALL GENERATED CHILDREN
    }
}
