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

    private ArrayList<Board> moveGen(Board current){
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
                    //BLACK PIECE MOVE GENERATION
                        /**********************Left Side Empty or Jump***********************/
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
                                else if ((wPieces.contains(p + (n - 1)) || wkPieces.contains(p+(n-1))) //check to see if white piece left-forward adjacent
                                && !current.leftBorder.contains(p + (n - 1)) //check to see if square beyond white piece (side)
                                && !current.bottomBorder.contains(p + (n - 1)) // check to see if square beyond white piece (bottom)
                                 ) {
                                        if (current.isEmpty(p + (2 * (n - 1)))) { //check to see if square beyond is empty
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
                    /**********************************************************************************/


                    /******************************Right Side Empty or Jump ****************************/
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
                                else if ((wPieces.contains(p + (n+1) || wkPieces.contains(p+(n+1)))//check to see if white piece right-forward adjacent
                                  && !current.rightBorder.contains(p +(n+1) //check to see if square beyond white piece (side)
                                  && !current.bottomBorder.contains(p + (n+1)) //check to see if square beyond white piece (bottom)
                                  ) {
                                        if (current.isEmpty(p + (2 * (n+1))) { //check to see if square beyond is empty
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
               /******************************************************************************************************/
                                            
                                            
                                            
              /*********************************Black King Pieces*****************************************************/
                for(int k : kBPieces){
                    /*********************Left Side Empty or Jump *************************************************/
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
                                else if ((wPieces.contains(k + (n - 1)) || wkPieces.contains(k+(n-1))) //check to see if white piece left-forward adjacent
                                  && !current.leftBorder.contains(k + (n - 1)) //check to see if square beyond white piece (side)
                                  && !current.bottomBorder.contains(k + (n - 1)) // check to see if square beyond white piece (bottom)
                                  ) {
                                        if (current.isEmpty(k + (2 * (n - 1)))) { //check to see if square beyond is empty
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
                                else if ((wPieces.contains(k - (n + 1))|| wkPieces.contains(k-(n+1))) //check to see if white piece left-forward adjacent
                                  && !current.leftBorder.contains(k - (n + 1)) //check to see if square beyond white piece (side)
                                  && !current.bottomBorder.contains(k - (n + 1)) // check to see if square beyond white piece (bottom)
                                   {
                                        if (current.isEmpty(k - (2 * (n + 1)))) { //check to see if square beyond is empty
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
                        /*******************************************************************************************************/


                        /****************************Right Side Empty or Jump******************************************/
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
                                else if ((wPieces.contains(k + (n+1) || wkPieces.contains(k+(n+1))) //check to see if white piece right-forward adjacent
                                  && !current.rightBorder.contains(k +(n+1) //check to see if square beyond white piece (side)
                                  && !current.bottomBorder.contains(k + (n+1)) //check to see if square beyond white piece (bottom)
                                  ) {
                                        if (current.isEmpty(k + (2 * (n+1))) { //check to see if square beyond is empty
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
                                            
                               //NEED RIGHT REAR EMPTY OR JUMP CODE HERE
                        }

                }

        }
        else{
            //Do all the white piece move generation
            for(int p : wPieces){
                        if(!leftBorder.contains(p)){
                                if(current.isEmpty(p-(n+1))){ //check backward left for empty
                                        child = new Board();
                                        child.setBlack(current.getBlack());
                                        child.setWhite(current.getWhite());
                                        child.setkWhite(current.getkWhite());
                                        child.setkBlack(current.getkBlack());
                                        child.moveWhite(p, p-(n+1));
                                        children.add(child);
                                }
                                else if ((bPieces.contains(p - (n + 1)) || bkPieces.contains(p-(n+1))) //check to see if  left-rear adjacent
                                && !current.leftBorder.contains(p - (n + 1)) //check to see if square beyond piece (side)
                                && !current.topBorder.contains(p - (n + 1)) // check to see if square beyond piece (top)
                                 ) {
                                        if (current.isEmpty(p - (2 * (n + 1)))) { //check to see if square beyond is empty
                                        child = new Board();
                                        child.setBlack(current.getBlack());
                                        child.setWhite(current.getWhite());
                                        child.setkWhite(current.getkWhite());
                                        child.setkBlack(current.getkBlack());
                                        child.moveWhite(p, p-(2*(n+1)));
                                        child.moveBlack(p-(n+1), 0);
                                        children.add(child);
                                        }
                                }
                        }
                        if(!rightBorder.contains(p)){
                                if(current.isEmpty(p-(n-1))){ //check rear right for empty
                                        child = new Board();
                                        child.setBlack(current.getBlack());
                                        child.setWhite(current.getWhite());
                                        child.setkWhite(current.getkWhite());
                                        child.setkBlack(current.getkBlack());
                                        child.moveWhite(p, p-(n-1));
                                        children.add(child);

                                }
                                else if ((bPieces.contains(p - (n-1) || bkPieces.contains(p-(n-1)))//check to see right-rear adjacent
                                  && !current.rightBorder.contains(p -(n-1) //check to see if square beyond piece (side)
                                  && !current.topBorder.contains(p - (n-1)) //check to see if square beyond piece (bottom)
                                  ) {
                                        if (current.isEmpty(p - (2 * (n-1))) { //check to see if square beyond is empty
                                                child = new Board();
                                                child.setBlack(current.getBlack());
                                                child.setWhite(current.getWhite());
                                                child.setkWhite(current.getkWhite());
                                                child.setkBlack(current.getkBlack());
                                                child.moveWhite(p, p-(2*(n-1))); //move white piece
                                                child.moveBlack(p-(n-1), 0); //delete black piece
                                                children.add(child);
                                        }
                                }
                        }
                }
                for(int k : kWPieces){
                        if(!leftBorder.contains(k)){//check left rear for empty
                                if(current.isEmpty(k-(n+1))){
                                        child = new Board();
                                        child.setBlack(current.getBlack());
                                        child.setWhite(current.getWhite());
                                        child.setkWhite(current.getkWhite());
                                        child.setkBlack(current.getkBlack());
                                        child.movekWhite(k, k-(n+1));
                                        children.add(child);
                                }
                                else if ((bPieces.contains(k - (n + 1)) || bkPieces.contains(k-(n+1))) //check to see left-rear adjacent
                                  && !current.leftBorder.contains(k - (n + 1)) //check to see if square beyond piece (side)
                                  && !current.topBorder.contains(k - (n + 1)) // check to see if square beyond piece (top)
                                  ) {
                                        if (b.isEmpty(k - (2 * (n + 1)))) { //check to see if square beyond is empty
                                                child = new Board();
                                                child.setBlack(current.getBlack());
                                                child.setWhite(current.getWhite());
                                                child.setkWhite(current.getkWhite());
                                                child.setkBlack(current.getkBlack());
                                                child.movekWhite(k, k-(2*(n+1)));
                                                child.moveBlack(k-(n+1), 0);
                                                children.add(child);
                                        }
                                }
                                if(current.isEmpty(k+(n-1))){//check forward left
                                        child = new Board();
                                        child.setBlack(current.getBlack());
                                        child.setWhite(current.getWhite());
                                        child.setkWhite(current.getkWhite());
                                        child.setkBlack(current.getkBlack());
                                        child.movekWhite(k, k+(n-1));
                                        children.add(child);
                                }
                                else if ((bPieces.contains(k + (n - 1))|| bkPieces.contains(k+(n-1))) //check to see left-forward adjacent
                                  && !current.leftBorder.contains(k + (n - 1)) //check to see if square beyond piece (side)
                                  && !current.bottomBorder.contains(k + (n - 1)) // check to see if square beyond piece (bottom)
                                   {
                                        if (current.isEmpty(k + (2 * (n - 1)))) { //check to see if square beyond is empty
                                        child = new Board();
                                        child.setBlack(current.getBlack());
                                        child.setWhite(current.getWhite());
                                        child.setkWhite(current.getkWhite());
                                        child.setkBlack(current.getkBlack());
                                        child.movekWhite(k, k+(2*(n-1)));
                                        child.moveBlack(k+(n-1), 0);
                                        children.add(child);
                                        }
                                }

                        }


        }
        return children;
    }


}
