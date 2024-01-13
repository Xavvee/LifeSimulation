package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Genotype.Genotype;
import agh.ics.oop.model.Genotype.RandomGenotype;
import agh.ics.oop.model.Map.MoveValidator;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

public class Animal implements WorldElement {

    private MapDirection direction;

    private Vector2d position;
    private int energy;
    private int dayBorn;
    private Genotype genotype;
    public Animal(){
        this(new Vector2d(2,2), 20);
    }

    public Animal(Vector2d position, int startingEnergy){
        this.position = position;
        this.energy = startingEnergy;
        this.genotype = new RandomGenotype();
        this.direction = genotype.getGenotype().get(0);
        this.dayBorn = 0;
    }


    public Animal(Vector2d position, Genotype genotype){
        this.position = position;
        this.genotype = genotype;
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

    public void move(MoveValidator validator){
        this.rotate();
        Vector2d newPosition = position.add(this.direction.toUnitVector());
        if(validator.canMoveTo(newPosition)){
            this.position = newPosition;
        }
    }

    public void rotate() {
        MapDirection activeGene = this.direction;
        direction = direction.rotateBy(activeGene);
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

    public Vector2d getPosition() {
        return position;
    }

    public int getDayBorn() {
        return dayBorn;
    }

}
