/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knightminmaxab;

import java.util.ArrayList;
import java.util.Iterator;

public class minMaxAB {

    private static final int MAX_DEPTH = 2;

    public enum Player {
        min, max
    }

    public ValueStructure start(Board position, int depth, Player player, int maxUse, int minPass) {
        return minMax(position, depth, player, maxUse, minPass);
    }

    public ValueStructure minMax(Board position, int depth, Player player, int useThresh, int passThresh) {
        ValueStructure currValueStructure = new ValueStructure();
        if (deepEnough(position, depth)) {
            currValueStructure.setValue(Static(position, player));
            //currValueStructure.addToPath(null);
            return currValueStructure;
        }
        ArrayList<Board> successors = moveGen(position, player);

        if (successors.isEmpty()) {
            //currValueStructure.setValue(Static(position, player));
            //currValueStructure.addToPath();
            return currValueStructure;
        }

        for (Board v : successors) {
            ValueStructure resultSucc = minMax(v, (depth + 1), switchPlayer(player), ((-1) * passThresh), ((-1) * useThresh));
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

    public boolean deepEnough(Board position, int depth) {
        return depth >= MAX_DEPTH;
    }

    public int Static(Board position, Player player) {
		//Make a call to eval function
        return evalFunc.getScore(position, player);
    }

    private Player switchPlayer(Player currPlayer) {
        if (currPlayer == Player.max) {
            return Player.min;
        }
        return Player.max;
    }

    private ArrayList<Board> moveGen(Board current, Player player){
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
		
		
		
		///**************************BLACK*********************************///
        if (player ==player.max) {
			//**********************BLACK PIECE MOVE GENERATION**********************//
            Iterator<Integer> iter = bPieces.iterator();
            while (iter.hasNext()) {
                int i = iter.next();
		int p = i;	
                /*************************Left Side Empty or Jump*************************/
                if (!leftBorder.contains(p)) {
                    if (current.isEmpty(p + (n - 1))) { //check forward left for empty
                        child = new Board();
                        child.setWhite(current.getWhite());
                        child.setBlack(current.getBlack());
                        child.setkBlack(current.getkBlack());
                        child.setkWhite(current.getkWhite());
                        child.moveBlack(p, p + (n - 1)); //move black piece to empty square
                        children.add(child);
                    } 
					else if (((wPieces.contains(p + (n - 1))) || (wkPieces.contains(p + (n - 1)))) //check to see if white piece left-forward adjacent
					&& !leftBorder.contains(p + (n - 1)) //check to see if square beyond white piece (side)
					&& !bottomBorder.contains(p + (n - 1)) // check to see if square beyond white piece (bottom)
					&& current.isEmpty(p + (2 * (n - 1))) //check to see if square beyond is empty
					){    
						//Jump the white piece
						child = new Board();
						child.setWhite(current.getWhite());
						child.setBlack(current.getBlack());
						child.setkBlack(current.getkBlack());
						child.setkWhite(current.getkWhite());
						child.moveBlack(p, p + (2 * (n - 1))); //Move Black Piece
						child.moveWhite(p + (n - 1), 0); //Delete Jumped White piece
						children.add(child);
                    }
                }
                /*************************End Left Side Check*****************************/

                /************************Right Side Empty or Jump*************************/
                if (!rightBorder.contains(p)) {
                    if (current.isEmpty(p + (n + 1))) { //check forward right for empty
                        child = new Board();
                        child.setWhite(current.getWhite());
                        child.setBlack(current.getBlack());
                        child.setkBlack(current.getkBlack());
                        child.setkWhite(current.getkWhite());
                        child.moveBlack(p, p + (n + 1)); //move token to forward right
                        children.add(child);
                    } 
			else if (((wPieces.contains(p + (n + 1))) || (wkPieces.contains(p + (n + 1))))//check to see if white piece right-forward adjacent
			&& !rightBorder.contains(p + (n + 1)) //check to see if square beyond white piece (side)
			&& !bottomBorder.contains(p + (n + 1)) //check to see if square beyond white piece (bottom)
			&& current.isEmpty(p + (2 * (n + 1))) //check to see if square beyond is empty
			){
						//Jump White Piece
						child = new Board();
						child.setWhite(current.getWhite());
						child.setBlack(current.getBlack());
						child.setkBlack(current.getkBlack());
						child.setkWhite(current.getkWhite());
						child.moveBlack(p, p + (2 * (n + 1))); //move black piece
						child.moveWhite(p + (n + 1), 0); //remove jumped white token
						children.add(child);                        
                    }
                }
				/**************************End Right Side Check****************************/
            }
			//********************END BLACK PIECE MOVE GENERATION*****************//

            //**********************BLACK KING MOVE GENERATION**********************//
            for (int k : bkPieces) {
                /*********************Left Side Empty or Jump*************************/
                if (!leftBorder.contains(k)) { //CHECK LEFT FRONT AND REAR
                    
                    if (current.isEmpty(k + (n - 1))) {//CHECK LEFT FRONT FOR EMPTY SPACE
					
                        child = new Board();
                        child.setWhite(current.getWhite());
                        child.setBlack(current.getBlack());
                        child.setkBlack(current.getkBlack());
                        child.setkWhite(current.getkWhite());
                        child.moveBlack(k, k + (n - 1)); //move black token to empty square
                        children.add(child);
                    } 
					
					else if ( //CHECK LEFT FORWARD FOR JUMP
					((wPieces.contains(k + (n - 1))) || (wkPieces.contains(k + (n - 1)))) //check to see if white piece left-forward adjacent
					&& !leftBorder.contains(k + (n - 1)) //check to see if square beyond white piece (side)
					&& !bottomBorder.contains(k + (n - 1)) // check to see if square beyond white piece (bottom)
					&& current.isEmpty(k + (2 * (n - 1))) //check to see if square beyond is empty
					){
                         
						child = new Board();
						child.setWhite(current.getWhite());
						child.setBlack(current.getBlack());
						child.setkBlack(current.getkBlack());
						child.setkWhite(current.getkWhite());
						child.moveBlack(k, k + (2 * (n - 1)));
						child.moveWhite(k + (n - 1), 0);
						children.add(child);
					}
                    
                    if (current.isEmpty(k - (n + 1))) {//CHECK LEFT REAR FOR EMPTY
                        child = new Board();
                        child.setWhite(current.getWhite());
                        child.setBlack(current.getBlack());
                        child.setkBlack(current.getkBlack());
                        child.setkWhite(current.getkWhite());
                        child.moveBlack(k, k - (n + 1)); //move black token to empty spot
                        children.add(child);
                    } 
					
					else if ( //CHECK FOR LEFT REAR JUMP
					((wPieces.contains(k - (n + 1))) || (wkPieces.contains(k - (n + 1)))) //check to see if white piece left rear
					&& !leftBorder.contains(k - (n + 1)) //check to see if square beyond white piece (side)
					&& !topBorder.contains(k - (n + 1)) // check to see if square beyond white piece (top)
					&& current.isEmpty(k - (2 * (n + 1))) //check to see if square beyond is empty
					){
                        
						child = new Board();
						child.setWhite(current.getWhite());
						child.setBlack(current.getBlack());
						child.setkBlack(current.getkBlack());
						child.setkWhite(current.getkWhite());
						child.moveBlack(k, k - (2 * (n + 1)));
						child.moveWhite(k - (n + 1), 0);
						children.add(child);
                        
                    }

                }
                /***********************End Left Side Checks**************************/

                /****************************Right Side Empty or Jump***************/
                if (!rightBorder.contains(k)) { 
                    if (current.isEmpty(k + (n + 1))) {//CHECK RIGHT FORWARD FOR EMPTY
                        child = new Board();
                        child.setWhite(current.getWhite());
                        child.setBlack(current.getBlack());
                        child.setkBlack(current.getkBlack());
                        child.setkWhite(current.getkWhite());
                        child.moveBlack(k, k + (n + 1)); //move black token forward right to empty space
                        children.add(child);

                    } 
					
					else if (//CHECK RIGHT FORWARD FOR JUMP
					((wPieces.contains(k + (n + 1))) || (wkPieces.contains(k + (n + 1)))) //check to see if white piece right-forward adjacent
                    && !current.rightBorder.contains(k + (n + 1)) //check to see if square beyond white piece (side)
                    && !current.bottomBorder.contains(k + (n + 1)) //check to see if square beyond white piece (bottom)
					&& current.isEmpty(k + (2 * (n + 1))) //check to see if square beyond is empty
                    ){                        
						child = new Board();
						child.setWhite(current.getWhite());
						child.setBlack(current.getBlack());
						child.setkBlack(current.getkBlack());
						child.setkWhite(current.getkWhite());
						child.moveBlack(k, k + (2 * (n + 1)));
						child.moveWhite(k + (n + 1), 0);
						children.add(child);                        
                    }

                    if (current.isEmpty(k - (n - 1))) {//CHECK RIGHT REAR FOR EMPTY
                        child = new Board();
                        child.setWhite(current.getWhite());
                        child.setBlack(current.getBlack());
                        child.setkBlack(current.getkBlack());
                        child.setkWhite(current.getkWhite());
                        child.moveBlack(k, k - (n - 1)); //move black king right rear to empty space
                        children.add(child);

                    } 
					
					else if (//CHECK RIGHT REAR FOR JUMP
					((wPieces.contains(k - (n - 1))) || (wkPieces.contains(k - (n - 1)))) //check to see if white piece right rear
                    && !rightBorder.contains(k - (n - 1)) //check to see if square beyond white piece (side)
                    && !topBorder.contains(k - (n - 1)) //check to see if square beyond white piece (top)
					&& current.isEmpty(k - (2 * (n - 1))) //check to see if square beyond is empty
                    ){                        
						child = new Board();
						child.setWhite(current.getWhite());
						child.setBlack(current.getBlack());
						child.setkBlack(current.getkBlack());
						child.setkWhite(current.getkWhite());
						child.moveBlack(k, k - (2 * (n - 1)));
						child.moveWhite(k - (n - 1), 0);
						children.add(child);                        
                    }
                }
				/***********************End Right Side Checks**************************/

            }
			//********************END BLACK KING MOVE GENERATION*****************//
        }
		///************************END BLACK*******************************///
		
		
		
		
		
		
		///**************************WHITE*********************************///		
		else {
			
			//**********************WHITE PIECE MOVE GENERATION**********************//
            for (int p : wPieces) {
				/*************************Left Side Empty or Jump*************************/
                if (!leftBorder.contains(p)) { // CHECK FOR LEFT REAR EMPTY OR JUMP
				
                    if (current.isEmpty(p - (n + 1))) { //check left rear for empty
                        child = new Board();
                        child.setBlack(current.getBlack());
                        child.setWhite(current.getWhite());
                        child.setkWhite(current.getkWhite());
                        child.setkBlack(current.getkBlack());
                        child.moveWhite(p, p - (n + 1));
                        children.add(child);
                    } 
					else if ( //CHECK LEFT REAR FOR JUMP
					((bPieces.contains(p - (n + 1))) || (bkPieces.contains(p - (n + 1)))) //check to see if  left rear 
                    && !current.leftBorder.contains(p - (n + 1)) //check to see if square beyond piece (side)
					&& !current.topBorder.contains(p - (n + 1)) // check to see if square beyond piece (top)
					&& current.isEmpty(p - (2 * (n + 1))) //check to see if square beyond is empty
					){                        
						child = new Board();
						child.setBlack(current.getBlack());
						child.setWhite(current.getWhite());
						child.setkWhite(current.getkWhite());
						child.setkBlack(current.getkBlack());
						child.moveWhite(p, p - (2 * (n + 1))); //move white token
						child.moveBlack(p - (n + 1), 0);  //remove jumped black token
						children.add(child);                        
                    }
                }
                /*************************End Left Side Check*****************************/
				
				/************************Right Side Empty or Jump*************************/
				if (!rightBorder.contains(p)) { //CHECK FOR RIGHT REAR EMPTY OR JUMP
                    
					if (current.isEmpty(p - (n - 1))) { //check rear right for empty
                        child = new Board();
                        child.setBlack(current.getBlack());
                        child.setWhite(current.getWhite());
                        child.setkWhite(current.getkWhite());
                        child.setkBlack(current.getkBlack());
                        child.moveWhite(p, p - (n - 1)); //move white token right rear
                        children.add(child);

                    } 
					
					else if ( //check for right rear jump
					((bPieces.contains(p - (n - 1))) || (bkPieces.contains(p - (n - 1))))//check to see right-rear adjacent
					&& !current.rightBorder.contains(p - (n - 1)) //check to see if square beyond piece (side)
					&& !current.topBorder.contains(p - (n - 1)) //check to see if square beyond piece (top)
					&& current.isEmpty(p - (2 * (n - 1))) //check to see if square beyond is empty
					){                        
						child = new Board();
						child.setBlack(current.getBlack());
						child.setWhite(current.getWhite());
						child.setkWhite(current.getkWhite());
						child.setkBlack(current.getkBlack());
						child.moveWhite(p, p - (2 * (n - 1))); //move white piece
						child.moveBlack(p - (n - 1), 0); //delete black piece
						children.add(child);                        
                    }
                }
				/**************************End Right Side Check****************************/
			}
            //********************END WHITE PIECE MOVE GENERATION*****************//
			
			
			
			
			//**********************WHITE KING MOVE GENERATION**********************//
			for (int k : wkPieces) {
				/*************************Left Side Empty or Jump*************************/
                if (!leftBorder.contains(k)) {
                    if (current.isEmpty(k - (n + 1))) {//check left rear for empty
                        child = new Board();
                        child.setBlack(current.getBlack());
                        child.setWhite(current.getWhite());
                        child.setkWhite(current.getkWhite());
                        child.setkBlack(current.getkBlack());
                        child.moveWhite(k, k - (n + 1));
                        children.add(child);
                    } 
					
					else if ( // check left rear for jump
					((bPieces.contains(k - (n + 1))) || (bkPieces.contains(k - (n + 1)))) //check to see left-rear adjacent
					&& !current.leftBorder.contains(k - (n + 1)) //check to see if square beyond piece (side)
					&& !current.topBorder.contains(k - (n + 1)) // check to see if square beyond piece (top)
					&& current.isEmpty(k - (2 * (n + 1))) //check to see if square beyond is empty
					){                         
                            child = new Board();
                            child.setBlack(current.getBlack());
                            child.setWhite(current.getWhite());
                            child.setkWhite(current.getkWhite());
                            child.setkBlack(current.getkBlack());
                            child.moveWhite(k, k - (2 * (n + 1))); //move white king
                            child.moveBlack(k - (n + 1), 0); //remove jumped black token
                            children.add(child);                        
                    }
                    
					if (current.isEmpty(k + (n - 1))) {//check forward left for empty
                        child = new Board();
                        child.setBlack(current.getBlack());
                        child.setWhite(current.getWhite());
                        child.setkWhite(current.getkWhite());
                        child.setkBlack(current.getkBlack());
                        child.moveWhite(k, k + (n - 1)); //move left forward
                        children.add(child);
                    } 
					
					else if (//check left forward for jump
					((bPieces.contains(k + (n - 1))) || (bkPieces.contains(k + (n - 1)))) //check to see left-forward adjacent
					&& !current.leftBorder.contains(k + (n - 1)) //check to see if square beyond piece (side)
					&& !current.bottomBorder.contains(k + (n - 1)) // check to see if square beyond piece (bottom)
					&& current.isEmpty(k + (2 * (n - 1))) //check to see if square beyond is empty
					){                        
						child = new Board();
						child.setBlack(current.getBlack());
						child.setWhite(current.getWhite());
						child.setkWhite(current.getkWhite());
						child.setkBlack(current.getkBlack());
						child.moveWhite(k, k + (2 * (n - 1))); //move white king
						child.moveBlack(k + (n - 1), 0); //remove jumped black token
						children.add(child);                        
                    }

                }
				/*************************End Left Side Check*****************************/
				
				/************************Right Side Empty or Jump*************************/
				if (!rightBorder.contains(k)) { //CHECK FOR RIGHT REAR EMPTY OR JUMP
                    
					if (current.isEmpty(k - (n - 1))) { //check rear right for empty
                        child = new Board();
                        child.setBlack(current.getBlack());
                        child.setWhite(current.getWhite());
                        child.setkWhite(current.getkWhite());
                        child.setkBlack(current.getkBlack());
                        child.moveWhite(k, k - (n - 1)); //move white token right rear
                        children.add(child);

                    } 
					
					else if ( //check for right rear jump
					((bPieces.contains(k - (n - 1))) || (bkPieces.contains(k - (n - 1))))//check to see right-rear adjacent
					&& !current.rightBorder.contains(k - (n - 1)) //check to see if square beyond piece (side)
					&& !current.topBorder.contains(k - (n - 1)) //check to see if square beyond piece (top)
					&& current.isEmpty(k - (2 * (n - 1))) //check to see if square beyond is empty
					){                        
						child = new Board();
						child.setBlack(current.getBlack());
						child.setWhite(current.getWhite());
						child.setkWhite(current.getkWhite());
						child.setkBlack(current.getkBlack());
						child.moveWhite(k, k - (2 * (n - 1))); //move white piece
						child.moveBlack(k - (n - 1), 0); //delete black piece
						children.add(child);                        
                    }
					
					if (current.isEmpty(k + (n + 1))) { //check front right for empty
                        child = new Board();
                        child.setBlack(current.getBlack());
                        child.setWhite(current.getWhite());
                        child.setkWhite(current.getkWhite());
                        child.setkBlack(current.getkBlack());
                        child.moveWhite(k, k + (n + 1)); //move white token right front
                        children.add(child);

                    } 
					
					else if ( //check for right front jump
					((bPieces.contains(k + (n + 1))) || (bkPieces.contains(k + (n + 1))))//check to see right front adjacent
					&& !current.rightBorder.contains(k + (n + 1)) //check to see if square beyond piece (side)
					&& !current.bottomBorder.contains(k + (n + 1)) //check to see if square beyond piece (bottom)
					&& current.isEmpty(k + (2 * (n + 1))) //check to see if square beyond is empty
					){                        
						child = new Board();
						child.setBlack(current.getBlack());
						child.setWhite(current.getWhite());
						child.setkWhite(current.getkWhite());
						child.setkBlack(current.getkBlack());
						child.moveWhite(k, k + (2 * (n + 1))); //move white piece
						child.moveBlack(k + (n + 1), 0); //delete black piece
						children.add(child);                        
                    }
                }
				/**************************End Right Side Check****************************/
				
            }
            //********************END WHITE KING MOVE GENERATION*****************//
        }
		///************************END WHITE*******************************///
		
		return children; //RETURN ALL GENERATED CHILDREN
    }
}