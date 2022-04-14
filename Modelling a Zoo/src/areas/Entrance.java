package areas;

import java.util.ArrayList;

public class Entrance implements IArea{
    private ArrayList<Integer> adjacentAreas = new ArrayList<Integer>();

    public Entrance(){

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
}
