package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Genotype.Genotype;
import agh.ics.oop.model.Genotype.RandomGenotype;
import agh.ics.oop.model.Map.MoveValidator;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

public class Animal implements WorldElement {

    private static final int STARTING_ENERGY = 50;
    private final Vector2d lowerBound = new Vector2d(0,0);
    private final Vector2d upperBound = new Vector2d(4,4);
    private MapDirection direction;

    private Vector2d position;
    private int energy;
    private Genotype genotype;
    public Animal(){
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d position){
        this.position = position;
        this.energy = STARTING_ENERGY;
        this.genotype = new RandomGenotype();
        this.direction = genotype.getGenotype().get(0);
    }

    @Override
    public String toString(){
        return switch (direction){
            case NORTH -> "^";
            case WEST -> "<";
            case SOUTH -> "v";
            case NORTHEAST -> "-|";
            case EAST -> ">";
            case SOUTHEAST -> "_|";
            case SOUTHWEST -> "L";
            case NORTHWEST -> "|-";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    // raczej do wyjebania w obecnej formie, zawsze musi zrotować zwierzę, a później spróbowac ruszyć je do przodu
    public void move(MoveValidator validator){

        Vector2d newPosition = position.add(this.direction.toUnitVector());
        if(validator.canMoveTo(newPosition)){
            this.position = newPosition;
        }
    }

    private void rotate(){
        return;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d position() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
