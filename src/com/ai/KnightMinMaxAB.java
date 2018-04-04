/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai;

/**
 *
 * @author root
 */
public class KnightMinMaxAB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        minMaxAB tester = new minMaxAB();
        ValueStructure result = tester.start(0,0, minMaxAB.Player.max, 99, 1);
        System.out.println(result.toString());
    }
    
    
}
