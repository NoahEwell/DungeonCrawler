package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import entity.Player;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 20;
    final int screenWidth = tileSize * maxScreenRow;
    final int screenHeight = tileSize * maxScreenCol; 
    
    //Constructers + class logic
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //repeats gameThread
    Player player = new Player(this, keyH);

    //Game vars
    int[][] map = new int[maxScreenRow][maxScreenCol];
    int[][] lava = new int[maxScreenRow][maxScreenCol];

    
    int playerX = 1;
    int playerY = 1;
    int FPS = 15;
    boolean gameLost = false;
    boolean gameWon  = false;
    boolean playerHasKey = false;

    //object constants
    int PLAYER = 1;
    int WALL   = 2;
    int LAVA   = 3;
    int KEY    = 4;
    int DOOR   = 5;
    int TEDDY  = 6;

    //gfx
    BufferedImage imgUp;
    BufferedImage imgDown;
    BufferedImage imgLeft;
    BufferedImage imgRight;
    BufferedImage imgWall;
    BufferedImage imgLava;
    BufferedImage imgDoor;
    BufferedImage imgKey;
    BufferedImage imgTeddy;
    
    Random random = new Random();

    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); /* If .setDoubleBuffered() is set to true, all the drawing from this component will be done in an offscreen painting buffer. this helps with game rendering images. */
        this.addKeyListener(keyH);
        this.setFocusable(true); //with this, the GamePanel can be "focused" to receive key input.
        
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start(); //Here gameThread automatically calls the run method.

        //create our lava
        for (int i = 0; i < 80; i++) {
            lava[random.nextInt(maxScreenRow-2) + 2][random.nextInt(maxScreenCol-2) + 2] = LAVA;
        }
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j <maxScreenCol; j++) {
                map[i][j] = lava[i][j];
            }
        }

        //create our walls
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                if (i == 0 || i == maxScreenRow-1) {
                    map[i][j] = WALL;
                }
                else if (j == 0 || j == maxScreenCol-1) {
                    map[i][j] = WALL;
                }
            }
        }

        map[random.nextInt(maxScreenRow-3) + 2][random.nextInt(maxScreenCol-3) + 2] = KEY;
        map[random.nextInt(maxScreenRow-3) + 2][random.nextInt(maxScreenCol-3) + 2] = DOOR;
        map[random.nextInt(maxScreenRow-3) + 2][random.nextInt(maxScreenCol-3) + 2] = TEDDY;

        //load images into memory
        
        try {
            imgUp = ImageIO.read(getClass().getResourceAsStream("up.png"));
            imgDown = ImageIO.read(getClass().getResourceAsStream("down.png"));
            imgLeft = ImageIO.read(getClass().getResourceAsStream("left.png"));
            imgRight = ImageIO.read(getClass().getResourceAsStream("right.png"));
            imgWall = ImageIO.read(getClass().getResourceAsStream("wall.png"));
            imgLava = ImageIO.read(getClass().getResourceAsStream("lava.png"));
            imgDoor = ImageIO.read(getClass().getResourceAsStream("door.png"));
            imgKey = ImageIO.read(getClass().getResourceAsStream("key.png"));
            imgTeddy = ImageIO.read(getClass().getResourceAsStream("teddy.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        System.out.println("Use WASD keys to move");

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
            update();
            repaint();
            delta--;
            }    
        }
    }
        
    public void update() {

        if (keyH.upPressed == true) {
            if (!checkCollide(player.x, player.y, 0, -1, map)) {
                player.y -= 1;
                player.direction = "up";
            }
        }
        else if(keyH.downPressed == true) {
            if (!checkCollide(player.x, player.y, 0, 1, map)) {
                player.y += 1;
                player.direction = "down";
            }
        }
        else if(keyH.leftPressed == true) {
            if (!checkCollide(player.x, player.y, -1, 0, map)) {
                player.x -= 1;
                player.direction = "left";
            }
        }
        else if(keyH.rightPressed == true) {
            if (!checkCollide(player.x, player.y, 1, 0, map)) {
                player.x += 1;
                player.direction = "right";
            }
        }

        map[player.x][player.y] = PLAYER;

    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g); 

        Graphics2D g2 = (Graphics2D)g;

        if (gameLost == true) {
            g2.setColor(Color.red);
            
            g2.fillRect(0, 0, maxScreenRow*tileSize, maxScreenCol*tileSize);
            return;
        }

        if (gameWon == true) {
            g2.setColor(Color.GREEN);
            
            g2.fillRect(0, 0, maxScreenRow*tileSize, maxScreenCol*tileSize);
            return;
        }

        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                BufferedImage image = null;

                if (map[i][j] == PLAYER) {
                    switch(player.direction) {
                        case "up":
                            image = imgUp;
                            break;
                        case "down":
                            image = imgDown;
                            break;
                        case "left":
                            image = imgLeft;
                            break;
                        case "right":
                            image = imgRight;
                            break;
                        }
                        g2.drawImage(image, player.x*tileSize, player.y*tileSize, tileSize, tileSize, null);
                }

                else if (map[i][j] == WALL) {
                    image = imgWall;
                    g2.drawImage(image, i*tileSize, j*tileSize, tileSize, tileSize, null);
                }
                else if (map[i][j] == LAVA) {
                    image = imgLava;
                    g2.drawImage(image, i*tileSize, j*tileSize, tileSize, tileSize, null);
                }
                else if (map[i][j] == KEY) {
                    image = imgKey;
                    g2.drawImage(image, i*tileSize, j*tileSize, tileSize, tileSize, null);
                }
                else if (map[i][j] == DOOR) {
                    image = imgDoor;
                    g2.drawImage(image, i*tileSize, j*tileSize, tileSize, tileSize, null);
                }
                else if (map[i][j] == TEDDY) {
                    image = imgTeddy;
                    g2.drawImage(image, i*tileSize, j*tileSize, tileSize, tileSize, null);
                }
            }
        }

        g2.dispose(); 
    }

    public boolean checkCollide(int playerX, int playerY, int veloX, int veloY, int[][] map) {
    
        if (((playerX + veloX) < 0 || (playerY + veloY) < 0) || ((playerX + veloX) > 25 || (playerY + veloY) > 25)) {
            return true;
        }

        if (map[playerX + veloX][playerY + veloY] == WALL) {
            return true;
        }
        else if (map[playerX + veloX][playerY + veloY] == LAVA) {
            gameLost = true;
            return true;
        }
        else if (map[playerX + veloX][playerY + veloY] == KEY) {
            playerHasKey = true;
            return false;
        }
        else if (map[playerX + veloX][playerY + veloY] == DOOR) {
            if (playerHasKey == true) {
                gameWon = true;
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
}
