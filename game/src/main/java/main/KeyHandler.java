package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, selectItemPressed, plusPressed, minusPressed, gPressed, spacePressed;
    public boolean arrowLeftPressed, arrowRightPressed;
    public int selectedInventoryItem;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_6) {
            selectItemPressed = true;
            selectedInventoryItem = code - 48;
        }
        if (code == KeyEvent.VK_P) {
            plusPressed = true;
        }
        if (code == KeyEvent.VK_O) {
            minusPressed = true;
        }
        if (code == KeyEvent.VK_G) {
            gPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            arrowLeftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            arrowRightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_6) {
            selectItemPressed = false;
        }
        if (code == KeyEvent.VK_P) {
            plusPressed = false;
        }
        if (code == KeyEvent.VK_O) {
            minusPressed = false;
        }
        if (code == KeyEvent.VK_G) {
            gPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            arrowLeftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            arrowRightPressed = false;
        }
    }
}
