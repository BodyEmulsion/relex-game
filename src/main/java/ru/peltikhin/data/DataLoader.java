package ru.peltikhin.data;

import ru.peltikhin.models.Field;

public class DataLoader {
    private String inputName;
    private String outputName;

    public DataLoader(String inputName, String outputName) {
        this.inputName = inputName;
        this.outputName = outputName;
    }

    public Field getField(){
        return new Field();
    }

    public void saveResult(){

    }

}
