package ru.peltikhin;

import ru.peltikhin.data.DataLoader;
import ru.peltikhin.models.Direction;
import ru.peltikhin.models.Field;
import ru.peltikhin.models.ResultSimulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic {
    private Field gameField;
    private DataLoader dataLoader;
    private int gameTime;

    public GameLogic(DataLoader dataLoader) throws IOException {
        this.dataLoader = dataLoader;
        this.gameField = dataLoader.getField();
        gameTime = dataLoader.getGameStartTime();
    }

    public void startSimulation() throws InterruptedException, IOException {
        System.out.println("Simulation started!");
        List<ResultSimulation> result = startSimulationBranch(this.gameField,null,gameTime);
        System.out.println("Simulation ended!");
        dataLoader.saveResult(result);
    }

    private List<ResultSimulation> startSimulationBranch(Field field, Direction nextStepDirection, int gameTime){
        boolean gameWorking = true;
        boolean wined = false;
        List<ResultSimulation> resultSimulationList = new ArrayList<ResultSimulation>();

        if(nextStepDirection!=null){
            field.BallMove(nextStepDirection);
            wined = field.isBallOnFinish();
            if(wined){
                gameWorking = false;
            }
            field.ReverseDynamicWalls(gameTime);
            gameTime++;
        }

        while (gameWorking){
            field.ReverseDynamicWalls(gameTime);
            List<Direction> moveVariations = getProbableMoveVariations(field);
            switch (moveVariations.size()){
                case 0:
                    gameWorking = false;
                    break;
                case 1:
                    field.BallMove(moveVariations.get(0));
                    moveVariations.clear();
                case 2:
                case 3:
                case 4:
                    for(var option : moveVariations){
                        resultSimulationList.addAll(startSimulationBranch(new Field(field),option,gameTime));
                    }
                    return resultSimulationList;
            }
            wined = field.isBallOnFinish();
            if(wined){
                gameWorking = false;
            }
            field.ReverseDynamicWalls(gameTime);
            gameTime++;
        }
        resultSimulationList.add(new ResultSimulation(gameTime,wined,field.getFieldView()));
        return resultSimulationList;
    }

    public List<Direction> getProbableMoveVariations(Field gameField){
        List<Direction> moveVariations = new ArrayList<Direction>(Arrays.asList(Direction.values()));
        moveVariations.removeIf(option -> !gameField.canBallMove(option));
        return gameField.getProbableMoveVariations(moveVariations);
    }



}
