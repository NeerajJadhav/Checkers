
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
class minMaxAB {

    private static final int MAX_DEPTH = 5;

    public enum Player {
        min, max
    }

    private static int Static(Board position, Player player) {
        //Make a call to eval function
        return Utility.getScore(position, player);
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
        ArrayList<Board> successors = Utility.moveGen(position, player);

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
}
