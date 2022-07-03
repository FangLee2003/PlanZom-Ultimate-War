package model;

import model.Plant.*;
import model.Zombie.*;

import java.util.ArrayList;

public class Data {
    public int zomHealth = 10000;
    public int plantHealth = 10000;

    public Plant[][] lanePlants = new Plant[5][5];
    public Grave[][] laneGraves = new Grave[5][4];

    public ArrayList<ArrayList<Pea>> lanePeas = new ArrayList<>();
    public ArrayList<ArrayList<Zom>> laneZoms = new ArrayList<>();

    public Data() {
        for (int i = 0; i < 5; i++) {
            lanePeas.add(new ArrayList<>());
            laneZoms.add(new ArrayList<>());
        }
    }
}
