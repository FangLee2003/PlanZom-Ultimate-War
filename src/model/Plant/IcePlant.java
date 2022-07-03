package model.Plant;

import controller.*;
import model.Data;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class IcePlant extends Plant {
    public int health = 2000;
    public Timer shootTimer;
    Data data;

    public IcePlant(PlantGamePanel parent, Data data, int m, int n) {
        super(parent, data, m, n);

        shootTimer = new Timer(1000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if(gp.laneZombies.get(y).size() > 0) {
            data.lanePeas.get(m).add(new Ice(gp, data, m, n));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }

}