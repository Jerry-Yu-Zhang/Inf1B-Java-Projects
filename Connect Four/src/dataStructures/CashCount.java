package dataStructures;

import java.util.HashMap;

public class CashCount implements ICashCount{
    public CashCount() {
        cash.put(2000, 0);
        cash.put(1000, 0);
        cash.put(500, 0);
        cash.put(200, 0);
        cash.put(100, 0);
        cash.put(50, 0);
        cash.put(20, 0);
        cash.put(10, 0);
    }

    HashMap<Integer,Integer> cash = new HashMap<Integer,Integer>();

    @Override
    public void setNrNotes_20pounds(int noteCount) {
        cash.put(2000, noteCount);
    }

    @Override
    public void setNrNotes_10pounds(int noteCount) {
        cash.put(1000, noteCount);
    }

    @Override
    public void setNrNotes_5pounds(int noteCount) {
        cash.put(500, noteCount);
    }

    @Override
    public void setNrCoins_2pounds(int coinCount) {
        cash.put(200, coinCount);
    }

    @Override
    public void setNrCoins_1pound(int coinCount) {
        cash.put(100, coinCount);
    }

    @Override
    public void setNrCoins_50p(int coinCount) {
        cash.put(50, coinCount);
    }

    @Override
    public void setNrCoins_20p(int coinCount) {
        cash.put(20, coinCount);
    }

    @Override
    public void setNrCoins_10p(int coinCount) {
        cash.put(10, coinCount);
    }

    @Override
    public int getNrNotes_20pounds() {
        return cash.get(2000);
    }

    @Override
    public int getNrNotes_10pounds() {
        return cash.get(1000);
    }

    @Override
    public int getNrNotes_5pounds() {
        return cash.get(500);
    }

    @Override
    public int getNrCoins_2pounds() {
        return cash.get(200);
    }

    @Override
    public int getNrCoins_1pound() {
        return cash.get(100);
    }

    @Override
    public int getNrCoins_50p() {
        return cash.get(50);
    }

    @Override
    public int getNrCoins_20p() {
        return cash.get(20);
    }

    @Override
    public int getNrCoins_10p() {
        return cash.get(10);
    }
}
