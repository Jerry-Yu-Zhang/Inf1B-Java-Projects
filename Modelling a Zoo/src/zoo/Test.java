package zoo;

import dataStructures.CashCount;
import dataStructures.ICashCount;

public class Test {

    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        ICashCount cashSupply = new CashCount();
        ICashCount cashInserted = new CashCount();

        cashSupply.setNrNotes_20pounds(0);
        cashSupply.setNrNotes_10pounds(0);
        cashSupply.setNrNotes_5pounds(0);
        cashSupply.setNrCoins_2pounds(10);
        cashSupply.setNrCoins_1pound(10);
        cashSupply.setNrCoins_50p(10);
        cashSupply.setNrCoins_20p(10);
        cashSupply.setNrCoins_10p(10);

        cashInserted.setNrNotes_20pounds(2);
        cashInserted.setNrNotes_10pounds(0);
        cashInserted.setNrNotes_5pounds(0);
        cashInserted.setNrCoins_2pounds(0);
        cashInserted.setNrCoins_1pound(0);
        cashInserted.setNrCoins_50p(0);
        cashInserted.setNrCoins_20p(0);
        cashInserted.setNrCoins_10p(0);

        zoo.setEntranceFee(24, 60);
        zoo.setCashSupply(cashSupply);

        System.out.println("Entrance fee: " + zoo.getEntranceFee());
        System.out.println("Cash supply before purchase: " + zoo.getCashValue(zoo.getCashSupply()));
        System.out.println("Cash inserted: " + zoo.getCashValue(cashInserted));
        System.out.println("Cash returned: " + zoo.getCashValue(zoo.payEntranceFee(cashInserted)));
        System.out.println("Cash supply after purchase: " + zoo.getCashValue(zoo.getCashSupply()));
    }

}
