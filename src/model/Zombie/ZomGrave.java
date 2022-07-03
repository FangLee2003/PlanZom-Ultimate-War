package model.Zombie;

import controller.ZombieGamePanel;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ZomGrave extends Grave {
    public int health = 2000;
    public Timer shootTimer;

    public ZomGrave(ZombieGamePanel parent, Data data, int m, int n) {
        super(parent, data, m, n);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (data.laneZoms.get(y).size() > 0) {
            data.laneZoms.get(m).add(new Zom(gp, data, m, n));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }
}
