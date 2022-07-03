package model.Plant;

import controller.*;
import model.*;

public abstract class Plant {


    /**
     * Created by Armin on 6/25/2016.
     */

    public int health = 1000;

    public int m;
    public int n;

    public PlantGamePanel gp;
    public Data data;

    public Plant(PlantGamePanel parent, Data data, int m, int n) {
        gp = parent;
        this.data = data;
        this.m = m;
        this.n = n;
    }

    public void stop() {
    }

}

