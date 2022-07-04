package model.Plant;

import controller.*;
import model.Data;
import model.Zombie.Grave;

import java.awt.*;

public class Ice extends Pea {
    private int dmg = 200;

    public Ice(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        super(pP, zP, data, m, n);
    }

    @Override
    public void advance() {

        Rectangle pRect = new Rectangle(posX, 130 + m * 120, 28, 28);
        for (int i = 0; i < data.laneGraves.length; i++) {
            for (int j = 0; j < data.laneGraves[i].length; j++) {
                if (data.laneGraves[i][j] != null) {
                    Grave z = data.laneGraves[i][j];
                    Rectangle zRect = new Rectangle(544 + z.n * 100, 109 + z.m * 120, 120, 120);
                    if (pRect.intersects(zRect)) {
                        z.health -= dmg;
//                        boolean exit = false;
                        if (z.health < 0) {
                            System.out.println("Zombie[" + i + "][" + j + "] die");
                            data.removeGrave(i, j);
                            try {
                                zP.collidersZombie[j + i * 4].removeZombie();
                            } catch (Exception ignored) {

                            }
                            // PlantGamePanel.setProgress(10);
//                      exit = true;
                        }
                        data.lanePeas.get(m).remove(this);
//                if(exit) break;
                    }
                }
            }
        }
        if (posX > 1000) {
            data.zomHealth -= dmg;
            data.lanePeas.get(m).remove(this);
        }
        posX += 20;
    }

}
