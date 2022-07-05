package controller;

import model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Health extends JPanel {
    JProgressBar plantHealth = new JProgressBar(0, 10000);
    JProgressBar zombieHealth = new JProgressBar(0, 10000);

    Timer healthTimer;

    public Health(Data data, int x, int y) {
        setSize(120, 30);
        setLocation(x, y);
        setOpaque(false);
        setLayout(new GridLayout(2, 1));

        plantHealth.setForeground(new Color(81, 236, 132));
        zombieHealth.setForeground(new Color(0, 0, 0));

        add(plantHealth);
        add(zombieHealth);

        healthTimer = new Timer(100, (ActionEvent e) -> {
            plantHealth.setValue(data.plantHealth);
            zombieHealth.setValue(data.zomHealth);
        });
        healthTimer.start();
    }
}
