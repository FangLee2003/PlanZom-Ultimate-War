package model.Zombie;

import controller.ZombieGamePanel;
import model.Lane;
import model.Plant.Plant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Armin on 6/29/2016.
 */
public class ConeHeadZom extends Zom {
    private int dmg = 200;

    public ConeHeadZom(ZombieGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);
    }

    public void advance() {

        Rectangle zRect = new Rectangle(posX, 130 + y * 120, 28, 28);
        for (int i = 0; i < lane.lanePlants.get(y).size(); i++) {
            Plant p = lane.lanePlants.get(y).get(i);
            Rectangle pRect = new Rectangle(p.x, 109 + y * 120, 400, 120);
            if (zRect.intersects(pRect)) {
                p.health -= dmg;
                boolean exit = false;
                if (p.health < 0) {
                    System.out.println("PLANT DIE");

                    lane.lanePlants.get(y).remove(i);
                    //PlantGamePanel.setProgress(10);
//                    exit = true;
                }
                lane.laneZoms.get(y).remove(this);
//                if(exit) break;
            }
        }
        if (posX < 0) {
            gp.planHealth -= dmg;
            System.out.println(gp.planHealth);
            lane.laneZoms.get(y).remove(this);
        }
        posX -= 20;
    }
}
