package model.Zombie;

import controller.*;
import model.Lane;

import java.awt.*;

public abstract class Grave {


    /**
     * Created by Armin on 6/25/2016.
     */


    public int health = 2000;

    public int x;
    public int y;

    public Lane lane;
    public ZombieGamePanel gp;


    public Grave(ZombieGamePanel parent, Lane lane, int x, int y) {
        gp = parent;
        this.lane = lane;
        this.x = x;
        this.y = y;
    }

    public void stop() {
    }

}

