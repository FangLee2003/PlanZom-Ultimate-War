package model.Plant;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class Sunflower extends Plant {

    Timer sunProduceTimer;

    public Sunflower(PlantGamePanel parent, int x, int y) {
        super(parent, x, y);
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
            Mana sta = new Mana(gp, 60 + x * 100, 110 + y * 120, 130 + y * 120, "sun");
            gp.activeManas.add(sta);
            gp.add(sta, new Integer(1));
        });
        sunProduceTimer.start();
    }

}
