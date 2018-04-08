/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knightminmaxab;

import java.util.ArrayList;

public class evalFunc {

    private static final int PAWN_VALUE = 100;
    private static final int FORWARD_PAWN_VALUE = 50;
    private static final int KING_VALUE = 175;
    private static final int NEAR_KING_VALUE = 100;
    private static final int JUMP_PAWN = 200;
    private static final int JUMP_KING = 400;    

    public static int getScore(Board b, minMaxAB.Player p) {
        float bValue = 0;
        float wValue = 0;
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
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if(wK_Pieces.contains(i+(n-1))) {bValue += JUMP_KING;}
                else {bValue += JUMP_PAWN;}
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if(wK_Pieces.contains(i+(n+1))) {bValue += JUMP_KING;}
                else {bValue += JUMP_PAWN;}
            }
        }
        for (Integer i : bK_Pieces) {
            
            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if(wK_Pieces.contains(i+(n-1))) {bValue += JUMP_KING;}
                else {bValue += JUMP_PAWN;}
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if(wK_Pieces.contains(i+(n+1))) {bValue += JUMP_KING;}
                else {bValue += JUMP_PAWN;}
            }

            if (b.canJumpRearLeft(i)) { // check left rear jump
                if(wK_Pieces.contains(i-(n+1))) {bValue += JUMP_KING;}
                else {bValue += JUMP_PAWN;}
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if(wK_Pieces.contains(i-(n-1))) {bValue += JUMP_KING;}
                else {bValue += JUMP_PAWN;}
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
            if (b.canJumpRearLeft(i)) { // check left rear jump
                if(bK_Pieces.contains(i-(n+1))) {wValue += JUMP_KING;}
                else {wValue += JUMP_PAWN;}
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if(bK_Pieces.contains(i-(n-1))) {wValue += JUMP_KING;}
                else {wValue += JUMP_PAWN;}
            }
        }
        //Evaluate jumps for KING White pieces
        for (Integer i : wK_Pieces) {           

            if (b.canJumpForwardLeft(i)) { // check left foward jump
                if(bK_Pieces.contains(i+(n-1))) {wValue += JUMP_KING;}
                else {wValue += JUMP_PAWN;}
            }
            if (b.canJumpForwardRight(i)) {// check right foward jump
                if(bK_Pieces.contains(i+(n+1))) {wValue += JUMP_KING;}
                else {wValue += JUMP_PAWN;}
            }

            if (b.canJumpRearLeft(i)) { // check left rear jump
                if(bK_Pieces.contains(i-(n+1))) {wValue += JUMP_KING;}
                else {wValue += JUMP_PAWN;}
            }
            if (b.canJumpRearRight(i)) {// check right rear jump
                if(bK_Pieces.contains(i-(n-1))) {wValue += JUMP_KING;}
                else {wValue += JUMP_PAWN;}
            }
        }
    if(p == minMaxAB.Player.max){
            return (int) (bValue - wValue);
        }
        else if (b.getTerminal() && p == minMaxAB.Player.min) {
            return (-1) * (int) (bValue - wValue);
        }
        return (int) (wValue - bValue);    
    }

}
