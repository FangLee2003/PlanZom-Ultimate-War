package model.Zombie;

import controller.PlantGamePanel;
import controller.ZombieGamePanel;
import model.Data;
import model.Plant.Plant;

import java.awt.*;

public class Zom {
    protected PlantGamePanel pP;
    protected ZombieGamePanel zP;

    public int posX;
    Data data;
    public int m;
    private int dmg = 100;

    public Zom(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        this.pP = pP;
        this.zP = zP;
        this.data = data;

        posX = 603 + (n * 100);
        this.m = m;
    }

    public void advance() {

        Rectangle zRect = new Rectangle(posX, 130 + m * 120, 28, 28);
        for (int i = 0; i < data.lanePlants.length; i++) {
            for (int j = 0; j < data.lanePlants[i].length; j++) {
                if (data.lanePlants[i][j] != null) {
                    Plant p = data.lanePlants[i][j];
                    Rectangle pRect = new Rectangle(44 + p.n * 100, 109 + p.m * 120, 120, 120);
                    if (zRect.intersects(pRect)) {
                        p.health -= dmg;
//                        boolean exit = false;
                        if (p.health < 0) {
                            System.out.println("Plant[" + i + "][" + j + "] die");
                            data.removePlant(i, j);
                            try {
                                pP.collidersPlant[j + i * 5].removePlant();
                            } catch (Exception ignored) {

                            }
                            //PlantGamePanel.setProgress(10);
//                    exit = true;
                        }
                        data.laneZoms.get(m).remove(this);
//                if(exit) break;
                    }
                }
            }
        }
        if (posX < 0) {
            data.plantHealth -= dmg;
            data.laneZoms.get(m).remove(this);
        }
        posX -= 5;
    }

}
