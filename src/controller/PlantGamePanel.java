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
import java.net.*;
import java.util.*;

public class PlantGamePanel extends JLayeredPane implements MouseMotionListener {
    ZombieGamePanel zP;

    static ServerSocket serverSocket;
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

    public ColliderPlant[] collidersPlant;

    public Data data;
    Health healthPanel;

    Thread graveTimer;
    Timer redrawTimer;
    Timer advancerTimer;

    public ArrayList<Sun> activeSuns;
    Timer sunProducer;
    private int sunScore;
    JLabel sunScoreBoard;

    public PlantWindow.PlantType activePlantingBrush = PlantWindow.PlantType.None;
    int mouseX, mouseY;

    String zomData;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreBoard.setText(String.valueOf(sunScore));
    }

    public PlantGamePanel(JLabel sunScoreBoard) throws IOException {
        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreBoard = sunScoreBoard;
        setSunScore(150);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getClassLoader().getResource("images/background1.png")).getImage();

        data = new Data();
        healthPanel = new Health(data, 865, 0);
        add(healthPanel);

        collidersPlant = new ColliderPlant[25];
        for (int i = 0; i < 25; i++) {
            ColliderPlant cP = new ColliderPlant();
            cP.setLocation(44 + (i % 5) * 100, 109 + (i / 5) * 120); // First cell (0, 0) in place (44, 109), second cell (0, 1) in place (144, 109),...
            cP.setAction(new PlantActionListener((i / 5), (i % 5)));
            collidersPlant[i] = cP;
            add(cP, new Integer(0));
        }

        activeSuns = new ArrayList<>();

        System.out.println("Waiting for enemy...");

        serverSocket = new ServerSocket(3304);
        socket = serverSocket.accept();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        System.out.println("WAR START!");

        graveTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        zomData = in.readUTF();
                        if (!zomData.equals(" ")) {
                            new ZombieActionListener(Integer.parseInt(String.valueOf(zomData.charAt(0))), Integer.parseInt(String.valueOf(zomData.charAt(1))), Integer.parseInt(String.valueOf(zomData.charAt(2))));
                            zomData = " ";
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        graveTimer.start();

        redrawTimer = new Timer(25, (ActionEvent e) -> {
            repaint();
//            for (int i = 0; i < data.laneGraves.length; i++) {
//                for (int j = 0; j < data.laneGraves[i].length; j++) {
//                    if (data.laneGraves[i][j] != null) {
//                        System.out.print(data.laneGraves[i][j].getClass());
//                    }
//                    System.out.print(" null ");
//                }
//                System.out.println("\n");
//            }
        });
        redrawTimer.start();

        advancerTimer = new Timer(60, (ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Random rnd = new Random();
            Sun sun = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeSuns.add(sun);
            add(sun, new Integer(1));
        });
        sunProducer.start();

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
//                    z = Grave.getZombie(Level[i], PlantGamePanel.this, l);
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

        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);

        for (int i = 0; i < 25; i++) {
            ColliderPlant cP = collidersPlant[i];
            if (cP.assignedPlant != null) {
                Image plant_img = null;
                if (cP.assignedPlant instanceof PeaPlant) {
                    plant_img = pea_plant_img;
                } else if (cP.assignedPlant instanceof IcePlant) {
                    plant_img = ice_plant_img;
                } else {
                    plant_img = sun_plant_img;
                }
                g.drawImage(plant_img, 60 + (i % 5) * 100, 129 + (i / 5) * 120, null);
            }
        }
        for (int i = 0; i < data.laneGraves.length; i++) {
            for (int j = 0; j < data.laneGraves[i].length; j++) {
                Grave cZ = data.laneGraves[i][j];
                if (cZ != null) {
                    Image zombie_img = null;
                    if (cZ instanceof ZomGrave) {
                        zombie_img = zom_grave_img;
                    } else if (cZ instanceof ConeHeadZomGrave) {
                        zombie_img = coneheadzom_grave_img;
                    } else {
                        zombie_img = brain_grave_img;
                    }
                    g.drawImage(zombie_img, 560 + j * 100, 129 + i * 120, null);
                }
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

    class PlantActionListener implements ActionListener {

        int m, n;

        public PlantActionListener(int m, int n) {
            this.m = m;
            this.n = n;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (activePlantingBrush == PlantWindow.PlantType.SunPlant) {
                if (getSunScore() >= 50) {
                    SunPlant p = new SunPlant(PlantGamePanel.this, zP, data, m, n);
                    collidersPlant[n + m * 5].setCharacter(p);
                    data.lanePlants[m][n] = p;
                    try {
                        out.writeUTF(String.format("%d%d%d", 0, m, n));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setSunScore(getSunScore() - 50);
                }
            }
            if (activePlantingBrush == PlantWindow.PlantType.PeaPlant) {
                if (getSunScore() >= 100) {
                    PeaPlant p = new PeaPlant(PlantGamePanel.this, zP, data, m, n);
                    collidersPlant[n + m * 5].setCharacter(p);
                    data.lanePlants[m][n] = p;
                    try {
                        out.writeUTF(String.format("%d%d%d", 1, m, n));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setSunScore(getSunScore() - 100);
                }
            }

            if (activePlantingBrush == PlantWindow.PlantType.IcePlant) {
                if (getSunScore() >= 175) {
                    IcePlant p = new IcePlant(PlantGamePanel.this, zP, data, m, n);
                    collidersPlant[n + m * 5].setCharacter(p);
                    data.lanePlants[m][n] = p;
                    try {
                        out.writeUTF(String.format("%d%d%d", 2, m, n));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setSunScore(getSunScore() - 175);
                }
            }
            activePlantingBrush = PlantWindow.PlantType.None;
        }
    }

    class ZombieActionListener {

        int type, m, n;

        public ZombieActionListener(int type, int m, int n) {
            this.type = type;
            this.m = m;
            this.n = n;

            if (type == 0) {
                data.laneGraves[m][n] = new BrainGrave(PlantGamePanel.this, zP, data, m, n);
            }
            if (type == 1) {
                data.laneGraves[m][n] = new ZomGrave(PlantGamePanel.this, zP, data, m, n);
            }
            if (type == 2) {
                data.laneGraves[m][n] = new ConeHeadZomGrave(PlantGamePanel.this, zP, data, m, n);
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
