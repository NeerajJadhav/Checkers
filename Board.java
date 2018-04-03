import java.util.*;

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
public class Board () {
		private ArrayList<Integer> white = new ArrayList<>();
		private ArrayList<Integer> black = new ArrayList<>();
		private ArrayList<Integer> kBlack = new ArrayList<>();
		private ArrayList<Integer> kWhite = new ArrayList<>();
		private float value;
		
		public Board(){
			value = null;
		}
		
		public Board(Board b){
			this.white = b.getWhite();
			this.black = b.getBlack();
			this.kBlack = b.getkBlack();
			this.kWhite = b.getkWhite();
			value = null;
		}
		
		public void setWhite(ArrayList<Integer> white){
			this.white = white;
		}
		public ArrayList<Integer> getWhite(){
			return this.white;
		}
		public void setkWhite(ArrayList<Integer> kWhite){
			this.kWhite = kWhite;
		}
		public ArrayList<Integer> getkWhite(){
			return this.kWhite;
		}
		public void setBlack(ArrayList<Integer> black){
			this.black = black;
		}
		public ArrayList<Integer> getBlack(){
			return this.black;
		}
		public void setkBlack(ArrayList<Integer> kBlack){
			this.kBlack = kBlack;
		}
		public ArrayList<Integer> getkBlack(){
			return this.kBlack;
		}
		public float getValue(){
			return value;
		}
		public void setValue(float value){
			this.value = value;
		}
}