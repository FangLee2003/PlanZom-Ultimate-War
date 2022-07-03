package model.Plant;

import controller.*;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class SunPlant extends Plant {
    Timer sunProduceTimer;

    public SunPlant(PlantGamePanel parent, Data data, int m, int n) {
        super(parent, data, m, n);

        sunProduceTimer = new Timer(5000, (ActionEvent e) -> {
            Sun sun = new Sun(gp, 60 + n * 100, 110 + m * 120, 130 + n * 120);
            gp.activeSuns.add(sun);
            gp.add(sun, new Integer(1));
        });
        sunProduceTimer.start();
    }

}
