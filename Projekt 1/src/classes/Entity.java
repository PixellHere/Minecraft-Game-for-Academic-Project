package classes;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    public String name;
    public int health=10;
    boolean onFire = false;
    public int positionX;
    public int positionY;
    final int offset = World.world.length/2;
    public static List<Entity> entities = new ArrayList<>();
    public Entity(String name) {
        this.name = name;
    }

    public abstract void hit();

    public void kill(Entity entity){
        entity.health=0;
        Entity.entities.remove(this);
    }

    public boolean isOnFire() {
        return this.onFire;
    }

    public void setOnFire(Entity entity){
        for (int i = 0; i < 3; i++) {
            entity.health--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getStatus(){
        System.out.println("Health: "+health);
        System.out.println(isOnFire()?"On fire!":"Not on fire");
        System.out.println("Current location: x: "+(getPositionX()-offset)+" y: "+(getPositionY()-offset));
    }
    public void checkHealth() {
        List<Entity> entitiesToRemove = new ArrayList<>();

        for (Entity entity : Entity.entities) {
            if (entity.health <= 0 && !(entity instanceof Player)) {
                entitiesToRemove.add(entity);
                System.out.println(entity.toString() + " died!!!");
            }
        }

        Entity.entities.removeAll(entitiesToRemove);

        for (Entity entity : Entity.entities) {
            if (entity instanceof Player && entity.health <= 0) {
                System.out.println("Game Over! Player has died.");
                System.exit(0);
            }
        }
    }

    public void moveUp() {
        if(this.positionY+1>=World.world.length)
            System.out.println("You can't move up no more");
        else
            this.positionY++;
    }

    public void moveDown() {
        if(this.positionY-1<0)
            System.out.println("You can't move up no more");
        else
            this.positionY--;
    }

    public void moveLeft() {
        if(this.positionX-1<0)
            System.out.println("You can't move up no more");
        else
            this.positionX--;
    }

    public void moveRight() {
        if(this.positionX+1>=World.world.length)
            System.out.println("You can't move up no more");
        else
            this.positionX++;
    }

    public Double distance(Entity e1, Entity e2){
        return Math.sqrt(Math.pow(e1.getPositionX()-e2.getPositionX(),2)+Math.pow(e1.getPositionY()-e2.getPositionY(),2));
    }

    public Entity findClosestEntity(){
        Entity closestEntity = null;
        double minDistance = Double.MAX_VALUE;
        for (Entity entity: Entity.entities) {
            double entityDistance = distance(this, entity);
            System.out.println("Distance to " + entity + ": " + entityDistance);
            if (entityDistance < minDistance) {
                minDistance = entityDistance;
                closestEntity = entity;
            }
        }
        System.out.println("Closest entity: " + closestEntity + " at distance: " + minDistance);
        return closestEntity;
    }

    public int getPositionX(){
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
