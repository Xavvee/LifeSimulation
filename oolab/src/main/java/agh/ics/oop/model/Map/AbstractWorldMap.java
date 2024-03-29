package agh.ics.oop.model.Map;

import agh.ics.oop.Comparator;
import agh.ics.oop.MapVisualizer;
import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Elements.WorldElement;
import agh.ics.oop.model.Genotype.GenotypeFactory;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected int width;
    protected int startingEnergy;
    protected int height;
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected Comparator XComparator = new Comparator(true);

    protected Comparator YComparator = new Comparator(false);
    protected TreeSet<Vector2d> sortedX = new TreeSet<>(XComparator);
    protected TreeSet<Vector2d> sortedY = new TreeSet<>(YComparator);
    protected List<MapChangeListener> observers;
    protected Map<Vector2d, Grass> grasses;
    protected int numberOfGrasses;

    protected ArrayList<Vector2d> freeHexes;
    protected int numberOfAnimals;
    protected int dailyNumberOfGrasses;
    protected Map<Vector2d, Water> waters;
    protected UUID id;

    protected int minimumNumberOfMutations;
    protected int maximumNumberOfMutations;
    protected int genomeLength;
    protected GenotypeFactory genotypeFactory;
    protected MapType mapType;
    protected ArrayList<Vector2d> freeHexesInEquator;
    protected ArrayList<Vector2d> freeHexesAboveEquator;
    protected ArrayList<Vector2d> freeHexesBelowEquator;
    protected ArrayList<Vector2d> hexesEligibleForGrassGrowth;

    public AbstractWorldMap(int height, int width, int numberOfGrasses, int numberOfAnimals, int dailyNumberOfGrasses, int startingEnergy, int minimumNumberOfMutations, int maximumNumberOfMutations, int genomeLength, MapType mapType, GenotypeFactory genotypeFactory){
        this.mapType = mapType;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrasses = numberOfGrasses;
        this.startingEnergy = startingEnergy;
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
        this.genomeLength = genomeLength;
        this.genotypeFactory = genotypeFactory;
        this.height = height;
        this.width = width;
        this.observers = new ArrayList<>();
        this.freeHexes = new ArrayList<>();
        generateFreeHexes();
        this.freeHexesAboveEquator = new ArrayList<>();
        this.freeHexesBelowEquator = new ArrayList<>();
        this.freeHexesInEquator = new ArrayList<>();
        calculateFreeHexes();
        this.hexesEligibleForGrassGrowth = new ArrayList<>(freeHexes);
        this.addObserver(new ConsoleMapDisplay());
        this.id = UUID.randomUUID();
        this.dailyNumberOfGrasses = dailyNumberOfGrasses;
        generateMapObjects();
    }
    protected void generateMapObjects(){
        this.grasses = new HashMap<>();
        generateGrasses();
        this.animals = new HashMap<>();
        generateAnimals();
    }

    protected void generateFreeHexes(){
        for(int i = 0; i < width ; i++){
            for(int j = 0; j < height; j++){
                freeHexes.add(new Vector2d(i,j));
            }
        }
    }
    public void subtractFreeHex(Vector2d position){
        this.freeHexes.remove(position);
        this.hexesEligibleForGrassGrowth.remove(position);
        if( checkWhereBelongs(position) == 1){
            freeHexesInEquator.remove(position);
        } else if (checkWhereBelongs(position) == 2) {
            freeHexesAboveEquator.remove(position);
        } else if (checkWhereBelongs(position) == 0) {
            freeHexesBelowEquator.remove(position);
        }
    }
    public void addFreeHex(Vector2d position){
        if(!freeHexes.contains(position)) {
            this.freeHexes.add(position);
            this.hexesEligibleForGrassGrowth.add(position);
            if (checkWhereBelongs(position) == 1) {
                freeHexesInEquator.add(position);
            } else if (checkWhereBelongs(position) == 2) {
                freeHexesAboveEquator.add(position);
            } else if (checkWhereBelongs(position) == 0) {
                freeHexesBelowEquator.add(position);
            }
        }
    }

    protected void generateAnimals(){
        for ( int i = 0; i < numberOfAnimals; i++){
            while (true){
                if(generateAnimal()){
                    break;
                }
            }
        }
    }

    protected void generateGrasses(){
        for ( int i = 0; i < numberOfGrasses; i++){
            while (true){
                if(generateGrass()!=null){
                    break;
                }
            }
        }
    }

    @Override
    public List<Grass> spawnGrass(){
        List<Grass> newGrasses = new ArrayList<>();
        List<Animal> animalList = getListOfAnimals();
        List<Grass> grassList = getListOfGrasses();
        for( Animal animal : animalList){
            Vector2d position = animal.getPosition();
            if(!freeHexes.contains(position) && !grassList.contains(new Grass(position))){
                hexesEligibleForGrassGrowth.add(position);
            }
        }
        if(animals.isEmpty()){
            setHexesEligibleForGrassGrowth();
        }
        for(int i = 0; i < dailyNumberOfGrasses; i++){
            if(!hexesEligibleForGrassGrowth.isEmpty()){
                while (true){
                    Grass grass = generateGrass();
                    if(grass != null){
                        newGrasses.add(grass);
                        hexesEligibleForGrassGrowth.remove(grass.position());
                        break;
                    }
                }
            } else break;
        }
        setHexesEligibleForGrassGrowth();
        return newGrasses;
    }

    private void setHexesEligibleForGrassGrowth(){
        this.hexesEligibleForGrassGrowth = new ArrayList<>(freeHexes);
    }
    @Override
    public boolean generateAnimal(){
        Random rand = new Random();
        int randomFreeHex = rand.nextInt(freeHexes.size());
        Vector2d randomPosition = freeHexes.get(randomFreeHex);
        if(this.isOccupied(randomPosition)){
            return false;
        }
        if(!isOccupied(randomPosition)){
            subtractFreeHex(randomPosition);
        }
        animals.put(randomPosition, new Animal(randomPosition, this.startingEnergy, this.genomeLength, this.minimumNumberOfMutations, this.maximumNumberOfMutations, genotypeFactory, MapType.GLOBE));
        addElement(randomPosition);
        return true;
    }


    @Override
    public Grass generateGrass(){
        Random rand = new Random();
        int index;
        Vector2d randomPosition = null;
        List<Vector2d> grassGrowthHexesInEquator = new ArrayList<>();
        List<Vector2d> grassGrowthHexesAboveEquator = new ArrayList<>();
        List<Vector2d> grassGrowthHexesBelowEquator = new ArrayList<>();
        for( Vector2d position : hexesEligibleForGrassGrowth){
            int res = checkWhereBelongs(position);
            if(res == 0){
                grassGrowthHexesBelowEquator.add(position);
            } else if (res == 1) {
                grassGrowthHexesInEquator.add(position);
            } else {
                grassGrowthHexesAboveEquator.add(position);
            }
        }
        while( randomPosition == null){
            if (rand.nextDouble() < 0.8 && !grassGrowthHexesInEquator.isEmpty()) {
                index = rand.nextInt(grassGrowthHexesInEquator.size());
                randomPosition = grassGrowthHexesInEquator.get(index);
            } else {
                // below the equator
                if(rand.nextDouble() < 0.5 && !grassGrowthHexesBelowEquator.isEmpty()){
                    index = rand.nextInt(grassGrowthHexesBelowEquator.size());
                    randomPosition = grassGrowthHexesBelowEquator.get(index);
                } else if (!grassGrowthHexesAboveEquator.isEmpty()) {
                    // above the equator
                    index = rand.nextInt(grassGrowthHexesAboveEquator.size());
                    randomPosition = grassGrowthHexesAboveEquator.get(index);
                }
            }
        }
        if (objectAt(randomPosition) instanceof Grass || objectAt(randomPosition) instanceof Water) {
            return null;
        }
        Grass grass = new Grass(randomPosition);
        grasses.put(randomPosition, grass);
        addElement(randomPosition);
        if(!(objectAt(randomPosition) instanceof Animal)){
            subtractFreeHex(randomPosition);
        }
        return grass;
    }

    private int checkWhereBelongs(Vector2d position){
        if( position.getY() >= this.getEquatorBounds().getX() && position.getY() <= this.getEquatorBounds().getY()){
            return 1;
        } else if (position.getY() < this.height && position.getY() > this.getEquatorBounds().getY()) {
            return 2;
        } else if (position.getY() >= 0 && position.getY() < this.getEquatorBounds().getX()) {
            return 0;
        }
        return -1;
    }

    protected void calculateFreeHexes(){
        for( Vector2d hex : freeHexes){
            if( checkWhereBelongs(hex) == 1){
                freeHexesInEquator.add(hex);
            } else if (checkWhereBelongs(hex) == 2) {
                freeHexesAboveEquator.add(hex);
            } else if (checkWhereBelongs(hex) == 0) {
                freeHexesBelowEquator.add(hex);
            }
        }
    }

    protected boolean canMoveTo(Vector2d position) {
        return position.precedes(this.getUpperRight()) && (this.getLowerLeft().precedes(position));
    }

    @Override
    public boolean place(Animal animal) throws PositionAlreadyOccupied {
        if(canMoveTo(animal.position())){
            animals.put(animal.position(), animal);
            addElement(animal.position());
            return true;
        } else {
            throw new PositionAlreadyOccupied(animal.position());
        }
    }

    @Override
    public void move(Animal animal) {
        if(animals.containsKey(animal.position())){
            Vector2d oldPosition = animal.position();
            animal.rotate();
            animal.move(this);
            Vector2d newPosition = animal.position();
            if (canMoveTo(newPosition)) {
                if(!isWaterAt(oldPosition) && !isGrassAt(oldPosition)){
                    addFreeHex(oldPosition);
                }
                animals.remove(oldPosition, animal);
                removeElement(oldPosition);
                if(!isOccupied(newPosition)){
                    subtractFreeHex(newPosition);
                }
                animals.put(newPosition, animal);
                addElement(newPosition);
            }
        }
    }

    @Override
    public void addElement(Vector2d element){
        sortedX.add(element);
        sortedY.add(element);
    }


    @Override
    public void removeElement(Vector2d element){
        sortedY.remove(element);
        sortedX.remove(element);
    }

    @Override
    public void removeAnimal(Vector2d position){
        animals.remove(position);
    }

    @Override
    public void removeGrass(Vector2d position){
        grasses.remove(position);
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animalElement = animals.get(position);
        if (animalElement != null) {
            return animalElement;
        }
        return grasses.get(position);
    }


    @Override
    public Vector2d getEquatorBounds(){
        int middleOfY = height/2;
        double tenPercent = height * 0.1;
        int tenPercentOfHeight = (int) Math.round(tenPercent);
        return new Vector2d(middleOfY - tenPercentOfHeight, Math.max(middleOfY + tenPercentOfHeight - 1, middleOfY));
    }

    @Override
    public String toString(){
        return visualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public Map<Vector2d, WorldElement> getElements() {
        Map<Vector2d, WorldElement> allElements = new HashMap<>();
        allElements.putAll(this.animals);
        allElements.putAll(this.grasses);
        return allElements;
    }

    @Override
    public UUID getId(){
        return this.id;
    }

    public void addAnimal(Animal animal){
        this.animals.put(animal.getPosition(), animal);
    }
    public void addObserver(MapChangeListener listener){
        this.observers.add(listener);
    }

    public void removeObserver(MapChangeListener listener){
        this.observers.remove(listener);
    }


    public void signalObservers(String message){
        for( MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }
    public Boundary getCurrentBounds(){
        return new Boundary(getLowerLeft(), getUpperRight());
    }

    protected  Vector2d getUpperRight(){
        return new Vector2d(width - 1, height - 1);
    }
    protected Vector2d getLowerLeft(){
        return new Vector2d(0, 0);
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    public List<Animal> getListOfAnimals(){
        return new ArrayList<>(animals.values());
    }

    public List<Grass> getListOfGrasses(){
        return new ArrayList<>(grasses.values());
    }

    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }

    public abstract Map<Vector2d, Water> getWaters();


    public int getGenomeLength() {
        return genomeLength;
    }

    public int getMaximumNumberOfMutations() {
        return maximumNumberOfMutations;
    }

    public int getMinimumNumberOfMutations() {
        return minimumNumberOfMutations;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MapType getMapType() {
        return mapType;
    }

    @Override
    public int getNumberOfFreeHexes(){
        return freeHexes.size();
    }

    public int getFreeHexesAboveEquator() {
        return freeHexesAboveEquator.size();
    }

    public int getFreeHexesBelowEquator() {
        return freeHexesBelowEquator.size();
    }

    public int getFreeHexesInEquator() {
        return freeHexesInEquator.size();
    }

    public boolean isAnimalAt(Vector2d position){
        return animals.get(position) != null;
    }

    public boolean isGrassAt(Vector2d position){
        return grasses.get(position) != null;
    }


    public boolean isWaterAt(Vector2d position){
        return false;
    }
}
