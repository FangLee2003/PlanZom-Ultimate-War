package model;

import controller.PlantGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Armin on 6/27/2016.
 */
public class Mana extends JPanel implements MouseListener {

    PlantGamePanel gp;
    Image manaImage;

    int myX;
    int myY;
    int endY;

    int destruct = 200;

    public Mana(PlantGamePanel parent, int startX, int startY, int endY, String type){
        this.gp = parent;
        this.endY = endY;
        setSize(80,80);
        setOpaque(false);
        myX = startX;
        myY = startY;
        setLocation(myX,myY);
        manaImage = new ImageIcon(this.getClass().getResource("images/" +type+".png")).getImage();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(manaImage,0,0,null);
    }

    public void advance(){
        if(myY < endY) {
            myY+= 4;
        }else{
            destruct--;
            if(destruct<0){
                gp.remove(this);
                gp.activeManas.remove(this);
            }
        }
        setLocation(myX,myY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.setManaScore(gp.getManaScore()+25);
        gp.remove(this);
        gp.activeManas.remove(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
