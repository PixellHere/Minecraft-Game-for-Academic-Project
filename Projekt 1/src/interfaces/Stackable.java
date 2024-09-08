package interfaces;

import classes.Inventory;

public interface Stackable {
    int minQuantity=0;
    int maxQuantity=64;
    int count ();
    int addToStack(int quantity);
    void removeFromStack(int quantity, Inventory inventory);
}
