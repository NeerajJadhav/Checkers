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
    private ArrayList<Integer> path;

    public ValueStructure() {

    }

    public ValueStructure(int v, ArrayList<Integer> p) {
        this.value = v;
        this.path = p;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void addToPath(int newPathNode) {
        path.add(newPathNode);
    }

    public void addToPath(ArrayList<Integer> newPath) {
        if (newPath != null) {
            path.addAll(newPath);
        }
    }

    @Override
    public String toString() {
        return "ValueStructure{" + "value=" + value + ", path=" + path + '}';
    }
    
}