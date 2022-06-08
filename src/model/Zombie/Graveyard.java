package model.Zombie;

import controller.*;
import model.Plant.Plant;
import model.Plant.Sun;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class Graveyard extends Zombie {

    Timer brainProduceTimer;

    public Graveyard(ZombieGamePanel parent, int x, int y) {
        super(parent, x, y);
        brainProduceTimer = new Timer(15000,(ActionEvent e) -> {
            Brain sta = new Brain(gp,60 + x*100,110 + y*120,130 + y*120);
            gp.activeBrains.add(sta);
            gp.add(sta,new Integer(1));
        });
        brainProduceTimer.start();
    }

}
