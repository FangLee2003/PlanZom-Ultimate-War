package model.Plant;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/25/2016.
 */
public class PeaPlant extends Plant {
    public int health = 1000;
    public Timer shootTimer;


    public PeaPlant(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        super(pP,zP, data, m, n);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (gp.laneZombies.get(y).size() > 0) {
            data.lanePeas.get(m).add(new Pea(this.pP,this.zP, data, m, n));
//            }
        });
        shootTimer.start();
    }

    public void stop() {
        shootTimer.stop();
    }

}
