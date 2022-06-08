package model.Zombie;

import controller.*;
import model.Plant.Plant;
import model.Plant.Sun;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class Graveyard extends Plant {

    Timer brainProduceTimer;

    public Graveyard(PlantGamePanel parent, int x, int y) {
        super(parent, x, y);
        brainProduceTimer = new Timer(15000,(ActionEvent e) -> {
            Sun sta = new Sun(gp,60 + x*100,110 + y*120,130 + y*120, "brain");
            gp.activeManas.add(sta);
            gp.add(sta,new Integer(1));
        });
        brainProduceTimer.start();
    }

}
