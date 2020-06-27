package ru.peltikhin;

import ru.peltikhin.data.DataLoader;
import ru.peltikhin.models.Field;

public class Game {
    public static void main(String[] args) {
        System.out.println("Ah shit, here we go again...");
        Field gameField = new DataLoader(args[0], args[1]).getField();
        gameField.showField();

    }
}
