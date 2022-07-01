package controller;

import model.Plant.Plant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Armin on 6/25/2016.
 */
public class ColliderPlant extends JPanel implements MouseListener {

    ActionListener al;

    public ColliderPlant(){
        //setBorder(new LineBorder(Color.RED));
        setOpaque(false);
        addMouseListener(this);
        //setBackground(Color.green);
        setSize(100,120);
    }

    public Plant assignedPlant;

    public void setCharacter(Plant p){
        assignedPlant = p;
    }

    public void removeCharacter(){
        assignedPlant.stop();
        assignedPlant = null;
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
