package view;

import controller.PlantGamePanel;
import model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Armin on 6/25/2016.
 */
public class PlantWindow extends JFrame {

    public enum PlantType {
        None,
        SunPlant,
        PeaPlant,
        IcePlant
    }

    //PlantType activePlantingBrush = PlantType.None;

    public PlantWindow() throws IOException, InterruptedException {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("PlanZom Ultimate War");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/logo.png")));

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        PlantGamePanel gp = new PlantGamePanel(sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));

        Card sunflower = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_sunflower.png")).getImage());
        sunflower.setLocation(110, 8);
        sunflower.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.SunPlant;
        });
        getLayeredPane().add(sunflower, new Integer(3));

        Card peashooter = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_peashooter.png")).getImage());
        peashooter.setLocation(175, 8);
        peashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.PeaPlant;
        });
        getLayeredPane().add(peashooter, new Integer(3));

        Card freezepeashooter = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_freezepeashooter.png")).getImage());
        freezepeashooter.setLocation(240, 8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.IcePlant;
        });
        getLayeredPane().add(freezepeashooter, new Integer(3));

        getLayeredPane().add(sun, new Integer(2));

        setVisible(true);
    }

    public PlantWindow(boolean b) {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("PlanZom Ultimate War");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/logo.png")));

        Menu menu = new Menu("Plant");
        menu.setLocation(0, 0);

        getLayeredPane().add(menu, new Integer(0));
        menu.repaint();

        setVisible(true);
    }

    public static PlantWindow gw;

    public static void begin() throws IOException, InterruptedException {
        gw.dispose();
        gw = new PlantWindow();
    }

    public static void main(String[] args) {
        gw = new PlantWindow(true);
    }

}
