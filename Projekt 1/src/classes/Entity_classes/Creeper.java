package classes.Entity_classes;

import classes.Entity;
import classes.Player;
import classes.World;
import interfaces.Movable;

import java.util.Random;
import javax.swing.*;

public class Creeper extends Entity implements Movable {

    Random random = new Random();
    private Timer timer;
    public Creeper(String name) {
        super(name);
        Entity.entities.add(this);
//        this.positionX = random.nextInt(World.world.length);
//        this.positionY = random.nextInt(World.world.length);
        this.positionX=100;
        this.positionY=102;
        startMoving(true);
    }

    @Override
    public void hit() {
        for (Entity entity: Entity.entities) {
            if(entity instanceof Player){
                if (distance(entity,this)<=2){
                    startMoving(false);
                    try {
                        Thread.sleep(1300);
                        if (distance(entity,this)<=2){
                            entity.health-=6;
                            this.kill(this);
                        }else
                            startMoving(true);
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
