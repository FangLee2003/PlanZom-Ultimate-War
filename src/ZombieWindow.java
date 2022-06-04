import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/25/2016.
 */
public class ZombieWindows extends JFrame {

    enum PlantType{
        None,
        NormalZombie,
        ConeHeadZombie
    }

    //PlantType activePlantingBrush = PlantType.None;

    public ZombieWindows(){
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel moon = new JLabel("moon");
        moon.setLocation(37,80);
        moon.setSize(60,20);

        GamePanel gp = new GamePanel(moon);
        gp.setLocation(0,0);
        getLayeredPane().add(gp,new Integer(0));

        Card normalZombie = new Card(new ImageIcon(this.getClass().getResource("images/cards/card_NormalZombie.png")).getImage());
        normalZombie.setLocation(110,8);
        normalZombie.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.NormalZombie;
        });
        getLayeredPane().add(normalZombie,new Integer(3));

        Card coneHeadZombie = new Card(new ImageIcon(this.getClass().getResource("images/cards/card_ConeHeadZombie.png")).getImage());
        coneHeadZombie.setLocation(175,8);
        coneHeadZombie.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.ConeHeadZombie;
        });
        getLayeredPane().add(coneHeadZombie,new Integer(3));

        getLayeredPane().add(moon,new Integer(2));
        setResizable(false);
        setVisible(true);
    }
    public ZombieWindows(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0,0);
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu,new Integer(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }
    static ZombieWindows gw;
    public static void begin() {
        gw.dispose();
        gw = new ZombieWindows();
    }
    public static void main(String[] args) {
        gw = new ZombieWindows(true);
    }

}
