package ru.peltikhin;
import ru.peltikhin.data.DataLoader;

import java.io.IOException;

public class Game {
    public static void main(String[] args) {
        try {
            new GameLogic(new DataLoader(args[0], args[1]),0).startGame();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
