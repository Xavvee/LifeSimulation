package agh.ics.oop.model.Map;

import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place a animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    boolean place(Animal animal) throws PositionAlreadyOccupied;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    /**
     * Return all elements on the map.
     *
     * @return hashmap of all elements on map.
     */
    Map<Vector2d, WorldElement> getElements();

    /**
     * @return current boundaries of the map.
     */
    Boundary getCurrentBounds();

    /**
     * @return Identificator of map
     */
    UUID getId();

    /**
     * Function that generates starting grass.
     * @return true if grass was correctly generated
     */
    boolean generateGrass();

    /**
     * Function that generates starting animals.
     * @return true if animal was spawned
     */
    boolean generateAnimal();

    /**
     * Function that spawns grass every day.
     * @return true if grass was correctly spawned
     */
    void spawnGrass();

    /**
     * Function that returns lower and upper bounds of the equator.
     * @return Vector2d object where first coordinate is lower value of y - coordinate, second coordinate is the upper value of y - coordinate.
     */
    Vector2d getEquatorBounds();

    /**
     * Function that helps control the animals.
     * @return List of animals on map.
     */
    List<Animal> getListOfAnimals();


    /**
     * Function that helps control the grasses.
     * @return List of grasses on map.
     */
    List<Grass> getListOfGrasses();

}
