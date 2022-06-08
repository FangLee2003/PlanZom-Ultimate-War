package model.Zombie;

import controller.*;
import view.*;

import javax.swing.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class Zombie {

    public int health = 1000;
    public int speed = 1;

    public ZombieGamePanel gp;

    public int posX = 1000;
    public int x;
    public int y;
    public int myLane;
    public boolean isMoving = true;

    public Zombie(ZombieGamePanel parent, int x, int y) {
        this.gp = parent;
        this.x = x;
        this.y = y;
    }

    public void advance() {
        if (isMoving) {
            boolean isCollides = false;
            ColliderZombie collided = null;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.collidersZombie[i].assignedZombie != null && gp.collidersZombie[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.collidersZombie[i];
                }
            }
            if (!isCollides) {
                if (slowInt > 0) {
                    if (slowInt % 2 == 0) {
                        posX--;
                    }
                    slowInt--;
                } else {
                    posX -= 1;
                }
            } else {
                collided.assignedZombie.health -= 10;
                if (collided.assignedZombie.health < 0) {
                    collided.removeZombie();
                }
            }
            if (posX < 0) {
                isMoving = false;
                JOptionPane.showMessageDialog(gp, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                PlantWindow.gw.dispose();
                PlantWindow.gw = new PlantWindow();
            }
        }
    }

    int slowInt = 0;

    public void slow() {
        slowInt = 1000;
    }

    public static Zombie getZombie(ZombieGamePanel parent, int x, int y, String type) {
        Zombie z = new Zombie(parent, x, y);
        switch (type) {
            case "Graveyard":
                z = new Graveyard(parent, x, y);
                break;
            case "NormalZombie":
                z = new NormalZombie(parent, x, y);
                break;
            case "ConeHeadZombie":
                z = new ConeHeadZombie(parent, x, y);
                break;
        }
        return z;
    }
}
