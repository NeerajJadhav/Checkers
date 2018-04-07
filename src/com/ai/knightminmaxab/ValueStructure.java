/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knightminmaxab;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class ValueStructure {

    private int value;
    private ArrayList<Board> path;

    public ValueStructure() {

    }
    
    public ValueStructure(Board b){
        this.addToPath(b);
    }
    
    public ValueStructure(int v, ArrayList<Board> b) {
        this.value = v;
        this.path = b;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<Board> getPath() {
        return path;
    }

    public void addToPath(Board newPathNode) {
        path.add(newPathNode);
    }

    public void addToPath(ArrayList<Board> newPath) {
        if (newPath != null) {
            path.addAll(newPath);
        }
    }

    @Override
    public String toString() {
        return "ValueStructure{" + "value=" + value + ", path=" + path.toString() + '}';
    }
    
}