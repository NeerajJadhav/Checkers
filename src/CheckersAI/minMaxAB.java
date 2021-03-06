/*
 * Created by $user on 22/4/2018 at $hour24:$minute
 */

package CheckersAI;

import java.util.ArrayList;

/**
 *
 * Implements the Min-Max - AB Algorithm by Rich & Knight
 */
public class minMaxAB {

    private static final int MAX_DEPTH = 6;
    private static int eval = 0;
    /**
     * Starter function for calling minMax algorithm.
     *
     * @param position Current board to be analyzes
     * @param depth current depth of the tree
     * @param player denotes which player has the turn
     * @param maxUse maximum threshold value available
     * @param minPass minimum threshold value available.
     * @return
     */
    public ValueStructure start(Board position, int depth, Board.Player player, int maxUse, int minPass) {
        return minMax(position, depth, player, maxUse, minPass);
    }

    public static void setEval(int eval) {
        if (eval == 0 || eval == 1)
            minMaxAB.eval = eval;
    }
    /**
     * Algorithm MinMax with Pruning: Finds the best possible move to make generating a tree and taking path that has
     * the highest score. Pruning is done to the nodes that do not satisfy use threshold values.
     *
     * @param position
     * @param depth
     * @param player
     * @param useThresh
     * @param passThresh
     * @return
     */
    private ValueStructure minMax(Board position, int depth, Board.Player player, int useThresh, int passThresh) {
        ValueStructure currValueStructure = new ValueStructure();
        ArrayList<Board> currPath = new ArrayList<>(); //Used to temporarily store best path
        if (deepEnough(depth)) {
            currValueStructure.boardsEvaluatedCount++;
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
            currPath = new ArrayList<>();
            currPath.add(position);
            currValueStructure.addToPath(currPath);
           // currValueStructure.addToPath(position);
            return currValueStructure;
        }

        for (Board v : successors) {
            ValueStructure resultSucc = minMax(v, (depth + 1), switchPlayer(player), ((-1) * passThresh), ((-1) * useThresh));
            currValueStructure.boardsEvaluatedCount += resultSucc.boardsEvaluatedCount;
            currValueStructure.pruneCount += resultSucc.pruneCount;
            int newValue = ((resultSucc.getValue() * (-1))); //new value
            if (newValue > passThresh) { //Better child board found
                passThresh = newValue;
                currPath = new ArrayList<>();
                currPath.add(v);
                currPath.addAll(resultSucc.getPath());
            }
            if (passThresh >= useThresh) { //Prune
                currValueStructure.pruneCount++;
                currValueStructure.setValue(passThresh);
                currValueStructure.addToPath(currPath);
                return currValueStructure;
            }
        }
        currValueStructure.addToPath(currPath);
        currValueStructure.setValue(passThresh);
        return currValueStructure;
    }

    /**
     * Generates a score using getScore() evaluation of board.
     * @param position - Board - board to be evaluated
     * @param player - Board.Player - current player of the board
     * @return int - Get score for the usefulness of the board
     */
    private static int Static(Board position, Board.Player player) {
        //Make a call to eval function
        if (eval == 0) {
            return evalFunc.getScore(position, player);
        } else {
            return evalFunc.getScore2(position, player);
        }
    }

    /**
     * Checks if defined depth is reached.
     *
     * @param depth current depth
     * @return Boolean true if current depth is greater than max depth.
     */
    private boolean deepEnough(int depth) {
        return depth > MAX_DEPTH;
    }

    public ValueStructure start(Board position, int depth, Board.Player player, int maxUse, int minPass, int eval) {
        return minMax(position, depth, player, maxUse, minPass);
    }

    /**
     * Return the opponent of the given player
     *
     * @param currPlayer Board.Player - Current player
     * @return Board.Player - Opponent player.
     */
    private Board.Player switchPlayer(Board.Player currPlayer) {
        if (currPlayer == Board.Player.black) {
            return Board.Player.white;
        }
        return Board.Player.black;
    }

