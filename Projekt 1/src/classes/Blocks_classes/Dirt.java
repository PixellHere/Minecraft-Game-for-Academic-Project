package classes.Blocks_classes;

import classes.Block;
import classes.Enum_classes.ToolsNames;
import classes.Inventory;

public class Dirt extends Block {
    public Dirt() {
        this.name="Dirt";
        this.timeToDestroy=5000;
        this.bestToDestroy= ToolsNames.SHOVEL;
        this.blockQuantity=1;
    }
    public String getColor() {
        return "green";
    }
}
