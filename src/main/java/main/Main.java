package main;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        ImageIcon iconMacOS = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/textures/icon/icon_macOS.png")));
        ImageIcon iconWindows = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/textures/icon/icon_full.png")));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Fishy");

        // for windows and linux
        window.setIconImage(iconWindows.getImage());

        // for macOS and other OS
        final Taskbar taskbar = Taskbar.getTaskbar();

        try {
            taskbar.setIconImage(iconMacOS.getImage());
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }


        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
