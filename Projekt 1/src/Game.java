import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import classes.*;
import classes.Entity_classes.Creeper;
import classes.Entity_classes.Pig;
import classes.Enum_classes.ToolMaterials;
import classes.Tools_classes.Pickaxe;
import classes.Tools_classes.Sword;
import interfaces.Stackable;

public class Game extends JPanel {
    private static final int TILE_SIZE = 40; // Rozmiar jednego bloku w pikselach
    private static final int VIEW_SIZE = 20; // Ilość bloków widocznych na ekranie w obu kierunkach

    private World world;
    private Player player;
    private Inventory inventory;
    private BufferedImage playerImage;
    private BufferedImage creeperImage;
    private BufferedImage pigImage;
    private JTextArea console;

    public Game() {
        world = new World();
        console = new JTextArea(10, 50);
        inventory = new Inventory(console);
        inventory.placeInInventory(new Sword(ToolMaterials.DIAMOND));
        inventory.placeInInventory(new Pickaxe(ToolMaterials.IRON));
        player = new Player("Player", world, inventory, console);

        new Creeper("Creeper");
        new Pig("Pig");


        try {
            playerImage = ImageIO.read(new File("src/steve2_icon.png"));
            creeperImage = ImageIO.read(new File("src/creeper_head.png"));
            pigImage = ImageIO.read(new File("src/pig_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(TILE_SIZE * VIEW_SIZE, TILE_SIZE * VIEW_SIZE));
        setupUI();
    }

    private void setupUI() {
        JFrame frame = new JFrame("Game s30734");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(this, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton optionsButton = new JButton("Inventory Options");
        JButton showInventoryButton = new JButton("Show Inventory");
        JButton hitButton = new JButton("Hit");
        JButton mineButton = new JButton("Mine");
        JButton inspectButton = new JButton("Inspect");
        JButton placeButton = new JButton("Place Block");
        JButton changeItemButton = new JButton("Change Item");
        JButton removeFromStackButton = new JButton("Remove from stack");

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.showInventoryOptions();
                requestFocusInWindow(); // Przywrócenie focusa na panel gry po zamknięciu dialogu
            }
        });

        showInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventory.showInventory();
                requestFocusInWindow(); // Przywrócenie focusa na panel gry po zamknięciu dialogu
            }
        });

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.hit();
                requestFocusInWindow(); // Przywrócenie focusa na panel gry po zamknięciu dialogu
            }
        });

        mineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.mine();
                requestFocusInWindow(); // Przywrócenie focusa na panel gry po zamknięciu dialogu
            }
        });

        inspectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.inspectBlock();
                requestFocusInWindow();
            }
        });

        placeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.placeBlock();
                requestFocusInWindow();
                repaint(); // Odświeżenie widoku po umieszczeniu bloku
            }
        });

        changeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the inventory index (1-36):");
                try {
                    int index = Integer.parseInt(input) - 1;
                    if (index >= 0 && index < Inventory.inventory.length) {
                        player.setCurrentInHand(index);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid index. Please enter a number between 1 and 36.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
                }
                requestFocusInWindow();
            }
        });

        removeFromStackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter amount to remove from stack (1-64):");
                try {
                    int amount = Integer.parseInt(input);
                    if (amount >= 1 && amount <= Stackable.maxQuantity) {
                        if(Inventory.inventory[player.getCurrentIndex()] instanceof Block){
                            ((Block)Inventory.inventory[player.getCurrentIndex()]).removeFromStack(amount,inventory);
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "Object in hand is not a Block");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a number between 1 and 64.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
                }
                requestFocusInWindow();
            }
        });

        controlPanel.add(optionsButton);
        controlPanel.add(showInventoryButton);
        controlPanel.add(hitButton);
        controlPanel.add(mineButton);
        controlPanel.add(inspectButton);
        controlPanel.add(placeButton);
        controlPanel.add(changeItemButton);
        controlPanel.add(removeFromStackButton);

        // Konsola
        console.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(console);

        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.EAST); // Konsola po prawej stronie

        frame.pack();
        frame.setVisible(true);

        // Umożliwienie poruszania się gracza za pomocą klawiszy WASD
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_W:
                        player.moveUp();
                        break;
                    case java.awt.event.KeyEvent.VK_S:
                        player.moveDown();
                        break;
                    case java.awt.event.KeyEvent.VK_A:
                        player.moveLeft();
                        break;
                    case java.awt.event.KeyEvent.VK_D:
                        player.moveRight();
                        break;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWorld(g);
    }

    private void drawWorld(Graphics g) {
        int offsetX = Player.positionX - VIEW_SIZE / 2;
        int offsetY = Player.positionY - VIEW_SIZE / 2;

        for (int i = 0; i < VIEW_SIZE; i++) {
            for (int j = 0; j < VIEW_SIZE; j++) {
                int worldX = i + offsetX;
                int worldY = j + offsetY;

                if (worldX >= 0 && worldX < 200 && worldY >= 0 && worldY < 200) {
                    Block block = World.world[worldX][worldY];
                    switch (block.getColor()) {
                        case "green":
                            g.setColor(Color.GREEN);
                            break;
                        case "gray":
                            g.setColor(Color.GRAY);
                            break;
                        default:
                            g.setColor(Color.BLACK);
                    }
                    g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Rysowanie gracza jako obrazek
        if (playerImage != null) {
            g.drawImage(playerImage, VIEW_SIZE / 2 * TILE_SIZE, VIEW_SIZE / 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        } else {
            // Fallback to a colored square if the image failed to load
            g.setColor(Color.RED);
            g.fillRect(VIEW_SIZE / 2 * TILE_SIZE, VIEW_SIZE / 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        for (Entity entity : Entity.entities) {
            if (entity instanceof Creeper) {
                if (creeperImage != null) {
                    g.drawImage(creeperImage, (entity.positionX - offsetX) * TILE_SIZE, (entity.positionY - offsetY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                } else {
                    g.setColor(Color.DARK_GRAY); // Fallback if the image fails to load
                    g.fillRect((entity.positionX - offsetX) * TILE_SIZE, (entity.positionY - offsetY) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            } else if(entity instanceof Pig){
                if (pigImage != null) {
                    g.drawImage(pigImage, (entity.positionX - offsetX) * TILE_SIZE, (entity.positionY - offsetY) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                } else {
                    g.setColor(Color.RED); // Fallback if the image fails to load
                    g.fillRect((entity.positionX - offsetX) * TILE_SIZE, (entity.positionY - offsetY) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}









