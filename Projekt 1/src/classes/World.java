package classes;

import classes.Blocks_classes.Dirt;
import classes.Blocks_classes.Stone;

import java.util.Arrays;
import java.util.Random;

public class World {
    public static final Block[][] world = new Block[200][200];
    final int offset = World.world.length/2; //Zmienia wartości i i j z tablicy by na końcu mieć możliwość minusowych koordynatów

    public World() {
        //Arrays.fill(world,new Dirt());
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                world[i][j] = new Dirt();
            }
        }
        Random random = new Random();
        for(int i=0; i<400; i++){
            world[random.nextInt(World.world.length)][random.nextInt(World.world.length)]=new Stone();
        }
    }
}