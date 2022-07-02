package model.Plant;

import controller.*;
import model.*;

public abstract class Plant {


    /**
     * Created by Armin on 6/25/2016.
     */

    public int health = 1000;

    public int x;
    public int y;

    public PlantGamePanel gp;
    public Lane lane;

    public Plant(PlantGamePanel parent, Lane lane, int x, int y) {
        gp = parent;
        this.lane = lane;
        this.x = x;
        this.y = y;
    }

    public void stop() {
    }

}

