package model.Zombie;

import controller.*;
import model.Lane;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class BrainGrave extends Grave {
    Timer brainProduceTimer;

    public BrainGrave(ZombieGamePanel parent, Lane lane, int x, int y) {
        super(parent, lane, x, y);

        brainProduceTimer = new Timer(1000, (ActionEvent e) -> {
            Brain brain = new Brain(gp, 560 + x * 100, 110 + y * 120, 130 + y * 120);
            gp.activeBrains.add(brain);
            gp.add(brain, new Integer(1));
        });
        brainProduceTimer.start();
    }
}
