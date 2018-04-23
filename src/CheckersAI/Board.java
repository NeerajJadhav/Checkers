
package CheckersAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * ****************************************************************************
 * CHECKERS
 * 1  2  3  4  5  6  7  8
 * A |01|02|03|04|05|06|07|08|
 * B |09|10|11|12|13|14|15|16|
 * C |17|18|19|20|21|22|23|24|
 * D |25|26|27|28|29|30|31|32|
 * E |33|34|35|36|37|38|39|40|
 * F |41|42|43|44|45|46|47|48|
 * G |49|50|51|52|53|54|55|56|
 * H |57|58|59|60|61|62|63|64|
 * <p>
 * <p>
 * Player Max: -Black Tokens -Computer Controlled -Starts Top Border
 * <p>
 * Player Min: -White Tokens -Player or Computer Controlled -Starts Bottom
 * Border
 * *****************************************************************************
 */
public class Board {

    /**
     *
     */
    private int depth;
    private static final int DIMENSION = 8;
    private HashMap<String, Integer> boardMapper = new HashMap<>();

    /**
     * Initialize and mark borders of the board
     */
    private ArrayList<Integer> rightBorder = new ArrayList<>() { //right
        {
            add(8);
            add(16);
            add(24);
            add(32);
            add(40);
            add(48);
            add(56);
            add(64);
        }
    };

    private ArrayList<Integer> leftBorder = new ArrayList<>() { //left
        {
            add(1);
            add(9);
            add(17);
            add(25);
            add(33);
            add(41);
            add(49);
            add(57);
        }
    };

    private ArrayList<Integer> bottomBorder = new ArrayList<>() { //bottom
        {
            add(57);
            add(58);
            add(59);
            add(60);
            add(61);
            add(62);
            add(63);
            add(64);
        }
    };

