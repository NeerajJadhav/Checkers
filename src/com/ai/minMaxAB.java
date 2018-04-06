package com.ai;

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
        return depth >= MAX_DEPTH;
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

    public ArrayList<Board> moveGen(Board current){
        int n = current.getDIMENSION();
        ArrayList<Integer> bPieces = current.getBlack();
        ArrayList<Integer> wPieces = current.getWhite();
        ArrayList<Integer> bkPieces = current.getkBlack();
        ArrayList<Integer> wkPieces = current.getkWhite();
        ArrayList<Integer> topBorder = current.getTopBorder();
        ArrayList<Integer> bottomBorder = current.getBottomBorder();
        ArrayList<Integer> leftBorder = current.getLeftBorder();
        ArrayList<Integer> rightBorder = current.getRightBorder();
        ArrayList<Board> children = new ArrayList<>();
        Board child;
        if(player = black){
                for(int p : bPieces){
                        if(!leftBorder.contains(p)){
                                if(current.isEmpty(p+(n-1))){ //check forward left for empty
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.moveBlack(p, p+(n-1));
                                        children.add(child);
                                }
                                else if (wPieces.contains(p + (n - 1)) //check to see if white piece left-forward adjacent
                                && !b.leftBorder.contains(p + (n - 1)) //check to see if square beyond white piece (side)
                                && !b.bottomBorder.contains(p + (n - 1)) // check to see if square beyond white piece (bottom)
                                 ) {
                                        if (b.isEmpty(p + (2 * (n - 1)))) { //check to see if square beyond is empty
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.moveBlack(p, p+(2*(n-1)));
                                        child.moveWhite(p+(n-1), 0);
                                        children.add(child);
                                        }
                                }
                        }



                        if(!rightBorder.contains(p)){
                                if(current.isEmpty(p+(n+1))){ //check forward right for empty
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.moveBlack(p, p+(n+1));
                                        children.add(child);

                                }
                                else if (wPieces.contains(p + (n+1) //check to see if white piece right-forward adjacent
                                  && !b.rightBorder.contains(p +(n+1) //check to see if square beyond white piece (side)
                                  && !b.bottomBorder.contains(p + (n+1)) //check to see if square beyond white piece (bottom)
                                  ) {
                                        if (b.isEmpty(p + (2 * (n+1))) { //check to see if square beyond is empty
                                                child = new Board();
                                                child.setWhite(current.getWhite());
                                                child.setBlack(current.getBlack());
                                                child.setkBlack(current.getkBlack());
                                                child.setkWhite(current.getkWhite());
                                                child.moveBlack(p, p+(2*(n+1)));
                                                child.moveWhite(p+(n+1), 0);
                                                children.add(child);
                                        }
                                }
                        }
                }
                for(int k : kBPieces){
                        if(!leftBorder.contains(k)){//check left forward for empty
                                if(current.isEmpty(k+(n-1))){
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.movekBlack(k, k+(n-1));
                                        children.add(child);
                                }
                                else if (wPieces.contains(k + (n - 1)) //check to see if white piece left-forward adjacent
                                  && !b.leftBorder.contains(k + (n - 1)) //check to see if square beyond white piece (side)
                                  && !b.bottomBorder.contains(k + (n - 1)) // check to see if square beyond white piece (bottom)
                                  ) {
                                        if (b.isEmpty(k + (2 * (n - 1)))) { //check to see if square beyond is empty
                                                child = new Board();
                                                child.setWhite(current.getWhite());
                                                child.setBlack(current.getBlack());
                                                child.setkBlack(current.getkBlack());
                                                child.setkWhite(current.getkWhite());
                                                child.movekBlack(k, k+(2*(n-1)));
                                                child.moveWhite(k+(n-1), 0);
                                                children.add(child);
                                        }
                                }
                                if(current.isEmpty(k-(n+1))){//check back left
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.movekBlack(k, k-(n+1));
                                        children.add(child);
                                }
                                else if (wPieces.contains(k - (n + 1)) //check to see if white piece left-forward adjacent
                                  && !b.leftBorder.contains(k - (n + 1)) //check to see if square beyond white piece (side)
                                  && !b.bottomBorder.contains(k - (n + 1)) // check to see if square beyond white piece (bottom)
                                   {
                                        if (b.isEmpty(k - (2 * (n + 1)))) { //check to see if square beyond is empty
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.movekBlack(k, k-(2*(n+1)));
                                        child.moveWhite(k-(n+1), 0);
                                        children.add(child);
                                        }
                                }

                        }



                        if(!rightBorder.contains(k)){ //check right forward for empty move
                                if(current.isEmpty(k+(n+1))){
                                        child = new Board();
                                        child.setWhite(current.getWhite());
                                        child.setBlack(current.getBlack());
                                        child.setkBlack(current.getkBlack());
                                        child.setkWhite(current.getkWhite());
                                        child.movekBlack(k, k+(n+1));
                                        children.add(child);

                                }
                                else if (wPieces.contains(k + (n+1) //check to see if white piece right-forward adjacent
                                  && !b.rightBorder.contains(k +(n+1) //check to see if square beyond white piece (side)
                                  && !b.bottomBorder.contains(k + (n+1)) //check to see if square beyond white piece (bottom)
                                  ) {
                                        if (b.isEmpty(k + (2 * (n+1))) { //check to see if square beyond is empty
                                                child = new Board();
                                                child.setWhite(current.getWhite());
                                                child.setBlack(current.getBlack());
                                                child.setkBlack(current.getkBlack());
                                                child.setkWhite(current.getkWhite());
                                                child.movekBlack(k, k+(2*(n+1)));
                                                child.moveWhite(k+(n+1), 0);
                                                children.add(child);
                                        }
                                }
                        }

                }

        }
        else{

        }
}


}
