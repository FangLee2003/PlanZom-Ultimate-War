package model.Plant;

import controller.PlantGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Armin on 6/27/2016.
 */
public class Sun extends JPanel implements MouseListener {

    PlantGamePanel gp;
    Image sunImage;

    int X;
    int Y;
    int endY;

    int destruct = 200;

    public Sun(PlantGamePanel parent, int startX, int startY, int endY){
        this.gp = parent;
        X = startX;
        Y = startY;
        this.endY = endY;

        setSize(80,80);
        setOpaque(false);
        setLocation(X, Y);
        sunImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/sun.png")).getImage();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunImage,0,0,null);
    }

    public void advance(){
        if(Y < endY) {
            Y += 4;
        }else{
            destruct--;
            if(destruct<0){
                gp.remove(this);
                gp.activeSuns.remove(this);
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
        gp.setSunScore(gp.getSunScore()+25);
        gp.remove(this);
        gp.activeSuns.remove(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
