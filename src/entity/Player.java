package entity;

import main.GamePanel;
import main.KeyHandler;
//These classes were used in testing but are no longer needed.
//import java.awt.Graphics2D;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
// import java.awt.Color;
//import java.awt.image.BufferedImage;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    
    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        direction = "down";

        setDefaultValues();
    }

    public void setDefaultValues() {

        x = 1;
        y = 1;
        direction = "down";
    }
}
