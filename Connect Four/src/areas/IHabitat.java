package areas;

import animals.Animal;

import java.util.ArrayList;

public interface IHabitat {
    public ArrayList<Animal> animalsIncluded();

    public boolean isHabitat(Animal animal);

    public boolean isFull ();

    public void addAnimal(Animal animal);
}
