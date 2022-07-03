package model.Plant;

import controller.PlantGamePanel;
import model.Data;
import model.Zombie.Grave;

import java.awt.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class Pea {

    public int posX;
    protected PlantGamePanel gp;
    Data data;
    public int m;
    int dmg = 100;

    public Pea(PlantGamePanel parent, Data data, int m, int n) {
        this.gp = parent;
        this.data = data;
        posX = 103 + (n * 100);
        this.m = m;
    }

    public void advance() {

        Rectangle pRect = new Rectangle(posX, 130 + m * 120, 28, 28);
        for (int i = 0; i < data.laneGraves.length; i++) {
            for (int j = 0; j < data.laneGraves[i].length; j++) {
                if (data.laneGraves[i][j] != null) {
                    Grave z = data.laneGraves[i][j];
                    Rectangle zRect = new Rectangle(544 + z.n * 100, 109 + z.m * 120, 120, 120);
                    if (pRect.intersects(zRect)) {
                        z.health -= dmg;
                        boolean exit = false;
                        if (z.health < 0) {
                            System.out.println("ZOMBIE DIE");

                            data.laneGraves[i][j] = null;
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
