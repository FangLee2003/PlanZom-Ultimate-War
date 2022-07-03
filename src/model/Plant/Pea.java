package model.Plant;

import controller.PlantGamePanel;
import controller.ZombieGamePanel;
import model.Data;
import model.Zombie.Grave;

import java.awt.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class Pea {
    protected PlantGamePanel pP;
    protected ZombieGamePanel zP;

    public int posX;
    Data data;
    public int m;
    int dmg = 100;

    public Pea(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        this.pP = pP;
        this.zP = zP;
        this.data = data;
        posX = 103 + (n * 100);
        this.m = m;
    }

    public void advance() {

        Rectangle pRect = new Rectangle(posX, 130 + m * 120, 28, 28);
        for (int i = 0; i < data.laneGraves.length; i++) {
            for (int j = 0; j < data.laneGraves[i].length; j++) {
                if (data.laneGraves[i][j] != null && zP.collidersZombie[j + i * 4].assignedGrave != null) {
                    Grave z = data.laneGraves[i][j];
                    Rectangle zRect = new Rectangle(544 + z.n * 100, 109 + z.m * 120, 120, 120);
                    if (pRect.intersects(zRect)) {
                        z.health -= dmg;
//                        boolean exit = false;
                        if (z.health < 0) {
                            System.out.println("ZOMBIE " + i + " " + j + " DIE");
                            data.laneGraves[i][j].stop();
                            data.laneGraves[i][j] = null;
                            zP.collidersZombie[j + i * 4].removeZombie();
                            //PlantGamePanel.setProgress(10);
//                    exit = true;
                        }
                        data.lanePeas.get(m).remove(this);
//                if(exit) break;
                    }
                }
            }
        }
        if (posX > 1000) {
            data.zomHealth -= dmg;
            System.out.println(data.zomHealth);
            data.lanePeas.get(m).remove(this);
        }
        posX += 10;
    }

}
