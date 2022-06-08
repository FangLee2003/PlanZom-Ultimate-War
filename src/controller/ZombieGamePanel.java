package controller;

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

    Image sunflowerImage;
    Image peashooterImage;
    Image peaImage;
    Image freezePeashooterImage;
    Image freezePeaImage;

    Image graveyardImage;
    Image normalZombieImage;
    Image coneHeadZombieImage;

    public ColliderZombie[] collidersZombie;

    ArrayList<ArrayList<Zombie>> laneZombies;
    ArrayList<ArrayList<Pea>> lanePeas;
    public ArrayList<Brain> activeBrains;

    Timer redrawTimer;
    Timer advancerTimer;
    Timer brainProducer;
    JLabel brainScoreboard;

    public ZombieWindow.ZombieType activeZombieBrush = ZombieWindow.ZombieType.None;

    int mouseX, mouseY;

    private int brainScore;

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

        bgImage = new ImageIcon(this.getClass().getResource("images/background2.png")).getImage();

        sunflowerImage = new ImageIcon(this.getClass().getResource("images/plants/brainflower.gif")).getImage();
        peashooterImage = new ImageIcon(this.getClass().getResource("images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("images/plants/freezepeashooter.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("images/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("images/freezepea.png")).getImage();

        graveyardImage = new ImageIcon(this.getClass().getResource("images/zombies/graveyard.png")).getImage();
        normalZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie1.png")).getImage();
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie2.png")).getImage();

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

        collidersZombie = new ColliderZombie[20];
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
        laneZombies.get(1).add(new model.Zombie.NormalZombie(this,1));*/

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
//            Zombie z = null;
//            for (int i = 0; i < LevelValue.length; i++) {
//                if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
//                    z = Zombie.getZombie(Level[i], ZombieGamePanel.this, l);
//                }
//            }
//            laneZombies.get(l).add(z);
//        });
//        zombieProducer.start();

    }

    private void advance() {
        for (int i = 0; i < 5; i++) {
            for (Zombie z : laneZombies.get(i)) {
                z.advance();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
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

        //Draw Plants
        for (int i = 0; i < 20; i++) {
            ColliderZombie cZ = collidersZombie[i];
            if (cZ.assignedZombie != null) {
                Zombie z = cZ.assignedZombie;
                if (z instanceof NormalZombie) {
                    g.drawImage(normalZombieImage, 560 + (i % 4) * 100, 729 + (i / 4) * 120, null);
                }
                if (z instanceof ConeHeadZombie) {
                    g.drawImage(coneHeadZombieImage, 560 + (i % 4) * 100, 729 + (i / 4) * 120, null);
                }
                else{
                    g.drawImage(graveyardImage, 560 + (i % 4) * 100, 729 + (i / 4) * 120, null);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                if (p instanceof FreezePea) {
                    g.drawImage(freezePeaImage, p.posX, 130 + (i * 120), null);
                } else {
                    g.drawImage(peaImage, p.posX, 130 + (i * 120), null);
                }
            }

//            for (Zombie z : laneZombies.get(i)) {
//                if (z instanceof NormalZombie) {
//                    g.drawImage(normalZombieImage, z.posX, 109 + (i * 120), null);
//                } else if (z instanceof ConeHeadZombie) {
//                    g.drawImage(coneHeadZombieImage, z.posX, 109 + (i * 120), null);
//                } else {
//                    g.drawImage(graveyardImage, z.posX, 109 + (i * 120), null);
//                }
            }
        }

    //if(!"".equals(activePlantingBrush)){
        //System.out.println(activePlantingBrush);
            /*if(activePlantingBrush == view.ZombieWindow.ZombieType.Brainflower) {
                g.drawImage(sunflowerImage,mouseX,mouseY,null);
            }*/

        //}
    class ZombieActionListener implements ActionListener {

        int x, y;

        public ZombieActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activeZombieBrush == ZombieWindow.ZombieType.Graveyard) {
                if (getBrainScore() >= 50) {
                    collidersZombie[x + y * 4].setZombie(new Graveyard(ZombieGamePanel.this, x, y));
                    setBrainScore(getBrainScore() - 50);
                }
            }
            if (activeZombieBrush == ZombieWindow.ZombieType.NormalZombie) {
                if (getBrainScore() >= 75) {
                    collidersZombie[x + y * 4].setZombie(new NormalZombie(ZombieGamePanel.this, x, y));
                    setBrainScore(getBrainScore() - 75);
                }
            }

            if (activeZombieBrush == ZombieWindow.ZombieType.ConeHeadZombie) {
                if (getBrainScore() >= 100) {
                    collidersZombie[x + y * 4].setZombie(new ConeHeadZombie(ZombieGamePanel.this, x, y));
                    setBrainScore(getBrainScore() - 100);
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
