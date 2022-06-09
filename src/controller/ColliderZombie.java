package controller;

import model.Zombie.Zombie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Armin on 6/25/2016.
 */
public class ColliderZombie extends JPanel implements MouseListener {

    ActionListener al;

    public ColliderZombie(){
        //setBorder(new LineBorder(Color.RED));
        setOpaque(false);
        addMouseListener(this);
        //setBackground(Color.green);
        setSize(100,120);
    }

    public Zombie assignedZombie;

    public void setZombie(Zombie z){
        assignedZombie = z;
    }

    public void removeZombie(){
        assignedZombie = null;
    }

    public boolean isInsideCollider(int tx){
        return (tx > getLocation().x) && (tx < getLocation().x + 100);
    }

    public void setAction(ActionListener al){
        this.al = al;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(al != null){
            al.actionPerformed(new ActionEvent(this,ActionEvent.RESERVED_ID_MAX+1,""));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}