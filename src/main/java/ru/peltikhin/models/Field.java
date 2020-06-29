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

    public boolean canBallMove(Direction direction) {
        Coordinate coordinateOfPotentialMove = getCoordinateAroundBall(direction);
        return (coordinateOfPotentialMove.getY() >= 0 &&
                coordinateOfPotentialMove.getY() <= 7 &&
                coordinateOfPotentialMove.getX() >= 0 &&
                coordinateOfPotentialMove.getX() <= 7 &&
                getElement(coordinateOfPotentialMove).isOpen() &&
                getElement(coordinateOfPotentialMove).getAltitude() < getElement(ballCoordinate).getAltitude()
        );
    }

    public void BallMove(Direction direction) {
       moveBallByCoordinate(getCoordinateAroundBall(direction));
    }

    public boolean isBallOnFinish() {
        return field[ballCoordinate.getX()][ballCoordinate.getY()].isFinish();
    }

    public void ReverseDynamicWalls(int gameTime) {
        for(var elements: field){
            for(var element: elements){
                if ((
                        element.getElementType() == ElementType.OPENABLE_WALL ||
                        element.getElementType() == ElementType.CLOSABLE_WALL) &&
                        gameTime % element.getPeriod() == 0){
                    element.setOpen(!element.isOpen());
                }
            }
        }
    }

    private Coordinate getCoordinateAroundBall(Direction direction){
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
        ballCoordinate = coordinate;
        field[ballCoordinate.getX()][ballCoordinate.getY()].setIsContainBall(true);
    }

    private Element getElement(Coordinate coordinate){
        return field[coordinate.getX()][coordinate.getY()];
    }

    private Element getElementAroundBall(Direction direction){
        return getElement(getCoordinateAroundBall(direction));
    }

    public List<Direction> getProbableMoveVariations(List<Direction> directions) {
        directions.removeIf(direction -> {
            return getElementAroundBall(direction).getAltitude() >
                    getElementAroundBall(Collections.min(directions, Comparator.comparingInt(item -> {
                        return getElementAroundBall(item).getAltitude();
                    }))).getAltitude();
        });
        //TODO repair
        return directions;
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
