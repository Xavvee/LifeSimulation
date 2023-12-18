package agh.ics.oop.model;

import agh.ics.oop.model.Genotype.AbstractGenotype;
import agh.ics.oop.model.Genotype.Genotype;
import agh.ics.oop.model.Genotype.RandomGenotype;

import java.util.Objects;

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
        this.direction = convertGenotypeToMapDirection(genotype.getGenotype().get(0));
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

    public void move(MoveDirection direction, MoveValidator validator){
        switch (direction) {
            case FORWARD -> {
                Vector2d newPosition = position.add(this.direction.toUnitVector());
                if(validator.canMoveTo(newPosition)){
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = position.subtract(this.direction.toUnitVector());
                if(validator.canMoveTo(newPosition)){
                    this.position = newPosition;
                }
            }
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
        }
    }


    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection convertGenotypeToMapDirection(int genotype){
        return switch (genotype){
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTHEAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTHEAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTHWEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTHWEST;
            default -> throw new IllegalArgumentException("Nieznany genotyp: " + genotype);
        };
    }

}
