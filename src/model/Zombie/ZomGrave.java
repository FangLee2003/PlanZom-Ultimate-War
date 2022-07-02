package model.Zombie;

import controller.ZombieGamePanel;
import model.Lane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ZomGrave extends Grave {
    public int health = 2000;
    public Timer shootTimer;

    public ZomGrave(ZombieGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (lane.laneZoms.get(y).size() > 0) {
            lane.laneZoms.get(x).add(new Zom(gp, lane, x, y));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }
}
