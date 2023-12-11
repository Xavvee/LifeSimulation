package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

public class Comparator implements java.util.Comparator<Vector2d> {

    private final boolean compareX;

    public Comparator(boolean compareX){
        this.compareX = compareX;
    }

    @Override
    public int compare(Vector2d v1, Vector2d v2) {
        if(compareX){
            if(v1.getX() < v2.getX()){
                return -1;
            } else if (v1.getX() > v2.getX()) {
                return 1;
            } else if (v1.getY() < v2.getY()) {
                return -1;
            } else if (v1.getY() > v2.getY()) {
                return 1;
            }
        } else {
            if(v1. getY() < v2.getY()){
                return -1;
            } else if (v1.getY() > v2.getY()) {
                return 1;
            } else if (v1.getX() < v2.getX()) {
                return -1;
            } else if (v1.getX() > v1.getX()) {
                return 1;
            }
        }
        return 0;
    }
}
