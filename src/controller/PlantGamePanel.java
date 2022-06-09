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
public class PlantGamePanel extends JLayeredPane implements MouseMotionListener {

    Image bgImage;
    Image sunflowerImage;
    Image peashooterImage;
    Image peaImage;
    Image freezePeashooterImage;
    Image freezePeaImage;
    Image graveyardImage;
    Image normalZombieImage;
    Image coneHeadZombieImage;
    public ColliderPlant[] collidersPlant;

    public ArrayList<ArrayList<Zombie>> laneZombies;
    public ArrayList<ArrayList<Pea>> lanePeas;
    public ArrayList<Sun> activeSuns;
    Timer redrawTimer;
    Timer advancerTimer;
    Timer sunProducer;
    JLabel manaScoreboard;

    public PlantWindow.PlantType activePlantingBrush = PlantWindow.PlantType.None;

    int mouseX, mouseY;

    private int sunScore;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        manaScoreboard.setText(String.valueOf(sunScore));
    }

    public PlantGamePanel(JLabel manaScoreboard) {
        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.manaScoreboard = manaScoreboard;
        setSunScore(150);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/background1.png")).getImage();

        sunflowerImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/sunflower.gif")).getImage();
        peashooterImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/freezepeashooter.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/plants/freezepea.png")).getImage();

        graveyardImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/graveyard.png")).getImage();
        normalZombieImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/zombie1.png")).getImage();
        coneHeadZombieImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/zombies/zombie2.png")).getImage();

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

        collidersPlant = new ColliderPlant[25];
        for (int i = 0; i < 25; i++) {
            ColliderPlant cP = new ColliderPlant();
            cP.setLocation(44 + (i % 5) * 100, 109 + (i / 5) * 120); // First cell (0, 0) in place (44, 109), second cell (0, 1) in place (144, 109),...
            cP.setAction(new PlantActionListener((i % 5), (i / 5)));
            collidersPlant[i] = cP;
            add(cP, new Integer(0));
        }

        //colliders[0].setPlant(new model.model.Plant.Plant.FreezePeashooter(this,0,0));
/*
        colliders[9].setPlant(new model.model.Plant.Plant.Peashooter(this,0,1));
        laneZombies.get(1).add(new model.Zombie.NormalZombie(this,1));*/

        activeSuns = new ArrayList<>();

        redrawTimer = new Timer(25, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60, (ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeSuns.add(sta);
            add(sta, new Integer(1));
        });
        sunProducer.start();

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
//                    z = Zombie.getZombie(Level[i], PlantGamePanel.this, l);
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

        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);

        //Draw Plants
        for (int i = 0; i < 25; i++) {
            ColliderPlant cP = collidersPlant[i];
            if (cP.assignedPlant != null) {
                Plant p = cP.assignedPlant;
                if (p instanceof Peashooter) {
                    g.drawImage(peashooterImage, 60 + (i % 5) * 100, 129 + (i / 5) * 120, null);
                }
                if (p instanceof FreezePeashooter) {
                    g.drawImage(freezePeashooterImage, 60 + (i % 5) * 100, 129 + (i / 9) * 120, null);
                }
                if (p instanceof Sunflower) {
                    g.drawImage(sunflowerImage, 60 + (i % 5) * 100, 129 + (i / 9) * 120, null);
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

            for (Zombie z : laneZombies.get(i)) {
                if (z instanceof NormalZombie) {
                    g.drawImage(normalZombieImage, z.posX, 109 + (i * 120), null);
                } else if (z instanceof ConeHeadZombie) {
                    g.drawImage(coneHeadZombieImage, z.posX, 109 + (i * 120), null);
                } else {
                    g.drawImage(graveyardImage, z.posX, 109 + (i * 120), null);
                }
            }
        }

        //if(!"".equals(activePlantingBrush)){
        //System.out.println(activePlantingBrush);
            /*if(activePlantingBrush == view.GameWindow.PlantType.model.model.Plant.Plant.Sunflower) {
                g.drawImage(sunflowerImage,mouseX,mouseY,null);
            }*/

        //}


    }

    class PlantActionListener implements ActionListener {

        int x, y;

        public PlantActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activePlantingBrush == PlantWindow.PlantType.Sunflower) {
                if (getSunScore() >= 50) {
                    collidersPlant[x + y * 5].setPlant(new Sunflower(PlantGamePanel.this, x, y));
                    setSunScore(getSunScore() - 50);
                }
            }
            if (activePlantingBrush == PlantWindow.PlantType.Peashooter) {
                if (getSunScore() >= 100) {
                    collidersPlant[x + y * 5].setPlant(new Peashooter(PlantGamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                }
            }

            if (activePlantingBrush == PlantWindow.PlantType.FreezePeashooter) {
                if (getSunScore() >= 175) {
                    collidersPlant[x + y * 5].setPlant(new FreezePeashooter(PlantGamePanel.this, x, y));
                    setSunScore(getSunScore() - 175);
                }
            }

            activePlantingBrush = PlantWindow.PlantType.None;
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
//                PlantWindow.gw.dispose();
//                LevelData.write("2");
//                PlantWindow.gw = new PlantWindow();
//            } else {
//                JOptionPane.showMessageDialog(null, "Level Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
//                LevelData.write("1");
//                System.exit(0);
//            }
//            progress = 0;
//        }
//    }
}
