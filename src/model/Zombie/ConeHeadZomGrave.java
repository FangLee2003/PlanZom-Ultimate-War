package model.Zombie;

import controller.PlantGamePanel;
import controller.ZombieGamePanel;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/25/2016.
 */
public class ConeHeadZomGrave extends Grave {
    public int health = 3000;
    public Timer shootTimer;
    public ConeHeadZomGrave(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        super(pP, zP, data, m, n);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (data.laneZoms.get(y).size() > 0) {
            data.laneZoms.get(m).add(new ConeHeadZom(this.pP, this.zP, data, m, n));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }
}
