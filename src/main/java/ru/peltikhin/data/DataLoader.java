package ru.peltikhin.data;

import ru.peltikhin.models.Field;
import ru.peltikhin.models.elements.Element;
import ru.peltikhin.models.elements.ElementType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

    public Field getField(){
        try {
            List<Element> elementList = Files.lines(Paths.get(inputName), StandardCharsets.UTF_8)
                    .flatMap(string -> Stream.of(string.split(" ")))
                    .map(value -> {
                        List<String> parameters = Arrays.asList(value.split(":"));
                        Element resultElement = new Element();
                        if(ElementType.of(parameters.get(0)).isEmpty()){
                            System.err.println("ERROR IN ENUM");
                            return null;
                        }
                        //TODO переделать
                        switch (ElementType.of(parameters.get(0)).get()){
                            case WALL:
                                resultElement.setElementType(ElementType.WALL);
                                break;
                            case ROOM:
                                resultElement.setElementType(ElementType.ROOM);
                                resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
                                resultElement.setStart(Boolean.getBoolean(parameters.get(2)));
                                resultElement.setEnd(Boolean.getBoolean(parameters.get(3)));
                                break;
                            case CLOSABLE_WALL:
                                resultElement.setElementType(ElementType.CLOSABLE_WALL);
                                resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
                                resultElement.setFirstMoveTime(Integer.parseInt(parameters.get(2)));
                                resultElement.setPeriod(Integer.parseInt(parameters.get(3)));
                                resultElement.setOpen(true);
                                break;
                            case OPENABLE_WALL:
                                resultElement.setElementType(ElementType.OPENABLE_WALL);
                                resultElement.setAltitude(Integer.parseInt(parameters.get(1)));
                                resultElement.setFirstMoveTime(Integer.parseInt(parameters.get(2)));
                                resultElement.setPeriod(Integer.parseInt(parameters.get(3)));
                                resultElement.setOpen(false);
                                break;
                        }
                        return resultElement;
                    }).collect(Collectors.toList());
            if(elementList.size() != 64){
                System.err.println("SIZE OF FIELD IN SAVE WRONG");
            }
            Element[][] field = new Element[8][8];
            int i=0,j=0;
            for(var element : elementList){
                field[i][j] = element;
                i++;
                if(i==8){
                    j++;
                    i=0;
                }
            }
            return new Field(field);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        //TODO Починить тут все, и сделать красиво

    }

    public void saveResult(){

    }

}