    private ArrayList<Integer> topBorder = new ArrayList<>() { //top
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
            add(7);
            add(8);
        }
    };

    //Initialize pawns and kings of each player
    private ArrayList<Integer> white = new ArrayList<>();
    private ArrayList<Integer> black = new ArrayList<>();
    private ArrayList<Integer> kBlack = new ArrayList<>();
    private ArrayList<Integer> kWhite = new ArrayList<>();

    // value of the board to be used in evaluating usefulness of the board
    private float value;
    // terminal is set to true if the board satisfies terminal condition: See minmax algorithms.
    private boolean terminal;

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public enum Player {
        white,
        black
    }

    /**
     * Default constructor inits, value and terminal as well as maps board to alhpanumeric pattern
     */
    private Board() {
        value = 0;
        terminal = false;
        populateBoardMapper();
    }

    /**
     * Maps board tile to human readable alpha numeric locations. Ex: tile "5" is "A5"
     */
    private void populateBoardMapper() {
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        int squareNumber = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 8; j++) {
                boardMapper.put(letters[i] + j, squareNumber);
                squareNumber++;
            }
        }
    }

    /**
     * Returns the mapped integer value of an alpha numeric combination of a tile.
     *
     * @param s - String - Mapped value has be fit the pattern [A-H][1-8]
     * @return int -returns appropriate mapped value. Range 1-64.
     */
    public int mapValue(String s) {
        return boardMapper.get(s);
    }

    /**
     * Initializes a new board and assigns default starting to both player pieces.
     *
     * @return Board - A board with 24 pieces 12 of each player in default locations.
     */
    public static Board getStartBoard() {
        Board b = new Board();
        b.setBlack(new ArrayList<>(Arrays.asList(2, 4, 6, 8, 9, 11, 13, 15, 18, 20, 22, 24)));
        b.setWhite(new ArrayList<>(Arrays.asList(41, 43, 45, 47, 50, 52, 54, 56, 57, 59, 61, 63)));
        return b;
    }

    /**
     * Getter for terminal variable.
     *
     * @return Boolean True of terminal board, false otherwise.
     */
    public boolean getTerminal() {
        return this.terminal;
    }

    /**
     * Setter for terminal variable
     */
    public void setTerminal() {
        this.terminal = true;
    }

    /**
     * Getter for right border markers
     *
     * @return
     */
    public ArrayList<Integer> getRightBorder() {
        return rightBorder;
    }

    /**
     * Getter for left border markers
     *
     * @return
     */
    public ArrayList<Integer> getLeftBorder() {
        return leftBorder;
    }

    /**
     * Getter for bottom border markers
     *
     * @return
     */
    public ArrayList<Integer> getBottomBorder() {
        return bottomBorder;
    }

    /**
     * Getter for top border markers
     *
     * @return
     */
    public ArrayList<Integer> getTopBorder() {
        return topBorder;
    }

    /**
     * Returns current dimensions set for the board. Default is 8.
     *
     * @return
     */
    public int getDIMENSION() {
        return DIMENSION;
    }

    /**
     * Returns list of white pawn pieces
     *
     * @return
     */
    public ArrayList<Integer> getWhite() {
        return new ArrayList<>(this.white);
    }

    /**
     * Set a list of pieces as white pieces.
     *
     * @param white
     */
    public void setWhite(ArrayList<Integer> white) {
        this.white = white;
    }

    /**
     * @return
     */
    public ArrayList<Integer> getkWhite() {
        return new ArrayList<>(this.kWhite);
    }

    /**
     * @param kWhite
     */
    public void setkWhite(ArrayList<Integer> kWhite) {
        this.kWhite = kWhite;
    }

    /**
     * @return
     */
    public ArrayList<Integer> getBlack() {
        return new ArrayList<>(this.black);
    }

    /**
     * @param black
     */
    public void setBlack(ArrayList<Integer> black) {
        this.black = black;
    }

    /**
     * @return
     */
    public ArrayList<Integer> getkBlack() {
        return new ArrayList<>(this.kBlack);
    }

    /**
     * @param kBlack
     */
    public void setkBlack(ArrayList<Integer> kBlack) {
        this.kBlack = kBlack;
    }

    /**
     * @return
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * Returns true if a space on the board is not occupied by any of the pieces from either player.
     *
     * @param space - int - Space to be checked Range [1-64]
     * @return boolean - True if space is unoccupied
     */
    public boolean isEmpty(int space) {

        return (!white.contains(space) && !black.contains(space)
                && !kWhite.contains(space) && !kBlack.contains(space));
    }

    /**
     * Validates and moves a black piece to a new location. Executing kills if any, f the opponent..
     *
     * @param oldp int - current position of a piece.
     * @param newp int - new position of a piece.
     */
    public void moveBlack(int oldp, int newp) {
        /*System.out.println("Before Black");
        this.printBoard();*/
        Integer oldP = oldp;
        Integer newP = newp;
        if (newP != 0) {
            if (this.kBlack.contains(oldP)) {
                this.kBlack.remove(oldP);
                this.kBlack.add(newP);
            } else if (this.black.contains(oldP)) {
                this.black.remove(oldP);
                if (this.bottomBorder.contains(newP)) {
                    this.kBlack.add(newP);
                } else {
                    this.black.add(newP);
                }

            }
        } else if (this.kBlack.contains(oldP)) {
            this.kBlack.remove(oldP);
        } else this.black.remove(oldP);
        checkTerminal();
        /*System.out.println("After Black");
        this.printBoard();
        System.out.println("\n\n");*/
    }

    /**
     * Executes moves a white piece to a new location. Executing kills if any, of the opponent.
     *
     * @param oldp
     * @param newp
     */
    public void moveWhite(int oldp, int newp) {
        /*System.out.println("Before White:");
        this.printBoard();*/
        Integer oldP = oldp;
        Integer newP = newp;
        if (newP != 0) {
            if (kWhite.contains(oldP)) {
                kWhite.remove(oldP);
                kWhite.add(newP);
            } else if (white.contains(oldP)) {
                white.remove(oldP);
                if (this.topBorder.contains(newP)) {
                    this.kWhite.add(newP);
                } else {
                    white.add(newP);
                }
            }
        } else if (kWhite.contains(oldP)) {
            kWhite.remove(oldP);
        } else white.remove(oldP);
        checkTerminal();
        /*System.out.println("After White");
        this.printBoard();
        System.out.println("\n\n");*/
    }

    /**
     * Displays formatted board
     */
    public void printBoard() {
        int n = getDIMENSION();
        ArrayList<Integer> bP = getBlack();
        ArrayList<Integer> wP = getWhite();
        ArrayList<Integer> bK = getkBlack();
        ArrayList<Integer> wK = getkWhite();
        char[] rowIndex = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        char[] colIndex = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        int tile = 1;
        for (int row = -1; row < n; row++) {
            if (row >= 0) {
                System.out.print(rowIndex[row] + "\t");
            } else {
                System.out.print("\t");
            }
            for (int col = 0; col < n; col++) {
                if (row == -1) {
                    System.out.print("  " + colIndex[col] + " ");
                    continue;
                }
                char token;
                if (bP.contains(tile)) {
                    token = 'b';
                } else if (wP.contains(tile)) {
                    token = 'w';
                } else if (bK.contains(tile)) {
                    token = 'B';
                } else if (wK.contains(tile)) {
                    token = 'W';
                } else {
                    token = '_';
                }
                System.out.print("| " + token + " ");
                tile++;
            }
            System.out.println();
        }
        //System.out.println("Value is: " + this.getValue());
    }

    /**
     * If either of the player has ran out of pieces the terminal variable is set to true.
     */
    private void checkTerminal() {
        if ((this.black.isEmpty() && this.kBlack.isEmpty()) || (this.white.isEmpty() && this.kWhite.isEmpty())) {
            this.terminal = true;
        }
    }

    //***************MOVE VALIDATION************************//

    /**
     * Checks whether a move is valid or not. Valid moves are ones that move one tile in diagonally in front for pawns
     * and front or back for kings. Also checks for already existing pieces in new location. If all conditions of a
     * standard checkers move are met returns true.
     *
     * @param currMove - String - Current location of the piece. Has to be of format [A-Z][1-8]
     * @param newMove  - String - New location of the piece. Has to be of format [A-Z][1-8]
     * @return boolean - True if move is valid else false.
     */
    public boolean isValidMove(String currMove, String newMove) {
        //convert B1 to 9
        int parsedCurrMove;
        int parsedNewMove;

        if (boardMapper.containsKey(currMove) && boardMapper.containsKey(newMove)) {
            parsedCurrMove = boardMapper.get(currMove);
            parsedNewMove = boardMapper.get(newMove);
            if (white.contains(parsedCurrMove) || kWhite.contains(parsedCurrMove)) {
                if (parsedNewMove == (parsedCurrMove - (DIMENSION + 1))) {
                    if (canMoveRearLeft(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        return true;
                    }
                } else if (parsedNewMove == (parsedCurrMove - (DIMENSION - 1))) {
                    if (canMoveRearRight(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        return true;
                    }
                } else if (parsedNewMove == (parsedCurrMove - 2 * (DIMENSION + 1))) {
                    if (canJumpRearLeft(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        this.moveBlack(parsedCurrMove - (DIMENSION + 1), 0);
                        return true;
                    }
                } else if (parsedNewMove == (parsedCurrMove - 2 * (DIMENSION - 1))) {
                    if (canJumpRearRight(parsedCurrMove)) {
                        this.moveWhite(parsedCurrMove, parsedNewMove);
                        this.moveBlack(parsedCurrMove - (DIMENSION - 1), 0);
                        return true;
                    }
                }
                if (kWhite.contains(parsedCurrMove)) {
                    if (parsedNewMove == (parsedCurrMove + (DIMENSION + 1))) {
                        if (canMoveForwardRight(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            return true;
                        }
                    } else if (parsedNewMove == (parsedCurrMove + (DIMENSION - 1))) {
                        if (canMoveForwardLeft(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            return true;
                        }
                    } else if (parsedNewMove == (parsedCurrMove + 2 * (DIMENSION + 1))) {
                        if (canJumpForwardRight(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            this.moveBlack(parsedCurrMove + (DIMENSION + 1), 0);
                            return true;
                        }
                    } else if (parsedNewMove == (parsedCurrMove + 2 * (DIMENSION - 1))) {
                        if (canJumpForwardLeft(parsedCurrMove)) {
                            this.moveWhite(parsedCurrMove, parsedNewMove);
                            this.moveBlack(parsedCurrMove + (DIMENSION - 1), 0);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a piece can MOVE one tile diagonally FORWARD LEFT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canMoveForwardLeft(int pos) {
        int pos2 = pos + (this.getDIMENSION() - 1);
        return !this.getBottomBorder().contains(pos) && !this.getLeftBorder().contains(pos)
                && !this.getBlack().contains(pos2) && !this.getWhite().contains(pos2)
                && !this.getkBlack().contains(pos2) && !this.getkWhite().contains(pos2);

    }

    /**
     * Checks if a piece can MOVE one tile diagonally FORWARD RIGHT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canMoveForwardRight(int pos) {
        int pos2 = pos + (this.getDIMENSION() + 1);
        return !this.getBottomBorder().contains(pos) && !this.getRightBorder().contains(pos)
                && !this.getBlack().contains(pos2) && !this.getWhite().contains(pos2)
                && !this.getkBlack().contains(pos2) && !this.getkWhite().contains(pos2);
    }

    /**
     * Checks if a piece can MOVE one tile diagonally REAR LEFT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canMoveRearLeft(int pos) {
        int pos2 = pos - (this.getDIMENSION() + 1);
        return !this.getTopBorder().contains(pos) && !this.getLeftBorder().contains(pos)
                && !this.getBlack().contains(pos2) && !this.getWhite().contains(pos2)
                && !this.getkBlack().contains(pos2) && !this.getkWhite().contains(pos2);
    }

    /**
     * Checks if a piece can MOVE one tile diagonally REAR RIGHT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canMoveRearRight(int pos) {
        int pos2 = pos - (this.getDIMENSION() - 1);
        return !this.getTopBorder().contains(pos) && !this.getRightBorder().contains(pos)
                && !this.getBlack().contains(pos2) && !this.getWhite().contains(pos2)
                && !this.getkBlack().contains(pos2) && !this.getkWhite().contains(pos2);
    }

    /**
     * Checks if a piece can JUMP OPPONENT PIECE diagonally FORWARD LEFT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canJumpForwardLeft(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos + (DIMENSION - 1);
        int pos3 = pos2 + (DIMENSION - 1);
        return enemy.contains(pos2) && !this.getBottomBorder().contains(pos2)
                && !this.getLeftBorder().contains(pos2) && isEmpty(pos3);
    }

    /**
     * Checks if a piece can JUMP OPPONENT PIECE diagonally FORWARD RIGHT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canJumpForwardRight(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos + (DIMENSION + 1);
        int pos3 = pos2 + (DIMENSION + 1);
        return enemy.contains(pos2) && !this.getBottomBorder().contains(pos2)
                && !this.getRightBorder().contains(pos2) && isEmpty(pos3);
    }

    /**
     * Checks if a piece can JUMP OPPONENT PIECE diagonally REAR LEFT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canJumpRearLeft(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos - (DIMENSION + 1);
        int pos3 = pos2 - (DIMENSION + 1);
        return enemy.contains(pos2) && !this.getTopBorder().contains(pos2)
                && !this.getLeftBorder().contains(pos2) && isEmpty(pos3);
    }

    /**
     * Checks if a piece can JUMP OPPONENT PIECE diagonally REAR RIGHT of a board from its current location.
     *
     * @param pos - current position of the piece
     * @return boolean - returns true if piece can make the move.
     */
    public boolean canJumpRearRight(int pos) {
        ArrayList<Integer> enemy = this.getEnemies(pos);
        int pos2 = pos - (DIMENSION - 1);
        int pos3 = pos2 - (DIMENSION - 1);
        return enemy.contains(pos2) && !this.getTopBorder().contains(pos2)
                && !this.getRightBorder().contains(pos2) && isEmpty(pos3);
    }

//***************END MOVE VALIDATION************************//

    /**
     * Returns a list of remaining enemies on the board of the given piece
     *
     * @param pos - current position of the piece
     * @return ArrayList Integer - list of opponent pieces
     */
    private ArrayList<Integer> getEnemies(int pos) {
        ArrayList<Integer> enemy;
        if (this.getBlack().contains(pos) || this.getkBlack().contains(pos)) {
            enemy = this.getWhite();
            enemy.addAll(this.getkWhite());
        } else {
            enemy = this.getBlack();
            enemy.addAll(this.getkBlack());
        }
        return enemy;
    }

    /**
     * Returns a new Board object which has exact same layout and values of this board.
     *
     * @return Board - New copy of this board.
     */
    public Board cloneBoard() {
        Board child = new Board();
        child.setBlack(this.getBlack());
        child.setWhite(this.getWhite());
        child.setkBlack(this.getkBlack());
        child.setkWhite(this.getkWhite());
        if (this.getTerminal() == true) {
            child.setTerminal();
        }
        return child;
    }

    @Override
    public String toString() {

        int n = getDIMENSION();
        StringBuilder s = new StringBuilder();
        s.append("Depth =").append(this.depth).append("\n");
        ArrayList<Integer> bP = getBlack();
        ArrayList<Integer> wP = getWhite();
        ArrayList<Integer> bK = getkBlack();
        ArrayList<Integer> wK = getkWhite();
        char[] rowIndex = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        char[] colIndex = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        int tile = 1;
        for (int row = -1; row < n; row++) {
            if (row >= 0) {
                s.append(rowIndex[row]).append("\t");
            } else {
                s.append("\t");
            }
            for (int col = 0; col < n; col++) {
                if (row == -1) {
                    s.append("  ").append(colIndex[col]).append(" ");
                    continue;
                }
                char token;
                if (bP.contains(tile)) {
                    token = 'b';
                } else if (wP.contains(tile)) {
                    token = 'w';
                } else if (bK.contains(tile)) {
                    token = 'B';
                } else if (wK.contains(tile)) {
                    token = 'W';
                } else {
                    token = '_';
                }
                s.append("| ").append(token).append(" ");
                tile++;
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Returns winner if any based on the current board position. Considers remaining pieces of either board players
     *
     * @return String - Message declaring a winner.
     */
    public String getWinner() {
        if (this.getkWhite().size() + this.getWhite().size() == 0)
            return "Player with Black pieces is the winner";
        else if (this.getBlack().size() + this.getkBlack().size() == 0) {
            return "Player with White pieces is the winner";
        }
        return "Winner not decided yet";
    }
}
