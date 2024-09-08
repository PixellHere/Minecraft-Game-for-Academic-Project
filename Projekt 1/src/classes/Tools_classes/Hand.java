package classes.Tools_classes;

import classes.Tool;

public class Hand extends Tool {

    public Hand() {
    }

    @Override
    public void repair() {
        System.out.println("You can't repair your hand");
    }
}
