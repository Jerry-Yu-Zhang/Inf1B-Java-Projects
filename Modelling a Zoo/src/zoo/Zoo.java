package zoo;

import animals.Animal;
import areas.Entrance;
import areas.IArea;
import areas.IHabitat;
import dataStructures.CashCount;
import dataStructures.ICashCount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Zoo implements IZoo{
    private HashMap<Integer, IArea> areaHashMap = new HashMap<Integer, IArea>();
    private Set<Integer> reachableAreas = new HashSet<>();
    private int recursionCount;
    private ICashCount cashSupply;
    private ICashCount cashReturned;
    private BigDecimal entranceFee;
    private BigDecimal cashValue;

    public Zoo() {
        Entrance entrance = new Entrance();
        this.addArea(entrance);
    }

    public BigDecimal getEntranceFee() {
        return entranceFee;
    }

    public ICashCount getCashReturned() {
        return cashReturned;
    }

    @Override
    public int addArea(IArea area) {
        if (areaHashMap.containsValue(area)){
            return -1;
        } else if (area instanceof Entrance) {
            if (areaHashMap.containsKey(0)) {
                return -1;
            } else {
                areaHashMap.put(0, area);
                return 0;
            }
        } else {
            int generatedId = (int) (Math.random() * (1000)) + 1;
            while (areaHashMap.containsKey(generatedId)) {
                generatedId = (int) (Math.random() * (1000)) + 1;
            }
            areaHashMap.put(generatedId, area);
            return generatedId;
        }
    }

    @Override
    public boolean removeArea(int areaId) {
        if (areaId == 0 || !areaHashMap.containsKey(areaId)){
            return false;
        } else {
            areaHashMap.remove(areaId);
            return true;
        }
    }

    @Override
    public IArea getArea(int areaId) {
        return areaHashMap.get(areaId);
    }

    @Override
    public byte addAnimal(int areaId, Animal animal) {
        IArea area = getArea(areaId);
        if (area instanceof IHabitat) {
            IHabitat habitat = (IHabitat) area;
            if (habitat.isHabitat(animal)) {
                ArrayList<Animal> animals = habitat.animalsIncluded();
                boolean status = true;
                if (!habitat.isFull()) {
                    for (int i = 0; i < animals.size(); i++) {
                        if (!animals.get(i).isCompatibleWith(animal)) {
                            status = false;
                        }
                    }
                    if (status) {
                        habitat.addAnimal(animal);
                    } else {
                        return Codes.INCOMPATIBLE_INHABITANTS;
                    }
                } else {
                    return Codes.HABITAT_FULL;
                }
            } else {
                return Codes.WRONG_HABITAT;
            }
        } else {
            return Codes.NOT_A_HABITAT;
        }
        return Codes.ANIMAL_ADDED;
    }

    @Override
    public void connectAreas(int fromAreaId, int toAreaId) {
        if (fromAreaId == toAreaId
                || getArea(fromAreaId) == null
                || getArea(toAreaId) == null ) {
        } else {
            getArea(fromAreaId).addAdjacentArea(toAreaId);
        }
    }

    @Override
    public boolean isPathAllowed(ArrayList<Integer> areaIds) {
        if (areaIds.size() == 0) {
            return false;
        } else if (areaIds.size() == 1) {
            return true;
        } else {
            boolean status = true;
            ArrayList<Integer> adjacentAreas;
            for (int i = 0; i < areaIds.size() - 1; i++) {
                adjacentAreas = getArea(areaIds.get(i)).getAdjacentAreas();
                if (!adjacentAreas.contains(areaIds.get(i + 1))) {
                    status = false;
                    break;
                }
            }
            return status;
        }
    }

    @Override
    public ArrayList<String> visit(ArrayList<Integer> areaIdsVisited) {
        if (!isPathAllowed(areaIdsVisited)) {
            return null;
        } else {
            ArrayList<String> animalVisited = new ArrayList<>();
            for (int i = 0; i < areaIdsVisited.size(); i++) {
                IArea area = getArea(areaIdsVisited.get(i));
                if (area instanceof IHabitat) {
                    IHabitat habitat = (IHabitat) area;
                    ArrayList<Animal> animals = habitat.animalsIncluded();
                    for (int j = 0; j < animals.size(); j++) {
                        animalVisited.add(animals.get(j).getNickname());
                    }
                }
            }
            return animalVisited;
        }
    }

    private void recurseFrom(int number) {
        reachableAreas.addAll(getArea(number).getAdjacentAreas());
        recursionCount ++;
        for (int i = 0; i < getArea(number).getAdjacentAreas().size(); i++) {
            while (recursionCount < Math.pow(areaHashMap.size(), areaHashMap.size())) {
                recurseFrom(getArea(number).getAdjacentAreas().get(i));
            }
        }
    }

    @Override
    public Set<Integer> findUnreachableAreas() {
        Set<Integer> unreachableAreas = new HashSet<Integer>(areaHashMap.keySet());
        reachableAreas.add(0);
        recursionCount = 0;
        recurseFrom(0);
        for (int unreachableElement : unreachableAreas) {
            for (int reachableElement : reachableAreas) {
                if (unreachableElement == reachableElement) {
                    unreachableAreas.remove(unreachableElement);
                }
            }
        }
        reachableAreas.clear();
        return unreachableAreas;
    }

    @Override
    public void setEntranceFee(int pounds, int pence) {
        entranceFee = BigDecimal.valueOf(pence).divide(BigDecimal.valueOf(100)).add(BigDecimal.valueOf(pounds));
    }

    @Override
    public void setCashSupply(ICashCount coins) {
        cashSupply = new CashCount();
        cashSupply.setNrNotes_20pounds(coins.getNrNotes_20pounds());
        cashSupply.setNrNotes_10pounds(coins.getNrNotes_10pounds());
        cashSupply.setNrNotes_5pounds(coins.getNrNotes_5pounds());
        cashSupply.setNrCoins_2pounds(coins.getNrCoins_2pounds());
        cashSupply.setNrCoins_1pound(coins.getNrCoins_1pound());
        cashSupply.setNrCoins_50p(coins.getNrCoins_50p());
        cashSupply.setNrCoins_20p(coins.getNrCoins_20p());
        cashSupply.setNrCoins_10p(coins.getNrCoins_10p());
    }

    @Override
    public ICashCount getCashSupply() {
        return cashSupply;
    }

    public BigDecimal getCashValue(ICashCount cashInserted) {
        cashValue = (BigDecimal.valueOf(cashInserted.getNrNotes_20pounds()).multiply(BigDecimal.valueOf(20))).
                add((BigDecimal.valueOf(cashInserted.getNrNotes_10pounds())).multiply(BigDecimal.valueOf(10))).
                add((BigDecimal.valueOf(cashInserted.getNrNotes_5pounds())).multiply(BigDecimal.valueOf(5))).
                add((BigDecimal.valueOf(cashInserted.getNrCoins_2pounds())).multiply(BigDecimal.valueOf(2))).
                add((BigDecimal.valueOf(cashInserted.getNrCoins_1pound())).multiply(BigDecimal.valueOf(1))).
                add((BigDecimal.valueOf(cashInserted.getNrCoins_50p())).multiply(BigDecimal.valueOf(0.5))).
                add((BigDecimal.valueOf(cashInserted.getNrCoins_20p())).multiply(BigDecimal.valueOf(0.2))).
                add((BigDecimal.valueOf(cashInserted.getNrCoins_10p())).multiply(BigDecimal.valueOf(0.1)));
        return cashValue;
    }

    @Override
    public ICashCount payEntranceFee(ICashCount cashInserted) {
        ICashCount cashSupplyClone = new CashCount();
        cashSupplyClone.setNrNotes_20pounds(cashSupply.getNrNotes_20pounds());
        cashSupplyClone.setNrNotes_10pounds(cashSupply.getNrNotes_10pounds());
        cashSupplyClone.setNrNotes_5pounds(cashSupply.getNrNotes_5pounds());
        cashSupplyClone.setNrCoins_2pounds(cashSupply.getNrCoins_2pounds());
        cashSupplyClone.setNrCoins_1pound(cashSupply.getNrCoins_1pound());
        cashSupplyClone.setNrCoins_50p(cashSupply.getNrCoins_50p());
        cashSupplyClone.setNrCoins_20p(cashSupply.getNrCoins_20p());
        cashSupplyClone.setNrCoins_10p(cashSupply.getNrCoins_10p());
        cashSupply.setNrNotes_20pounds(cashSupply.getNrNotes_20pounds() + cashInserted.getNrNotes_20pounds());
        cashSupply.setNrNotes_10pounds(cashSupply.getNrNotes_10pounds() + cashInserted.getNrNotes_10pounds());
        cashSupply.setNrNotes_5pounds(cashSupply.getNrNotes_5pounds() + cashInserted.getNrNotes_5pounds());
        cashSupply.setNrCoins_2pounds(cashSupply.getNrCoins_2pounds() + cashInserted.getNrCoins_2pounds());
        cashSupply.setNrCoins_1pound(cashSupply.getNrCoins_1pound() + cashInserted.getNrCoins_1pound());
        cashSupply.setNrCoins_50p(cashSupply.getNrCoins_50p() + cashInserted.getNrCoins_50p());
        cashSupply.setNrCoins_20p(cashSupply.getNrCoins_20p() + cashInserted.getNrCoins_20p());
        cashSupply.setNrCoins_10p(cashSupply.getNrCoins_10p() + cashInserted.getNrCoins_10p());
        if (getCashValue(cashInserted).compareTo(entranceFee) < 0) {
            cashSupply.setNrNotes_20pounds(cashSupplyClone.getNrNotes_20pounds());
            cashSupply.setNrNotes_10pounds(cashSupplyClone.getNrNotes_10pounds());
            cashSupply.setNrNotes_5pounds(cashSupplyClone.getNrNotes_5pounds());
            cashSupply.setNrCoins_2pounds(cashSupplyClone.getNrCoins_2pounds());
            cashSupply.setNrCoins_1pound(cashSupplyClone.getNrCoins_1pound());
            cashSupply.setNrCoins_50p(cashSupplyClone.getNrCoins_50p());
            cashSupply.setNrCoins_20p(cashSupplyClone.getNrCoins_20p());
            cashSupply.setNrCoins_10p(cashSupplyClone.getNrCoins_10p());
            return cashInserted;
        } else {
            cashReturned = new CashCount();
            BigDecimal exchange = (getCashValue(cashInserted).subtract(entranceFee)).multiply(BigDecimal.valueOf(100));
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(2000))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(2000))).compareTo(BigDecimal.valueOf(cashSupply.getNrNotes_20pounds())) <= 0)) {
                cashReturned.setNrNotes_20pounds(exchange.divideToIntegralValue(BigDecimal.valueOf(2000)).intValue());
                cashSupply.setNrNotes_20pounds(cashSupply.getNrNotes_20pounds() - cashReturned.getNrNotes_20pounds());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrNotes_20pounds())).multiply(BigDecimal.valueOf(2000)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(1000))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(1000))).compareTo(BigDecimal.valueOf(cashSupply.getNrNotes_10pounds())) <= 0)) {
                cashReturned.setNrNotes_10pounds(exchange.divideToIntegralValue(BigDecimal.valueOf(1000)).intValue());
                cashSupply.setNrNotes_10pounds(cashSupply.getNrNotes_10pounds() - cashReturned.getNrNotes_10pounds());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrNotes_10pounds())).multiply(BigDecimal.valueOf(1000)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(500))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(500))).compareTo(BigDecimal.valueOf(cashSupply.getNrNotes_5pounds())) <= 0)) {
                cashReturned.setNrNotes_5pounds(exchange.divideToIntegralValue(BigDecimal.valueOf(500)).intValue());
                cashSupply.setNrNotes_5pounds(cashSupply.getNrNotes_5pounds() - cashReturned.getNrNotes_5pounds());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrNotes_5pounds())).multiply(BigDecimal.valueOf(500)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(200))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(200))).compareTo(BigDecimal.valueOf(cashSupply.getNrCoins_2pounds())) <= 0)) {
                cashReturned.setNrCoins_2pounds(exchange.divideToIntegralValue(BigDecimal.valueOf(200)).intValue());
                cashSupply.setNrCoins_2pounds(cashSupply.getNrCoins_2pounds() - cashReturned.getNrCoins_2pounds());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrCoins_2pounds())).multiply(BigDecimal.valueOf(200)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(100))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(100))).compareTo(BigDecimal.valueOf(cashSupply.getNrCoins_1pound())) <= 0)) {
                cashReturned.setNrCoins_1pound(exchange.divideToIntegralValue(BigDecimal.valueOf(100)).intValue());
                cashSupply.setNrCoins_1pound(cashSupply.getNrCoins_1pound() - cashReturned.getNrCoins_1pound());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrCoins_1pound())).multiply(BigDecimal.valueOf(100)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(50))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(50))).compareTo(BigDecimal.valueOf(cashSupply.getNrCoins_50p())) <= 0)) {
                cashReturned.setNrCoins_50p(exchange.divideToIntegralValue(BigDecimal.valueOf(50)).intValue());
                cashSupply.setNrCoins_50p(cashSupply.getNrCoins_50p() - cashReturned.getNrCoins_50p());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrCoins_50p())).multiply(BigDecimal.valueOf(50)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(20))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(20))).compareTo(BigDecimal.valueOf(cashSupply.getNrCoins_20p())) <= 0)) {
                cashReturned.setNrCoins_20p(exchange.divideToIntegralValue(BigDecimal.valueOf(20)).intValue());
                cashSupply.setNrCoins_20p(cashSupply.getNrCoins_20p() - cashReturned.getNrCoins_20p());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrCoins_20p())).multiply(BigDecimal.valueOf(20)));
            }
            if (((exchange.divideToIntegralValue(BigDecimal.valueOf(10))).compareTo(BigDecimal.valueOf(0)) > 0)
                    && ((exchange.divideToIntegralValue(BigDecimal.valueOf(10))).compareTo(BigDecimal.valueOf(cashSupply.getNrCoins_10p())) <= 0)) {
                cashReturned.setNrCoins_10p(exchange.divideToIntegralValue(BigDecimal.valueOf(10)).intValue());
                cashSupply.setNrCoins_10p(cashSupply.getNrCoins_10p() - cashReturned.getNrCoins_10p());
                exchange = exchange.subtract((BigDecimal.valueOf(cashReturned.getNrCoins_10p())).multiply(BigDecimal.valueOf(10)));
            }
            if (exchange.compareTo(BigDecimal.valueOf(0)) == 0) {
                return cashReturned;
            } else {
                cashSupply.setNrNotes_20pounds(cashSupplyClone.getNrNotes_20pounds());
                cashSupply.setNrNotes_10pounds(cashSupplyClone.getNrNotes_10pounds());
                cashSupply.setNrNotes_5pounds(cashSupplyClone.getNrNotes_5pounds());
                cashSupply.setNrCoins_2pounds(cashSupplyClone.getNrCoins_2pounds());
                cashSupply.setNrCoins_1pound(cashSupplyClone.getNrCoins_1pound());
                cashSupply.setNrCoins_50p(cashSupplyClone.getNrCoins_50p());
                cashSupply.setNrCoins_20p(cashSupplyClone.getNrCoins_20p());
                cashSupply.setNrCoins_10p(cashSupplyClone.getNrCoins_10p());
                return cashInserted;
            }
        }
    }
}
