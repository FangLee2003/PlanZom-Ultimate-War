package model.Zombie;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Brain extends JPanel implements MouseListener {

    ZombieGamePanel gp;
    Image brainImage;

    int X;
    int Y;
    int endY;

    int destruct = 200;

    public Brain(ZombieGamePanel parent, int startX, int startY, int endY) {
        this.gp = parent;
        X = startX;
        Y = startY;
        this.endY = endY;

        setSize(80, 80);
        setOpaque(false);
        setLocation(this.X, this.Y);
        brainImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/brain.png")).getImage();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(brainImage, 0, 0, null);
    }

    public void advance() {
        if (Y < endY) {
            Y += 4;
        } else {
            destruct--;
            if (destruct < 0) {
                gp.remove(this);
                gp.activeBrains.remove(this);
            }
        }
        setLocation(X, Y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.setBrainScore(gp.getBrainScore() + 25);
        gp.remove(this);
        gp.activeBrains.remove(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
