package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Stewie's 2D Adventure");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        
        window.setLocationRelativeTo(null);

        window.setVisible(true);

        gamePanel.startGameThread();

        JOptionPane.showMessageDialog(null, "Help Stewie get the key and escape through the locked door! Beware of lava and don't forget Rupert! \n Use WASD keys to move. Press Escape to close the window once you succeed (green screen), or if you die (red screen).","How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

}