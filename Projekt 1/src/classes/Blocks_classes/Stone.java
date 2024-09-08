package classes.Blocks_classes;

import classes.Block;
import classes.Enum_classes.ToolsNames;
import classes.Inventory;

public class Stone extends Block {

    public Stone() {
        this.name="Stone";
        this.timeToDestroy=5500;
        this.bestToDestroy= ToolsNames.PICKAXE;
        this.blockQuantity=1;
    }
    public String getColor() {
        return "gray";
    }
}
