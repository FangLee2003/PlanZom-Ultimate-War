package controller;

import model.Lane;
import model.Plant.*;
import model.Zombie.*;
import view.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class ZombieGamePanel extends JLayeredPane implements MouseMotionListener {

    Image bgImage;

    Image sun_plant_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/sunflower.gif")).getImage();
    Image pea_plant_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/peashooter.gif")).getImage();
    Image ice_plant_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/freezepeashooter.gif")).getImage();

    Image pea_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/pea.png")).getImage();
    Image ice_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/freezepea.png")).getImage();

    Image zom_grave_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/normalzombiegraveyard.png")).getImage();
    Image coneheadzom_grave_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/coneheadzombiegraveyard.png")).getImage();
    Image brain_grave_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/braingraveyard.png")).getImage();

    public Image zom_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/normalzombie.png")).getImage();
    public Image coneheadzom_img = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/coneheadzombie.png")).getImage();

    public int planHealth = 10000;
    public int zomHealth = 10000;

    public ColliderZombie[] collidersZombie;
    public ColliderPlant[] collidersPlant;

    public Lane lane;

    Timer redrawTimer;
    Timer advancerTimer;

    public ArrayList<Brain> activeBrains;
    Timer brainProducer;
    private int brainScore;
    JLabel brainScoreboard;

    public ZombieWindow.ZombieType activeZombieBrush = ZombieWindow.ZombieType.None;

    int mouseX, mouseY;

    public int getBrainScore() {
        return brainScore;
    }

    public void setBrainScore(int brainScore) {
        this.brainScore = brainScore;
        brainScoreboard.setText(String.valueOf(brainScore));
    }

    public ZombieGamePanel(JLabel brainScoreboard) {
        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.brainScoreboard = brainScoreboard;
        setBrainScore(150);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/background2.png")).getImage();

        collidersZombie = new ColliderZombie[20];
        lane = new Lane();

//        for (int i = 0; i < 25; i++) {
//            ColliderPlant cP = new ColliderPlant();
//            cP.setLocation(44 + (i % 5) * 100, 109 + (i / 5) * 120); // First cell (0, 0) in place (44, 109), second cell (0, 1) in place (144, 109),...
//            cP.setAction(new PlantGamePanel.PlantActionListener((i % 5), (i / 5)));
//            collidersPlant[i] = cP;
//            add(cP, new Integer(0));
//        }

        for (int i = 0; i < 20; i++) {
            ColliderZombie cZ = new ColliderZombie();
            cZ.setLocation(544 + (i % 4) * 100, 109 + (i / 4) * 120); // First cell (0, 0) in place (544, 109), second cell (0, 1) in place (644, 109),...
            cZ.setAction(new ZombieActionListener((i % 4), (i / 4)));
            collidersZombie[i] = cZ;
            add(cZ, new Integer(0));
        }

        //colliders[0].setPlant(new model.model.Plant.Plant.FreezePeashooter(this,0,0));
/*
        colliders[9].setPlant(new model.model.Plant.Plant.Peashooter(this,0,1));
        laneZombies.get(1).add(new model.Grave.Zom(this,1));*/

        activeBrains = new ArrayList<>();

        redrawTimer = new Timer(25, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60, (ActionEvent e) -> advance());
        advancerTimer.start();

        brainProducer = new Timer(5000, (ActionEvent e) -> {
            Random rnd = new Random();
            Brain sta = new Brain(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeBrains.add(sta);
            add(sta, new Integer(1));
        });
        brainProducer.start();

//        zombieProducer = new Timer(7000, (ActionEvent e) -> {
//            Random rnd = new Random();
//            LevelData lvl = new LevelData();
//            String[] Level = lvl.Level[Integer.parseInt(lvl.Lvl) - 1];
//            int[][] LevelValue = lvl.LevelValue[Integer.parseInt(lvl.Lvl) - 1];
//            int l = rnd.nextInt(5);
//            int t = rnd.nextInt(100);
//            Grave z = null;
//            for (int i = 0; i < LevelValue.length; i++) {
//                if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
//                    z = Grave.getZombie(Level[i], ZombieGamePanel.this, l);
//                }
//            }
//            laneZombies.get(l).add(z);
//        });
//        zombieProducer.start();

    }

    private void advance() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < lane.lanePeas.get(i).size(); j++) {
                Pea p = lane.lanePeas.get(i).get(j);
                p.advance();
                if (zomHealth == 0) {
                    System.out.println("PLANTS CLEARED ZOMBIES!");
                    System.exit(0);
                }
            }
            for (int j = 0; j < lane.laneZoms.get(i).size(); j++) {
                Zom z = lane.laneZoms.get(i).get(j);
                z.advance();
                if (planHealth == 0) {
                    System.out.println("ZOMBIES ATE BRAIN!");
                    System.exit(0);
                }
            }
        }

        for (int i = 0; i < activeBrains.size(); i++) {
            activeBrains.get(i).advance();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);

