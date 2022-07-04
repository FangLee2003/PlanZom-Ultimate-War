package model.Plant;

import controller.*;
import model.Data;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class IcePlant extends Plant {
    public int health = 2000;
    public Timer shootTimer;
    Data data;

    public IcePlant(PlantGamePanel pP, ZombieGamePanel zP,Data data, int m, int n) {
        super(pP, zP, data, m, n);


        shootTimer = new Timer(1000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
//            if(gp.laneZombies.get(y).size() > 0) {
            data.lanePeas.get(m).add(new Ice(this.pP, this.zP, data, m, n));
//            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }

}