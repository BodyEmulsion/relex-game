package ru.peltikhin.models;

public class ResultSimulation {
    private static int counter = 0;
    private final int id = counter;
    private int gameTime;
    private boolean isWined;
    private StringBuilder endSimulationView;

    public ResultSimulation(int gameTime, boolean isWined, StringBuilder endSimulationView){
        this.gameTime = gameTime;
        this.isWined = isWined;
        this.endSimulationView = endSimulationView;
        counter++;
    }

    public int getId() {
        return id;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public boolean isWined() {
        return isWined;
    }

    public void setWined(boolean wined) {
        isWined = wined;
    }

    public StringBuilder getEndSimulationView() {
        return endSimulationView;
    }

    public void setEndSimulationView(StringBuilder endSimulationView) {
        this.endSimulationView = endSimulationView;
    }
}
