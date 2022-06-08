package model.Zombie;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Armin on 6/27/2016.
 */
public class Brain extends JPanel implements MouseListener {

    ZombieGamePanel gp;
    Image manaImage;

    int myX;
    int myY;
    int endY;

    int destruct = 200;

    public Brain(ZombieGamePanel parent, int startX, int startY, int endY){
        this.gp = parent;
        this.endY = endY;
        setSize(80,80);
        setOpaque(false);
        myX = startX;
        myY = startY;
        setLocation(myX,myY);
        manaImage = new ImageIcon(this.getClass().getResource("../../../resource/images/brain.png")).getImage();
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
                gp.activeBrains.remove(this);
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
        gp.setBrainScore(gp.getBrainScore()+25);
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
