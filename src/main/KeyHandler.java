package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{//KeyHandler requires you to add unimplemented methods to it. It calls the methods keyTyped, keyPressed, and keyReleased

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    } //No need to use keyTyped.

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode(); //getKeyCode returns a number of the key that was pressed. Each key has a number associated with it.

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }                
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }      
        
    }/* //The listener interface for receiving keyboard events

    ]*/

}