package model;

import model.Plant.*;
import model.Zombie.*;

import java.util.ArrayList;

public class Lane {

    public ArrayList<ArrayList<Plant>> lanePlants = new ArrayList<>();
    public ArrayList<ArrayList<Grave>> laneGraves = new ArrayList<>();

    public ArrayList<ArrayList<Pea>> lanePeas = new ArrayList<>();
    public ArrayList<ArrayList<Zom>> laneZoms = new ArrayList<>();

    public Lane() {
        for (int i = 0; i < 5; i++) {
            lanePlants.add(new ArrayList<>());
            laneGraves.add(new ArrayList<>());

            lanePeas.add(new ArrayList<>());
            laneZoms.add(new ArrayList<>());
        }
    }
}
