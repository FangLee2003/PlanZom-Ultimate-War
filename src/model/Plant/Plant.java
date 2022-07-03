package model.Plant;

import controller.*;
import model.*;

public abstract class Plant {
    public int health = 1000;

    public int m;
    public int n;

    public PlantGamePanel pP;
    public ZombieGamePanel zP;
    public Data data;

    public Plant(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        this.pP = pP;
        this.zP = zP;
        this.data = data;
        this.m = m;
        this.n = n;
    }

    public void stop() {
    }

}

