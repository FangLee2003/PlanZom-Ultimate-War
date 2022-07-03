package model.Zombie;

import controller.*;
import model.Data;

public abstract class Grave {
    public int health = 2000;

    public PlantGamePanel pP;
    public ZombieGamePanel zP;

    public Data data;

    public int m;
    public int n;

    public Grave(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        this.pP = pP;
        this.zP = zP;
        this.data = data;
        this.m = m;
        this.n = n;
    }

    public void stop() {
    }

}

