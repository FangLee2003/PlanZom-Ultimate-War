package model.Zombie;

import controller.ZombieGamePanel;

/**
 * Created by Armin on 6/29/2016.
 */
public class ConeHeadZombie extends Zombie {
    public ConeHeadZombie(ZombieGamePanel parent, int lane, int x, int y){
        super(parent,lane, x, y);
        health = 1800;
    }
}
