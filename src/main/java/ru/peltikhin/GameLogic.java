package ru.peltikhin;

import ru.peltikhin.data.DataLoader;
import ru.peltikhin.models.Directions;
import ru.peltikhin.models.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic {
    private Field gameField;
    private DataLoader dataLoader;
    private int gameTime;
    private boolean gameWorking;
    private boolean wined;

    public GameLogic(DataLoader dataLoader,int m) throws IOException {
        this.gameField = dataLoader.getField();
        this.dataLoader = dataLoader;
        gameTime = m;
        gameWorking = true;
        wined = false;
    }

    public void startGame() throws InterruptedException, IOException {
        System.out.println(gameField.getFieldView());
        System.out.println("Game started!");
        while (gameWorking){
            gameField.ReverseDynamicWalls(gameTime);
            System.out.println(gameField.getFieldView());
            ballMove();
            gameField.ReverseDynamicWalls(gameTime);
            gameTime++;
            Thread.sleep(1000);
        }
        System.out.println("Game ended!");
        System.out.println(gameField.getFieldView());
        System.out.println(wined);
        dataLoader.saveResult(String.valueOf(wined));
    }

    public void ballMove(){
        List<Directions> moveOptions = new ArrayList<Directions>(Arrays.asList(Directions.values()));
        moveOptions.removeIf(option -> !gameField.canBallMove(option));
        if(moveOptions.isEmpty()) {
            gameWorking = false;
        } else if (moveOptions.size()==1){
            gameField.BallMove(moveOptions.get(0));
        } else if(moveOptions.size()>1){
            gameField.selectBestOption(moveOptions);
            //TODO придумать нормальное решение
        }
        wined = gameField.isBallOnFinish();
    }

    public void dynamicWallFirstStageMove(){
        gameField.ReverseDynamicWalls(gameTime);
    }



}
