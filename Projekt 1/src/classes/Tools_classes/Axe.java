package classes.Tools_classes;

import classes.Enum_classes.ToolMaterials;
import classes.Enum_classes.ToolsNames;
import classes.Inventory;
import classes.Tool;

import java.util.Scanner;

public class Axe extends Tool {

    Scanner scanner = new Scanner(System.in);
    public Axe(ToolMaterials toolMaterials) {
        super(toolMaterials);
        toolsNames = ToolsNames.AXE;
        damageToEntity+=1;
    }

    @Override
    public void repair() {
        System.out.print("Give index of another item to repair current item: ");
        int index = scanner.nextInt() - 1;
        if(index>=0&&index<Inventory.inventory.length){
            if(Inventory.inventory[index] instanceof Axe){
                Inventory.inventory[index]=0;
                System.out.println("Item have been repaired!");
            }
            else{
                System.out.println("Item on "+(index+1)+"is not Axe!");
            }
        }
        else
            System.out.println("There is no index "+(index+1));
    }
}
