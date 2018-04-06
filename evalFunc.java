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
    private static final int KING_VALUE = 150;
    private static final int NEAR_KING_VALUE = 100;
    private static final int PAWN_CAN_JUMP = 100;
    private static final int[] GOOD_TILES = new int[]{12, 22};


    public static int getScore(Board b, minMaxAB.Player p) {
        float bValue = 0;
        float wValue = 0;
        ArrayList<Integer> wPieces = b.getWhite();
        ArrayList<Integer> wK_Pieces = b.getkWhite();
        ArrayList<Integer> bPieces = b.getBlack();
        ArrayList<Integer> bK_Pieces = b.getkBlack();
        int n = b.getDIMENSION() / 2;


        bValue += b.getBlack().size() * PAWN_VALUE;
        bValue += b.getkBlack().size() * KING_VALUE;
        wValue += b.getWhite().size() * PAWN_VALUE;
        wValue += b.getkWhite().size() * KING_VALUE;

        //Evaluate jumps for Black pieces
        for (Integer i : bPieces) {
            if (i > 20) {
                if (i > 24) {
                    bValue += NEAR_KING_VALUE;
                } else {
                    bValue += FORWARD_PAWN_VALUE;
                }
            }
            if (!b.leftBorder.contains(i)   //check to see if left jump possible
                    && wPieces.contains(i + n - 1) //check to see if white piece left-forward adjacent
                    && !b.leftBorder.contains(i + n - 1) //check to see if square beyond white piece (side)
                    && !b.bottomBorder.contains(i + n - 1) // check to see if square beyond white piece (bottom)
                    ) {
                if (b.isEmpty(i + (2 * n - 1))) { //check to see if square beyond is empty
                    bValue += PAWN_CAN_JUMP;
                }
            }

            if (!b.rightBorder.contains(i) //check to see if right-forward jump possible
                    && wPieces.contains(i + n) //check to see if white piece right-forward adjacent
                    && !b.rightBorder.contains(i + n) //check to see if square beyond white piece (side)
                    && !b.bottomBorder.contains(i + n) //check to see if square beyond white piece (bottom)
                    ) {
                if (b.isEmpty(i + (2 * n))) { //check to see if square beyond is empty
                    bValue += PAWN_CAN_JUMP;
                }
            }

        }

        //Evaluate jumps for White pieces
        for (Integer i : wPieces) {
            if (i < 13) {
                if (i < 9) {
                    wValue += NEAR_KING_VALUE;
                } else {
                    wValue += FORWARD_PAWN_VALUE;
                }
            }

            if (!b.leftBorder.contains(i)   //check to see if left jump possible
                    && bPieces.contains(i - n + 1) //check to see if black piece left-forward adjacent
                    && !b.leftBorder.contains(i - n + 1) //check to see if square beyond black piece (side)
                    && !b.topBorder.contains(i - n + 1) // check to see if square beyond black piece (top)
                    ) {
                if (b.isEmpty(i - (2 * n + 1))) { //check to see if square beyond is empty
                    wValue += PAWN_CAN_JUMP;
                }
            }

            if (!b.rightBorder.contains(i) //check to see if right-forward jump possible
                    && wPieces.contains(i - n) //check to see if black piece right-forward adjacent
                    && !b.rightBorder.contains(i - n) //check to see if square black white piece (side)
                    && !b.topBorder.contains(i - n) //check to see if square black white piece (bottom)
                    ) {
                if (b.isEmpty(i + (2 * n))) { //check to see if square beyond is empty
                    wValue += PAWN_CAN_JUMP;
                }
            }
        }

        return (int)(bValue - wValue);
    }


}