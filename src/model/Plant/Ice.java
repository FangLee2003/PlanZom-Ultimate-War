package model.Plant;

import controller.*;
import model.Lane;
import model.Zombie.Grave;

import java.awt.*;

/**
 * Created by Armin on 6/28/2016.
 */
public class Ice extends Pea {
    private int dmg = 200;

    public Ice(PlantGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);
    }

    @Override
    public void advance() {

        Rectangle pRect = new Rectangle(posX, 130 + y * 120, 28, 28);
        for (int i = 0; i < lane.laneGraves.length; i++) {
            for (int j = 0; j < lane.laneGraves[i].length; j++) {
                if (lane.laneGraves[i][j] != null) {
                    Grave z = lane.laneGraves[i][j];
                    Rectangle zRect = new Rectangle(544 + z.x * 100, 109 + y * 120, 400, 120);
                    if (pRect.intersects(zRect)) {
                        z.health -= dmg;
                        boolean exit = false;
                        if (z.health < 0) {
                            System.out.println("ZOMBIE DIE");

                            lane.laneGraves[i][j] = null;
                            //PlantGamePanel.setProgress(10);
//                    exit = true;
                        }
                        lane.lanePeas.get(y).remove(this);
//                if(exit) break;
                    }
                }
            }
        }
        if (posX > 1000) {
            gp.zomHealth -= dmg;
            System.out.println(gp.zomHealth);
            lane.lanePeas.get(y).remove(this);
        }
        posX += 20;
    }

}
