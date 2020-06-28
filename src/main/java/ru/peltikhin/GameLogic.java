package ru.peltikhin;

import ru.peltikhin.models.Directions;
import ru.peltikhin.models.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic {
    private Field gameField;
    private int gameTime = 0;
    private boolean gameWorking = true;
    private boolean wined = false;

    public GameLogic(Field gameField) {
        this.gameField = gameField;
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


    public void startGame() {
        System.out.println(gameField.getFieldView());
        while (gameWorking){
            gameField.ReverseDynamicWalls(gameTime);
            System.out.println(gameField.getFieldView());
            ballMove();
            gameField.ReverseDynamicWalls(gameTime);
            gameTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(gameField.getFieldView());
    }
}
