package CheckersAI;

import java.util.ArrayList;

/**
 * The purpose of this class is to wrap several attributes for propagation through the tree.
 *     Both needed tangible data such as Value and Path are included, as well as meta data like
 *     boards evaluated and prune count. An instance of ValueStructure will be the result of an
 *     AI algorithm.
 * @author Zachary, Niraj
 */
public class ValueStructure {
    
    private int value;
    //private int newValue;
    private ArrayList<Board> path;

    /**
     *
     */
    public int boardsEvaluatedCount=0;

    /**
     *
     */
    public int pruneCount = 0;

    /**
     *
     */
    public ValueStructure() {
        path = new ArrayList<>();
    }

    /** Constructor for value structure function.
     *
     * @param b
     */
    public ValueStructure(Board b) {
        this.path = new ArrayList<>();
        this.path.add(b);
    }

    /**
     *
     * @param v
     */
    public ValueStructure (int v){
        this.value = v;
    }

    /**
     * Constructor for value structure.
     * @param v
     * @param b
     */
    public ValueStructure(int v, ArrayList<Board> b) {
        this.value = v;
        this.path = new ArrayList<>();
        path.addAll(b);
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value of value structure to new value provided
     * @param value value to be updated to
     */
    public void setValue(int value) {
        this.value = value;
    }
//    public int getNewValue() {
//        return this.newValue;
//    }
//    public void setNewValue(int value) {
//        this.newValue = value;
//    }

    /**
     *
     * @return
     */
    public ArrayList<Board> getPath() {
        return new ArrayList<>(path);
    }

    /**
     * Add the given board as a path to the value structure
     *
     * @param newPathNode - Board to be added to the path
     */
    public void addToPath(Board newPathNode) {
        //if (newPathNode) {
            path.add(newPathNode);
        //}
    }

    /**
     * Adds an arraylist of boards as path to value structure path
     * @param newPath
     */
    public void addToPath(ArrayList<Board> newPath) {
       //if (newPath != null) {
            path.addAll(newPath);
       //}
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ValueStructure value=");
        s.append(value);        
        s.append(", path=\n");
        if (!path.isEmpty()) {
            for (Board b : path) {
                s.append(b.toString());
                s.append("\n Value=");
                s.append(b.getValue());
                s.append("\n\n");
            }
        }
        else {s.append("NULL}");}
        return s.toString();
    }

}
