package knightminmaxab;

import java.util.ArrayList;

public class minMaxAB {

    private static final int MAX_DEPTH = 5;

    public enum Player {
        min, max
    }

    public ValueStructure start(int position, int depth, Player player, int maxUse, int minPass) {
        return minMax(position, depth, player, maxUse, minPass);
    }

    public ValueStructure minMax(int position, int depth, Player player, int useThresh, int passThresh) {
        ValueStructure currValueStructure = new ValueStructure();
        if (deepEnough(position, depth)) {
            currValueStructure.setValue(Static(position, player));
            currValueStructure.addToPath(null);
            return currValueStructure;
        }
        ArrayList<ValueStructure> successors = moveGen(position, player);

        if (successors.isEmpty()) {
            //currValueStructure.setValue(Static(position, player));
            currValueStructure.addToPath(null);
            return currValueStructure;
        }

        for (ValueStructure v : successors) {
            ValueStructure resultSucc = minMax(position, (depth+1), switchPlayer(player), ((-1) * passThresh),( (-1) * useThresh));
            int newValue = (resultSucc.getValue() * (-1));
            if (newValue > passThresh) {
                passThresh = newValue;
                currValueStructure.addToPath(resultSucc.getPath());
            }
            if (passThresh >= useThresh) {
                currValueStructure.setValue(passThresh);
                return currValueStructure;
            }
        }
        currValueStructure.setValue(passThresh);
        return currValueStructure;
    }

    public boolean deepEnough(int position, int depth) {
        if (depth >= MAX_DEPTH) {
            return true;
        } else {
            return false;
        }
    }

    public int Static(int position, Player player) {
        return 1;
    }

    private Player switchPlayer(Player currPlayer) {
        if (currPlayer == Player.max) {
            return Player.min;
        }
        return Player.max;
    }

    private ArrayList<ValueStructure> moveGen(int position, Player player) {
        ArrayList<ValueStructure> testReturn = new ArrayList<>();

        for (int i = 7; i < 10; i++) {
            ArrayList<Integer> p = new ArrayList<>();
            p.add(i);
            p.add(i + 1);
            p.add(i + 2);
            testReturn.add(new ValueStructure(i, p));
        }

        return testReturn;
    }
}
