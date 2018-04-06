/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knightminmaxab;
import java.util.ArrayList;
import java.util.Arrays;

/******************************************************************************
 _   _   _   _
 |_|02|_|2|_|3|_|4|  { 1- 4}
 |5|_|6|_|7|_|8|_|  { 5- 8}
 |_|9|_|0|_|1|_|2|  { 9-12}
 |3|_|4|_|5|_|6|_|  {13-16}
 |_|7|_|8|_|9|_|0|  {17-20}
 |1|_|2|_|3|_|4|_|  {21-24}
 |_|5|_|6|_|7|_|8|  {25-28}
 |9|_|0|_|1|_|2|_|  {29-32}
 
|01|02|03|04|05|06|07|08| 
|09|10|11|12|13|14|15|16| 
|17|18|19|20|21|22|23|24| 
|25|26|27|28|29|30|31|32| 
|33|34|35|36|37|38|39|40| 
|41|42|43|44|45|46|47|48| 
|49|50|51|52|53|54|55|56| 
|57|58|59|60|61|62|63|64| 
 *******************************************************************************/
public class Board {
    private static final int DIMENSION = 8;
    ArrayList<Integer> rightBorder = new ArrayList<Integer>() {{
        add(8);
        add(16);
        add(24);
        add(32);
        add(40);
        add(48);
        add(56);
        add(64);
    }};
    ArrayList<Integer> leftBorder = new ArrayList<Integer>() {{
        add(01);
        add(9);
        add(17);
        add(25);
        add(33);
        add(41);
        add(49);
        add(57);
    }};
    ArrayList<Integer> bottomBorder = new ArrayList<Integer>() {{
        add(57);
        add(58);
        add(59);
        add(60);
        add(61);
        add(62);
        add(63);
        add(64);
    }};
    ArrayList<Integer> topBorder = new ArrayList<Integer>() {{
        add(01);
        add(02);
        add(03);
        add(04);
        add(05);
        add(06);
        add(07);
        add(8);
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
    
    public static Board getStartBoard(){
        Board b = new Board();
        b.setBlack(new ArrayList<>(Arrays.asList(2,4,6,8,9,11,13,15,18,20,22,24)));
        b.setWhite(new ArrayList<>(Arrays.asList(41, 43, 45, 47, 50, 52, 54, 56, 57, 59, 61, 63)));
        return b;
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
    
    public void moveBlack(int oldP, int newP){
       if(newP != 0){
             if (this.kBlack.contains(oldP)){ 
                 this.kBlack.remove(oldP);
                 this.kBlack.add(newP);
             }
             else if(black.contains(oldP)){                  
                  this.black.add(newP);                  
                  this.black.remove(black.lastIndexOf(oldP));
             }
       }
       else {
             if (kBlack.contains(oldP)){ 
                 kBlack.remove(oldP);
             }
             else if(black.contains(oldP)){
                  black.remove(oldP);
             }
       }
   }
    
    public void moveWhite(int oldP, int newP){
       if(newP != 0){
             if (kWhite.contains(oldP)){ 
                 kWhite.remove(oldP);
                 kWhite.add(newP);
             }
             else if(white.contains(oldP)){
                  white.remove(oldP);
                  white.add(newP);
             }
       }
       else {
             if (kWhite.contains(oldP)){ 
                 kWhite.remove(oldP);
             }
             else if(white.contains(oldP)){
                  white.remove(oldP);
             }
       }
   }
    
    /*
    public boolean canMoveForwardLeft(Board b, int pos){
        pos = pos+ (b.getDIMENSION() -1);
        if( b.getTopBorder().contains(pos) || b.getLeftBorder().contains(pos)
        || b.getBlack().contains(pos) || b.getWhite().contains(pos) ||
        b.getkBlack().contains(pos) || b.getkWhite().contains(pos)
        ){
            return false;
        }
        else {return true;} 
    }
    public boolean canMoveForwardRight(int pos){
        
    }
    public boolean canMoveRearLeft(int pos){
        
    }
    public boolean canMoveRearRight(Board b, int pos){
        
    }
    */
}