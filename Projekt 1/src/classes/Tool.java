package classes;

import classes.Enum_classes.ToolMaterials;
import classes.Enum_classes.ToolsNames;
import interfaces.Repairable;

public abstract class Tool implements Repairable {
    int damage=1000; // Łatwiej dopasowac do czasu zniszczenia
    public int damageToEntity=1;
    public ToolsNames toolsNames;
    ToolMaterials toolMaterials;

    public Tool() {
    }

    public Tool(ToolMaterials toolMaterials) {
        this.toolMaterials = toolMaterials;
        switch(this.toolMaterials) {
            case WOOD:
                this.damage = 1500;
                this.damageToEntity++;
                break;
            case STONE:
                this.damage = 2000;
                this.damageToEntity+=2;
                break;
            case GOLD:
                this.damage = 2500;
                this.damageToEntity+=3;
                break;
            case IRON:
                this.damage = 3000;
                this.damageToEntity+=3;
                break;
            case DIAMOND:
                this.damage = 3500;
                this.damageToEntity+=4;
                break;
        }
    }

    @Override
    public abstract void repair();

    @Override
    public String toString() {
        return ""+this.toolMaterials+" "+this.toolsNames;
    }
}
