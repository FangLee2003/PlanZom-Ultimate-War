package model.Plant;

import controller.*;
import model.Lane;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class IcePlant extends Plant {
    public int health = 2000;
    public Timer shootTimer;
    Lane lane;

    public IcePlant(PlantGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);

        shootTimer = new Timer(1000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if(gp.laneZombies.get(y).size() > 0) {
            lane.lanePeas.get(y).add(new Ice(gp, lane,  x, y));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }

}