    /**
     * Generates a list of child boards containing moves for every piece of a given board and player. All moves are made
     * in accordance with standard rules of checkers.
     *
     * @param current - Board - Given board to be analyzed
     * @param player - Board.Player - Player whose current turn is to play on the board
     * @return ArrayList Board - List of boards
     */
    private ArrayList<Board> moveGen(Board current, Board.Player player) {
        int n = current.getDIMENSION();
        ArrayList<Integer> bPieces = current.getBlack();
        ArrayList<Integer> wPieces = current.getWhite();
        ArrayList<Integer> bkPieces = current.getkBlack();
        ArrayList<Integer> wkPieces = current.getkWhite();
        ArrayList<Board> children = new ArrayList<>();
        Board child;

        ///**************************BLACK*********************************///
        if (player == Board.Player.black) {
            //**********************BLACK PIECE MOVE GENERATION**********************//
            for (int p : bPieces) {
                /**
                 * ***********************Left Side Empty or Jump*************
                 */
                if (current.canMoveForwardLeft(p)) { //check forward left for empty
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (n - 1)); //move black piece to empty square
                    children.add(child);
                } else if (current.canJumpForwardLeft(p)) { //Jump the white piece
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (2 * (n - 1))); //Move Black Piece
                    child.moveWhite(p + (n - 1), 0); //Delete Jumped White piece
                    children.add(child);
                }
                /**
                 * ***********************End Left Side Check*****************
                 */

                /**
                 * **********************Right Side Empty or Jump*************
                 */
                if (current.canMoveForwardRight(p)) { //check forward right for empty
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (n + 1)); //move token to forward right
                    children.add(child);
                } else if (current.canJumpForwardRight(p)) { //Jump White Piece
                    child = current.cloneBoard();
                    child.moveBlack(p, p + (2 * (n + 1))); //move black piece
                    child.moveWhite(p + (n + 1), 0); //remove jumped white token
                    children.add(child);
                }
                /**
                 * ************************End Right Side Check***************
                 */
            }
            //********************END BLACK PIECE MOVE GENERATION*****************//

            //**********************BLACK KING MOVE GENERATION**********************//
            for (int k : bkPieces) {
                /**
                 * *******************Left Side Empty or
                 * Jump************************
                 */

                if (current.canMoveForwardLeft(k)) {//CHECK LEFT FRONT FOR EMPTY SPACE
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (n - 1)); //move black token to empty square
                    children.add(child);
                } else if (current.canJumpForwardLeft(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (2 * (n - 1)));
                    child.moveWhite(k + (n - 1), 0);
                    children.add(child);
                }

                if (current.canMoveRearLeft(k)) {//CHECK LEFT REAR FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (n + 1)); //move black token to empty spot
                    children.add(child);
                } else if (current.canJumpRearLeft(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (2 * (n + 1)));
                    child.moveWhite(k - (n + 1), 0);
                    children.add(child);

                }
                /**
                 * *********************End Left Side
                 * Checks*************************
                 */

                /**
                 * **************************Right Side Empty or
                 * Jump**************
                 */
                if (current.canMoveForwardRight(k)) {//CHECK RIGHT FORWARD FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (n + 1)); //move black token forward right to empty space
                    children.add(child);

                } else if (//CHECK RIGHT FORWARD FOR JUMP
                        current.canJumpForwardRight(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k + (2 * (n + 1)));
                    child.moveWhite(k + (n + 1), 0);
                    children.add(child);
                }

                if (current.canMoveRearRight(k)) {//CHECK RIGHT REAR FOR EMPTY
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (n - 1)); //move black king right rear to empty space
                    children.add(child);

                } else if (//CHECK RIGHT REAR FOR JUMP
                        current.canJumpRearRight(k)) {
                    child = current.cloneBoard();
                    child.moveBlack(k, k - (2 * (n - 1)));
                    child.moveWhite(k - (n - 1), 0);
                    children.add(child);
                }
                /**
                 * *********************End Right Side
                 * Checks*************************
                 */
            }
            //********************END BLACK KING MOVE GENERATION*****************//
        } ///************************END BLACK*******************************///
        ///**************************WHITE*********************************///		
        else {
            //**********************WHITE PIECE MOVE GENERATION**********************//
            for (int p : wPieces) {
                /**
                 * ***********************Left Side Empty or
                 * Jump************************
                 */
                if (current.canMoveRearLeft(p)) { //check left rear for empty
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (n + 1));
                    children.add(child);
                } else if ( //CHECK LEFT REAR FOR JUMP
                        current.canJumpRearLeft(p)) {
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (2 * (n + 1))); //move white token
                    child.moveBlack(p - (n + 1), 0);  //remove jumped black token
                    children.add(child);
                }
                /**
                 * ***********************End Left Side
                 * Check****************************
                 */

                /**
                 * **********************Right Side Empty or
                 * Jump************************
                 */
                if (current.canMoveRearRight(p)) { //check rear right for empty
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (n - 1)); //move white token right rear
                    children.add(child);

                } else if ( //check for right rear jump
                        current.canJumpRearRight(p)) {
                    child = current.cloneBoard();
                    child.moveWhite(p, p - (2 * (n - 1))); //move white piece
                    child.moveBlack(p - (n - 1), 0); //delete black piece
                    children.add(child);
                }
            }
            /**
             * *********************End Right Side Check**********************
             */

            //********************END WHITE PIECE MOVE GENERATION*****************//
            //**********************WHITE KING MOVE GENERATION**********************//
            for (int k : wkPieces) {
                /**
                 * **********************Left Side Empty or Jump******************
                 */
                if (current.canMoveRearLeft(k)) {//check left rear for empty
                    child = current.cloneBoard();
                    child.moveWhite(k, k - (n + 1));
                    children.add(child);
                } else if ( // check left rear for jump
                        current.canJumpRearLeft(k)) {
                    child = current.cloneBoard();
                    child.moveWhite(k, k - (2 * (n + 1))); //move white king
                    child.moveBlack(k - (n + 1), 0); //remove jumped black token
                    children.add(child);
                }

                if (current.canMoveForwardLeft(k)) {//check forward left for empty
                    child = current.cloneBoard();
                    child.moveWhite(k, k + (n - 1)); //move left forward
                    children.add(child);
                } else if (//check left forward for jump
                        current.canJumpForwardLeft(k)) {
                    child = current.cloneBoard();
                    child.moveWhite(k, k + (2 * (n - 1))); //move white king
                    child.moveBlack(k + (n - 1), 0); //remove jumped black token
                    children.add(child);
                }
                /**
                 * *****************End Left Side Check***************************
                 */

                /**
                 * ***************Right Side Empty or Jump************************
                 */
                if (current.canMoveRearRight(k)) { //check rear right for empty
                    child = current.cloneBoard();
                    child.moveWhite(k, k - (n - 1)); //move white token right rear
                    children.add(child);
                } else if ( //check for right rear jump
                        current.canJumpRearRight(k)) {
                    child = current.cloneBoard();
                    child.moveWhite(k, k - (2 * (n - 1))); //move white piece
                    child.moveBlack(k - (n - 1), 0); //delete black piece
                    children.add(child);
                }

                if (current.canMoveForwardRight(k)) { //check front right for empty
                    child = current.cloneBoard();
                    child.moveWhite(k, k + (n + 1)); //move white token right front
                    children.add(child);
                } else if ( //check for right front jump
                        current.canJumpForwardRight(k)) {
                    child = current.cloneBoard();
                    child.moveWhite(k, k + (2 * (n + 1))); //move white piece
                    child.moveBlack(k + (n + 1), 0); //delete black piece
                    children.add(child);
                }
                /**
                 * *****************End Right Side
                 * Check***************************
                 */
                //********************END WHITE KING MOVE GENERATION*****************//
            }
            ///************************END WHITE*******************************///
        }
        return children; //RETURN ALL GENERATED CHILDREN
    }
}
