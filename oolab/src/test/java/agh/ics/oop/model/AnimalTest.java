package agh.ics.oop.model;

import agh.ics.oop.model.Elements.Animal;
import org.junit.jupiter.api.Test;

public class AnimalTest {

    @Test
    public void rotateTest(){
        //given
        Animal animal = new Animal(new Vector2d(2,2), 20);
        System.out.println(animal.getDirection());
        //when
        animal.rotate();
        System.out.println(animal.getDirection());
        animal.rotate();
        System.out.println(animal.getDirection());
        animal.rotate();
        System.out.println(animal.getDirection());
        animal.rotate();
        //then
        System.out.println(animal.getDirection());
        System.out.println(animal.getGenotype().getGenotype());
    }

    @Test
    public void moveTest(){

    }
}
