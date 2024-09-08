package classes;

import classes.Blocks_classes.None;
import classes.Tools_classes.Hand;
import interfaces.Movable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Player extends Entity implements Movable {
    private Object currentInHand;
    private int currentIndex;
    private World world;
    private JTextArea textArea;
    private Inventory inventory;

    public static int positionX;
    public static int positionY;

    private final Set<Integer> pressedKeys = new HashSet<>();

    public Player(String name) {
        super(name);
        positionX = World.world.length / 2;
        positionY = World.world.length / 2;
        setCurrentInHand(0);
    }

    public Player(String name, World world,Inventory inventory, JTextArea textArea) {
        super(name);
        this.world = world;
        this.inventory=inventory;
        this.textArea = textArea;
        positionX = World.world.length / 2;
        positionY = World.world.length / 2;
        setCurrentInHand(0);
    }

    public void setCurrentInHand(int n) {
        currentIndex = n;
        if (Inventory.inventory[n] == null || Inventory.inventory[n].equals(0))
            currentInHand = new Hand();
        else
            this.currentInHand = Inventory.inventory[n];
    }

    public void placeBlock() {
        Object blockToPlace = currentInHand;
        if (blockToPlace instanceof Block) {
            Block block = (Block) blockToPlace;
            if (block.blockQuantity >= 1 && World.world[Player.positionX][Player.positionY] instanceof None) {
                block.place();
                System.out.println("Before removing from stack: " + block.blockQuantity);
                ((Block) Inventory.inventory[currentIndex]).removeFromStack(1, inventory);
                System.out.println("After removing from stack: " + block.blockQuantity);
                appendToConsole("Placed " + block.getClass().getSimpleName() + " at (" + (positionX - offset) + ", " + (positionY - offset) + ")");
            } else {
                appendToConsole("You don't have blocks anymore or the place is occupied.");
            }
        } else {
            appendToConsole("You do not have a valid block in hand to place.");
        }
    }

    @Override
    public void hit() {
        Entity entity = this.findClosestEntity();
        if (entity == null) {
            appendToConsole("No entities to hit");
            return;
        }
        double distance = distance(this, entity);
        if (distance <= 2) {
            if (currentInHand instanceof Tool)
                entity.health -= ((Tool) currentInHand).damageToEntity;
            else
                entity.health--;

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            appendToConsole("You are too far to hit");
        }
    }

    public void moveUp() {
        if (positionY - 1 < 0) {
            appendToConsole("You can't move up no more");
        } else {
            positionY--;
            //appendToConsole("Moved up to (" + (positionX - offset) + ", " + (positionY - offset) + ")");
        }
    }

    public void moveDown() {
        if (positionY + 1 >= World.world.length) {
            appendToConsole("You can't move down no more");
        } else {
            positionY++;
            //appendToConsole("Moved down to (" + (positionX - offset) + ", " + (positionY - offset) + ")");
        }
    }

    public void moveLeft() {
        if (positionX - 1 < 0) {
            appendToConsole("You can't move left no more");
        } else {
            positionX--;
            //appendToConsole("Moved left to (" + (positionX - offset) + ", " + (positionY - offset) + ")");
        }
    }

    public void moveRight() {
        if (positionX + 1 >= World.world.length) {
            appendToConsole("You can't move right no more");
        } else {
            positionX++;
            //appendToConsole("Moved right to (" + (positionX - offset) + ", " + (positionY - offset)+ ")");
        }
    }

    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void move() {
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            moveUp();
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            moveLeft();
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            moveDown();
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            moveRight();
        }
        //appendToConsole("Player moved to position: (" + (positionX - offset) + ", " + (positionY - offset) + ")");
    }


    public void mine() {
        new Thread(() -> {
            if (World.world[positionX][positionY].getClass().equals(None.class)) {
                appendToConsole("There is no block here!");
            } else {
                appendToConsole("Mining...");
                int currentTimeToDestroy = World.world[positionX][positionY].timeToDestroy;
                if (currentInHand instanceof Tool) {
                    if (World.world[positionX][positionY].bestToDestroy.equals(((Tool) currentInHand).toolsNames)) {
                        currentTimeToDestroy -= ((Tool) currentInHand).damage;
                    }
                }
                try {
                    Thread.sleep(currentTimeToDestroy);
                    appendToConsole("Mined "+World.world[positionX][positionY].toString()+"!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                SwingUtilities.invokeLater(() -> {
                    try {
                        Block block = World.world[positionX][positionY];
                        //appendToConsole("Before collect");
                        block.collect(inventory);
                        //appendToConsole("Po collect");
                        World.world[positionX][positionY] = new None();
                        //appendToConsole("Block replaced with None");
                    } catch (Exception e) {
                        appendToConsole("Error during block collection: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    public void inspectBlock() {
        appendToConsole("You are standing on " + World.world[Player.positionX][Player.positionY].toString());
    }

    private void appendToConsole(String message) {
        textArea.append(message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public int getPositionX() {
        return Player.positionX;
    }

    @Override
    public int getPositionY() {
        return Player.positionY;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}



