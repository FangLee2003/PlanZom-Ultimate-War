package controller;

import model.Data;
import model.Plant.*;
import model.Zombie.*;
import view.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class ZombieGamePanel extends JLayeredPane implements MouseMotionListener {
    PlantGamePanel pP;

    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
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

    public ColliderZombie[] collidersZombie;

    public Data data;
    Health healthPanel;

    Thread plantTimer;
    Timer redrawTimer;
    Timer advancerTimer;

    public ArrayList<Brain> activeBrains;
    Timer brainProducer;
    private int brainScore;
    JLabel brainScoreboard;

    public ZombieWindow.ZombieType activeZombieBrush = ZombieWindow.ZombieType.None;

    int mouseX, mouseY;

    String plantData;

    public int getBrainScore() {
        return brainScore;
    }

    public void setBrainScore(int brainScore) {
        this.brainScore = brainScore;
        brainScoreboard.setText(String.valueOf(brainScore));
    }

    public ZombieGamePanel(JLabel brainScoreboard) throws IOException {
        System.out.println("WAR START!");

        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.brainScoreboard = brainScoreboard;
        setBrainScore(150);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/background2.png")).getImage();

        data = new Data();
        healthPanel = new Health(data, 15, 0);
        add(healthPanel);

        collidersZombie = new ColliderZombie[20];
        for (int i = 0; i < 20; i++) {
            ColliderZombie cZ = new ColliderZombie();
            cZ.setLocation(544 + (i % 4) * 100, 109 + (i / 4) * 120); // First cell (0, 0) in place (544, 109), second cell (0, 1) in place (644, 109),...
            cZ.setAction(new ZombieActionListener((i / 4), (i % 4)));
            collidersZombie[i] = cZ;
            add(cZ, new Integer(0));
        }

        activeBrains = new ArrayList<>();

        socket = new Socket("127.0.0.1", 3304); // On the device running the server, open cmd and enter ipconfig to get ipv4
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        plantTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        plantData = in.readUTF();
                        if (!plantData.equals(" ")) {
                            new PlantActionListener(Integer.parseInt(String.valueOf(plantData.charAt(0))), Integer.parseInt(String.valueOf(plantData.charAt(1))), Integer.parseInt(String.valueOf(plantData.charAt(2))));
                        }
                        plantData = " ";
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        plantTimer.start();
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
            for (int j = 0; j < data.lanePeas.get(i).size(); j++) {
                Pea p = data.lanePeas.get(i).get(j);

                p.advance();
                if (data.zomHealth <= 0) {
                    System.out.println("PLANTS CLEARED ZOMBIES!");
                    System.exit(0);
                }
            }
            for (int j = 0; j < data.laneZoms.get(i).size(); j++) {
                Zom z = data.laneZoms.get(i).get(j);
                z.advance();
                if (data.plantHealth <= 0) {
                    System.out.println("ZOMBIES CLEARED PLANTS!");
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

        for (int i = 0; i < data.lanePlants.length; i++) {
            for (int j = 0; j < data.lanePlants[i].length; j++) {
                Plant cP = data.lanePlants[i][j];
                if (cP != null) {
                    Image plant_img = null;
                    if (cP instanceof PeaPlant) {
                        plant_img = pea_plant_img;
                    } else if (cP instanceof IcePlant) {
                        plant_img = ice_plant_img;
                    } else {
                        plant_img = sun_plant_img;
                    }
                    g.drawImage(plant_img, 60 + j * 100, 129 + i * 120, null);
                }
            }
        }

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
            for (int j = 0; j < data.lanePeas.get(i).size(); j++) {
                Pea p = data.lanePeas.get(i).get(j);
                if (p instanceof Ice) {
                    g.drawImage(ice_img, p.posX, 130 + (i * 120), null);
                } else {
                    g.drawImage(pea_img, p.posX, 130 + (i * 120), null);
                }
            }
            for (int j = 0; j < data.laneZoms.get(i).size(); j++) {
                Zom z = data.laneZoms.get(i).get(j);

                if (z instanceof ConeHeadZom) {
                    g.drawImage(coneheadzom_img, z.posX, 130 + (i * 120), null);
                } else {
                    g.drawImage(zom_img, z.posX, 130 + (i * 120), null);
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

    class ZombieActionListener implements ActionListener {

        int m, n;

        public ZombieActionListener(int m, int n) {
            this.m = m;
            this.n = n;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activeZombieBrush == ZombieWindow.ZombieType.BrainGrave) {
                if (getBrainScore() >= 50 && collidersZombie[n + m * 4].assignedGrave == null) {
                    BrainGrave z = new BrainGrave(pP, ZombieGamePanel.this, data, m, n);
                    collidersZombie[n + m * 4].setZombie(z);
                    data.laneGraves[m][n] = z;
                    try {
                        out.writeUTF(String.format("%d%d%d", 0, m, n));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setBrainScore(getBrainScore() - 50);
                }
            }
            if (activeZombieBrush == ZombieWindow.ZombieType.ZomGrave) {
                if (getBrainScore() >= 50 && collidersZombie[n + m * 4].assignedGrave == null) {
                    ZomGrave z = new ZomGrave(pP, ZombieGamePanel.this, data, m, n);
                    collidersZombie[n + m * 4].setZombie(z);
                    data.laneGraves[m][n] = z;
                    try {
                        out.writeUTF(String.format("%d%d%d", 1, m, n));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setBrainScore(getBrainScore() - 50);
                }
            }

            if (activeZombieBrush == ZombieWindow.ZombieType.ConeHeadZomGrave) {
                if (getBrainScore() >= 75 && collidersZombie[n + m * 4].assignedGrave == null) {
                    ConeHeadZomGrave z = new ConeHeadZomGrave(pP, ZombieGamePanel.this, data, m, n);
                    collidersZombie[n + m * 4].setZombie(z);
                    data.laneGraves[m][n] = z;
                    try {
                        out.writeUTF(String.format("%d%d%d", 2, m, n));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setBrainScore(getBrainScore() - 75);
                }
            }
            activeZombieBrush = ZombieWindow.ZombieType.None;
        }
    }

    class PlantActionListener {

        int type, m, n;

        public PlantActionListener(int type, int m, int n) {
            this.type = type;
            this.m = m;
            this.n = n;

            if (type == 0) {
                data.lanePlants[m][n] = new SunPlant(pP, ZombieGamePanel.this, data, m, n);
            }
            if (type == 1) {
                data.lanePlants[m][n] = new PeaPlant(pP, ZombieGamePanel.this, data, m, n);
            }
            if (type == 2) {
                data.lanePlants[m][n] = new IcePlant(pP, ZombieGamePanel.this, data, m, n);
            }
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

//    static int progress = 0;

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
