package ru.peltikhin.models;

import ru.peltikhin.models.elements.Element;
import ru.peltikhin.models.elements.ElementType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Field {
    private Element[][] field;
    private Coordinate ballCoordinate;

    public Field(Element[][] field) {
        this.field = field;
        for(int i = 0;i<8;i++){
            for (int j = 0;j<8;j++){
                if(field[i][j].isStart()){
                    ballCoordinate = new Coordinate(i,j);
                    field[i][j].setIsContainBall(true);
                }
            }
        }
    }

    public StringBuilder getFieldView() {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                result.append(field[j][i].getView());
            }
            result.append("\n");
        }
        return result;
    }

    public boolean canBallMove(Directions directions) {
        switch (directions){
            case LEFT:
                return (ballCoordinate.getX()-1>=0) &&
                        (getElement(getCoordinateAroundBall(Directions.LEFT)).isCanContaineBall()) &&
                        (getElement(getCoordinateAroundBall(Directions.LEFT)).getAltitude() < getElement(ballCoordinate).getAltitude());
            case RIGHT:
                return (ballCoordinate.getX()+1<=7) &&
                        (getElement(getCoordinateAroundBall(Directions.RIGHT)).isCanContaineBall()) &&
                        (getElement(getCoordinateAroundBall(Directions.RIGHT)).getAltitude() < getElement(ballCoordinate).getAltitude());
            case DOWN:
                return (ballCoordinate.getY()+1<=7) &&
                        (getElement(getCoordinateAroundBall(Directions.DOWN)).isCanContaineBall()) &&
                        (getElement(getCoordinateAroundBall(Directions.DOWN)).getAltitude() < getElement(ballCoordinate).getAltitude());
            case UP:
                return (ballCoordinate.getY()-1>=0) &&
                        (getElement(getCoordinateAroundBall(Directions.UP)).isCanContaineBall()) &&
                        (getElement(getCoordinateAroundBall(Directions.UP)).getAltitude() < getElement(ballCoordinate).getAltitude());
            default:
                System.out.println("ПРоблемы в енаме с направлениями или в field");
                //TODO исправить
                return false;
        }
    }

    public void BallMove(Directions directions) {
       moveBallByCoordinate(getCoordinateAroundBall(directions));
    }

    public boolean isBallOnFinish() {
        return field[ballCoordinate.getX()][ballCoordinate.getY()].isFinish();
    }

    public void ReverseDynamicWalls(int gameTime) {
        for(var elements: field){
            for(var element: elements){
                if ((element.getElementType() == ElementType.OPENABLE_WALL ||
                        element.getElementType() == ElementType.CLOSABLE_WALL) &&
                        (gameTime+element.getFirstMoveTime())%element.getPeriod()==0){
                    element.setOpen(!element.isOpen());
                }
            }
        }
    }

    private Coordinate getCoordinateAroundBall(Directions direction){
        switch (direction){
            case LEFT:
                return new Coordinate(ballCoordinate.getX()-1,ballCoordinate.getY());
            case RIGHT:
                return new Coordinate(ballCoordinate.getX()+1,ballCoordinate.getY());
            case DOWN:
                return new Coordinate(ballCoordinate.getX(),ballCoordinate.getY()+1);
            case UP:
                return new Coordinate(ballCoordinate.getX(),ballCoordinate.getY()-1);
        }
        return null;
    }

    private void moveBallByCoordinate(Coordinate coordinate){
        field[ballCoordinate.getX()][ballCoordinate.getY()].setIsContainBall(false);
        ballCoordinate.setX(coordinate.getX());
        ballCoordinate.setY(coordinate.getY());
        field[ballCoordinate.getX()][ballCoordinate.getY()].setIsContainBall(true);
    }

    private Element getElement(Coordinate coordinate){
        return field[coordinate.getX()][coordinate.getY()];
    }

    public void selectBestOption(List<Directions> options) {
        List<Coordinate> coordinates = options.stream()
                .map(this::getCoordinateAroundBall)
                .collect(Collectors.toList());

        coordinates.removeIf(coordinate -> {
            return getElement(coordinate).getAltitude() >
                    getElement(Collections.min(coordinates, Comparator.comparingInt(element -> {
                        return getElement(element).getAltitude();
                    }))).getAltitude();
        });

        moveBallByCoordinate(coordinates.get(new Random().nextInt(coordinates.size())));
    }
}
