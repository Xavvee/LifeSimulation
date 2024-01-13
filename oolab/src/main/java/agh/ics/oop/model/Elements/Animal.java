package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Genotype.*;
import agh.ics.oop.model.Map.MoveValidator;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {

    private MapDirection direction;

    private Vector2d position;
    private int energy;
    private int age;
    private Genotype genotype;
    private int childrenCount;


    // randomly spawned starting animal
    public Animal(Vector2d position, int startingEnergy, int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, GenotypeType genotypeType){
        this.position = position;
        this.energy = startingEnergy;
        if(genotypeType.equals(GenotypeType.MINOR_CORRECTION)){
            this.genotype = new MinorCorrectionGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations);
        } else if (genotypeType.equals(GenotypeType.RANDOM)) {
            this.genotype = new RandomGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations);
        }
        this.direction = genotype.getGenotype().get(0);
        this.age = 0;
        this.childrenCount = 0;
    }

    // newborn child genotype
    public Animal(Vector2d position, Animal firstParent, Animal secondParent, int energyNeededForReproduction, int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, GenotypeType genotypeType){
        this.position = position;
        this.energy = 2*energyNeededForReproduction;
        if(genotypeType.equals(GenotypeType.MINOR_CORRECTION)){
            this.genotype = new MinorCorrectionGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, firstParent, secondParent);
        } else if (genotypeType.equals(GenotypeType.RANDOM)) {
            this.genotype = new RandomGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, firstParent, secondParent);
        }
        this.direction = genotype.getGenotype().get(0);
        this.age = 0;
        this.childrenCount = 0;
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


    public void addEnergy(int energy){
        this.energy+=energy;
    }

    public void addChild(){
        this.childrenCount+=1;
    }

    public void subtractEnergy(int subtractedEnergy){
        this.energy-=subtractedEnergy;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

}
