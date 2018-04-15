
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zachary
 */
public class AlphaBetaSearch {
    
    private static final int MAX_DEPTH = 5;
    
    public ValueStructure start(Board b){
        return alphaBetaSearch(b);
    }
    private ValueStructure alphaBetaSearch(Board b){
        return maxValue(b, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);         
    }
    
    /**
     * 
     * @param b
     * @param alpha
     * @param beta
     * @param depth
     * @return 
     */
    private ValueStructure maxValue(Board b, int alpha, int beta, int depth){
        ValueStructure currValStruct = new ValueStructure();
        ArrayList<Board> currPath = null;
        if (b.getTerminal()==true || depth > MAX_DEPTH){
            currValStruct.boardsEvaluatedCount++;
            currValStruct.setValue(Utility.getScore(b, minMaxAB.Player.max));
            return currValStruct;
        }
        currValStruct.setValue(Integer.MIN_VALUE);
        ArrayList<Board> successors = Utility.moveGen(b,minMaxAB.Player.max);
        for(Board s : successors){
            ValueStructure v = minValue(s, alpha, beta, ++depth);
            currValStruct.boardsEvaluatedCount+=v.boardsEvaluatedCount;
            currValStruct.pruneCount+=v.pruneCount;
            int x = v.getValue();
            if(currValStruct.getValue() < x){
                currValStruct.setValue(x);
                currPath = new ArrayList<>();
                currPath.add(s);
            }
            if(currValStruct.getValue() >= beta){
                currValStruct.pruneCount++;
                currValStruct.addToPath(currPath);
                return currValStruct;
            }
            alpha = Integer.max(alpha, currValStruct.getValue());
        }            
        currValStruct.addToPath(currPath);
        return currValStruct;
    }
    
    /**
     * 
     * @param b
     * @param alpha
     * @param beta
     * @param depth
     * @return 
     */
    private ValueStructure minValue(Board b, int alpha, int beta, int depth){
        ValueStructure currValStruct = new ValueStructure();
        ArrayList<Board> currPath = null;
        if (b.getTerminal()==true || depth > MAX_DEPTH){
            currValStruct.boardsEvaluatedCount++;
            currValStruct.setValue(Utility.getScore(b, minMaxAB.Player.max));
            return currValStruct;
        }
        
        currValStruct.setValue(Integer.MAX_VALUE);
        ArrayList<Board> successors = Utility.moveGen(b,minMaxAB.Player.min);
        for(Board s : successors){ 
            ValueStructure v = maxValue(s, alpha, beta, ++depth);
            currValStruct.boardsEvaluatedCount+=v.boardsEvaluatedCount;
            currValStruct.pruneCount+=v.pruneCount;
            int x = v.getValue();
            if(currValStruct.getValue() > x){
                currValStruct.setValue(x);
                currPath = new ArrayList<>();
                currPath.add(s);
            }            
            if(currValStruct.getValue() <= alpha){
                currValStruct.pruneCount++;
                currValStruct.addToPath(currPath);
                return currValStruct;
            }
            beta = Integer.min(beta, currValStruct.getValue());
        }
        currValStruct.addToPath(currPath);
        return currValStruct;
    }
    
}