//        for (int i = 0; i < 25; i++) {
//            ColliderPlant cP = collidersPlant[i];
//            if (cP.assignedPlant != null) {
//                Image plant_img = null;
//                if (cP.assignedPlant instanceof PeaPlant) {
//                    plant_img = pea_plant_img;
//                } else if (cP.assignedPlant instanceof IcePlant) {
//                    plant_img = ice_plant_img;
//                } else {
//                    plant_img = sun_plant_img;
//                }
//                g.drawImage(plant_img, 60 + (i % 5) * 100, 129 + (i / 5) * 120, null);
//            }
//        }

        for (int i = 0; i < 20; i++) {
            ColliderZombie cZ = collidersZombie[i];
            if (cZ.assignedGrave != null) {
                Image zombie_img = null;
                if (cZ.assignedGrave instanceof ZomGrave) {
                    zombie_img = zom_grave_img;
                } else if (cZ.assignedGrave instanceof ConeHeadZomGrave) {
                    zombie_img = coneheadzom_grave_img;
                } else {
                    zombie_img = brain_grave_img;
                }
                g.drawImage(zombie_img, 560 + (i % 4) * 100, 129 + (i / 4) * 120, null);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < lane.lanePeas.get(i).size(); j++) {
                Pea p = lane.lanePeas.get(i).get(j);
                if (p instanceof Ice) {
                    g.drawImage(ice_img, p.posX, 130 + (i * 120), null);
                } else {
                    g.drawImage(pea_img, p.posX, 130 + (i * 120), null);
                }
            }
            for (int j = 0; j < lane.laneZoms.get(i).size(); j++) {
                Zom z = lane.laneZoms.get(i).get(j);
                if (z instanceof ConeHeadZom) {
                    g.drawImage(coneheadzom_img, z.posX, 130 + (i * 120), null);
                } else {
                    g.drawImage(zom_img, z.posX, 130 + (i * 120), null);
                }
            }
            //            for (Grave z : laneCharacters.get(i)) {
//                if (z instanceof Zom) {
//                    g.drawImage(normalZombieImage, z.posX, 109 + (i * 120), null);
//                } else if (z instanceof ConeHeadZom) {
//                    g.drawImage(coneHeadZombieImage, z.posX, 109 + (i * 120), null);
//                } else {
//                    g.drawImage(graveyardImage, z.posX, 109 + (i * 120), null);
//                }
//            }
        }

        //if(!"".equals(activePlantingBrush)){
        //System.out.println(activePlantingBrush);
            /*if(activePlantingBrush == view.GameWindow.PlantType.model.model.Plant.Plant.Sunflower) {
                g.drawImage(sunflowerImage,mouseX,mouseY,null);
            }*/

        //}

    }

    class ZombieActionListener implements ActionListener {

        int x, y;

        public ZombieActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activeZombieBrush == ZombieWindow.ZombieType.BrainGrave) {
                if (getBrainScore() >= 50) {
                    collidersZombie[x + y * 4].setZombie(new BrainGrave(ZombieGamePanel.this, lane, x, y));
                    setBrainScore(getBrainScore() - 50);
                }
            }
            if (activeZombieBrush == ZombieWindow.ZombieType.ZomGrave) {
                if (getBrainScore() >= 50) {
                    collidersZombie[x + y * 4].setZombie(new ZomGrave(ZombieGamePanel.this, lane, x, y));
                    setBrainScore(getBrainScore() - 50);
                }
            }

            if (activeZombieBrush == ZombieWindow.ZombieType.ConeHeadZomGrave) {
                if (getBrainScore() >= 75) {
                    collidersZombie[x + y * 4].setZombie(new ConeHeadZomGrave(ZombieGamePanel.this, lane, x, y));
                    setBrainScore(getBrainScore() - 75);
                }
            }

            activeZombieBrush = ZombieWindow.ZombieType.None;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    static int progress = 0;

//    public static void setProgress(int num) {
//        progress = progress + num;
//        System.out.println(progress);
//        if (progress >= 150) {
//            if ("1".equals(LevelData.Lvl)) {
//                JOptionPane.showMessageDialog(null, "Level Completed !!!" + '\n' + "Starting next Level");
//                ZombieWindow.gw.dispose();
//                LevelData.write("2");
//                ZombieWindow.gw = new ZombieWindow();
//            } else {
//                JOptionPane.showMessageDialog(null, "Level Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
//                LevelData.write("1");
//                System.exit(0);
//            }
//            progress = 0;
//        }
//    }
}
