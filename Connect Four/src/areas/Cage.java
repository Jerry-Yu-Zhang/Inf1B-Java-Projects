package areas;

import animals.*;

import java.util.ArrayList;

public class Cage implements IArea, IHabitat{
    private int capacity;

    private ArrayList<Animal> animals = new ArrayList<Animal>();

    private ArrayList<Integer> adjacentAreas = new ArrayList<Integer>();

    public Cage(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public ArrayList<Integer> getAdjacentAreas() {
        return adjacentAreas;
    }

    @Override
    public void addAdjacentArea(int areaId) {
        adjacentAreas.add(areaId);
    }

    @Override
    public void removeAdjacentArea(int areaId) {
        adjacentAreas.remove(areaId);
    }

    @Override
    public ArrayList<Animal> animalsIncluded() {
        return animals;
    }

    @Override
    public boolean isHabitat(Animal animal) {
        if (animal instanceof Buzzard
                || animal instanceof Parrot) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isFull() {
        if (animals.size() >= capacity){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
}
