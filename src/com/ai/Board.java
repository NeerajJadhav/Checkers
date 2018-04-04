package com.ai;

import java.util.ArrayList;

/******************************************************************************
 _   _   _   _
 |_|1|_|2|_|3|_|4|  { 1- 4}
 |5|_|6|_|7|_|8|_|  { 5- 8}
 |_|9|_|0|_|1|_|2|  { 9-12}
 |3|_|4|_|5|_|6|_|  {13-16}
 |_|7|_|8|_|9|_|0|  {17-20}
 |1|_|2|_|3|_|4|_|  {21-24}
 |_|5|_|6|_|7|_|8|  {25-28}
 |9|_|0|_|1|_|2|_|  {29-32}

 *******************************************************************************/
public class Board {
    private static final int DIMENSION = 8;
    ArrayList<Integer> rightBorder = new ArrayList<Integer>() {{
        add(4);
        add(12);
        add(20);
        add(28);
    }};
    ArrayList<Integer> leftBorder = new ArrayList<Integer>() {{
        add(5);
        add(13);
        add(21);
        add(29);
    }};
    ArrayList<Integer> bottomBorder = new ArrayList<Integer>() {{
        add(1);
        add(2);
        add(3);
        add(4);
    }};
    ArrayList<Integer> topBorder = new ArrayList<Integer>() {{
        add(29);
        add(30);
        add(31);
        add(32);
    }};
    private ArrayList<Integer> white = new ArrayList<>();
    private ArrayList<Integer> black = new ArrayList<>();
    private ArrayList<Integer> kBlack = new ArrayList<>();
    private ArrayList<Integer> kWhite = new ArrayList<>();
    private float value;

    public Board() {
        value = 0;
    }

    public Board(Board b) {
        this.white = b.getWhite();
        this.black = b.getBlack();
        this.kBlack = b.getkBlack();
        this.kWhite = b.getkWhite();
        value = 0;
    }

    public ArrayList<Integer> getRightBorder() {
        return rightBorder;
    }

    public ArrayList<Integer> getLeftBorder() {
        return leftBorder;
    }

    public ArrayList<Integer> getBottomBorder() {
        return bottomBorder;
    }

    public ArrayList<Integer> getTopBorder() {
        return topBorder;
    }

    public int getDIMENSION() {
        return DIMENSION;
    }

    public ArrayList<Integer> getWhite() {
        return this.white;
    }

    public void setWhite(ArrayList<Integer> white) {
        this.white = white;
    }

    public ArrayList<Integer> getkWhite() {
        return this.kWhite;
    }

    public void setkWhite(ArrayList<Integer> kWhite) {
        this.kWhite = kWhite;
    }

    public ArrayList<Integer> getBlack() {
        return this.black;
    }

    public void setBlack(ArrayList<Integer> black) {
        this.black = black;
    }

    public ArrayList<Integer> getkBlack() {
        return this.kBlack;
    }

    public void setkBlack(ArrayList<Integer> kBlack) {
        this.kBlack = kBlack;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isEmpty(int space) {
        return (!white.contains(space) && !black.contains(space));
    }
}