package ru.peltikhin.data;

import ru.peltikhin.models.Field;
import ru.peltikhin.models.elements.Element;
import ru.peltikhin.models.elements.ElementType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {
    private String inputName;
    private String outputName;

    public DataLoader(String inputName, String outputName) {
        this.inputName = inputName;
        this.outputName = outputName;
    }

    public Field getField() throws IOException {
        List<Element> elements = Files
                .lines(Paths.get(inputName), StandardCharsets.UTF_8)
                .flatMap(string -> Stream.of(string.split(" ")))
                .map(this::parseElement)
                .collect(Collectors.toList());
        return createField(elements);
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
        if (parameters.size()!=4) {
            throw new InputMismatchException("Error in parse element, wrong parameters number");
        }
        resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
        resultElement.setStart(Boolean.parseBoolean(parameters.get(2)));
        resultElement.setEnd(Boolean.parseBoolean(parameters.get(3)));
        resultElement.setOpen(ElementType.ROOM.getStartPosition());
        return resultElement;
    }

    private Element createDynamicWall(List<String> parameters, ElementType dynamicWallType) throws InputMismatchException{
        Element resultElement = new Element();
        resultElement.setElementType(dynamicWallType);
        if (parameters.size()!=4) {
            throw new InputMismatchException("Error in parse element, wrong parameters number");
        }
        resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
        resultElement.setFirstMoveTime(Integer.parseInt(parameters.get(2)));
        resultElement.setPeriod(Integer.parseInt(parameters.get(3)));
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

    public void saveResult(StringBuilder result) throws IOException {
        Files.write(Path.of(outputName), result.toString().getBytes());
    }

}
