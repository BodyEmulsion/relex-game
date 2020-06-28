package ru.peltikhin.models;

import ru.peltikhin.models.elements.Element;
import ru.peltikhin.models.elements.ElementType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public Field(Field that){
        this.field = new Element[8][8];
        for(int i = 0;i<8;i++){
            for (int j = 0;j<8;j++){
                this.field[i][j]= new Element(that.field[i][j]);
            }
        }
        this.ballCoordinate = new Coordinate(that.ballCoordinate);
    }

    public boolean canBallMove(Directions directions) {
        switch (directions){
            case LEFT:
                return (ballCoordinate.getX()-1>=0) &&
                        (getElement(getCoordinateAroundBall(Directions.LEFT)).isOpen()) &&
                        (getElement(getCoordinateAroundBall(Directions.LEFT)).getAltitude() < getElement(ballCoordinate).getAltitude());
            case RIGHT:
                return (ballCoordinate.getX()+1<=7) &&
                        (getElement(getCoordinateAroundBall(Directions.RIGHT)).isOpen()) &&
                        (getElement(getCoordinateAroundBall(Directions.RIGHT)).getAltitude() < getElement(ballCoordinate).getAltitude());
            case DOWN:
                return (ballCoordinate.getY()+1<=7) &&
                        (getElement(getCoordinateAroundBall(Directions.DOWN)).isOpen()) &&
                        (getElement(getCoordinateAroundBall(Directions.DOWN)).getAltitude() < getElement(ballCoordinate).getAltitude());
            case UP:
                return (ballCoordinate.getY()-1>=0) &&
                        (getElement(getCoordinateAroundBall(Directions.UP)).isOpen()) &&
                        (getElement(getCoordinateAroundBall(Directions.UP)).getAltitude() < getElement(ballCoordinate).getAltitude());
            default:
                throw new UnknownError("It's impossible, but suddenly");
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
            default:
                throw new UnknownError("It's impossible, but suddenly");
        }
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

    public List<Directions> getProbableOption(List<Directions> options) {
        options.removeIf(directions -> {
            return getElement(getCoordinateAroundBall(directions)).getAltitude() >
                    getElement(getCoordinateAroundBall(Collections.min(options, Comparator.comparingInt(element -> {
                        return getElement(getCoordinateAroundBall(element)).getAltitude();
                    })))).getAltitude();
        });
        return options;
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
}
