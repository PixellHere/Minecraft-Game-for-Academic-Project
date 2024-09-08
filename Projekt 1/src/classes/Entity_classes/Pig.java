package classes.Entity_classes;

import classes.Entity;
import classes.World;
import interfaces.Movable;

import javax.swing.*;
import java.util.Random;

public class Pig extends Entity implements Movable {
    Random random = new Random();
    private Timer timer;
    public Pig(String name) {
        super(name);
        Entity.entities.add(this);
//        this.positionX = random.nextInt(World.world.length);
//        this.positionY = random.nextInt(World.world.length);
        this.positionX=107;
        this.positionY=100;
        startMoving(true);
    }

    @Override
    public void hit() {
        for (Entity entity: Entity.entities) {
            if(entity instanceof Creeper){
                if (distance(entity,this)<=1){
                   entity.health--;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void move() {
        int randomWalking = random.nextInt(4);
        switch(randomWalking){
            case 0 -> moveUp();
            case 1 -> moveDown();
            case 2 -> moveLeft();
            case 3 -> moveRight();
        }
    }

    private void startMoving(Boolean isWalking) {
        timer = new Timer(1000, e -> {
            move();
            checkHealth();
        });
        if(isWalking) {
            timer.start();
        }else{
            timer.stop();
        }
    }

    @Override
    public void checkHealth() {
        if (this.health <= 0) {
            if (timer != null) {
                timer.stop();
            }
            Entity.entities.remove(this);
            System.out.println(this.toString() + " died!!!");
        } else {
            super.checkHealth();
        }
    }
}
