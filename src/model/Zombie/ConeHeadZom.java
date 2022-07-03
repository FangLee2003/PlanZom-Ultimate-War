package model.Zombie;

import controller.ZombieGamePanel;
import model.Lane;
import model.Plant.Plant;

import java.awt.*;

/**
 * Created by Armin on 6/29/2016.
 */
public class ConeHeadZom extends Zom {
    private int dmg = 200;

    public ConeHeadZom(ZombieGamePanel parent, Lane lane, int row, int column) {
        super(parent, lane, row, column);
    }

    public void advance() {

        Rectangle zRect = new Rectangle(posX, 130 + row * 120, 28, 28);
        for (int i = 0; i < lane.lanePlants.length; i++) {
            for (int j = 0; j < lane.lanePlants[i].length; j++) {
                if (lane.lanePlants[i][j] != null) {
                    Plant p = lane.lanePlants[i][j];
                    Rectangle pRect = new Rectangle(44 + p.x * 100, 109 + row * 120, 400, 120);
                    if (zRect.intersects(pRect)) {
                        p.health -= dmg;
                        boolean exit = false;
                        if (p.health < 0) {
                            System.out.println("PLANT DIE");

                            lane.lanePlants[i][j] = null;
                            //PlantGamePanel.setProgress(10);
//                    exit = true;
                        }
                        lane.laneZoms.get(row).remove(this);
//                if(exit) break;
                    }
                }
            }
        }
        if (posX < 0) {
            gp.planHealth -= dmg;
            System.out.println(gp.planHealth);
            lane.laneZoms.get(row).remove(this);
        }
        posX -= 20;
    }
}
