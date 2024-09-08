package classes.Tools_classes;

import classes.Enum_classes.ToolMaterials;
import classes.Enum_classes.ToolsNames;
import classes.Inventory;
import classes.Tool;

import java.util.Scanner;

public class Pickaxe extends Tool {

    Scanner scanner = new Scanner(System.in);
    public Pickaxe(ToolMaterials toolMaterials) {
        super(toolMaterials);
        toolsNames=ToolsNames.PICKAXE;
    }

    @Override
    public void repair() {
        System.out.print("Give index of another item to repair current item: ");
        int index = scanner.nextInt() - 1;
        if(index>=0&&index< Inventory.inventory.length){
            if(Inventory.inventory[index] instanceof Pickaxe){
                Inventory.inventory[index]=0;
                System.out.println("Item have been repaired!");
            }
            else{
                System.out.println("Item on "+(index+1)+"is not Pickaxe!");
            }
        }
        else
            System.out.println("There is no index "+(index+1));
    }
}
