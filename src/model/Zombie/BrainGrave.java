package model.Zombie;

import controller.*;
import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BrainGrave extends Grave {
    Timer brainProduceTimer;

    public BrainGrave(PlantGamePanel pP, ZombieGamePanel zP, Data data, int m, int n) {
        super(pP, zP, data, m, n);

        brainProduceTimer = new Timer(5000, (ActionEvent e) -> {
            Brain brain = new Brain(zP, 560 + n * 100, 110 + m * 120, 130 + m * 120);
            zP.activeBrains.add(brain);
            zP.add(brain, new Integer(1));
        });
        brainProduceTimer.start();
    }

    public void stop() {
        brainProduceTimer.stop();
    }
}
