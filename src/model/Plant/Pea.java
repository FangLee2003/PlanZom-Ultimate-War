package model.Plant;

import controller.PlantGamePanel;
import model.Lane;
import model.Zombie.Grave;

import java.awt.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class Pea {

    public int posX;
    protected PlantGamePanel gp;
    Lane lane;
    public int y;
    int dmg = 100;

    public Pea(PlantGamePanel parent, Lane lane, int x, int y) {
        this.gp = parent;
        this.lane = lane;
        posX = 103 + x * 100;
        this.y = y;
    }

    public void advance() {

        Rectangle pRect = new Rectangle(posX, 130 + y * 120, 28, 28);
        for (int i = 0; i < lane.laneGraves.get(y).size(); i++) {
            Grave z = lane.laneGraves.get(y).get(i);
            Rectangle zRect = new Rectangle(z.x, 109 + y * 120, 400, 120);
            if (pRect.intersects(zRect)) {
                z.health -= dmg;
                boolean exit = false;
                if (z.health < 0) {
                    System.out.println("ZOMBIE DIE");

                    lane.laneGraves.get(y).remove(i);
                    //PlantGamePanel.setProgress(10);
//                    exit = true;
                }
                lane.lanePeas.get(y).remove(this);
//                if(exit) break;
            }
        }
        if (posX > 1000) {
            gp.zomHealth -= dmg;
            System.out.println(gp.zomHealth);
            lane.lanePeas.get(y).remove(this);
        }

        posX += 10;
    }

}
