package ru.peltikhin;

import ru.peltikhin.data.DataLoader;
import ru.peltikhin.models.Directions;
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

    public GameLogic(DataLoader dataLoader,int m) throws IOException {
        this.gameField = dataLoader.getField();
        this.dataLoader = dataLoader;
        gameTime = m;
    }

    public void startSimulation() throws InterruptedException, IOException {
        System.out.println(gameField.getFieldView());
        System.out.println("Game started!");
        List<ResultSimulation> result = startSimulationBranch(this.gameField,null,gameTime);
        System.out.println("Game ended!");
        StringBuilder r = new StringBuilder();
        for(var resultSimulation: result){
            r.append(resultSimulation.getId())
                    .append(":")
                    .append(resultSimulation.getGameTime())
                    .append("-")
                    .append(resultSimulation.isWined())
                    .append(" result view: \n")
                    .append(resultSimulation.getEndSimulationView());
        }
        dataLoader.saveResult(r);
    }

    private List<ResultSimulation> startSimulationBranch(Field field,Directions nextStepDirection, int gameTime){
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

            List<Directions> moveOptions = getProbableMoveVariations(field);
            if(moveOptions.isEmpty()) {
                gameWorking = false;
            } else if (moveOptions.size()==1){
                field.BallMove(moveOptions.get(0));
                moveOptions.clear();
            } else if (moveOptions.size()>1){
                for(var option : moveOptions){
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

    public List<Directions> getProbableMoveVariations(Field gameField){
        List<Directions> moveVariations = new ArrayList<Directions>(Arrays.asList(Directions.values()));
        moveVariations.removeIf(option -> !gameField.canBallMove(option));
        return gameField.getProbableOption(moveVariations);
    }



}
