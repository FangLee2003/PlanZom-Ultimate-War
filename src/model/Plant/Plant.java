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


    public Plant(PlantGamePanel parent, int x, int y){
        this.x = x;
        this.y = y;
        gp = parent;
    }

    public void stop(){}

}
