package classes;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class Inventory {
    public static Object[] inventory = new Object[36];
    private Random random = new Random();
    private JTextArea console;

    public Inventory(JTextArea console) {
        this.console = console;
        Arrays.fill(inventory, 0);
    }

    private void appendToConsole(String message) {
        console.append(message + "\n");
        console.setCaretPosition(console.getDocument().getLength());
    }

    public void showInventoryOptions() {
        //String[] options = {"Show inventory", "Remove from inventory (Specific item)", "Remove from inventory (Random item)", "Replace in inventory"};
        String[] options = {"Remove from inventory (Specific item)", "Remove from inventory (Random item)", "Replace in inventory"};
        String choice = (String) JOptionPane.showInputDialog(null, "Choose an option:", "Inventory Options",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice != null) {
            switch (choice) {
                //case "Show inventory" -> showInventory();
                case "Remove from inventory (Specific item)" -> {
                    String input = JOptionPane.showInputDialog("Choose number in inventory to remove:");
                    if (input != null) {
                        int location = Integer.parseInt(input);
                        removeFromInventory(location);
                    }
                }
                case "Remove from inventory (Random item)" -> removeFromInventory();
                case "Replace in inventory" -> {
                    String input1 = JOptionPane.showInputDialog("Choose item number 1:");
                    String input2 = JOptionPane.showInputDialog("Choose item number 2:");
                    if (input1 != null && input2 != null) {
                        int a = Integer.parseInt(input1);
                        int b = Integer.parseInt(input2);
                        replaceInInventory(a - 1, b - 1);
                    }
                }
                default -> JOptionPane.showMessageDialog(null, "There is no such option");
            }
        }
    }

    public void showInventory() {
        StringBuilder inventoryList = new StringBuilder();
        for (int i = 0; i < inventory.length; i++) {
            inventoryList.append((i + 1)).append(". ");
            if (inventory[i].equals(0))
                inventoryList.append("\n");
            else
                inventoryList.append(inventory[i].toString()).append("\n");
        }
        //appendToConsole(inventoryList.toString());
        JOptionPane.showMessageDialog(null, inventoryList.toString(), "Inventory", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isInInventory(Object object) {
        for (Object item : inventory) {
            if (item != null && item.getClass().equals(object.getClass())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInventoryFull() {
        for (Object item : inventory) {
            if (item.equals(0)||item==null) {
                return false;
            }
        }
        return true;
    }

    public void placeInInventory(Object object) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null || inventory[i].equals(0)) {
                inventory[i] = object;
                appendToConsole("Added " + object.getClass().getSimpleName() + " to inventory slot " + (i + 1));
                return; // Zatrzymaj po dodaniu przedmiotu do ekwipunku
            }
            appendToConsole("Proba dodania : "+i);
        }
        appendToConsole("Inventory is full");
    }


    public void removeFromInventory(int location) {
        if(inventory[location-1].equals(0)||inventory[location-1]==null){
            appendToConsole("There is no item in index: " + location + " in inventory");
        }else if ((location - 1) < inventory.length && (location - 1) > -1) {
            inventory[location - 1] = 0;
            appendToConsole("Removed item index: " + location);
        }
    }

    public void removeFromInventory() {
        int index = random.nextInt(inventory.length);
        inventory[index] = 0;
        appendToConsole("Removed random item at index: " + (index + 1));
    }

    public void replaceInInventory(int a, int b) {
        if (a < inventory.length && b < inventory.length && a >= 0 && b >= 0) {
            Object temp = inventory[b];
            inventory[b] = inventory[a];
            inventory[a] = temp;
            appendToConsole("Replaced items at indices: " + (a + 1) + " and " + (b + 1));
        } else if ((a >= inventory.length || a < 0) && (b < inventory.length && b >= 0)) {
            appendToConsole("There is no index a: " + (a + 1) + " in an inventory");
        } else if ((b >= inventory.length || b < 0) && (a < inventory.length && a >= 0)) {
            appendToConsole("There is no index b: " + (b + 1) + " in an inventory");
        } else {
            appendToConsole("There is no index a: " + (a + 1) + " and index b: " + (b + 1) + " in an inventory");
        }
    }
}


