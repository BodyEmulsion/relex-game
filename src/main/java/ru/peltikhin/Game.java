package ru.peltikhin;
import ru.peltikhin.data.DataLoader;

public class Game {
    public static void main(String[] args) {
        System.out.println("Ah shit, here we go again...");
        new GameLogic(new DataLoader(args[0], args[1]).getField()).startGame();
    }
}
