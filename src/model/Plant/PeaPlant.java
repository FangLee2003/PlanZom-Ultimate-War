package model.Plant;

import controller.*;
import model.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/25/2016.
 */
public class PeaPlant extends Plant {
    public int health = 1000;
    public Timer shootTimer;


    public PeaPlant(PlantGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (gp.laneZombies.get(y).size() > 0) {
            lane.lanePeas.get(y).add(new Pea(gp, lane,  x, y));
//            }
        });
        shootTimer.start();
    }

    public void stop() {
        shootTimer.stop();
    }

}
