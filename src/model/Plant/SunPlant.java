package model.Plant;

import controller.*;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SunPlant extends Plant {
    Timer sunProduceTimer;

    public SunPlant(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        super(pP, zP, data, m, n);


        sunProduceTimer = new Timer(5000, (ActionEvent e) -> {
            Sun sun = new Sun(pP, 60 + n * 100, 110 + m * 120, 130 + m * 120);
            pP.activeSuns.add(sun);
            pP.add(sun, new Integer(1));
        });
        sunProduceTimer.start();
    }

    public void stop() {
        sunProduceTimer.stop();
    }
}
