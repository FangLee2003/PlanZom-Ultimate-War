package view;

import controller.ZombieGamePanel;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Armin on 6/25/2016.
 */
public class ZombieWindow extends JFrame {

    public enum ZombieType {
        None,
        BrainGrave,
        ZomGrave,
        ConeHeadZomGrave
    }

    //PlantType activePlantingBrush = PlantType.None;

    public ZombieWindow() throws IOException {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("PlanZom Ultimate War");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/logo.png")));

        JLabel brain = new JLabel("BRAIN");
        brain.setLocation(925, 80);
        brain.setSize(60, 20);

        ZombieGamePanel gp = new ZombieGamePanel(brain);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));
        Card coneHeadZombie = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_ConeHeadZombie.png")).getImage());
        coneHeadZombie.setLocation(685, 8);
        coneHeadZombie.setAction((ActionEvent e) -> {
            gp.activeZombieBrush = ZombieType.ConeHeadZomGrave;
        });
        getLayeredPane().add(coneHeadZombie, new Integer(3));

        Card normalZombie = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_NormalZombie.png")).getImage());
        normalZombie.setLocation(755, 8);
        normalZombie.setAction((ActionEvent e) -> {
            gp.activeZombieBrush = ZombieType.ZomGrave;
        });
        getLayeredPane().add(normalZombie, new Integer(3));

        Card graveyardZombie = new Card(new ImageIcon(this.getClass().getClassLoader().getResource("images/cards/card_raveyard.png")).getImage());
        graveyardZombie.setLocation(825, 8);
        graveyardZombie.setAction((ActionEvent e) -> {
            gp.activeZombieBrush = ZombieType.BrainGrave;
        });
        getLayeredPane().add(graveyardZombie, new Integer(3));

        getLayeredPane().add(brain, new Integer(2));

        setVisible(true);
    }

    public ZombieWindow(boolean b) {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("PlanZom Ultimate War");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/logo.png")));

        Menu menu = new Menu("Grave");
        menu.setLocation(0, 0);

        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getLayeredPane().add(menu, new Integer(0));
        menu.repaint();

        setVisible(true);
    }

    public static ZombieWindow gw;

    public static void begin() throws IOException {
        gw.dispose();
        gw = new ZombieWindow();
    }

    public static void main(String[] args) {
        gw = new ZombieWindow(true);
    }
}