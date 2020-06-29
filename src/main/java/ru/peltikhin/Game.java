package ru.peltikhin;

import ru.peltikhin.data.DataLoader;

public class Game {
    public static void main(String[] args) {
        try {
            new GameLogic(new DataLoader(args[0], args[1], args[2])).startSimulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
