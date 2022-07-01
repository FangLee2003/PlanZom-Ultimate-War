package model.Plant;

import controller.*;
import model.Lane;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class SunPlant extends Plant {
    Timer sunProduceTimer;

    public SunPlant(PlantGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);

        sunProduceTimer = new Timer(5000, (ActionEvent e) -> {
            Sun sun = new Sun(gp, 60 + x * 100, 110 + y * 120, 130 + y * 120);
            gp.activeSuns.add(sun);
            gp.add(sun, new Integer(1));
        });
        sunProduceTimer.start();
    }

}
