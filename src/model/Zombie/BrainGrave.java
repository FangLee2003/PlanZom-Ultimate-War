package model.Zombie;

import controller.*;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/28/2016.
 */
public class BrainGrave extends Grave {
    Timer brainProduceTimer;

    public BrainGrave(ZombieGamePanel parent, Data data, int m, int n) {
        super(parent, data, m, n);

        brainProduceTimer = new Timer(5000, (ActionEvent e) -> {
            Brain brain = new Brain(gp, 560 + n * 100, 110 + m * 120, 130 + n * 120);
            gp.activeBrains.add(brain);
            gp.add(brain, new Integer(1));
        });
        brainProduceTimer.start();
    }
}
