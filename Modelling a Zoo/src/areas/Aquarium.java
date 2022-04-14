package areas;

import animals.Animal;
import animals.Seal;
import animals.Shark;
import animals.Starfish;

import java.sql.ShardingKey;
import java.util.ArrayList;

public class Aquarium implements IArea, IHabitat{
    private int capacity;

    private ArrayList<Animal> animals = new ArrayList<Animal>();

    private ArrayList<Integer> adjacentAreas = new ArrayList<Integer>();

    public Aquarium(int capacity) {
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
        if (animal instanceof Seal
                || animal instanceof Shark
                || animal instanceof Starfish) {
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
