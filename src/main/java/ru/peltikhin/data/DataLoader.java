package ru.peltikhin.data;

import ru.peltikhin.models.Field;
import ru.peltikhin.models.ResultSimulation;
import ru.peltikhin.models.elements.Element;
import ru.peltikhin.models.elements.ElementFunction;
import ru.peltikhin.models.elements.ElementType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {
    private String mapFileName;
    private String outputFileName;
    private String inputSettingsFileName;

    public DataLoader(String mapFileName, String outputFileName, String inputSettingsFileName) {
        this.mapFileName = mapFileName;
        this.outputFileName = outputFileName;
        this.inputSettingsFileName = inputSettingsFileName;
    }

    public int getGameStartTime() throws IOException {
        return Integer.parseInt(Files.readString(Path.of(inputSettingsFileName), StandardCharsets.UTF_8).trim());

    }

    public Field getField() throws IOException {
        List<Element> elements = Files
                .lines(Paths.get(mapFileName), StandardCharsets.UTF_8)
                .flatMap(string -> Stream.of(string.split(" ")))
                .map(this::parseElement)
                .collect(Collectors.toList());
        return createField(elements);
    }

    public void saveResult(List<ResultSimulation> resultSimulations) throws IOException {
        StringBuilder output = new StringBuilder();
        for(var resultSimulation: resultSimulations){
            output
                    .append("Simulation branch id: ")
                    .append(resultSimulation.getId())
                    .append("\n")
                    .append("Game time: ")
                    .append(resultSimulation.getGameTime())
                    .append("\n")
                    .append(resultSimulation.isWined()?"Success":"Failure")
                    .append("\n")
                    .append("result view: \n")
                    .append(resultSimulation.getEndSimulationView());
        }
        output.append("\n");
        output.append(getResultFirstTask(resultSimulations));
        Files.write(Path.of(outputFileName), output.toString().getBytes());
    }

    private Element parseElement(String input) throws InputMismatchException {
        List<String> parameters = Arrays.asList(input.split(":"));

        if(ElementType.of(parameters.get(0)).isEmpty()){
            throw new InputMismatchException("Error in parse element, error input format");
        }

        ElementType elementType = ElementType.of(parameters.get(0)).get();
        switch (elementType){
            case WALL:
                return createWall();
            case ROOM:
                return createRoom(parameters);
            case CLOSABLE_WALL:
            case OPENABLE_WALL:
                return createDynamicWall(parameters, elementType);
            default:
                throw new InputMismatchException("Error in parse element, wrong element type");
        }
    }

    private Element createWall(){
        Element resultElement = new Element();
        resultElement.setElementType(ElementType.WALL);
        resultElement.setOpen(ElementType.WALL.getStartPosition());
        return resultElement;
    }

    private Element createRoom(List<String> parameters)throws InputMismatchException{
        Element resultElement = new Element();
        resultElement.setElementType(ElementType.ROOM);
        if (parameters.size()!=3) {
            throw new InputMismatchException("Error in parse element, wrong parameters number");
        }
        resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
        resultElement.setElementFunction(ElementFunction.of(parameters.get(2)).get());
        resultElement.setOpen(ElementType.ROOM.getStartPosition());
        return resultElement;
    }

    private Element createDynamicWall(List<String> parameters, ElementType dynamicWallType) throws InputMismatchException{
        Element resultElement = new Element();
        resultElement.setElementType(dynamicWallType);
        if (parameters.size()!=3) {
            throw new InputMismatchException("Error in parse element, wrong parameters number");
        }
        resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
        resultElement.setPeriod(Integer.parseInt(parameters.get(2)));
        resultElement.setOpen(dynamicWallType.getStartPosition());
        return resultElement;
    }

    private Field createField(List<Element> elements) throws InputMismatchException{
        if(elements.size() != 64){
            throw new InputMismatchException("Error in parse element, wrong elements number");
        }
        Element[][] field = new Element[8][8];
        int i=0,j=0;
        for(var element : elements){
            field[i][j] = element;
            i++;
            if(i==8){
                j++;
                i=0;
            }
        }
        return new Field(field);
    }

    private String getResultFirstTask(List<ResultSimulation> resultSimulations){
        resultSimulations.removeIf(result -> !result.isWined());
        if(!resultSimulations.isEmpty()){
            ResultSimulation result = Collections.min(resultSimulations,
                    Comparator.comparingInt(ResultSimulation::getGameTime));
            return "Game end time: " + String.valueOf(result.getGameTime());
        } else {
            return "There is no decision";
        }
    }

    private String getResultSecondTask(List<ResultSimulation> resultSimulations){
        resultSimulations.removeIf(ResultSimulation::isWined);
        if(!resultSimulations.isEmpty()){
            return "Always success";
        } else {
            return "Not always success";
        }
    }


}
