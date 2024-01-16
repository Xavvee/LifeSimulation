package agh.ics.oop.model.Elements;

import agh.ics.oop.model.DirectedPosition;
import agh.ics.oop.model.Genotype.*;
import agh.ics.oop.model.Map.MapType;
import agh.ics.oop.model.Map.MoveCalculator;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

import java.util.Random;

public class Animal implements WorldElement {

    private MapDirection direction;

    private Vector2d position;
    private int energy;
    private int age;
    private Genotype genotype;
    private int childrenCount;
    private int startingIndex;
    private MapDirection activeGene;
    private MapType mapType;


    // randomly spawned starting animal
    public Animal(Vector2d position, int startingEnergy, int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, GenotypeType genotypeType, MapType mapType){
        this.position = position;
        this.energy = startingEnergy;
        if(genotypeType.equals(GenotypeType.MINOR_CORRECTION)){
            this.genotype = new MinorCorrectionGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations);
        } else if (genotypeType.equals(GenotypeType.RANDOM)) {
            this.genotype = new RandomGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations);
        }
        Random random = new Random();
        this.startingIndex = random.nextInt(genomeLength);
        this.direction = genotype.getGenotype().get(startingIndex);
        this.activeGene = genotype.getGenotype().get(startingIndex);
        this.age = 0;
        this.childrenCount = 0;
        this.mapType = mapType;
    }

    // newborn child genotype
    public Animal(Vector2d position, Animal firstParent, Animal secondParent, int energyNeededForReproduction, int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, GenotypeType genotypeType, MapType mapType){
        this.position = position;
        this.energy = 2*energyNeededForReproduction;
        if(genotypeType.equals(GenotypeType.MINOR_CORRECTION)){
            this.genotype = new MinorCorrectionGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, firstParent, secondParent);
        } else if (genotypeType.equals(GenotypeType.RANDOM)) {
            this.genotype = new RandomGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, firstParent, secondParent);
        }
        Random random = new Random();
        this.startingIndex = random.nextInt(genomeLength);
        this.direction = genotype.getGenotype().get(startingIndex);
        this.activeGene = genotype.getGenotype().get(startingIndex);
        this.age = 0;
        this.childrenCount = 0;
        this.mapType = mapType;
    }

    @Override
    public String toString(){
        return switch (direction){
            case NORTH -> "^";
            case WEST -> "<";
            case SOUTH -> "v";
            case NORTHEAST -> "NE";
            case EAST -> ">";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
            case NORTHWEST -> "NW";
        };
    }

    @Override
    public String getColor() {
        return "#fd0101";
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }


    public void move(MoveCalculator validator){
        DirectedPosition dirPos = validator.computeMove(new DirectedPosition(this.position, this.direction));
        this.position = dirPos.getPosition();
        this.direction = dirPos.getDirection();
    }

    public void rotate() {
        MapDirection previousDirection = this.direction;
        changeGene();
        direction = previousDirection.rotateBy(this.activeGene);
    }


    public void changeGene(){
        int genomeIndex = (this.age + this.startingIndex) % this.genotype.getGenomeLength();
        this.activeGene = this.genotype.getGenotype().get(genomeIndex);
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
