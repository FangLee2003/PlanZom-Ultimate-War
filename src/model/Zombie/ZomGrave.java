package model.Zombie;

import controller.PlantGamePanel;
import controller.ZombieGamePanel;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ZomGrave extends Grave {
    public int health = 2000;
    public Timer shootTimer;

    public ZomGrave(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        super(pP,zP, data, m, n);

        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if (data.laneZoms.get(y).size() > 0) {
            data.laneZoms.get(m).add(new Zom(this.pP,this.zP, data, m, n));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }
}
