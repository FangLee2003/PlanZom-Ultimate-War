package model.Zombie;

import controller.ZombieGamePanel;
import model.Lane;
import model.Plant.Plant;

import java.awt.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class Zom {
    public int posX;
    protected ZombieGamePanel gp;
    Lane lane;
    public int row;
    private int dmg = 100;

    public Zom(ZombieGamePanel parent, Lane lane, int row, int column) {
        this.gp = parent;
        this.lane = lane;

        posX = 603 + (column * 100);
        this.row = row;
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
        posX -= 10;
    }

}
