/*
 * Created by $user on 12/4/2018 at $hour24:$minute
 */

package src.com.ai;

import java.util.ArrayList;

class minMaxAB {

    private static final int MAX_DEPTH = 5;

    public enum Player {
        min, max
    }

    private static int Static(Board position, Player player) {
        //Make a call to eval function
        return evalFunc.getScore(position, player);
    }

    public ValueStructure start(Board position, int depth, Player player, int maxUse, int minPass) {
        return minMax(position, depth, player, maxUse, minPass);
    }

    private ValueStructure minMax(Board position, int depth, Player player, int useThresh, int passThresh) {
        ValueStructure currValueStructure = new ValueStructure();
        ArrayList<Board> currPath = new ArrayList<>(); //Used to temporarily store best path
        if (deepEnough(depth)) {
            currValueStructure.setValue(Static(position, player));
            //currValueStructure.addToPath(position); //should be null
            return currValueStructure;
        }

        //Generate children for this turn
        ArrayList<Board> successors = moveGen(position, player);

        //No children means terminal state reached
        if (successors.isEmpty()) {
            position.setTerminal();
            currValueStructure.setValue(Static(position, player));
            currValueStructure.addToPath(position);
            return currValueStructure;
        }

        for (Board v : successors) {
            ValueStructure resultSucc = minMax(v, (depth + 1), switchPlayer(player), ((-1) * passThresh), ((-1) * useThresh));
            int newValue = ((resultSucc.getValue() * (-1))); //new value
            if (newValue > passThresh) { //Better child board found
                passThresh = newValue;
                currPath = new ArrayList<>();
                currPath.add(v);
                currPath.addAll(resultSucc.getPath());
            }
            if (passThresh >= useThresh) { //Prune
                currValueStructure.setValue(passThresh);
                currValueStructure.addToPath(currPath);
                return currValueStructure;
            }
        }
        currValueStructure.addToPath(currPath);
        currValueStructure.setValue(passThresh);
        return currValueStructure;
    }

    private boolean deepEnough(int depth) {
        return depth > MAX_DEPTH;
    }

    private Player switchPlayer(Player currPlayer) {
        if (currPlayer == Player.max) {
            return Player.min;
        }
        return Player.max;
    }

    /**
     * Generates legal moves for the left side for a given piece and adds it to the array list of children
     *
     * @param current  Type: Board - Current board to be analyzed
     * @param children Type: Board ArrayList - Generates move and adds the new move to this list
     * @param isBlack  Type: Boolean - Is the piece designated in @param piece a black or a white piece
     * @param isKing   Type: Boolean - Is the piece designated in @param piece a king or a pawn
     * @param piece    Type : int - Piece number whose moves are to be analyzed.
     */
    private void makeMoveForLeft(Board current, ArrayList<Board> children, boolean isBlack, boolean isKing, int piece) {
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
     * Generates legal moves for the right side for a given piece and adds it to the array list of children
     *
     * @param current  Type: Board - Current board to be analyzed
     * @param children Type: Board ArrayList - Generates move and adds the new move to this list
     * @param isBlack  Type: Boolean - Is the piece designated in @param piece a black or a white piece
     * @param isKing   Type: Boolean - Is the piece designated in @param piece a king or a pawn
     * @param piece    Type : int - Piece number whose moves are to be analyzed.
     */
    private void makeMoveForRight(Board current, ArrayList<Board> children, boolean isBlack, boolean isKing, int piece) {
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

    private ArrayList<Board> moveGen(Board current, Player player) {
        int n = current.getDIMENSION();
        ArrayList<Integer> bPieces = current.getBlack();
        ArrayList<Integer> wPieces = current.getWhite();
        ArrayList<Integer> bkPieces = current.getkBlack();
        ArrayList<Integer> wkPieces = current.getkWhite();
        ArrayList<Board> children = new ArrayList<>();
        Board child;

        ///**************************BLACK*********************************///
        if (player == Player.max) {
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
