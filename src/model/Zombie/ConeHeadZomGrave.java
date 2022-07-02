package model.Zombie;

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
    Lane lane;

    public ConeHeadZomGrave(ZombieGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (lane.laneZoms.get(y).size() > 0) {
            lane.laneZoms.get(x).add(new ConeHeadZom(gp, lane, x, y));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }
}
