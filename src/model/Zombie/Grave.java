package model.Zombie;

import controller.*;
import model.Data;

public abstract class Grave {


    /**
     * Created by Armin on 6/25/2016.
     */


    public int health = 2000;

    public int m;
    public int n;

    public Data data;
    public ZombieGamePanel gp;


    public Grave(ZombieGamePanel parent, Data data, int m, int n) {
        gp = parent;
        this.data = data;
        this.m = m;
        this.n = n;
    }

    public void stop() {
    }

}

