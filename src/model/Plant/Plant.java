package model.Plant;

import controller.*;

/**
 * Created by Armin on 6/25/2016.
 */
public abstract class Plant {

    public int health = 200;

    public int x;
    public int y;

    public PlantGamePanel gp;


    public Plant(PlantGamePanel parent, int x, int y) {
        gp = parent;
        this.x = x;
        this.y = y;
    }

    public void stop() {
    }

}
