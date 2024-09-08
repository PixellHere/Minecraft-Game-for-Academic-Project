package classes;

import classes.Blocks_classes.None;
import classes.Enum_classes.ToolsNames;
import interfaces.Stackable;

import javax.swing.*;

public abstract class Block implements Stackable {
    public String name;
    public int blockQuantity;

    public int timeToDestroy = 5000; // W mili sekundach, 5000 to domyślna wartość
    public ToolsNames bestToDestroy;

    public Block(){
    }

    public Block(String name) {
        this.name = name;
    }

    public void place(){
        if(World.world[Player.positionX][Player.positionY] instanceof None) {
            World.world[Player.positionX][Player.positionY] = this;
            System.out.println("Zaktualizowano swiat gry? "+this.toString());
        }
        else
            System.out.println("This place is currently occupied by another block");
    }

    public void collect(Inventory inventory) {

        if (inventory.isInventoryFull()) {
            System.out.println("Inventory is full, you have to remove an item");
        } else {
            for (Object item : Inventory.inventory) {
                if (item != null && item.getClass().equals(this.getClass())) {
                    ((Block) item).addToStack(this.blockQuantity);
                    System.out.println("Added " + this.blockQuantity + " " + this.getClass().getSimpleName() + " to an existing stack.");
                    return;
                }
            }
            inventory.placeInInventory(this);
            System.out.println("Added " + this.getClass().getSimpleName() + " to inventory.");
        }
    }



    //DONE
    @Override
    public int count() {
        return blockQuantity;
    }

    //DONE
    @Override
    public int addToStack(int quantity) {
        if(quantity + blockQuantity <= Stackable.maxQuantity){
            blockQuantity+=quantity;
            return 0;
        }
        else{
            blockQuantity=Stackable.maxQuantity;
            return blockQuantity+quantity-Stackable.maxQuantity;
        }
    }

    public void removeFromStack(int quantity, Inventory inventory) {
        System.out.println("Attempting to remove " + quantity + " from stack with current quantity: " + blockQuantity);
        if (blockQuantity > quantity) {
            blockQuantity -= quantity;
            System.out.println("After removal, remaining quantity: " + blockQuantity);
        } else if (blockQuantity == quantity) {
            blockQuantity = 0;
            System.out.println("All blocks removed from stack. Removing block from inventory.");
            removeBlockFromInventory(inventory);
        } else {
            System.out.println("Error: Attempted to remove more blocks than available.");
        }
    }

    public void removeBlockFromInventory(Inventory inventory) {
        System.out.println("Removing block from inventory.");
        for (int i = 0; i < Inventory.inventory.length; i++) {
            if (Inventory.inventory[i] instanceof Block && Inventory.inventory[i].getClass() == this.getClass()) {
                inventory.removeFromInventory(i + 1);
                return;
            }
        }
    }


    public abstract String getColor();
    @Override
    public String toString() {
        return name+" "+blockQuantity;
    }
}
