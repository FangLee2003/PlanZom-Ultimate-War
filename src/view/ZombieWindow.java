package view;

import controller.ZombieGamePanel;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/25/2016.
 */
public class ZombieWindow extends JFrame {

    public enum ZombieType {
        None,
        Graveyard,
        NormalZombie,
        ConeHeadZombie
    }

    //PlantType activePlantingBrush = PlantType.None;

    public ZombieWindow() {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel brain = new JLabel("BRAIN");
        brain.setLocation(915, 115);
        brain.setSize(60, 20);

        ZombieGamePanel gp = new ZombieGamePanel(brain);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));
        Card coneHeadZombie = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_ConeHeadZombie.png")).getImage());
        coneHeadZombie.setLocation(660, 40);
        coneHeadZombie.setAction((ActionEvent e) -> {
            gp.activeZombieBrush = ZombieType.ConeHeadZombie;
        });
        getLayeredPane().add(coneHeadZombie, new Integer(3));

        Card normalZombie = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_NormalZombie.png")).getImage());
        normalZombie.setLocation(730, 40);
        normalZombie.setAction((ActionEvent e) -> {
            gp.activeZombieBrush = ZombieType.NormalZombie;
        });
        getLayeredPane().add(normalZombie, new Integer(3));

        Card graveyardZombie = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_Graveyard.png")).getImage());
        graveyardZombie.setLocation(800, 40);
        graveyardZombie.setAction((ActionEvent e) -> {
            gp.activeZombieBrush = ZombieType.Graveyard;
        });
        getLayeredPane().add(graveyardZombie, new Integer(3));

        getLayeredPane().add(brain, new Integer(2));
        setResizable(false);
        setVisible(true);
    }

    public ZombieWindow(boolean b) {
        Menu menu = new Menu("Zombie");
        menu.setLocation(0, 0);
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu, new Integer(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }

    public static ZombieWindow gw;

    public static void begin() {
        gw.dispose();
        gw = new ZombieWindow();
    }

    public static void main(String[] args) {
        gw = new ZombieWindow(true);
    }
}
